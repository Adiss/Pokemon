/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Data access object which can access the database.
 * @author Jakab �d�m
 * */
public enum Dao {

    /**
     * Instance of the singleton class.
     * */
    INSTANCE;

    /**
     * Host of the database.
     * */
    private static final String host = "jdbc:mysql://127.0.0.1:3306/pokemondb";
    /**
     * Username to the database.
     * */
    private static final String username = "root";
    /**
     * Password to the database.
     * */
    private static final String password = "parro";

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
     * This method checks the users existence.
     * @param email Email address of the user
     * @return Return true if the user exist, else false
     * */
    protected Boolean checkUserByEmail(String email){
        Boolean exist = false;
        String selectStatement = "SELECT id FROM users WHERE email = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, email);
            rs = prepStmt.executeQuery();
            if(rs.next()) exist = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return exist;
    }

    /**
     * This method checks the users existence.
     * @param name Username of the user
     * @return Return true if the user exist, else false
     * */
    protected Boolean checkUserByName(String name){
        Boolean exist = false;
        String selectStatement = "SELECT id FROM users WHERE username = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, name);
            rs = prepStmt.executeQuery();
            if(rs.next()) exist = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return exist;
    }

    /**
     * This method checks the users existence.
     * @param pw Password of the user
     * @return Return true if the user exist, else false
     * */
    protected Boolean checkUserByPassword(String pw){
        Boolean exist = false;
        String selectStatement = "SELECT id FROM users WHERE password = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, pw);
            rs = prepStmt.executeQuery();
            if(rs.next()) exist = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return exist;
    }

