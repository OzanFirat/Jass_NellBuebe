package Client;

import Client.Controller.*;
import Client.Model.ClientModel;
import Client.View.*;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    // methods for gameInstruction
    public void startPlayCardInfo(){
        gameView.showPlayingCards();
    }

    /**
    public void startTrumpRuleInfo(){
        gameView.showTrumpRule();
    }
    public void startMinorSuitInfo(){
        gameView.showMinorSuitRule();
    }
    public void startTopsDownInfo(){
        gameView.showTopsDownRule();
    }
    public void startBottomsUpInfo(){
        gameView.showBottomsUpRule();
    }
    **/



    // methods for gameInstructions
    public void showPlayingCards(){
        Alert rules = new Alert(Alert.AlertType.NONE,"You can choose between a deck of German playing cards or a deck of French playing cards.\n" +
                "\n" + "A deck of cards has 4 suits, each of which contains 9 playing cards. A total of 36 cards are used in the game. For every type of Jass game" +
                " played on Jass.ch 3 × 3 cards are dealt, i.e. each player receives 9 cards.\n" +
                "\n" + "The total value of all counters in the deck is 152 for Differenzler, Schieber and Coiffeur. Taking the last trick scores an additional 5 points, which means there are a total of 157 points to be won.");
        rules.setTitle("Playing Cards");
        Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Grundlagen_eng.jpg"));
        ImageView pCardView= new ImageView(pCards);
        pCardView.setFitHeight(400);
        pCardView.setFitWidth(450);
        rules.setGraphic(pCardView);
        rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
        rules.showAndWait();
    }

    public void showTrumpRule(){
        Alert rules = new Alert(Alert.AlertType.NONE,"Trump\n" +
                "\n" +
                "The suit declared trumps beats cards of all ranks in the minor suits. " +
                "The ranking within the trump suit as well as the point values are shown in " +
                "the adjoining table. A higher-ranked card beats a lower-ranked one.");
        rules.setTitle("Playing Cards");
        Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Trumpf_Rangfolge_Kartenwerte_eng.jpg"));
        ImageView pCardView= new ImageView(pCards);
        pCardView.setFitHeight(400);
        pCardView.setFitWidth(450);
        rules.setGraphic(pCardView);
        rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
        rules.showAndWait();
    }

    public void showMinorSuitRule(){
        Alert rules = new Alert(Alert.AlertType.NONE,"Minor suit\n" +
                "\n" +
                "The adjoining table shows the rankings within the minor suits as well" +
                " as the point values. A higher-ranked card beats a lower-ranked one.");
        rules.setTitle("Playing Cards");
        Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Nebenfarbe_Rangfolge_Kartenwerte_eng.jpg"));
        ImageView pCardView= new ImageView(pCards);
        pCardView.setFitHeight(400);
        pCardView.setFitWidth(450);
        rules.setGraphic(pCardView);
        rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
        rules.showAndWait();
    }

    public void showTopsDownRule(){
        Alert rules = new Alert(Alert.AlertType.NONE,"Tops-down\n" +
                "\n" +
                "There is no trump suit in tops-down. The adjoining table shows the ranking as well as the point values. In tops-down, Ace is highest. A higher-ranked card also beats a lower-ranked card in both tops-down and bottoms-up.");
        Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Obenabe_Rangfolge_Kartenwerte_eng.jpg"));
        ImageView pCardView= new ImageView(pCards);
        pCardView.setFitHeight(400);
        pCardView.setFitWidth(450);
        rules.setGraphic(pCardView);
        rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
        rules.showAndWait();
    }

    public void showBottomsUpRule(){
        Alert rules = new Alert(Alert.AlertType.NONE,"Bottoms-up\n" +
                "\n" +
                "There is no trump suit in bottoms-up. The adjoining table shows the ranking as well as the point values. In bottoms-up, Six is highest. A higher-ranked card also beats a lower-ranked card in both tops-down and bottoms-up.");
        Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Udenufe_Rangfolge_Kartenwerte_eng.jpg"));
        ImageView pCardView= new ImageView(pCards);
        pCardView.setFitHeight(400);
        pCardView.setFitWidth(450);
        rules.setGraphic(pCardView);
        rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
        rules.showAndWait();
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
