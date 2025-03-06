package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "";
    String password = "";

    public Connection connection;

    public ConnectionDB(){
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(connection!=null){
            System.out.println("Connection Established");
        }else{
            System.out.println("Connection failed");
        }
    }
}
