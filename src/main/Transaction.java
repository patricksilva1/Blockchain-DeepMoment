package com.example;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    // Contains a hash of transaction;
    public String transactionId;
    // Senders adress, public key;
    public PublicKey sender;
    // Recipients address, public key;
    public PublicKey reciepient;
    // Contains the amount we wish to send to the recipient;
    public float value;
    // This is to prevent anybody else from spending funds in our wallet.
    public byte[] signature;

    public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    // The count of how many transactions have been generated
    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, float value, List<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    public boolean processTransaction() {

        if (verifySignature() == false) {
            System.out.println("#Transaction Signature Failed to Verify");
            return false;
        }

        // To Do: add gathers for transaction inputs;

        for (TransactionInput i : inputs) {
            i.UTXO = DeepMoment.UTXOs.get(i.transactionOutputId);
        }

        // And here we need to check if transactions are valid;

        if (getInputsValue() < DeepMoment.minimumTransaction) {
            System.out.println("Transaction Inputs too small: " + getInputsValue());
            System.out.println("Please enter the amount greater than " + DeepMoment.minimumTransaction);
            return false;
        }

        // Generate transaction output;
        float leftOver = getInputsValue() - value;
        transactionId = calculateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
        outputs.add(new transactionOutput(this.sender, leftOver, transactionId));

        // Add Outputs;
        for (TransactionOutput o : outputs) {
            DeepMoment.UTXOs.put(o.Id, o);
        }

        // Remove transaction inputs from UTXO list as spent/
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) {
                continue;
            }

            DeepMoment.UTXOs.remove(i.UTXO.id);
        }

        return true;

    }

    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) {
                continue;
            }
            total += i.UTXO.value;
        }
        return total;
    }

    public void generatedSignature(PrivateKey privateKey) {
        String data = CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient)
                + Float.toString(value);
        signature = CommonUtils.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient)
                + Float.toString(value);

        return CommonUtils.verifyECDASig(sender, data, signature);
    }

    public float getOutputsValue() {
        float total = 0;
        for (TransactionInput o : outputs) {
            total += o.value;
        }
        return total;
    }

    private String calculateHash() {
        sequence++;

        return CommonUtils.applySha256(CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient)
                + Float.toString(value) + sequence);

    }

}
