/**
 * Created by Jakab on 2015.06.27..
 */
package hu.experiment_team;

import hu.experiment_team.dao.MoveDaoJDBC;
import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.dao.TrainerDaoJDBC;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class of the application.
 * @author Jakab Ádám
 * */
public class Main {

    public static void main(String[] args){

        // A trainer belép a rendszerbe
        Trainer t = UserMethods.INSTANCE.login("adiss", "422341");
        List<Pokemon> ownedPokemons = PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t.getId());


        // Kap 6 random pokémont
        if(ownedPokemons.size() < 6){
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
            PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
        }

        // Az első pokemon megüti a 2. pokemont

        System.out.println("Elso pokemon utes elott: ");
        System.out.println(ownedPokemons.get(1).getName());
        System.out.println(ownedPokemons.get(1).getHp());
        System.out.println();

        System.out.println("Masodik pokemon utes elott: ");
        System.out.println(ownedPokemons.get(0).getName());
        System.out.println(ownedPokemons.get(0).getHp());
        System.out.println();

        Battle.INSTANCE.dealDamage(ownedPokemons.get(0), ownedPokemons.get(1), MoveDaoJDBC.INSTANCE.getMoveById(ownedPokemons.get(0).getMove1Id()));

        /*

         Scanner scanIn = new Scanner(System.in);

        System.out.println("Enter your username:");
        String username = scanIn.nextLine();
        System.out.println("Enter your password:");
        String password = scanIn.nextLine();

        Trainer user = UserMethods.INSTANCE.login(username, password);

        if(user != null){
            System.out.println("Logged in!");
        } else {
            System.out.println("Wrong username or password!");
        }

        scanIn.close();

        */
    }
}
