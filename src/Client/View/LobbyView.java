package Client.View;

import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class LobbyView {
    private Stage stage;
    private ClientModel model;

    ServiceLocator sl;
    Translator t;

    private Label lblTitle;

    private TextArea playersInLobby;

    private HBox containerButtons;
    private Button btnStart = new Button("Start Game");
    private Button btnSettings = new Button("Exit");
    private Button btnChat = new Button("Chat");

    private Image background;
    private ImageView imvBackground;

    private Pane root;

    //Elements to display the languageSetting
    public ChoiceBox<String> choiceBoxLanguageLobbyView;

    public LobbyView(Stage lobbyStage, ClientModel model) {
        this.stage = lobbyStage;
        this.model = model;

        sl = ServiceLocator.getServiceLocator();
        t = sl.getTranslator();

        root = new Pane();
        createBackgroundImage();
        createButtons();
        createLobbyTextArea();
        createTitle();
        createCBLanguage();


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

    public void setStageTitle() {
        stage.setTitle("Lobby - " +model.getUserName());
    }

    private void createCBLanguage() {
        // defined languages
        choiceBoxLanguageLobbyView = new ChoiceBox<>();
        choiceBoxLanguageLobbyView.setValue("DE");
        choiceBoxLanguageLobbyView.getItems().add("EN");
        choiceBoxLanguageLobbyView.getItems().add("DE");
        choiceBoxLanguageLobbyView.setTranslateX(580);
        choiceBoxLanguageLobbyView.setTranslateY(120);

        choiceBoxLanguageLobbyView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                updateLobbyViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                updateLobbyViewTexts();
            }
        });
        root.getChildren().add(choiceBoxLanguageLobbyView);
    }

    private void createBackgroundImage() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));
        imvBackground = new ImageView(background);
        imvBackground.setFitHeight(400);
        imvBackground.setFitWidth(700);
        root.getChildren().addAll(imvBackground);
    }

    private void createButtons() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        btnStart = new Button(t.getString("lobby.btn.Start"));
        btnSettings = new Button(t.getString("lobby.btn.Settings"));
        btnChat = new Button("Chat");

        containerButtons = new HBox(50);
        containerButtons.getChildren().addAll(btnSettings, btnChat, btnStart);
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
        playersInLobby.setMaxWidth(280);
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

    protected void updateLobbyViewTexts() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        btnStart.setText(t.getString("lobby.btn.Start"));
        btnSettings.setText(t.getString("lobby.btn.Settings"));
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public Button getBtnSettings() {
        return btnSettings;
    }

    public Button getBtnChat() {
        return btnChat;
    }

    public TextArea getPlayersInLobby() {
        return playersInLobby;
    }




}
