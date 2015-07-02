package hu.experiment_team;

import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.models.Trainer;

/**
 * Main class of the application.
 * @author Jakab Ádám
 * */
public class Main {

    public static void main(String[] args){

        Trainer t1 = UserMethods.INSTANCE.login("adiss", "422341");
        Trainer t2 = UserMethods.INSTANCE.login("teszt", "teszt");

        int t2OwnedSize = PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t2.getId()).size();
        int t1OwnedSize = PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t1.getId()).size();
        if(t2OwnedSize < 10)
            for(int i = t2OwnedSize; i <= 10; i++)
                PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t2.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());
        if(t1OwnedSize < 10)
            for(int i = t1OwnedSize; i <= 10; i++)
                PokemonDaoJDBC.INSTANCE.addOwnedPokemon(t1.getId(), PokemonDaoJDBC.INSTANCE.getRandomPokemon().getId());

        t2 = TrainerMethods.INSTANCE.chooseRandomPartyPokemons(t2);

        Battle.INSTANCE.doBattle(t1, t2);

        /*
        * Server/Client jar file
        if(args[0].equals("server")){
            new Server(6789);
        } else if(args[0].equals("client")){
            if(args[1] != null){
                new Client(args[1], 6789).start();
            }
        }
        * */

    }

}
