package Server;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class ServerView {

    private Stage stage;
    private ServerModel model;

    //Labels
    Label serverIP;
    Label lblJassGame;

    // Buttons
    Button startBtn;

    // Menus
    Menu serverMenu;
    Menu languageMenu;
    Menu helpMenu;

    //MenuItems
    MenuItem DEItem;
    MenuItem ENGItem;

    //Define the image for the background in serverView
    private Image tafel = new Image(getClass().getClassLoader().getResourceAsStream("images/tafel.jpg"));


    public ServerView(Stage stage, ServerModel model) {
        this.stage = stage;

        // initializing the menuBar
        MenuBar mBar = new MenuBar();
        serverMenu = new Menu("File");
        languageMenu = new Menu("Language");
        helpMenu = new Menu("Help");

        // Inititalizing MenuItems
        DEItem = new MenuItem("DE");
        ENGItem = new MenuItem("ENG");

        serverMenu.getItems().addAll();
        languageMenu.getItems().addAll(DEItem, ENGItem);
        mBar.getMenus().addAll(serverMenu, languageMenu, helpMenu);

        // initializing all GUI elements
        lblJassGame = new Label("JASS GAME SERVER");
        lblJassGame.setFont(Font.font("Comic Sans MS", 20));
        lblJassGame.getStyleClass().add("server-text");

        serverIP = new Label("Server-IP: 127.0.0.1");
        serverIP.setFont(Font.font("Comic Sans MS", 20));
        serverIP.getStyleClass().add("server-text");

        startBtn = new Button("start Server");
        startBtn.setFont(Font.font("Comic Sans MS", 15));


        GridPane gp = new GridPane();

        // To align horizontally and vertically in the cell
        GridPane.setHalignment(lblJassGame, HPos.CENTER);
        GridPane.setValignment(lblJassGame, VPos.CENTER);

        GridPane.setHalignment(serverIP, HPos.CENTER);
        GridPane.setValignment(serverIP, VPos.CENTER);

        GridPane.setHalignment(startBtn, HPos.CENTER);
        GridPane.setValignment(startBtn, VPos.CENTER);

        gp.add(lblJassGame, 0, 0);
        gp.add(serverIP, 0, 1);
        gp.add(startBtn, 0, 2);
        gp.setBackground(new Background(new BackgroundImage(tafel, null, null, null, null)));

        // set up the scene
        Scene scene = new Scene(gp);
        scene.getStylesheets().add(getClass().getResource("server.css").toExternalForm());

        // set up the stage
        stage.setTitle("NellBuebe-Server");
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(300);

    }

    public void start(){
        stage.show();
    }

    public void stop(){
        stage.hide();
    }
    public Stage getStage() {
        return stage;
    }
}