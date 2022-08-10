package com.example;

import java.security.PublicKey;
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

    // to Do: add List transactions input and output here.

}
