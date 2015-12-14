package kucingvspanda.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import kucingvspanda.packet.models.HighScoreInfo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tifani
 */
public class DatabaseHandler {
    private static Connection conn = DatabaseConnector.connect();
    
    // Register akun baru, data username dan password ditambah ke db
    // Return 1 jika berhasil, return 0 jika gagal
    public static int register(String username) {
        try {
            String sql = "INSERT INTO user (username) VALUES (?)";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setString(1, username);
            dbStatement.executeUpdate();
            return 1;
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Validasi login akun 
    public static int login(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username=?";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setString(1, username);
            dbStatement.executeQuery();
            ResultSet rs = dbStatement.executeQuery();
            if (rs.next()) { 
                return 1;
            } else { // user hasn't been registered yet
                return register(username);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Menambahkan skor pada id tertentu
    public static void addPoints(String name, int points) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE user SET score = score + ? WHERE username=?";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setInt(1, points);
            dbStatement.setString(2, name);
            dbStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }   
    }
    
    // Mengambil highscore dengan rincian <username, score>
    public static ArrayList< HighScoreInfo > getHighscore() {
        ArrayList< HighScoreInfo > highScoreList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user ORDER BY score DESC";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            ResultSet rs = dbStatement.executeQuery();
            while (rs.next()) { 
                HighScoreInfo highScore = new HighScoreInfo();
                highScore.setPlayerName(rs.getString("username"));
                highScore.setScore(rs.getInt("score"));
               
                highScoreList.add(highScore);
            } 
        } catch(Exception e) {
            e.printStackTrace();
        }
        return highScoreList;
    }
    
}
