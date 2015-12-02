/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;

import java.sql.*;

/**
 *
 * @author Tifani
 */
public class DatabaseConnector {
    // JDBC driver name and database URL
    private static final String driver = "org.sqlite.JDBC";
    private static final String database = "jdbc:sqlite:gomoku.db";
    
    
    public static Connection connect() {
        Connection c = null;
        try {
            Class.forName(driver);
            c = DriverManager.getConnection(database);
            /*  If not exist: 
                CREATE TABLE `user` (
                    `id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    `username`	TEXT NOT NULL,
                    `score`	INT DEFAULT 0
                );
            */
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return c;
    }
}
