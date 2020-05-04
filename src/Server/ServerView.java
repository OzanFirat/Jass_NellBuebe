package Server;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

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

    private String externIP;
    private String internIP;

    public ServerView(Stage stage, ServerModel model) {
        this.stage = stage;

        /**
         try{
         this.externIP = InetAddress.getLocalHost().toString().substring(18);
         this.internIP = InetAddress.getLoopbackAddress().toString().substring(10);
         } catch (UnknownHostException e) {
         e.printStackTrace();
         }

         **/
        Logger logger = ServerMain.sMain.getLogger();


        // initializing the menuBar
        MenuBar mBar = new MenuBar();
        serverMenu = new Menu("File");
        languageMenu = new Menu("Language");
        helpMenu = new Menu("Help");

        // Inititalizing MenuItems
        DEItem = new MenuItem("DE");
        ENGItem = new MenuItem("ENG");

        serverMenu.getItems().addAll();
        languageMenu.getItems().addAll(DEItem,ENGItem);
        mBar.getMenus().addAll(serverMenu,languageMenu,helpMenu);

        // initializing all GUI elements
        lblJassGame = new Label("JASS_GAME-SERVER");
        serverIP = new Label("Server-IP: ");
        startBtn = new Button("star Server");

        GridPane gp = new GridPane();
        gp.add(lblJassGame, 0, 0);
        gp.add(serverIP, 0, 1);
        gp.add(startBtn,0,2);

        BorderPane bp = new BorderPane();
        bp.setTop(mBar);
        bp.setCenter(gp);

        // set up the scene
        Scene scene = new Scene(bp);

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