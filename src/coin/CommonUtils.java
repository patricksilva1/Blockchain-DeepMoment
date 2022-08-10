import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Enumeration;

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

 




}