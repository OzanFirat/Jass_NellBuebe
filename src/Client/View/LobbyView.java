package Client.View;

import Client.Model.ClientModel;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class LobbyView {
    private Stage stage;
    private ClientModel model;

    private Label lblTitle;

    private TextArea playersInLobby;

    private HBox containerButtons;
    private Button btnStart = new Button("Start Game");
    private Button btnExit = new Button("Exit");
    private Button btnChat = new Button("Chat");

    private Image background;
    private ImageView imvBackground;

    private ChoiceBox cbCardStyle;
    private Label lblCardStyle;


    private Pane root;

    public LobbyView(Stage lobbyStage, ClientModel model) {
        this.stage = lobbyStage;
        this.model = model;

        root = new Pane();
        createBackgroundImage();
        createButtons();
        createLobbyTextArea();
        createTitle();
        createChoiceBoxCardStyle();

        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        stage.setScene(scene);
    }

    public void start(){
        stage.show();
    }

    public void stop(){
        stage.hide();
    }

    private void createBackgroundImage() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));
        imvBackground = new ImageView(background);
        imvBackground.setFitHeight(400);
        imvBackground.setFitWidth(700);
        root.getChildren().add(imvBackground);
    }

    private void createButtons() {
        btnStart = new Button("Start Game");
        btnExit = new Button("Exit");
        btnChat = new Button("Chat");

        containerButtons = new HBox(50);
        containerButtons.getChildren().addAll(btnExit, btnChat, btnStart);
        containerButtons.setTranslateX(350);
        containerButtons.setTranslateY(300);
        root.getChildren().add(containerButtons);
    }

    private void createLobbyTextArea() {
        playersInLobby = new TextArea();
        playersInLobby.setEditable(false);
        playersInLobby.setTranslateX(350);
        playersInLobby.setTranslateY(160);
        playersInLobby.setMaxHeight(120);
        playersInLobby.setMaxWidth(250);
        root.getChildren().add(playersInLobby);
    }

    private void createTitle() {
        lblTitle = new Label("Lobby");
        lblTitle.setTranslateX(250);
        lblTitle.setTranslateY(120);
        lblTitle.setMinWidth(250);
        lblTitle.getStyleClass().add("login-text");
        root.getChildren().add(lblTitle);
    }

    private void createChoiceBoxCardStyle() {
        lblCardStyle = new Label("Choose the style of the cards");
        lblCardStyle.getStyleClass().add("login-text");
        cbCardStyle = new ChoiceBox<>(FXCollections.observableArrayList("DE", "FR"));
        lblCardStyle.setTranslateX(350);
        lblCardStyle.setTranslateY(80);
        cbCardStyle.setTranslateX(620);
        cbCardStyle.setTranslateY(80);

        root.getChildren().addAll(lblCardStyle, cbCardStyle);
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public Button getBtnChat() {
        return btnChat;
    }

    public TextArea getPlayersInLobby() {
        return playersInLobby;
    }

    public ChoiceBox getCbCardStyle() {
        return cbCardStyle;
    }

    public void setCbCardStyle(ChoiceBox cbCardStyle) {
        this.cbCardStyle = cbCardStyle;
    }
}
