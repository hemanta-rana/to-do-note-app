package com.example.noteapplication;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Note {

    private String title;
    private  String description ;
    private LocalDateTime currentTime ;
    private String status ;
    private  String category;


    public Note(String title, String description, String status, String category){
        this.title = title;
        this.description = description;
        this.status = status;
        this.currentTime = LocalDateTime.now();
        this.category = category;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
     return description;
    }

    public String getStatus (){
        return status;
    }

    public LocalDateTime getCurrentTime(){
        return currentTime;
    }
    public String getCategory(){
        return category;
    }
    public String getFormattedTime() {

        return currentTime.format(DateTimeFormatter.ofPattern("hh:mma"));
    }

    public String getFormattedDate() {

        return currentTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }





}
