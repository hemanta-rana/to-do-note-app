package com.example.noteapplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // get all notes from the database of table note

    public List<Note> getAllNotes(){
        ArrayList <Note> noteList = new ArrayList<>();

        String sql = "SELECT * FROM note";

        try( Connection conn = connect();
             Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                String title  = rs.getString("noteTitle");
                String description = rs.getString("noteDescription");
                Date date = rs.getDate("noteCurrentTIme");
                String status = rs.getString("noteStatus");
                String category = rs.getString("noteCategory");
                noteList.add(new Note(title, description,status,category));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("sql error ");
        }
        return noteList;
    }


    // add note the database
    public void addNote(Note note){

        String sql = "INSERT INTO note( noteTitle, noteDescription,noteCurrentTIme, noteStatus, noteCategory) VALUES (?,?,?,?,?) ";

        try ( Connection conn = connect();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, note.getTitle());
            ps.setString(2,note.getDescription());
            java.sql.Date sqlDate = java.sql.Date.valueOf(note.getCurrentTime().toLocalDate());
            ps.setDate(3, sqlDate);
            ps.setString(4, note.getStatus());
            ps.setString(5,note.getCategory());
            ps.executeUpdate();


        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("failed to add ");
        }
    }

    // filter the data by status
    public List<Note> getNotesByStatus( String title ){
        ArrayList<Note> noteList = new ArrayList<>();

        String sql = "SELECT * FROM note WHERE noteTitle = ? ";

        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String noteTitle = rs.getString("noteTitle");
                String description = rs.getString("noteDescription");
                String status = rs.getString("noteStatus");
                String category = rs.getString("noteCategory");

                noteList.add(new Note(noteTitle, description,status, category));

            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("sql error ");
        }
        return noteList;

    }

    // delete a row in note table

    public void deleteNote(Note note){
        String sql = "DELETE FROM note WHERE title = ? ";

        try (Connection conn = connect();
           PreparedStatement ps = conn.prepareStatement(sql)  ){
            ps.setString(1,note.getTitle());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("failed to delete ");
        }
    }











}
