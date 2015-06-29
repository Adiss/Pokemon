/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

import java.sql.Date;
import java.util.List;

/**
 * This model contains the trainer.
 * @author Jakab �d�m
 * */
public class Trainer {

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
     * List of actually carried pokemons
     * */
    private List<Pokemon> partyPokemons;
    /**
     * Number of active ( healthy ) pokemons
     * */
    private int activePokemons;
    /**
     * Amount of wins
     * */
    private int matchWin;
    /**
     * Amount of looses
     * */
    private int matchLoose;

    /**
     * This inner class builds the object.
     * @author Jakab �d�m
     * */
    public static class Builder {

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
         * List of actually carried pokemons
         * */
        private List<Pokemon> partyPokemons;
        /**
         * Number of active ( healthy ) pokemons
         * */
        private int activePokemons;
        /**
         * Amount of wins
         * */
        private int matchWin;
        /**
         * Amount of looses
         * */
        private int matchLoose;

        /**
         * Constructor.
         * @param username Username of the user
         * @param password Password of the user
         * @param email Email of the user
         * */
        public Builder(String username, String password, String email){
            this.username = username;
            this.password = password;
            this.email = email;
        }
        public Builder register_date(Date val){ register_date = val; return this; }
        public Builder displayName(String val){ displayName = val; return this; }
        public Builder partyPokemons(List<Pokemon> val){ this.partyPokemons = val; return this; }
        public Builder activePokemons(int val){ this.activePokemons = val; return this; }
        public Builder matchWin(int val){ matchWin = val; return this; }
        public Builder matchLoose(int val){ matchLoose = val; return this; }
        public Trainer build(){ return new Trainer(this); }

    }

    /**
     * Constructor.
     * @param builder Data came from the builder class
     * */
    private Trainer(Builder builder){
        this.username = builder.username;
        this.displayName = builder.displayName;
        this.password = builder.password;
        this.email = builder.email;
        this.register_date = builder.register_date;
        this.partyPokemons = builder.partyPokemons;
        this.activePokemons = builder.activePokemons;
        this.matchWin = builder.matchWin;
        this.matchLoose = builder.matchLoose;
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
     * @return List of party pokemons
     * */
    public List<Pokemon> getPartyPokemons() { return partyPokemons; }
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
     * @param displayName Display name of the user
     * */
    public void setDisplayName(String displayName) { this.displayName = displayName; }
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

}
