package Client;

import Client.Controller.*;
import Client.Model.ClientModel;
import Client.View.*;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;

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

        // ******** Login View MVC
        model = new ClientModel();
        loginView = new LoginView(primaryStage, model);
        loginController = new LoginController(model, loginView);
        log.info("Login View MVC erstellt");

        // ******* Lobby View MVC
        Stage lobbyStage = new Stage();
        lobbyView = new LobbyView(lobbyStage, model);
        lobbyController = new LobbyController(model, lobbyView);
        log.info("Lobby MVC erstellt");

        // ********* Chat View MVC
        Stage chatStage = new Stage();
        chatView = new ChatView(chatStage, model);
        chatController = new ChatController(model, chatView);
        log.info("Chat View MVC erstellt");

        // ********* Settings / Create Game View MVC
        Stage settingsStage = new Stage();
        settingsView = new SettingsView(settingsStage, model);
        settingsController = new SettingsController(model, settingsView);
        log.info("Settings View MVC erstellt");

        // ********* Game View MVC
        Stage gameStage = new Stage();
        gameView = new GameView(gameStage, model);
        gameController = new GameController(model, gameView);
        log.info("Game View MVC erstellt");


        // ********* GameOver View MVC
        Stage gameOverStage = new Stage();
        gameOverView = new GameOverView(gameOverStage, model);
        gameOverController = new GameOverController(model, gameOverView);
        log.info("Game Over View MVC erstellt");

        loginView.start();

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
