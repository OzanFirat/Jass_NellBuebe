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


    public LoginView(Stage stage, ClientModel model){
        this.stage = stage;
        this.model = model;

        ServiceLocator sl = ServiceLocator.getServiceLocator();

        // defined languages
        choiceBoxLanguageLoginView = new ChoiceBox<>();
        choiceBoxLanguageLoginView.setValue("DE");
        choiceBoxLanguageLoginView.getItems().add("EN");
        choiceBoxLanguageLoginView.getItems().add("DE");
        choiceBoxLanguageLoginView.setTranslateX(480);
        choiceBoxLanguageLoginView.setTranslateY(258);


        root = new Pane();


        choiceBoxLanguageLoginView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                updateLoginViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                updateLoginViewTexts();
            }
        });

        Scene scene = new Scene(root, 570, 331);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Jass by NellBuebe - Log In");
        createBackground();
        createLoginGrid();
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
        imvBackground.setFitWidth(570);
        imvBackground.setFitHeight(331);
        root.getChildren().add(imvBackground);
    }

    private void createLoginGrid() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        gridLogin = new GridPane();
        gridLogin.setHgap(20);
        gridLogin.setVgap(20);
        gridLogin.setPadding(new Insets(20));

        lblWelcome = new Label(t.getString("login.lbl.Welcome"));
        lblUserName = new Label(t.getString("login.lbl.UserName"));
        lblIpAddress = new Label(t.getString("login.lbl.IpAddress"));
        inputIpAdress.setText("127.0.0.1");
        btnLogIn.setText(t.getString("login.btn.Login"));
        btnLogIn.setTranslateX(0);

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

        gridLogin.setTranslateX(240);
        gridLogin.setTranslateY(100);


        root.getChildren().addAll(gridLogin, choiceBoxLanguageLoginView);
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
