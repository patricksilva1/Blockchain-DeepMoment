package com.example;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

import javax.management.RuntimeErrorException;

public class CommonUtils {

    public static boolean isLocal(String host) {

        try {
            Enumeration<NetworkInterface> allNetInterface = NetworkInterface.getNetworkInterfaces();
            while (allNetInterface.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterface.nextElement();
            }

            if (netInterface.isLoopback || netInterface.isVirtual() || !netInterface.isUp()) {
                continue;
            }

            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();
                if (ip != null) {
                    if (ip.getHostAddress().equals(host))
                        return true;
                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String applySha256(String input) {
        MessageDigest digest = DigestUtils.getSha256Digest();
        byte[] hash = digest.digest(StringUtils.getBytesUtf8(input));
        return Hex.encodeHexString(hash);
    }

    // * Applies ECDSA Signature and return the result (as bytes).

    public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
        Signature dsa;
        byte[] output = new byte[0];

        try {
            dsa = Signature.getInstance("ECDSA", "BC");

            dsa.initSign(privateKey);

            byte[] strByte = input.getBytes();

            dsa.update(strByte);

            byte[] realSig = dsa.sign();

            output = realSig;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    /**
     * Verifies a String signature
     * 
     * @param publicKey
     * @param data
     * @param signature
     * @return
     */

    public static boolean verifyECDASig(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);

        } catch (Exception e) {
            throw new RuntimeErrorException(e);

        }

    }

    // Short hand helper to turn Object into a JSON String
    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }

    // Return difficulty String Target, to compare to hash.
    // * e.g.: Difficulty of 5 return "00000" *

    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static String getMerkleRoot(List<Transaction> transactions) {
        int count = transactions.size();

        List<String> previousTreeLayer = new ArrayList<String>();

        for (Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.transactionID);
        }

        List<String> treeLayer = previousTreeLayer;

        while (count > 1) {
            treeLayer = new ArrayList<String>();
            for (int i = 1; i < previousTreeLayer.size(); i += 2) {
                treeLayer.add(applySha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }

        return (treeLayer.size() == 1 ? treeLayer.get(0) : "");

    }

    // Working with Strings
    public static boolean isBrank(final CharSequence cs) {
        int strLen;

        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;

    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBrank(cs);
    }

}