/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public enum TrainerDaoJDBC implements TrainerDaoInterface {
    INSTANCE;

    private static final String host = "jdbc:mysql://127.0.0.1:3306/pokemondb";
    private static final String username = "root";
    private static final String password = "parro";

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    @Override
    public void insert(Trainer t){
        String insertStatement = "INSERT INTO users (username, displayName, password, email) VALUES(?, ?, ?, ?);";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
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

    @Override
    public Trainer selectByName(String name){
        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE username = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
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
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    @Override
    public Trainer selectByPassword(String pass){
        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE password = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
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
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

    @Override
    public Trainer selectByEmail(String email){
        Trainer t = null;
        String selectStatement = "SELECT * FROM users WHERE email = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
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
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return t;
    }

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
