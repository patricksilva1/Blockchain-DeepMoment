package com.example;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;

public class Block {
    private String timeStamp;
    private String hash;
    private String prevHash;
    private String merkleRoot;

    private int index;
    private int difficulty;
    private int nonce;

    public transient List<Transaction> transaction = new ArrayList<Transaction>();

    // Calculating Hash
    public static String calculateHash(Block block) {
        String record = block.getIndex() + block.getTimeStamp() + block.getNonce() + block.getPrevHash() +
                block.getMerkleRoot();

        MessageDigest digest = DigestUtils.getSha256Digest();
        byte[] hash = digest.digest((StringUtils.getBytesUtf8(record)));
        return Hex.encodeHexString(hash);
    }

    /*
     * to Do: Increases nonce value until hash target is reached. (Check this later)
     * 
     * @param difficulty
     * 
     * @return hash
     */

    public String mineBlock() {
        this.merkleRoot = CommonUtils.getMerkleRoot(transaction);
        String target = CommonUtils.getDifficultyString(difficulty);
        hash = calculateHash(this);

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash(this);
        }

        return hash;
    }

    /**
     * Add transactions to this block
     * 
     * @param transaction
     * @return
     *
     */

    // This check the process transaction and verify if valid, unless block is
    // genesis block then ignore.
    public boolean addTransaction(Transaction transaction) {

        if (transaction == null)
            return false;

        if (!"0".equals(prevHash)) {
            if (transaction.processTransaction() != true) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transaction.add(transaction);
        System.out.println("Transaction Sucessfully added to Block");

        return true;
    }

    // Getters and Setters

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeString) {
        this.timeStamp = timeString;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHaString) {
        this.prevHash = prevHaString;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

}
