package ru.alex.myBlog.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHash {
    public static String HashCalculate(String algrtm, String text) throws NoSuchAlgorithmException {
        //algrtm "SHA-256"
        MessageDigest md = MessageDigest.getInstance(algrtm);
//        String text = "Text to hash, cryptographically.";

        // Change this to UTF-16 if needed
        md.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }
}
