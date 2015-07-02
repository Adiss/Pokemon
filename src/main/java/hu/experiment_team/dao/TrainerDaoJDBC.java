/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Ez az osztály fogja kezelni a trainerekkel (felhasználókkal) kapcsolatos adatbázis műveleteket.
 * Singleton osztály, nem kell példányosítani, az INSTANCE-on keresztül használható.
 * */
public enum TrainerDaoJDBC implements TrainerDaoInterface {

    /**
     * Ezen a mezőn keresztül érhetőek el az osztály metódusai.
     * */
    INSTANCE;
    /**
     * Létrehozunk egy props változót a properties fájlnak, amiben az adatbázis eléréséhez szükséges információk vannak.
     * */
    Properties props = new Properties();
    /**
     * This contains the actual connection.
     * */
    private Connection conn = null;
    /**
     * This contains the mysql statement.
     * */
    private PreparedStatement prepStmt = null;
    /**
     * This contains the result of the query.
     * */
    private ResultSet rs = null;

    /**
     * Hozzáad egy trainert (felhasználót) az adatbázishoz.
     * @param t A trainer osztály egy példánya a megfelelő mezőkkel feltöltve.
     * */
    @Override
    public void insert(Trainer t){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String insertStatement = "INSERT INTO users (username, displayName, password, email) VALUES(?, ?, ?, ?);";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(insertStatement);
            prepStmt.setString(1, t.getUsername());
            prepStmt.setString(2, t.getDisplayName());
            prepStmt.setString(3, t.getPassword());
            prepStmt.setString(4, t.getEmail());
            prepStmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a neve alapján.
     * @param name A trainer felhasználó neve.
     * */
    @Override
    public Trainer selectByName(String name){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE username = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, name);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<Pokemon> partyPokemons = new ArrayList<Pokemon>(){{
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon1")));
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon2")));
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon3")));
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon4")));
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon5")));
                    add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .activePokemons(rs.getInt("ActivePokemons"))
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) a jelszava alapján.
     * Ennek a jelszónak már az SHA1 kódolt jelszónak kell lennie.
     * @param pass SHA1 kódolt jelszó.
     * */
    @Override
    public Trainer selectByPassword(String pass){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE password = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, pass);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<Pokemon> partyPokemons = new ArrayList<Pokemon>(){{
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon1")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon2")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon3")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon4")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon5")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .activePokemons(rs.getInt("ActivePokemon"))
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Kiválaszt egy trainert (felhasználót) az e-mail címe alapján.
     * @param email A trainer email címe.
     * */
    @Override
    public Trainer selectByEmail(String email){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE email = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, email);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                List<Pokemon> partyPokemons = new ArrayList<Pokemon>(){{
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon1")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon2")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon3")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon4")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon5")));
                    this.add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(rs.getInt("PartyPokemon6")));
                }};
                t = new Trainer.Builder(rs.getString("username"), rs.getString("displayName"), rs.getString("password"), rs.getString("email"))
                        .partyPokemons(partyPokemons)
                        .activePokemons(rs.getInt("ActivePokemons"))
                        .matchWin(rs.getInt("wins"))
                        .matchLoose(rs.getInt("looses"))
                        .register_date(rs.getDate("register_date"))
                        .id(rs.getInt("id"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    /**
     * Ez a függvény fogja lezárni az adatbázis kapcsolatokat.
     * */
    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (prepStmt != null) {
                prepStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
