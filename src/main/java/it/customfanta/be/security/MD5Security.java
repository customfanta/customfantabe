package it.customfanta.be.security;

import lombok.AllArgsConstructor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Security {

    public static String getMD5Pass(String password) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        StringBuilder hashtext = new StringBuilder(bigInt.toString(16));
        while(hashtext.length() < 32 ){
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }

}
