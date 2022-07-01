package com.dev.tourguide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  
public class MySQLConnUtils {
  
 public static Connection getMySQLConnection()
         throws ClassNotFoundException, SQLException {
     String hostName = "localhost";
     String dbName = "tourguide";
     String userName = "root";
     String password = "root";
     return getMySQLConnection(hostName, dbName, userName, password);
 }
  
 public static Connection getMySQLConnection(String hostName, String dbName,
         String userName, String password) throws SQLException,
         ClassNotFoundException {
    
     Class.forName("com.mysql.cj.jdbc.Driver");
     String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName+"?autoReconnect=true&useSSL=false";
     Connection conn = DriverManager.getConnection(connectionURL, userName,
             password);
     return conn;
 }
}
