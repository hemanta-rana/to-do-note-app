package com.example.noteapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private String path = "jdbc:mysql://localhost:3306/notes";
    private String username = "root";
    private String password = "";


    // connection to the database
    public  Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(path,username,password);
            return connection;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }







}
