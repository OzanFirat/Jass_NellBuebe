package Client.Controller;


import Client.JassClient;
import Client.Model.ClientModel;
import Client.Model.PlayerScoreTuple;
import Client.View.ChatView;
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

    private ClientModel model = JassClient.mainProgram.getClientModel();
    // private GameController gameController = JassClient.mainProgram.getGameController();


    //127.0.0.1 or localhost
    private String server = "127.0.0.1";
    // portNumber
    private int portNr = 55555;

    // I/O elements
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String userName;
    private ArrayList<String> playerNames = new ArrayList<>(3);

    private ChatView chatView;

    public void setServer(String server) {
        this.server = server;
    }

    public static synchronized ClientCommunication getInstance() {
        if (ClientCommunication.instance == null) {
            ClientCommunication.instance = new ClientCommunication();
        }
        return ClientCommunication.instance;
    }

    /**
     * This method sends a message to the console
     */
    private void display(String msg) {
        System.out.println(msg);
    }

    /**
     * This method sends a message to the server
     */
    void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            out.reset();
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    /**
     * When something goes wrong
     * Close the Input/Output streams and disconnect
     */
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
            //TODO
            socket = new Socket(server, portNr);
            logger.info(socket.toString() + "starting the socket...");
            logger.info("the socket has successfully started");

            // if connection to server failed
        } catch (Exception e) {
            display("Connection to Server has failed:" + e);
            JassClient.mainProgram.getLoginController().showAlertConnectionFailed();
            return false;
        }

        String success = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(success);


        // creating input- and outputStreams
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            logger.info("I/O streams successfully created");
        } catch (IOException io) {
            display("I/O Exception: " + io);
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
                        display(userName + " Exception reading Streams: " + e);
                        break;
                    }
                    switch (receivedMessage.getType()) {
                        case CHATMESSAGE:
                            JassClient.mainProgram.getChatcontroller().updateChatView(receivedMessage.getMessage());
                            logger.info("Chat Message received: " + receivedMessage.getMessage());
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
                        case LOGINACCEPTED:
                            model.setUserName(receivedMessage.getUserName());
                            JassClient.mainProgram.getLoginController().loginAccepted();
                            logger.info("Login accepted");
                            break;
                        case WHOISIN:
                            model.setPlayerNames(receivedMessage.getPlayerNames());
                            JassClient.mainProgram.getLobbyController().updateLobby();
                            JassClient.mainProgram.getChatcontroller().updateChatEntry();
                            logger.info("Who is in message received");
                            break;

                        case STARTGAMEREJECTED:
                            logger.info("StartGame rejected");
                            int playerCount = receivedMessage.getPlayerCount();
                            logger.info("Number of Players: " + playerCount);
                            if(playerCount <= 3)
                                JassClient.mainProgram.getLobbyController().showAlertLessPlayer();
                            break;

                        case STARTGAME:
                            // initialize the game

                            if (gameCounter == 0) {
                                model.fillOppPlayerList();
                                // fill the ObservableList to show the updated scores during the game
                                model.fillPlayerWithPoints();
                                JassClient.mainProgram.getGameController().initializeGame();
                                JassClient.mainProgram.getSettingsView().stop();
                            }

                            if (model.getUserName().equals(receivedMessage.getPlayerName())) {
                                JassClient.mainProgram.getGameView().createTrumpfChoice();
                                JassClient.mainProgram.getGameController().handleTrumpfChoiceAction();
                            }
                            gameCounter++;
                            break;

                        case TRUMPF:
                            model.setTrumpf(receivedMessage.getTrumpf());
                            // fill the ObservableList to show the updated scores during the game
                            JassClient.mainProgram.getGameController().updateTrumpfElements();
                            break;
                        case DEALCARDS:
                            // Set your cards and put the opponents in the right order to display in view
                            if (model.getUserName().equals(receivedMessage.getPlayerName())) {
                                JassClient.mainProgram.getSettingsController().setCardStyle();
                                model.setYourCards(receivedMessage.getArrayList());
                                logger.info(userName +" has received his cards");

                                logger.info("The game has launched");
                                JassClient.mainProgram.getGameController().updateYourCards();
                            }
                            break;

                        case YOURTURN:
                            if (receivedMessage.getPlayerName().equals(model.getUserName())) {
                                logger.info("It's your turn - " + model.getUserName());
                                // if it's your turn, remove the overlay and set the cards on action
                                // if it's your turn, remove the overlay and set the cards on action
                                if (receivedMessage.getIllegalCards() != null) {
                                    model.createIndexIllegalCards(receivedMessage.getIllegalCards());
                                }
                                JassClient.mainProgram.getGameView().hideOverlayNotYourTurn();
                                JassClient.mainProgram.getGameController().handleCardAction();
                            } else {
                                logger.info("It's not your turn");
                                // Create the overlay if it's not your turn
                                JassClient.mainProgram.getGameView().showOverlayNotYourTurn();
                                // gameInstruction needs tp be optimized  here
                            }
                            break;

                        case CARDPLAYED:
                            for (int i = 0; i < model.getOppPlayerNames().size(); i++) {
                                if (receivedMessage.getCurrentPlayer().equals(model.getOppPlayerNames().get(i))) {
                                    logger.info(model.getOppPlayerNames().get(i) + " has played card: " + receivedMessage.getCardString());
                                    model.setIndexCurrentPlayer(model.getOppPlayerNames().indexOf(model.getOppPlayerNames().get(i)));
                                    JassClient.mainProgram.getGameView().updateCardsPlayedByOpps(receivedMessage.getCardString(), model.getIndexCurrentPlayer());
                                }
                            }
                            break;

                        case ROUNDFINISHED:
                            System.out.println("The Winner of the round is: " + receivedMessage.getPlayerName());
                            for (PlayerScoreTuple p : model.getPlayerWithPoints()) {
                                if (receivedMessage.getPlayerName().equals(p.getName()))
                                    p.setPoints(receivedMessage.getPointsOfRound());
                            }

                            JassClient.mainProgram.getGameView().showRoundWinner(receivedMessage.getPlayerName());
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            JassClient.mainProgram.getGameView().hideRoundWinner(receivedMessage.getPlayerName());
                            JassClient.mainProgram.getGameView().getTvScoreTable().refresh();
                            JassClient.mainProgram.getGameController().updateGameHistory(receivedMessage.getPlayerName());
                            JassClient.mainProgram.getGameView().removeCardsInMiddle();
                            break;

                        case GAMEFINISHED:
                            JassClient.mainProgram.getGameView().showAlertGameFinished(receivedMessage.getWinnerName());
                            break;

                        case MAXPOINTSREACHED:
                            JassClient.mainProgram.getGameOverView().showAlertGameOver(receivedMessage.getWinnerName());
                        default:
                            logger.info("no cases occurred");

                    }
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}

