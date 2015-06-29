/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class do the registration and login.
 * @author Jakab Ádám
 * */
public enum UserMethods {
    /**
     * Instance of the singleton class.
     * */
    INSTANCE;

    /**
     * This method registers the user.
     * @param rusername Entered username of the user
     * @param rpassword First entered password of the user
     * @param rpassword2 Second entered password of the user
     * @param remail Entered email of the user
     * @return Errors as a String list
     * */
    public List<String> register(String rusername, String rpassword, String rpassword2,String remail) {
        List<String> errors = new ArrayList<>();

        if(!Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(remail).matches()){ errors.add("Ez az email-cim nem valodi."); }
        if(Dao.INSTANCE.checkUserByEmail(remail)){ errors.add("Mar van felhasznalo ilyen e-mail cimmel."); }
        if(Dao.INSTANCE.checkUserByName(rusername)){ errors.add("Mar van felhasznalo ilyen nevvel."); }
        if(!rpassword.equals(rpassword2)){ errors.add("A ket beirt jelszo nem egyezik meg."); }
        if(rusername.length() < 2){ errors.add("A felhasznalonev tol rovid."); }
        if(rusername.length() > 32){ errors.add("A felhasznalonev tol hosszu."); }
        if(rpassword.length() < 3){ errors.add("A jelszo tul rovid."); }
        if(rpassword.length() > 32){ errors.add("A jelszo tul hosszu."); }
        if(errors.isEmpty()){
            Dao.INSTANCE.insertUser(rusername, Utility.INSTANCE.SHA1(rusername, rpassword), remail);
            errors.add("Sikeres regisztracio!");
            return errors;
        } else {
            return errors;
        }
    }

    /**
     * Log in the user.
     * @param lusername Username of the user
     * @param lpassword Password of the user
     * @return The user as Trainer object
     * */
    public Trainer login (String lusername, String lpassword){
        Trainer user = null;
        if(Dao.INSTANCE.checkUserByName(lusername) && Dao.INSTANCE.checkUserByPassword(Utility.INSTANCE.SHA1(lusername, lpassword))){
            user = Dao.INSTANCE.getUserBySHAPass(Utility.INSTANCE.SHA1(lusername, lpassword));
        }
        return user;
    }

}
