package Server;


import Common.Messages.Message;
import Common.ServiceLocator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerModel {

    // arrayList for threads of the clients
    private ArrayList<ClientThread> clientThreads;


    ServiceLocator sl = ServiceLocator.getServiceLocator();

    // Initializing Logger
    private Logger logger = sl.getLogger();


    // declaration of the MAX and MIN player for the JassGame
    private final int MIN_PLAYER = 0;
    private final int MAX_PLAYER = 4;


    // initializing portNumber to check if server is running
    private int portNr;

    // list of all playerNames
    private ArrayList<String> playerNames = new ArrayList<String>();

    // checks if server is running
    private boolean isRunning;

    // GameModel to control the logic of the game
    private GameModel gameModel;

    // reports
    private String report = " *** ";


    public ServerModel(int portNr) {
        // the port
        this.portNr = portNr;
        clientThreads = new ArrayList<ClientThread>(MAX_PLAYER);
        gameModel = GameModel.getGameModel();
    }

    public void startServer() {
        logger.info("-----------GameServer for JassGame---------------");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(portNr);
                    isRunning = true;
                    while (isRunning) {
                        logger.info("Waiting for connections for the game to start on port:" + portNr + ".....");

                        // accept connections from the client whenever requested
                        Socket socket = ss.accept();

                        // creates Thread when client is connected
                        ClientThread t = new ClientThread(socket);
                        clientThreads.add(t);

                        t.start();
                        // needs to be optimized
                        logger.info("Thread for client has been started!");


                    }
                    try {
                        ss.close();
                        for (int i = 0; i < 4; ++i) {
                            ClientThread tc = clientThreads.get(i);
                            try {
                                // close all data streams and socket
                                tc.in.close();
                                tc.out.close();
                                tc.socket.close();
                            } catch (IOException ioE) {
                            }
                        }
                    } catch (Exception e) {
                        logger.info("Exception closing the server and clients: " + e);
                    }
                } catch (IOException e) {
                    String msg = "Exception in new ServerSocket";
                    logger.info(msg);
                }
            }
        };
        Thread t = new Thread(r, "ServerSocket");
        t.start();
    }


    // TODO OPTIMIZE EVERYTHING BELOW !!!!!!!!!!!!  @LEVIN


    // neeeds to be optimized
    //checks at the login and on startgame if there are enough, but not to many players
    protected boolean checkCountPlayers(int arraySize) {
        if (arraySize == MIN_PLAYER || arraySize > MAX_PLAYER) {
            return false;
        }
        return true;
    }

    // method to broadcast a message to all clients
    public synchronized void broadcast(Message message) {
        // loop in reverse order in case we would have lost a client
        for (ClientThread t : clientThreads) {
            t.writeMessage(message);
        }
    }

    public synchronized String getPlayerByName(String name) {
       return playerNames.stream().filter(player -> player.equals(name)).toString();
    }

    public synchronized void addPlayerByName(String name){
        playerNames.add(name);
    }

    public synchronized ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }
}

