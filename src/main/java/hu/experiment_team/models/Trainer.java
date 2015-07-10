package hu.experiment_team.models;

import hu.experiment_team.dao.PokemonDaoJDBC;

import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This model contains the trainer.
 * @author Jakab �d�m
 * */
public class Trainer {

    /**
     * Database ID of the trainer
     * */
    private int id;
    /**
     * Username of the user.
     * */
    private String username;
    /**
     * Display name of the user.
     * */
    private String displayName;
     /**
     * Password of the user.
     * */
    private String password;
    /**
     * Email of the user.
     * */
    private String email;
    /**
     * The date when the user registered.
     * */
    private Date register_date;
    /**
     * List of owned pokémons.
     * */
    private List<Pokemon> ownedPokemons;
    /**
     * List of actually carried pokemons.
     * */
    private List<Pokemon> partyPokemons;
    /**
     * Number of active ( healthy ) pokemons.
     * */
    private int activePokemons;
    /**
     * Amount of wins.
     * */
    private int matchWin;
    /**
     * Amount of looses.
     * */
    private int matchLoose;
    /**
     * Online status of the palyer.
     * */
    private int online;

    /**
     * This inner class builds the object.
     * @author Jakab �d�m
     * */
    public static class Builder {

        /**
         * Database ID of the trainer
         * */
        private int id;
        /**
         * Username of the user.
         * */
        private String username;
        /**
         * Display name of the user.
         * */
        private String displayName;
        /**
         * Password of the user.
         * */
        private String password;
        /**
         * Email of the user.
         * */
        private String email;

        /**
         * The date when the user registered.
         * */
        private Date register_date = null;
        /**
         * List of owned pokémons
         * */
        private List<Pokemon> ownedPokemons;
        /**
         * List of actually carried pokemons
         * */
        private List<Pokemon> partyPokemons = null;
        /**
         * Number of active ( healthy ) pokemons
         * */
        private int activePokemons = 0;
        /**
         * Amount of wins
         * */
        private int matchWin = 0;
        /**
         * Amount of looses
         * */
        private int matchLoose = 0;
        /**
         * Online status of the palyer
         * */
        private int online = 0;

        /**
         * Constructor.
         * @param username Username of the user
         * @param displayName Display name of the user
         * @param password Password of the user
         * @param email Email of the user
         * */
        public Builder(String username, String displayName, String password, String email){
            this.username = username;
            this.displayName = displayName;
            this.password = password;
            this.email = email;
        }
        public Builder register_date(Date val){ register_date = val; return this; }
        public Builder ownedPokemons(List<Pokemon> val){ this.ownedPokemons = val; return this; }
        public Builder partyPokemons(List<Pokemon> val){ this.partyPokemons = val; return this; }
        public Builder activePokemons(int val){ this.activePokemons = val; return this; }
        public Builder matchWin(int val){ matchWin = val; return this; }
        public Builder matchLoose(int val){ matchLoose = val; return this; }
        public Builder online(int val){ online = val; return this; }
        public Builder id(int val){ id = val; return this; }
        public Trainer build(){ return new Trainer(this); }

    }

    /**
     * Constructor.
     * @param builder Data came from the builder class
     * */
    private Trainer(Builder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.displayName = builder.displayName;
        this.password = builder.password;
        this.email = builder.email;
        this.register_date = builder.register_date;
        this.ownedPokemons = builder.ownedPokemons;
        this.partyPokemons = builder.partyPokemons;
        this.activePokemons = builder.activePokemons;
        this.matchWin = builder.matchWin;
        this.matchLoose = builder.matchLoose;
        this.online = builder.online;
    }

