package bitedu.bipa.simplesignbackend.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final SecureRandom sr = new SecureRandom();

    // Salt
    public static String getSalt(){
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        StringBuffer sb = new StringBuffer();
        for(byte b : salt){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

    // SHA-256
    public static String getEncode(String pwd, String salt){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((pwd + salt).getBytes());
            byte[] pwdSalt = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt){
                sb.append(String.format("%02x",b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
