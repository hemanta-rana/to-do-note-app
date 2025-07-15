package com.example.noteapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.atomic.AtomicReference;


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
        root.setLeft(sideBar);

//        create main content area

        ScrollPane mainContent = createMainContent();
        root.setCenter(mainContent);


        Scene scene = new Scene(root, 1400, 700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
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
        status.getStyleClass().add("sidebar-button");
        Button addButton = new Button("Add new Note ");
//        addButton action
        addButton.setOnAction(e-> showAddDialog());
        addButton.getStyleClass().add("sidebar-button");
//        addButton.setStyle("-fx-background-color: #2D5A27; -fx-text-fill: white; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold;");

        HBox leftSection = new HBox(10);
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.getChildren().addAll(circle,userName, new Label("  "), allNotes);

        HBox rightSection = new HBox(10);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.getChildren().addAll(status , addButton);

        header.getChildren().addAll(leftSection, spacer, rightSection );
        return header;
    }

    public VBox createSidebar() {
        VBox sideBar = new VBox(10);
        sideBar.setPadding(new Insets(20));
        sideBar.getStyleClass().add("sidebar");

        Label categoryLabel = new Label("Categories");
        categoryLabel.getStyleClass().add("sidebar-label");

        ListView<String> categories = new ListView<>();
        categories.getStyleClass().add("sidebar-listview");
        categories.getItems().addAll("All notes", "WishList", "Assignment", "Projects", "Work", "Study");

        Button addCategory = new Button("Add New Category ");
        addCategory.getStyleClass().add("sidebar-button");

        sideBar.getChildren().addAll(categoryLabel, categories, addCategory);
        return sideBar;
    }


    public ScrollPane createMainContent(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        notesContainer = new FlowPane();
        notesContainer.setHgap(20);
        notesContainer.setVgap(20);
        notesContainer.setPadding(new Insets(30));
        notesContainer.setStyle("_fx-background-color: #f5f5f5; ");

        scrollPane.setContent(notesContainer);
        return scrollPane;
    }
    public void showAddDialog(){
        showAddNoteDialog(null);
    }

    public void showAddNoteDialog(Note note){
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add New Note ");

        VBox dialogBox = new VBox(15);
        dialogBox.setPadding(new Insets(20));
        dialogBox.getStyleClass().add("dialog-box");

        TextField titleField = new TextField();
        titleField.setPromptText("Note title...");

//        category
        ComboBox< String > cateoryBox = new ComboBox<>();
        cateoryBox.getItems().addAll("WishList", "Assignment", "Projects", "Study", "Work");
        cateoryBox.getStyleClass().add("category-box");
        cateoryBox.setStyle("-fx-background-color: white; ");
        cateoryBox.getSelectionModel().selectFirst();
        AtomicReference<String> selectedCategory = new AtomicReference<>();
        cateoryBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldval, newVal) -> {
            System.out.println("userSelected"+newVal);
            selectedCategory.set(newVal);
        });



        // text area
        TextArea contentArea = new TextArea();
        contentArea.setPromptText("write note description ");

        HBox buttonBox  = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e-> {
                    Note newNote=   new Note(titleField.getText(),
                            contentArea.getText(),
                            "Incomplete",
                            selectedCategory.get()
                    );
                    notesContainer.getChildren().add(createNoteCard(newNote));
                    dialog.close();
        }

        );
        dialog.close();
        saveBtn.setPrefWidth(100);
        saveBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; ");

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefWidth(100);
        cancelBtn.setStyle("-fx-background-color: white; ");
        cancelBtn.setOnAction(e-> dialog.close());

        buttonBox.getChildren().addAll(cancelBtn, saveBtn);

        dialogBox.getChildren().addAll(
                new Label("Title"),titleField,
                new Label("Category :  "), cateoryBox,
                new Label("Note Description"), contentArea,buttonBox
        );
        Scene dialogScene = new Scene(dialogBox, 500,500);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public VBox createNoteCard (Note note){
        VBox card = new VBox(15);
        card.setPrefWidth(380);
        card.setPrefHeight(220);
        card.setPadding(new Insets(10));
        card.getStyleClass().add("note-card");

        // double click to edit the card
        card.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2 ){
                showEditNoteDialog(note);
            }
        });

        HBox header = new HBox(5);
        header.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(note.getTitle());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label categoryLabel = new Label(note.getCategory());

        categoryLabel.getStyleClass().add("categoryLabel-card");

       MenuButton statusButton = new MenuButton("Status");
       statusButton.getItems().addAll(
               new MenuItem("Completed"),
               new MenuItem("Incomplete")
       );
       statusButton.getStyleClass().add("status-button");

       Button deleteBtn = new Button(" x ");
       deleteBtn.setPrefWidth(50);
       deleteBtn.getStyleClass().add("note-deleteBtn");
       deleteBtn.setOnAction(e-> deleteNote(note));

       header.getChildren().addAll(titleLabel, spacer, categoryLabel, statusButton,deleteBtn);





        card.getChildren().addAll(header);
        return card;

    }
    public void showEditNoteDialog(Note note){}
    public void deleteNote(Note note){}

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args){
        launch(args);
    }
}