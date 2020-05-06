package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.CardLabel;
import Client.View.GameView;
import Common.Message;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.logging.Logger;

public class GameController {
    private ClientModel model;
    private GameView gameView;
    private Logger log;
    CardLabel card;
    public ClientCommunication clientCommunication = ClientCommunication.getInstance();


    public GameController(ClientModel model, GameView gameView) {
        this.model = model;
        this.gameView = gameView;
        log = JassClient.mainProgram.getLogger();


        // method for calling chatroom from gameView
        gameView.btnChatGame.setOnAction( e-> {
            JassClient.mainProgram.startChat();
        });
    }

    public void initializeElements(){
        updateYourCards();
        updateOpponentLabels();
        updateTrumpfElements();
        gameView.createGameHistory();
        gameView.createOverlayNotYourTurn();
        gameView.createTableViewScore();
    }

    public void initializeGame(){
        Platform.runLater(new Runnable() {
            public void run() {
                initializeElements();
                JassClient.mainProgram.stopLobby();
                JassClient.mainProgram.startGame();
            }
        });
    }

    public void updateTrumpfElements(){
        gameView.createTrumpfElements();
    }

    public void updateYourCards() {
        gameView.createYourCards();
    }

    public void updateOpponentLabels() {
        gameView.createPlayer2();
        gameView.createPlayer3();
        gameView.createPlayer4();
        gameView.addToRootJassGame(gameView.lblWinner2);
        gameView.addToRootJassGame(gameView.lblWinner3);
        gameView.addToRootJassGame(gameView.lblWinner4);
    }

    public void handleCardAction(){
        Platform.runLater( new Runnable() {
            @Override
            public void run() {
                for (CardLabel c : gameView.yourCards) {

                    c.setOnMouseEntered ( e -> {
                        c.getStyleClass().add("card-label-hover");
                        c.setTranslateY(gameView.yStartingPoint-10);
                    });


                    c.setOnMouseExited(e -> {
                        c.getStyleClass().clear();
                        c.setTranslateY(gameView.yStartingPoint);
                    });

                    c.setOnMouseClicked( e -> {
                        gameView.doAnimationPlayYourCard(c);
                        c.setOnMouseClicked(null);
                        c.setOnMouseEntered(null);
                        c.setOnMouseExited(null);
                        c.getStyleClass().clear();
                        clientCommunication.sendMessage(new Message (Message.Type.CARDPLAYED, c.getCardName()));
                    });

                }
            }
        });
    }

    public void changeToGameOverScene(String winnerName) {
        Platform.runLater( new Runnable() {

            @Override
            public void run() {
                JassClient.mainProgram.getGameOverView().createTableView();
                JassClient.mainProgram.getGameOverView().setWinner(winnerName);
                JassClient.mainProgram.getGameView().stop();
                JassClient.mainProgram.startGameOver();
            }
        });
    }

    public void updateGameHistory(String playerName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameView.gameHistory.appendText("Game-Info: "+playerName+" has won the round\n");
            }
        });
    }
}

