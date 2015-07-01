/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Pokemon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum PokemonDaoJDBC implements PokemonDaoInterface {
    INSTANCE;

    private static final String host = "jdbc:mysql://127.0.0.1:3306/pokemondb";
    private static final String username = "root";
    private static final String password = "parro";

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    @Override
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

    @Override
    public void addOwnedPokemon(int trainerId, int pokemonId){
        String insertStatement = "INSERT INTO `ownedpokemons` (`ownerId`, `pokemonId`, `displayName`, `internalName`, `kind`, `pokeDex`, `type1`, `type2`, `level`, `hp`, `attack`, `defense`, `speed`, `spAttack`, `spDefense`, `currentXp`, `rareness`, `baseXp`, `happiness`, `growthRate`, `stepsToHatch`, `color`, `habitat`, `effortPointsHp`, `effortPointsAttack`, `effortPointsDefense`, `effortPointsSpeed`, `effortPointsSPAttack`, `effortPointsSPDefense`, `hiddenAbility`, `compatibility`, `height`, `weight`, `genderRate`, `battlerPlayerY`, `battlerEnemyY`, `battlerAltitude`, `move1Id`, `move2Id`, `move3Id`, `move4Id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Pokemon p = getBasePokemonById(pokemonId);
        List<Integer> moveIds = MoveDaoJDBC.INSTANCE.getKnownMove(1, pokemonId);
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

    @Override
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
                                .move1Id(rs.getInt("move1Id"))
                                .move2Id(rs.getInt("move2Id"))
                                .move3Id(rs.getInt("move3Id"))
                                .move4Id(rs.getInt("move4Id"))
                                .ownedID(rs.getInt("id"))
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

    @Override
    public Pokemon resetPokemon(int ownedID){
        Pokemon p = null;
        String selectStatement = "SELECT * FROM ownedPokemons WHERE id = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, ownedID);
            rs = prepStmt.executeQuery();
            while(rs.next()){
                p = new Pokemon.Builder(rs.getInt("pokemonId"), rs.getString("displayName"), rs.getString("internalName"), rs.getInt("hp"), rs.getInt("attack"), rs.getInt("defense"), rs.getInt("speed"), rs.getInt("spAttack"), rs.getInt("spDefense"))
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
                        .move1Id(rs.getInt("move1Id"))
                        .move2Id(rs.getInt("move2Id"))
                        .move3Id(rs.getInt("move3Id"))
                        .move4Id(rs.getInt("move4Id"))
                        .ownedID(rs.getInt("id"))
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

    @Override
    public Pokemon getRandomPokemon(){
        Random r = new Random();
        Pokemon p = null;
        String selectStatement = "SELECT * FROM pokemons WHERE id = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, username, password);
            prepStmt = conn.prepareStatement(selectStatement);
            prepStmt.setInt(1, r.nextInt(649-1) + 1);
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
