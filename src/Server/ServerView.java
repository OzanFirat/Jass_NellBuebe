package Server;

import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.logging.Logger;


public class ServerView {

    private Stage stage;
    private ServerModel model;

    //Labels
    private Label lblServerIP;
    private Label lblJassGame;

    // Buttons
    Button btnStartServer;


    // MenuBar
    MenuBar mBar;

    // Menus
    Menu languageMenu;
    Menu helpMenu;

    // MenuItem
    MenuItem jassHelp;



    //Define the image for the background in serverView
    private Image tafel = new Image(getClass().getClassLoader().getResourceAsStream("images/tafel.jpg"));


    public ServerView(Stage stage, ServerModel model) {
        this.stage = stage;

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();
        Logger log = sl.getLogger();

        // initializing the menuBar
        mBar = new MenuBar();
        languageMenu = new Menu(t.getString("server.menu.language"));
        helpMenu = new Menu(t.getString("server.menu.help"));

        jassHelp = new MenuItem("Game Set Up");


        mBar.getMenus().addAll(languageMenu, helpMenu);
        helpMenu.getItems().add(jassHelp);


        // initializing all GUI elements
        lblJassGame = new Label(t.getString("server.label.JassGameServer"));
        lblJassGame.setFont(Font.font("Comic Sans MS", 20));
        lblJassGame.getStyleClass().add("server-text");

        lblServerIP = new Label(t.getString("server.label.serverIP"));
        lblServerIP.setFont(Font.font("Comic Sans MS", 20));
        lblServerIP.getStyleClass().add("server-text");

        btnStartServer = new Button(t.getString("server.button.startServer"));


        btnStartServer.setFont(Font.font("Comic Sans MS", 15));


        GridPane gp = new GridPane();
        gp.setVgap(10);

        // To align horizontally and vertically in the cell
        GridPane.setHalignment(lblJassGame, HPos.CENTER);
        GridPane.setValignment(lblJassGame, VPos.CENTER);

        GridPane.setHalignment(lblServerIP, HPos.CENTER);
        GridPane.setValignment(lblServerIP, VPos.CENTER);

        GridPane.setHalignment(btnStartServer, HPos.CENTER);
        GridPane.setValignment(btnStartServer, VPos.CENTER);


        gp.add(lblJassGame, 0, 0);
        gp.add(lblServerIP, 0, 1);
        gp.add(btnStartServer, 0, 2);

        HBox hb = new HBox(gp);
        hb.setTranslateX(50);
        hb.setTranslateY(120);

        BorderPane bp = new BorderPane(hb,mBar, null, null,null);


        bp.setBackground(new Background(new BackgroundImage(tafel, null, null, null, null)));



        // generating languageSettings with serviceLocator for serverView
        for (Locale loc : sl.getLocales()) {
            MenuItem language = new MenuItem(loc.getLanguage());
            languageMenu.getItems().add(language);
            language.setOnAction(event -> {
                sl.getConfiguration().setLocalOption("Language", loc.getLanguage());
                sl.setTranslator(new Translator(loc.getLanguage()));
                updateServerViewTexts();
            });
        }


        // set up the scene
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getResource("server.css").toExternalForm());

        // set up the stage
        stage.setTitle("NellBuebe-Server");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(300);

    }

    protected void updateServerViewTexts(){
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        // Menus
        languageMenu.setText(t.getString("server.menu.language"));
        helpMenu.setText(t.getString("server.menu.help"));

        //Labels and Buttons
        lblJassGame.setText(t.getString("server.label.JassGameServer"));
        lblServerIP.setText(t.getString("server.label.serverIP"));
        if(btnStartServer.isDisabled()){
            btnStartServer.setText(t.getString("server.button.runningServer"));
        }else{
            btnStartServer.setText(t.getString("server.button.startServer"));
        }
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

    public void showAlertHelp(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Help");
                alert.setHeaderText("Jass by Nellbuebe");
                alert.setContentText("To proceed the game u have to start the server first than all 4 clients.");
                alert.showAndWait();
            }
        });
    }
}