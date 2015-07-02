/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Move;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Ez az osztály fogja kezelni a képességekkel kapcsolatos adatbázis műveleteket.
 * Singleton osztály, nem kell példányosítani, az INSTANCE-on keresztül használható.
 * */
public enum MoveDaoJDBC implements MoveDaoInterface {

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
     * A megadott ID alapján lekérdez egy teljes képességet az adatbázisból.
     * @param moveId A képesség adatbázis beli ID-je
     * */
    @Override
    public Move getMoveById(int moveId){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Move m = null;
        String selectStatement = "SELECT * FROM moves WHERE id = ?;";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
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
     * A megadott pokemon ID és szint alapján lekérdezi az adott pokemon adott szintjén és alatta tudható spelleket.
     * @param level A pokémon szintje.
     * @param pokemonId A pokémon ID-je
     * */
    @Override
    public List<Integer> getKnownMove(int level, int pokemonId){

        try {
            props.load(new FileInputStream("src/main/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> moveIds = new ArrayList<>();
        String selectStatement = "SELECT moveId FROM pokemonmovesbylevel WHERE level <= ? AND pokemonId = ? ORDER BY level DESC LIMIT 4";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(props.getProperty("db.host"), props.getProperty("db.username"), props.getProperty("db.password"));
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