    /**
     * @return Username of the user
     * */
    public String getUsername(){ return username; }
    /**
     * @return Display name of the user
     * */
    public String getDisplayName(){ return displayName; }
    /**
     * @return Password of the user
     * */
    public String getPassword(){ return password; }
    /**
     * @return Email of the user
     * */
    public String getEmail(){ return email; }
    /**
     * @return Register date of the user
     * */
    public Date getRegisterDate(){ return register_date; }
    /**
     * @return List of owned pokemons
     * */
    public List<Pokemon> getOwnedPokemons() { return ownedPokemons; }
    /**
     * @return List of party pokemons
     * */
    public List<Pokemon> getPartyPokemons() {
        return new ArrayList<Pokemon>(){{
            addAll(partyPokemons.stream().filter(p -> p.getBaseStats().get("hp") > 0).collect(Collectors.toList()));
        }};
    }
    /**
     * @return Number of healthy pokemons
     * */
    public int getActivePokemons() { return activePokemons; }
    /**
     * @return Amount of wins
     * */
    public int getMatchWin() { return matchWin; }
    /**
     * @return Amount os looses
     * */
    public int getMatchLoose() { return matchLoose; }
    /**
     * @return The player's online status
     * */
    public int getOnline() { return online; }
    /**
     * @return The trainer's database id
     * */
    public int getId() { return id; }

    /**
     * @param displayName Display name of the user
     * */
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    /**
     * @param ownedPokemons List of owned pokemons
     * */
    public void setOwnedPokemons(List<Pokemon> ownedPokemons) { this.ownedPokemons = ownedPokemons; }
    /**
     * @param partyPokemons List of carried pokemons
     * */
    public void setPartyPokemons(List<Pokemon> partyPokemons) { this.partyPokemons = partyPokemons; }
    /**
     * @param activePokemons Number of active pokemons
     * */
    public void setActivePokemons(int activePokemons) { this.activePokemons = activePokemons; }
    /**
     * @param matchWin Amount of wins
     * */
    public void setMatchWin(int matchWin) { this.matchWin = matchWin; }
    /**
     * @param matchLoose Amount of looses
     * */
    public void setMatchLoose(int matchLoose) { this.matchLoose = matchLoose; }
    /**
     * @param online Set the trainer's online status
     * */
    public void setOnline(int online) { this.online = online; }

    /**
     * Methods
     * */

    /**
     * Hozzáad a Trainerhez egy pokémont.
     * @param p A pokémon objektuma
     * */
    public void addPokemon(Pokemon p){
        PokemonDaoJDBC.INSTANCE.addOwnedPokemon(this.id, p.getId());
    }
    public void addPokemon(int pokemonId){
        PokemonDaoJDBC.INSTANCE.addOwnedPokemon(this.id, pokemonId);
    }

    /**
     * Ha meghívjuk, a trainer megkapja a birtokolt pokémonjai listáját, amiből kiválaszthat 6-ot amit megával akar vinni.
     * */
    public void choosePartyPokemons(){

        Scanner sc = new Scanner(new InputStreamReader(System.in));

        System.out.println("A te pokemonjaid:\n");
        for(int i = 0; i < this.ownedPokemons.size(); i++){
            System.out.println("ID: "+ (i+1) + " " + this.ownedPokemons.get(i));
        }
        System.out.println("\n");

        List<Pokemon> partyPokemons = new ArrayList<>();
        for(int i = 0; (i < 6) && (i < this.ownedPokemons.size()); i++){
            System.out.println("Írd le a(z) " + (i+1) + ". választott pokémon ID-ját");
            partyPokemons.add(this.ownedPokemons.get(Integer.parseInt(sc.nextLine())));
        }

        this.partyPokemons = partyPokemons;
        this.activePokemons = partyPokemons.size();

    }

    /**
     * Kiválaszt a trainernek 6 random pokémont a birtokolt pokémonok közzül, amit magával vihet.
     * */
    public void chooseRandomPartyPokemons(){

        List<Pokemon> ownedPokemons = this.ownedPokemons;
        List<Pokemon> partyPokemons = new ArrayList<>();
        for(int i = 0; (i < 6) && (i < ownedPokemons.size()); i++){
            Collections.shuffle(ownedPokemons);
            partyPokemons.add(ownedPokemons.remove(0));
        }

        this.setPartyPokemons(partyPokemons);
        this.setActivePokemons(partyPokemons.size());

    }

    public Pokemon chooseOneRandomPartyPokemon(){
        Collections.shuffle(this.getPartyPokemons());
        return this.getPartyPokemons().get(0);
    }

}
