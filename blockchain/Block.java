package blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private String data;

    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }
    public String calculateHash() {
        String input = index + previousHash + timestamp + data;
        return applySHA256(input);
    }
  
    private String applySHA256(String input) {
        try {
            // Create a MessageDigest instance for SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hashing on the input bytes
            byte[] hashBytes = digest.digest(input.getBytes());

            // StringBuilder to store the final hexadecimal hash
            StringBuilder hexString = new StringBuilder();

            // Convert each byte into its hexadecimal representation
            for (byte b : hashBytes) {
                // Convert byte to unsigned integer and then to hex
                String hex = Integer.toHexString(0xff & b);

                // If hex value is a single digit, prepend '0' to maintain 2-digit format
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                // Append the hex value to the result
                hexString.append(hex);
            }

            // Return the complete hexadecimal hash string
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // This exception occurs if SHA-256 algorithm is not available
            // Wrap it in a RuntimeException for simplicity
            throw new RuntimeException("Error while hashing using SHA-256", e);
        }
    }
    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }
}
