package com.example;

import java.security.PublicKey;

public class TransactionOutput {

    public String id;
    public PublicKey reciepient; // Owner of these coins;
    public float value; // The amount of coins
    public String parentTransactionId; // The Id of the transaction this output was created in

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        // To do: add the Id = CommonUtils
    }

    // Check if coin belongs to u.

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == reciepient);
    }

}
