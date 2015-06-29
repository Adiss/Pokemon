/**
 * Created by Jakab on 2015.06.27..
 */
package hu.experiment_team;

/**
 * Main class of the application.
 * @author Jakab �d�m
 * */
public class Main {

    /**
     * Main function of the application.
     * @param args Arguments of the program
     * */
    public static void main(String[] args){
        Trainer user = UserMethods.INSTANCE.login("adiss", "422341");
        Pokemon p1 = Dao.INSTANCE.getBasePokemonById(113);

        if(p1 != null){
            System.out.println(p1.getId());
            System.out.println(p1.getName());
        } else {
            System.out.println("Wrong pokemon ID!");
        }

        if(user != null){
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getEmail());
        } else {
            System.out.println("Wrong username or password!");
        }
    }

}
