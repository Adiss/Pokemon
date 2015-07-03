package hu.experiment_team;

import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A trainerek játékon belüli aktivitásának függvényei. NEM ACCOUNT FÜGGVÉNYEK!
 * @author Jakab Ádám
 * */
public enum TrainerMethods {

    /**
     * Ezen a mezőn keresztül érhetőek el az osztály metódusai.
     * */
    INSTANCE;

    /**
     * Ha meghívjuk, a trainer megkapja a birtokolt pokémonjai listáját, amiből kiválaszthat 6-ot amit megával akar vinni.
     * @param t A trainer objektuma
     * */
    public Trainer choosePartyPokemons(Trainer t){

        Scanner sc = new Scanner(new InputStreamReader(System.in));

        System.out.println("A te pokemonjaid:\n");
        List<Pokemon> ownedPokemons = PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t.getId());
        ownedPokemons.forEach(System.out::println);
        System.out.println("\n\n");

        List<Pokemon> partyPokemons = new ArrayList<>();
        for(int i = 0; (i < 6) && (i < ownedPokemons.size()); i++){
            System.out.println("Írd le a(z) " + i + ". választott pokémon ID-ját");
            partyPokemons.add(PokemonDaoJDBC.INSTANCE.getOwnedPokemonById(Integer.parseInt(sc.nextLine())));
        }
        t.setPartyPokemons(partyPokemons);

        t.setActivePokemons(partyPokemons.size());

        return t;
    }

    /**
     * Kiválaszt a trainernek 6 random pokémont a birtokolt pokémonok közzül, amit magával vihet.
     * @param t A trainer objektuma
     * */
    public Trainer chooseRandomPartyPokemons(Trainer t){

        List<Pokemon> ownedPokemons = PokemonDaoJDBC.INSTANCE.getOwnedPokemons(t.getId());

        List<Pokemon> partyPokemons = new ArrayList<>();
        for(int i = 0; (i < 6) && (i < ownedPokemons.size()); i++){
            Collections.shuffle(ownedPokemons);
            partyPokemons.add(ownedPokemons.remove(0));
        }
        t.setPartyPokemons(partyPokemons);

        t.setActivePokemons(partyPokemons.size());

        return t;
    }

}
