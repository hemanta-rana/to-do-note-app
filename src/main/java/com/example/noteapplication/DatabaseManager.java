package com.example.noteapplication;

import java.sql.*;
import java.time.LocalDateTime;
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
                Timestamp timestamp = rs.getTimestamp("noteCurrentTIme");
                String status = rs.getString("noteStatus");
                String category = rs.getString("noteCategory");

                LocalDateTime dateTime = timestamp != null ?
                        timestamp.toLocalDateTime() : LocalDateTime.now();

                noteList.add(new Note(title, description,status,category, dateTime));
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
//            java.sql.Date sqlDate = java.sql.Date.valueOf(note.getCurrentTime().toLocalDate());
//            ps.setDate(3, sqlDate);
            ps.setTimestamp(3, Timestamp.valueOf(note.getCurrentTime()));
            ps.setString(4, note.getStatus());
            ps.setString(5,note.getCategory());
            ps.executeUpdate();


        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("failed to add ");
        }
    }
    public void updateNote(Note note) {
        String sql = "UPDATE note SET noteTitle = ?, noteDescription = ?, noteStatus = ?, noteCategory = ? WHERE noteTitle = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getDescription());
            ps.setString(3, note.getStatus());
            ps.setString(4, note.getCategory());
            ps.setString(5, note.getTitle()); // Using title as identifier

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Note updated successfully");
            } else {
                System.out.println("No note found to update");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update note");
        }
    }


    // filter the data by status
    public List<Note> getNotesByStatus( String status ){
        ArrayList<Note> noteList = new ArrayList<>();

        String sql = "SELECT * FROM note WHERE noteStatus = ? ";

        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String noteTitle = rs.getString("noteTitle");
                String description = rs.getString("noteDescription");
                Timestamp timestamp = rs.getTimestamp("noteCurrentTIme");
                String Notestatus = rs.getString("noteStatus");
                String category = rs.getString("noteCategory");

                LocalDateTime dateTime = timestamp != null ?
                        timestamp.toLocalDateTime() : LocalDateTime.now();

                noteList.add(new Note(noteTitle, description,Notestatus, category,dateTime ));

            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("sql error ");
        }
        return noteList;

    }

    // delete a row in note table

    public void deleteNote(Note note){
        String sql = "DELETE FROM note WHERE noteTitle = ? ";

        try (Connection conn = connect();
           PreparedStatement ps = conn.prepareStatement(sql)  ){
            ps.setString(1,note.getTitle());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("failed to delete ");
        }
    }
    public List<Note> getNotesByCategory(String category) {
        ArrayList<Note> noteList = new ArrayList<>();
        String sql = "SELECT * FROM note WHERE noteCategory = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String noteTitle = rs.getString("noteTitle");
                String description = rs.getString("noteDescription");
                Timestamp timestamp = rs.getTimestamp("noteCurrentTIme");
                String status = rs.getString("noteStatus");
                String noteCategory = rs.getString("noteCategory");

                LocalDateTime dateTime = timestamp != null ?
                        timestamp.toLocalDateTime() : LocalDateTime.now();

                noteList.add(new Note(noteTitle, description, status, noteCategory, dateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error in getNotesByCategory");
        }
        return noteList;
    }












}
