package hu.experiment_team;

import hu.experiment_team.models.Move;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * This class contains the utility methods like password encryption to SHA1 etc..
 * @author Jakab Ádám
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

    public String getImageName(int pokemonId){
        if(pokemonId < 10){
            return "00"+pokemonId;
        } else if(pokemonId < 100){
            return "0"+pokemonId;
        } else {
            return String.valueOf(pokemonId);
        }
    }

    public int getIndex(List<Move> l, String s){
        for(int i = 0; i < l.size(); i++){
            if(l.get(i).getInternalName().equals(s))
                return i;
        }
        return -1;
    }

}
