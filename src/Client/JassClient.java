package Client;

import Client.Controller.*;
import Client.Model.ClientModel;
import Client.View.*;
import Common.Configuration;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Logger;

public class JassClient extends Application {

    public static JassClient mainProgram; // Singleton to start the different views
    private Logger log = Logger.getLogger("");

    private LoginView loginView;
    private LobbyView lobbyView;
    private ChatView chatView;
    private SettingsView settingsView;
    private GameView gameView;
    private GameOverView gameOverView;

    private LoginController loginController;
    private LobbyController lobbyController;
    private ChatController chatController;
    private SettingsController settingsController;
    private GameController gameController;
    private GameOverController gameOverController;

    private ClientModel model;
    private ServiceLocator sl;

    public static void main(String[] args) {
        launch(args);
    }

    public void init(){
        if (mainProgram == null) {
            mainProgram = this;
        } else {
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // serviceLocator to hold our resources
        sl = ServiceLocator.getServiceLocator();
        sl.setConfiguration(new Configuration());
        String language  = sl.getConfiguration().getOption("language");
        sl.setTranslator(new Translator(language));

        // ******** Login View MVC
        model = new ClientModel();
        loginView = new LoginView(primaryStage, model);
        loginController = new LoginController(model, loginView);

        // ******* Lobby View MVC
        Stage lobbyStage = new Stage();
        lobbyView = new LobbyView(lobbyStage, model);
        lobbyController = new LobbyController(model, lobbyView);

        // ********* Chat View MVC
        Stage chatStage = new Stage();
        chatView = new ChatView(chatStage, model);
        chatController = new ChatController(model, chatView);

        // ********* Settings / Create Game View MVC
        Stage settingsStage = new Stage();
        settingsView = new SettingsView(settingsStage, model);
        settingsController = new SettingsController(model, settingsView);

        // ********* Game View MVC
        Stage gameStage = new Stage();
        gameView = new GameView(gameStage, model);
        gameController = new GameController(model, gameView);

        // ********* GameOver View MVC
        Stage gameOverStage = new Stage();
        gameOverView = new GameOverView(gameOverStage, model);
        gameOverController = new GameOverController(model, gameOverView);
        log.info("Client successfully started");

        loginView.start();

    }
    public void resetClientModel() {
        model.setPlayerNames(new ArrayList<>());
        model.setOppPlayerNames(new ArrayList<>());
        model.setYourCards(new ArrayList<>());
        model.setPlayerWithPoints(FXCollections.observableArrayList());
    }


    public void startLogin() {
        loginView.start();
    }

    public void stopLogin(){
        loginView.stop();
    }

    public void startLobby(){
        lobbyView.start();
    }

    public void stopLobby(){
        lobbyView.stop();
    }

    public void startSettings(){
        settingsView.start();
    }

    public void stopSettings(){
        settingsView.stop();
    }

    public void startGame(){
        gameView.start();
    }

    public void startGameOver() {
        gameOverView.start();
    }

    public void stopGameOver() {
        gameOverView.stop();
    }

    // methods for the chatRoom
    public void startChat(){
        chatView.start();
    }

    public void stopChat() {
        if(chatView != null) {
            chatView.stop();
        }
    }

    public Logger getLogger(){
        return log;
    }

    public ClientModel getClientModel(){
        return model;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameView getGameView() {
        return gameView;
    }

    public GameOverView getGameOverView() {
        return gameOverView;
    }

    public void setGameOverView(GameOverView gameOverView) {
        this.gameOverView = gameOverView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public ChatView getChatView() {
        return chatView;
    }

    public SettingsView getSettingsView() {
        return settingsView;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public GameOverController getGameOverController() {
        return gameOverController;
    }

    public ChatController getChatcontroller() {
        return chatController;
    }
}
