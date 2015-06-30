package hu.experiment_team.dao;

import hu.experiment_team.models.Move;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakab on 2015.06.30..
 */
public enum MoveDaoJDBC implements MoveDaoInterface {
    INSTANCE;

    private static final String host = "jdbc:mysql://127.0.0.1:3306/pokemondb";
    private static final String username = "root";
    private static final String password = "parro";

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    @Override
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

    @Override
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
