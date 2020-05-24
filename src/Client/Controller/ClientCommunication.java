package Client.Controller;


import Client.JassClient;
import Client.Model.ClientModel;
import Client.Model.PlayerScoreTuple;
import Common.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;


public class ClientCommunication {


    private Logger logger = JassClient.mainProgram.getLogger();

    //  declaration of ClientSideConnection
    private static ClientCommunication instance;

    // declaring all socket elements
    private Socket socket;

    private ClientModel clientModel = JassClient.mainProgram.getClientModel();

    //127.0.0.1 or localhost
    private String server = "127.0.0.1";
    // portNumber
    private int portNr = 55555;

    // I/O elements
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String userName;
    private ArrayList<String> playerNames = new ArrayList<>(3);

    private boolean gameViewLaunch = true;

    public void setServer(String server) {
        this.server = server;
    }

    public static synchronized ClientCommunication getInstance() {
        if (ClientCommunication.instance == null) {
            ClientCommunication.instance = new ClientCommunication();
        }
        return ClientCommunication.instance;
    }

    // The method used to send a message to the server
    void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            out.reset();
        } catch (IOException e) {
            logger.info("Exception writing to server: " + e);
        }
    }

    // Close the socket and I/O stream in case something goes wrong
    private void disconnect() {
        try {
            if (in != null) in.close();
        } catch (Exception e) {
        }
        try {
            if (out != null) out.close();
        } catch (Exception e) {
        }
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
        }
    }


    public boolean start() {
        try {
            socket = new Socket(server, portNr);
            logger.info(socket.toString() + "starting the socket...");
            logger.info("the socket has successfully started");

            // if connection to server failed
        } catch (Exception e) {
            logger.info("Connection to Server has failed:" + e);
            JassClient.mainProgram.getLoginController().showAlertConnectionFailed();
            return false;
        }

        String success = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        logger.info(success);


        // creating input- and outputStreams
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            logger.info("I/O streams successfully created");
        } catch (IOException io) {
            logger.info("I/O Exception: " + io);
            return false;
        }


        // creates the Thread to listen from the server
        new ListenFromServer().start();
        logger.info("ServerListener successfully started");


        // success we inform the caller that it worked
        return true;

    }


    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isGameViewLaunch() {
        return gameViewLaunch;
    }

    public void setGameViewLaunch(boolean gameViewLaunch) {
        this.gameViewLaunch = gameViewLaunch;
    }

    /*
     * a class that waits for the message from the server
     */
    class ListenFromServer extends Thread {
        volatile Message receivedMessage;
        int gameCounter = 0;

        public void run() {
            while (true) {
                try {
                    try {
                        receivedMessage = (Message) in.readObject();
                    } catch (IOException e) {
                        logger.info(userName + " Exception reading Streams: " + e);
                        break;
                    }
                    switch (receivedMessage.getType()) {
                        case CHATMESSAGE:
                            JassClient.mainProgram.getChatcontroller().updateChatView(receivedMessage.getMessage());
                            break;
                        case LOGINREJECTED:
                            disconnect();
                            logger.info("Login rejected");
                            JassClient.mainProgram.getLoginController().showAlertLoginRejectedSameNameUsed();
                            break;
                        case LOGINREJECTEDTOOMANYPLAYERS:
                            disconnect();
                            logger.info("Login rejected");
                            JassClient.mainProgram.getLoginController().showAlertLoginRejectedTooManyPlayers();
                            break;
                        case EXITGAME:
                            JassClient.mainProgram.getGameController().showInformationExitGame();
                            logger.info("Game cannot be continued, because one player has left the game");
                            break;
                        case LOGINACCEPTED:
                            clientModel.setUserName(receivedMessage.getUserName());
                            JassClient.mainProgram.getLoginController().loginAccepted();
                            logger.info("Login accepted");
                            break;
                        case WHOISIN:
                            clientModel.setPlayerNames(receivedMessage.getPlayerNames());
                            JassClient.mainProgram.getLobbyController().updateLobby();
                            JassClient.mainProgram.getChatcontroller().updateChatEntry();
                            break;

                        case STARTGAMEREJECTED:
                            int playerCount = receivedMessage.getPlayerCount();
                            logger.info("Start of game rejected, number of Players: " + playerCount);
                            if(playerCount <= 3)
                                JassClient.mainProgram.getLobbyController().showAlertLessPlayer();
                            break;

                        case STARTGAME:
                            // initialize the game
                            if (gameViewLaunch) {
                                gameCounter = 0;
                                gameViewLaunch = false;
                            }

                            if (gameCounter == 0) {
                                clientModel.fillOppPlayerList();
                                // fill the ObservableList to show the updated scores during the game
                                clientModel.fillPlayerWithPoints();
                                JassClient.mainProgram.getGameController().initializeGame(receivedMessage.getMaxPoints());
                                JassClient.mainProgram.getSettingsView().stop();
                            }

                            if (clientModel.getUserName().equals(receivedMessage.getPlayerName())) {
                                JassClient.mainProgram.getGameView().createTrumpfChoice();
                                JassClient.mainProgram.getGameController().handleTrumpfChoiceAction();
                            }
                            gameCounter++;
                            break;

                        case TRUMPF:
                            if (receivedMessage.getTrumpf() != null) {
                                clientModel.setTrumpf(receivedMessage.getTrumpf());
                            } else {
                                clientModel.setTrumpf(receivedMessage.getGameType());
                            }
                            // update the trumpf elements to display the trumpf for the game
                            JassClient.mainProgram.getGameController().updateTrumpfElements();
                            break;

                        case DEALCARDS:
                            // Set your cards and put the opponents in the right order to display in view
                            if (clientModel.getUserName().equals(receivedMessage.getPlayerName())) {
                                JassClient.mainProgram.getSettingsController().setCardStyle();
                                clientModel.setYourCards(receivedMessage.getArrayList());
                                logger.info("The game has launched");
                                JassClient.mainProgram.getGameController().updateYourCards();
                            }
                            break;

                        case YOURTURN:
                            if (receivedMessage.getPlayerName().equals(clientModel.getUserName())) {
                                // if it's your turn, remove the overlay and set the cards on action
                                // if it's your turn, remove the overlay and set the cards on action
                                if (receivedMessage.getIllegalCards() != null) {
                                    clientModel.createIndexIllegalCards(receivedMessage.getIllegalCards());
                                }
                                JassClient.mainProgram.getGameView().hideOverlayNotYourTurn();
                                JassClient.mainProgram.getGameController().handleCardAction();
                            } else {
                                // Create the overlay if it's not your turn
                                JassClient.mainProgram.getGameView().showOverlayNotYourTurn();
                                // gameInstruction needs tp be optimized  here
                            }
                            break;

                        case CARDPLAYED:
                            logger.info(receivedMessage.getCurrentPlayer() +" has played card: "+ receivedMessage.getCardString());
                            for (int i = 0; i < clientModel.getOppPlayerNames().size(); i++) {
                                if (receivedMessage.getCurrentPlayer().equals(clientModel.getOppPlayerNames().get(i))) {
                                    clientModel.setIndexCurrentPlayer(clientModel.getOppPlayerNames().indexOf(clientModel.getOppPlayerNames().get(i)));
                                    JassClient.mainProgram.getGameView().updateCardsPlayedByOpps(receivedMessage.getCardString(), clientModel.getIndexCurrentPlayer());
                                }
                            }
                            break;

                        case ROUNDFINISHED:
                            logger.info("The Winner of the round is: " + receivedMessage.getPlayerName());
                            for (PlayerScoreTuple p : clientModel.getPlayerWithPoints()) {
                                if (receivedMessage.getPlayerName().equals(p.getName()))
                                    p.setPoints(receivedMessage.getPointsOfRound());
                            }

                            JassClient.mainProgram.getGameView().showRoundWinner(receivedMessage.getPlayerName(), receivedMessage.getPointsOfRound());
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            JassClient.mainProgram.getGameView().hideRoundWinner(receivedMessage.getPlayerName());
                            clientModel.orderScores();
                            JassClient.mainProgram.getGameView().getTvScoreTable().refresh();
                            JassClient.mainProgram.getGameController().updateGameHistory(receivedMessage.getPlayerName());
                            JassClient.mainProgram.getGameView().removeCardsInMiddle();
                            break;

                        case GAMEFINISHED:
                            JassClient.mainProgram.getGameView().showAlertGameFinished(receivedMessage.getWinnerName());
                            logger.info(receivedMessage.getWinnerName()+ "has won the game round");
                            break;

                        case MAXPOINTSREACHED:
                            JassClient.mainProgram.getGameOverView().showAlertGameOver(receivedMessage.getWinnerName());
                            JassClient.mainProgram.getGameView().hideOverlayNotYourTurn();
                            logger.info("The game is finished, the winner is "+receivedMessage.getWinnerName());
                            gameViewLaunch = true;
                            break;


                    }
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}