    /**
     * This method checks the user's existence.
     * @param name Username of the user
     * @return Returns the user as Trainer object if user exists else null
     * */
    protected Trainer getUserBySHAPass(String name){
        Trainer user = null;
        String selectStatement = "SELECT * FROM users WHERE password = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setString(1, name);
            rs = prepStmt.executeQuery();
            if(rs.next()){
                user = new Trainer.Builder(rs.getString("username"), rs.getString("password"), rs.getString("email"))
                        .register_date(rs.getDate("register_date"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method inserts the user to the database.
     * @param rusername The users choosen name
     * @param  rpassword The users choosen password
     * @param  remail The users email address
     * */
    protected void insertUser(String rusername, String rpassword, String remail){
        String insertStatement = "INSERT INTO users (username, password, email) VALUES(?, ?, ?);";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(insertStatement);
            prepStmt.setString(1, rusername);
            prepStmt.setString(2, rpassword);
            prepStmt.setString(3, remail);
            prepStmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * This method queries the database to the object of a base pokemon by its id (Not the owned pokemon)
     * @param pokemonId the id of the pokemon
     * @return the Pokemon object with the base stats (Not owned pokemon stats), or null on error
     * */
    public Pokemon getBasePokemonById(int pokemonId){
        Pokemon p = null;
        String selectStatement = "SELECT * FROM pokemons WHERE id = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, pokemonId);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                p = new Pokemon.Builder(rs.getInt("id"), rs.getString("displayName"), rs.getString("internalName"), rs.getInt("hp"), rs.getInt("attack"), rs.getInt("defense"), rs.getInt("speed"), rs.getInt("spAttack"), rs.getInt("spDefense"))
                        .kind(rs.getString("kind"))
                        .pokeDex(rs.getString("pokeDex"))
                        .type1(rs.getString("type1"))
                        .type2(rs.getString("type2"))
                        .rareness(rs.getInt("rareness"))
                        .baseExp(rs.getInt("baseXp"))
                        .happiness(rs.getInt("happiness"))
                        .growthRate(rs.getString("growthRate"))
                        .stepsToHatch(rs.getInt("stepsToHatch"))
                        .color(rs.getString("color"))
                        .habitat(rs.getString("habitat"))
                        .effortPointsHp(rs.getInt("effortPointsHp"))
                        .effortPointsAttack(rs.getInt("effortPointsAttack"))
                        .effortPointsDefense(rs.getInt("effortPointsDefense"))
                        .effortPointsSpeed(rs.getInt("effortPointsSpeed"))
                        .effortPointsSpAttack(rs.getInt("effortPointsSPAttack"))
                        .effortPointsSpDefense(rs.getInt("effortPointsSPDefense"))
                        .hiddenAbility(rs.getString("hiddenAbility"))
                        .compatibility(rs.getString("compatibility"))
                        .height(rs.getDouble("height"))
                        .weight(rs.getDouble("weight"))
                        .genderRate(rs.getString("genderRate"))
                        .battlerPlayerY(rs.getInt("battlerPlayerY"))
                        .battlerEnemyY(rs.getInt("battlerEnemyY"))
                        .battlerAltitude(rs.getInt("battlerAltitude"))
                        .build();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return p;
    }

    /**
     * @param trainerId Id of the trainer who will owns the pokemon
     * @param pokemonId Id of the pokemon which will be owned
     * */
    public void addOwnedPokemon(int trainerId, int pokemonId){
        String insertStatement = "INSERT INTO ownedPokemons VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Pokemon p = getBasePokemonById(pokemonId);
        List<Integer> moveIds = getKnownMove(1, pokemonId);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(insertStatement);
            prepStmt.setInt(1, trainerId);
            prepStmt.setInt(2, p.getId());
            prepStmt.setString(3, p.getName());
            prepStmt.setString(4, p.getInternalName());
            prepStmt.setString(5, p.getKind());
            prepStmt.setString(6, p.getPokeDex());
            prepStmt.setString(7, p.getType1());
            prepStmt.setString(8, p.getType2());
            prepStmt.setInt(9, 1);
            prepStmt.setInt(10, p.getHp());
            prepStmt.setInt(11, p.getAttack());
            prepStmt.setInt(12, p.getDefense());
            prepStmt.setInt(13, p.getSpeed());
            prepStmt.setInt(14, p.getSpAttack());
            prepStmt.setInt(15, p.getSpDefense());
            prepStmt.setInt(16, p.getBaseExp());
            prepStmt.setInt(17, p.getRareness());
            prepStmt.setInt(18, p.getBaseExp());
            prepStmt.setInt(19, p.getHappiness());
            prepStmt.setString(20, p.getGrowthRate());
            prepStmt.setInt(21, p.getStepsToHatch());
            prepStmt.setString(22, p.getColor());
            prepStmt.setString(23, p.getHabitat());
            prepStmt.setInt(24, p.getEffortPointsHp());
            prepStmt.setInt(25, p.getEffortPointsAttack());
            prepStmt.setInt(26, p.getEffortPointsDefense());
            prepStmt.setInt(27, p.getEffortPointsSpeed());
            prepStmt.setInt(28, p.getEffortPointsSpAttack());
            prepStmt.setInt(29, p.getEffortPointsSpDefense());
            prepStmt.setString(30, p.getHiddenAbility());
            prepStmt.setString(31, p.getCompatibility());
            prepStmt.setDouble(32, p.getHeight());
            prepStmt.setDouble(33, p.getWeight());
            prepStmt.setString(34, p.getGenderRate());
            prepStmt.setInt(35, p.getBattlerPlayerY());
            prepStmt.setInt(36, p.getBattlerEnemyY());
            prepStmt.setInt(37, p.getBattlerAltitude());
            prepStmt.setInt(38, moveIds.get(0));
            if(moveIds.size() >= 2) prepStmt.setInt(39, moveIds.get(1)); else prepStmt.setInt(39, 0);
            if(moveIds.size() >= 3) prepStmt.setInt(40, moveIds.get(2)); else prepStmt.setInt(40, 0);
            if(moveIds.size() == 4) prepStmt.setInt(41, moveIds.get(3)); else prepStmt.setInt(41, 0);
            prepStmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * This method queries the database to the owned pokemons
     * @param trainerId Id of the trainer, who owns the pokemons
     * @return List of owned pokemons or an empty list on error
     * */
    public List<Pokemon> getOwnedPokemons(int trainerId){
        List<Pokemon> listOfOwnedPokemons = new ArrayList<>();
        String selectStatement = "SELECT * FROM ownedPokemons WHERE ownerId = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, trainerId);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                listOfOwnedPokemons.add(
                        new Pokemon.Builder(rs.getInt("pokemonId"), rs.getString("displayName"), rs.getString("internalName"), rs.getInt("hp"), rs.getInt("attack"), rs.getInt("defense"), rs.getInt("speed"), rs.getInt("spAttack"), rs.getInt("spDefense"))
                                .kind(rs.getString("kind"))
                                .pokeDex(rs.getString("pokeDex"))
                                .type1(rs.getString("type1"))
                                .type2(rs.getString("type2"))
                                .rareness(rs.getInt("rareness"))
                                .baseExp(rs.getInt("baseXp"))
                                .happiness(rs.getInt("happiness"))
                                .growthRate(rs.getString("growthRate"))
                                .stepsToHatch(rs.getInt("stepsToHatch"))
                                .color(rs.getString("color"))
                                .habitat(rs.getString("habitat"))
                                .effortPointsHp(rs.getInt("effortPointsHp"))
                                .effortPointsAttack(rs.getInt("effortPointsAttack"))
                                .effortPointsDefense(rs.getInt("effortPointsDefense"))
                                .effortPointsSpeed(rs.getInt("effortPointsSpeed"))
                                .effortPointsSpAttack(rs.getInt("effortPointsSPAttack"))
                                .effortPointsSpDefense(rs.getInt("effortPointsSPDefense"))
                                .hiddenAbility(rs.getString("hiddenAbility"))
                                .compatibility(rs.getString("compatibility"))
                                .height(rs.getDouble("height"))
                                .weight(rs.getDouble("weight"))
                                .genderRate(rs.getString("genderRate"))
                                .battlerPlayerY(rs.getInt("battlerPlayerY"))
                                .battlerEnemyY(rs.getInt("battlerEnemyY"))
                                .battlerAltitude(rs.getInt("battlerAltitude"))
                                .ownerId(rs.getInt("ownerId"))
                                .level(rs.getInt("level"))
                                .currentXp(rs.getInt("currentXp"))
                                .move1Id(rs.getInt("moveId1"))
                                .move2Id(rs.getInt("moveId2"))
                                .move3Id(rs.getInt("moveId3"))
                                .move4Id(rs.getInt("moveId4"))
                                .build());
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listOfOwnedPokemons;
    }

    /**
     * This method queries the database to the object of a move by Id
     * @param moveId Id of the move
     * @return The Move object, or null on error
     * */
    public Move getMoveById(int moveId){
        Move m = null;
        String selectStatement = "SELECT * FROM moves WHERE id = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, moveId);
            rs = prepStmt.executeQuery();
            if(rs.next()) {
                m = new Move.Builder(rs.getInt("id"), rs.getInt("baseDamage"), rs.getString("type"), rs.getString("moveCategory"), rs.getInt("accuracy"), rs.getInt("totalPP"), rs.getInt("additionalEffectChance"))
                        .target(rs.getInt("target"))
                        .priority(rs.getInt("priority"))
                        .flags(rs.getString("flags"))
                        .contestType(rs.getString("contestType"))
                        .description(rs.getString("description"))
                        .internalName(rs.getString("internalName"))
                        .displayName(rs.getString("displayName"))
                        .functionCode(rs.getString("functionCode"))
                        .build();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return m;
    }

    /**
     * This method gets the pokemon moveIds to add them when a pokemon got owned
     * @param level the actual level of the pokmeon
     * @param pokemonId Id of the pokemon
     * @return Returns the list of moves
     * */
    public List<Integer> getKnownMove(int level, int pokemonId){
        List<Integer> moveIds = new ArrayList<>();
        String selectStatement = "SELECT moveId FROM pokemonmovesbylevel WHERE level <= ? AND pokemonId = ? ORDER BY level DESC LIMIT 4";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, level);
            prepStmt.setInt(2, pokemonId);
            rs = prepStmt.executeQuery();
            while(rs.next()) {
                moveIds.add(rs.getInt("moveId"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return moveIds;
    }

    /**
     * This method close the mysql connection.
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