package com.example.alexis_addo.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    private static final String url = "jdbc:mysql://localhost:3306/inventory";
    private static final String user = "root";
    private static final String password = "morpheus1234#@";
    private static Connection connection;

//    public Connection getConnection() {
//        if (connection == null) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                connection = DriverManager.getConnection(url, user, password);
//                System.out.println("Connection Successful: " + url);
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }
 public DatabaseConnector() {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection Successful: " + url);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

    // Static method to get the singleton instance
    public static Connection getConnection() {
        if (connection == null) {
            new DatabaseConnector();
        }
        return connection;
    }







}
