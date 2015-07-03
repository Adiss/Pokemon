package hu.experiment_team;

import hu.experiment_team.dao.MoveDaoJDBC;
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

        if(t2.getOwnedPokemons().size() < 10)
            for(int i = t2.getOwnedPokemons().size(); i <= 10; i++)
                t2.addPokemon(PokemonDaoJDBC.INSTANCE.getRandomPokemon());
        if(t1.getOwnedPokemons().size() < 10)
            for(int i = t1.getOwnedPokemons().size(); i <= 10; i++)
                t1.addPokemon(PokemonDaoJDBC.INSTANCE.getRandomPokemon());

        t2.chooseRandomPartyPokemons();

        Battle.INSTANCE.doBattle(t1, t2);

        /*
        * Teszt BattleAI
        *
        System.out.println("NextMove: ");
        System.out.println("T1 Pokemon: " + PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t1.getId()).get(3));
        System.out.println("T2 Pokemon: " + PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t2.getId()).get(1));
        System.out.println(BattleAI.INSTANCE.calculateNextMove(PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t1.getId()).get(3), PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t2.getId()).get(1)));
        * */


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
