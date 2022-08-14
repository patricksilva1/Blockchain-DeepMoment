package com.example;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    // Contains a hash of transaction;
    public String transactionID;
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
            i.UTXO = Deep.UTXOs.get(i.transactionOutputId);
        }

        // And here we need to check if transactions are valid;

    }

}
