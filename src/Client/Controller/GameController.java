package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.CardLabel;
import Client.View.GameView;
import Client.View.TrumpfLabel;
import Common.Messages.Message;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import java.util.Optional;
import java.util.logging.Logger;

public class GameController {
    private ClientModel model;
    private GameView gameView;
    private Logger log;

    public ClientCommunication clientCommunication = ClientCommunication.getInstance();


    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator t = sl.getTranslator();

    boolean firstRound = true;


    public GameController(ClientModel model, GameView gameView) {
        this.model = model;
        this.gameView = gameView;
        log = JassClient.mainProgram.getLogger();

        // event for exiting game
        gameView.getGameStage().setOnCloseRequest(e -> {
            e.consume();
            showConfirmationExitGame();
        });


        // eventHandling for starting chatRoom
        gameView.btnChatGame.setOnAction( e-> {
            JassClient.mainProgram.startChat();
        });

        // eventHandling for all gameInstructions
        gameView.btngameCards.setOnAction(e->{
            gameView.showPlayingCards();
        });

        gameView.btnTrump.setOnAction(e->{
            gameView.showTrumpRule();
        });
        gameView.btnMinorSuit.setOnAction(e->{
            gameView.showMinorSuitRule();
        });

        gameView.btnTopsDown.setOnAction(e->{
            gameView.showTopsDownRule();
        });
        gameView.btnBottomsUp.setOnAction(e->{
            gameView.showBottomsUpRule();
        });
    }

    public void initializeElements(){
        updateOpponentLabels();
        gameView.createGameHistory();
        gameView.createOverlayNotYourTurn();
        gameView.showOverlayNotYourTurn();
        gameView.createTableViewScore();
        gameView.setStageTitle(model.getUserName());
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
        Platform.runLater(new Runnable() {
            public void run() {
                if (firstRound) {
                    gameView.createTrumpfElements();
                    firstRound = false;
                } else {
                    gameView.removeFromRootJassGame(gameView.getvBoxTrumpf());
                    gameView.createTrumpfElements();
                }
            }
        });
    }

    public void updateYourCards() {
        Platform.runLater(new Runnable() {
            public void run() {
                gameView.createYourCards();
            }
        });
    }

    public void updateOpponentLabels() {
        gameView.createPlayer2();
        gameView.createPlayer3();
        gameView.createPlayer4();
    }

    public void handleTrumpfChoiceAction() {
        Platform.runLater(new Runnable() {
            public void run() {
                for (TrumpfLabel lbl : gameView.getListTrumpfLabels()) {
                    lbl.setOnMouseEntered( e -> {
                        lbl.getStyleClass().add("trumpf-labels");
                    });

                    lbl.setOnMouseExited(e -> {
                        lbl.getStyleClass().clear();
                    });

                    lbl.setOnMouseClicked( e -> {
                        if (lbl.isTrumpf()) {
                            clientCommunication.sendMessage(new Message(Message.Type.TRUMPF, lbl.getName(), "Trumpf"));
                        } else {
                            clientCommunication.sendMessage(new Message(Message.Type.TRUMPF, null, lbl.getName()));
                        }
                        gameView.removeFromRootJassGame(gameView.getBoxTrumpfChoice());
                    });
                }
            }
        });
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
                        gameView.doPlayYourCard(c);
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
                JassClient.mainProgram.getGameOverView().setGameOverStageTitle();
                JassClient.mainProgram.getGameView().stop();
                JassClient.mainProgram.startGameOver();
            }
        });
    }

    public void updateGameHistory(String playerName) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameView.gameHistory.appendText(t.getString("game.gameHistory.info1")+ playerName+ " "+(t.getString("game.gameHistory.info2")));
            }
        });
    }

    // This method gives Alert to the player who leaves the game
    public void showConfirmationExitGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Leave Game");
                alert.setContentText("Are you sure u want to exit, game can't  be proceeded anymore");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    clientCommunication.sendMessage(new Message(Message.Type.EXITGAME,model.getUserName()+"has left the game"));
                    JassClient.mainProgram.getGameView().stop();
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
    }

    //This method informs every player that one player has left the game, and that the game cannot be proceeded anymore
    public void showInformationExitGame() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Player left game!");
                alert.setContentText("The game cannot be proceeded anymore");
                alert.showAndWait();
                if(alert.getResult() ==  ButtonType.OK){
                    clientCommunication.sendMessage(new Message(Message.Type.EXITGAME,model.getUserName()+"has left the game"));
                    JassClient.mainProgram.getGameView().stop();
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
    }


}

