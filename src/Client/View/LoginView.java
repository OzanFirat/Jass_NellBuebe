package Client.View;

import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    private ClientModel model;

    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator t = sl.getTranslator();

    protected GridPane gridLogin;
    private Pane root;

    public Label lblWelcome = new Label();
    public Label lblUserName = new Label();
    public Label lblIpAddress = new Label();
    public TextField inputUserName = new TextField();
    public Label inputIpAdress = new Label();
    public Button btnLogIn = new Button();


    private Image background;
    private ImageView imvBackground;

    //Elements to display the languageSetting
    private ChoiceBox<String> choiceBoxLanguageLoginView;

    private int sceneWidth = 600;
    private int sceneHeight = 330;


    public LoginView(Stage stage, ClientModel model){
        this.stage = stage;
        this.model = model;

        root = new Pane();
        createChoiceBoxLanguage();
        createBackground();
        createLoginGrid();

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Jass by NellBuebe - Log In");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png")));

    }

    public void start(){
        stage.show();
    }

    public void stop(){
        stage.hide();
    }

    private void createBackground(){
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));
        imvBackground = new ImageView(background);
        imvBackground.setFitWidth(sceneWidth);
        imvBackground.setFitHeight(sceneHeight);
        root.getChildren().add(imvBackground);
    }

    private void createChoiceBoxLanguage() {
        // defined languages
        choiceBoxLanguageLoginView = new ChoiceBox<>();
        choiceBoxLanguageLoginView.getStyleClass().add("choiceBox");
        choiceBoxLanguageLoginView.getItems().add("EN");
        choiceBoxLanguageLoginView.getItems().add("DE");

        choiceBoxLanguageLoginView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                t = sl.getTranslator();
                updateLoginViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                t = sl.getTranslator();
                updateLoginViewTexts();
            }
        });
    }

    private void createLoginGrid() {
        gridLogin = new GridPane();
        gridLogin.setHgap(20);
        gridLogin.setVgap(20);
        gridLogin.setPadding(new Insets(20));

        lblWelcome = new Label(t.getString("login.lbl.Welcome"));
        lblUserName = new Label(t.getString("login.lbl.UserName"));
        lblIpAddress = new Label(t.getString("login.lbl.IpAddress"));
        lblWelcome.setMinWidth(300);
        lblWelcome.setAlignment(Pos.CENTER_LEFT);
        inputIpAdress.setText("127.0.0.1");
        btnLogIn.setText(t.getString("login.btn.Login"));
        btnLogIn.setAlignment(Pos.CENTER_RIGHT);
        btnLogIn.getStyleClass().add("basic-button");

        lblWelcome.getStyleClass().add("login-text");
        lblUserName.getStyleClass().add("login-text");
        lblIpAddress.getStyleClass().add("login-text");
        inputIpAdress.getStyleClass().add("login-text");


        gridLogin.add(lblWelcome, 0, 0, 2, 1);
        gridLogin.add(lblUserName, 0, 1);
        gridLogin.add(lblIpAddress, 0, 2);
        gridLogin.add(inputUserName, 1, 1);
        gridLogin.add(inputIpAdress, 1, 2);
        gridLogin.add(btnLogIn, 1, 3);
        gridLogin.add(choiceBoxLanguageLoginView, 0, 3);

        gridLogin.setTranslateX(240);
        gridLogin.setTranslateY(100);


        root.getChildren().addAll(gridLogin);
    }
    protected void updateLoginViewTexts() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        // Menus
        lblWelcome.setText(t.getString("login.lbl.Welcome"));
        lblUserName.setText(t.getString("login.lbl.UserName"));
        lblIpAddress.setText(t.getString("login.lbl.IpAddress"));
        btnLogIn.setText(t.getString("login.btn.Login"));

    }
}
