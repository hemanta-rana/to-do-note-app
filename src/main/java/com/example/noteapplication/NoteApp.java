package com.example.noteapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class NoteApp  extends Application {

    private ObservableList<Note> notes = FXCollections.observableArrayList();
    private FlowPane notesContainer;
    private VBox sideBar ;
    private HBox headBar;
    private BorderPane root ;
    private MenuBar status ;



    @Override
    public void start(Stage primaryStage) {
//        main layout
        root = new BorderPane();
        root.setStyle("-fx-background-color: white");

//        create header
        headBar = createHeader();
        root.setTop(headBar);

//        create sider bar
        sideBar = createSidebar();
        root.setRight(sideBar);

//        create main content area

        ScrollPane mainContent = createMainContent();
        root.setCenter(mainContent);


        Scene scene = new Scene(root, 1400, 700);
//        scene.getStylesheets().add(getClass().getResource("com/example/noteapplication/styles.css").toExternalForm());
        primaryStage.setTitle("Note App");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle(
                "-fx-background-color: white; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 14, 0, 3, 23);"
        );

        Circle circle = new Circle(20);
        circle.setFill(Color.BLACK);

        Label userName = new Label("Admish Rana");
        userName.setFont(Font.font("System", 14));
        userName.setStyle("-fx-text-fill: black");

        Label allNotes = new Label("All Notes");
        allNotes.setFont(Font.font("System", FontWeight.BOLD, 24));
        allNotes.setStyle("-fx-text-fill: black");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        status = new MenuBar();
        Menu statusMenu = new Menu("Status  ");
        MenuItem incomplete = new MenuItem("Incomplete");
        MenuItem complete = new MenuItem("Completed");
        statusMenu.getItems().addAll(incomplete, complete);
        status.getMenus().add(statusMenu);
//        status.setStyle(
//                "-fx-background-color: #e0ded7; " +
//                        "-fx-font-size: 16px; " +
//                        "-fx-background-radius: 50;" +
//                        "-fx-border-radius: 10;"
//        );
        status.setStyle("-fx-background-color: Green;" +
                " -fx-text-fill: white;" +
                " -fx-border-radius: 8;" +
                " -fx-background-radius: 8;" +
                " -fx-font-weight: bold;");

        Button addButton = new Button("Add new Note ");
//        addButton action
        addButton.setOnAction(e-> showAddDialog());
        addButton.setStyle("-fx-background-color: #2D5A27; -fx-text-fill: white; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold;");

        HBox leftSection = new HBox(10);
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.getChildren().addAll(circle,userName, new Label("  "), allNotes);

        HBox rightSection = new HBox(10);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.getChildren().addAll(status , addButton);

        header.getChildren().addAll(leftSection, spacer, rightSection );
        return header;
    }

    public VBox createSidebar(){
        VBox vBox = new VBox();
        return vBox;

    }

    public ScrollPane createMainContent(){
        ScrollPane scrollPane = new ScrollPane();
        return scrollPane;
    }
    public void showAddDialog(){}


    public static void main(String[] args){
        launch(args);
    }
}