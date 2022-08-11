package com.example;
public class TransactionInput {

    // Reference to TransacationOutput -> transactionId
    public String transactionOutputId;

    // Constains the unspent transaction output
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;

    }

}
