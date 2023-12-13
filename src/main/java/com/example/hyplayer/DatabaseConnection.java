package com.example.hyplayer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getDatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/hyplayer_db";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
