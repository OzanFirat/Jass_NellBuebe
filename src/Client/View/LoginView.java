package Client.View;

import Client.Model.ClientModel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    protected Pane root;

    public Label lblWelcome = new Label();
    public Label lblUserName = new Label();
    public Label lblIpAddress = new Label();
    public TextField inputUserName = new TextField();
    public Label inputIpAdress = new Label();
    public Button btnLogIn = new Button();


    private Image background;
    private ImageView imvBackground;




    public LoginView(Stage stage, ClientModel model){
        this.stage = stage;
        this.model = model;

        root = new Pane();
        Scene scene = new Scene(root, 550, 311);
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
        imvBackground.setFitWidth(550);
        imvBackground.setFitHeight(311);
        root.getChildren().add(imvBackground);
    }

    private void createLoginGrid() {
        gridLogin = new GridPane();
        gridLogin.setHgap(20);
        gridLogin.setVgap(20);
        gridLogin.setPadding(new Insets(20));

        lblWelcome = new Label("Welcome to Jass by Nellbuebe");
        lblUserName = new Label("Username");
        lblIpAddress = new Label("IP-Address");
        inputIpAdress.setText("127.0.0.1");
        btnLogIn.setText("Log In");
        btnLogIn.setTranslateX(100);

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
        root.getChildren().add(gridLogin);
    }

    public void loginRejected() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Rejected");
                alert.setHeaderText("This Username is already taken");
                alert.setContentText("Try again with a different username");
                alert.showAndWait();
            }
        });
    }
}
