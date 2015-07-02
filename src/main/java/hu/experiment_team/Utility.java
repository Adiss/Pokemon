package hu.experiment_team;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class contains the utility methods like password encryption to SHA1 etc..
 * @author Jakab �d�m
 * */
public enum Utility {
    /**
     * Instance of the singleton class.
     * */
    INSTANCE;

    /**
     * @param array Byte array of the password
     * @return The encrypted password
     */
    public String byteArrayToHex(byte[] array){
        StringBuilder builder = new StringBuilder(array.length * 2);
        for(byte b : array)
            builder.append(String.format("%02x", b));
        return builder.toString();
    }

    /**
     * @param user The username which the user logged in with
     * @param pw Username which the user logged in with
     * @return Returns the encrypted password
     */
    public String SHA1(String user, String pw){
        MessageDigest md;
        byte[] sha1hash = new byte[40];
        String toEncrypt = user+pw;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(toEncrypt.getBytes("utf-8"), 0, toEncrypt.length());
            sha1hash = md.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteArrayToHex(sha1hash);
    }

}
