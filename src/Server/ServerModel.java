package Server;


import Common.Messages.Message;
import Common.ServiceLocator;
import javafx.application.Platform;

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


    // declaration of the allowed players for the JassGame
    private final int ALLOWEDPLAYERS = 4;


    // initializing portNumber to check if server is running
    private int portNr;

    // list of all playerNames
    private ArrayList<String> playerNames = new ArrayList<String>();

    // checks if server is running
    private boolean isRunning;

    // GameModel to control the logic of the game
    private GameModel gameModel;



    public ServerModel(int portNr) {
        // the port
        this.portNr = portNr;
        clientThreads = new ArrayList<ClientThread>(ALLOWEDPLAYERS);
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

    // method to broadcast a message to all clients
    public synchronized void broadcast(Message message) {

        // loop in reverse order in case we would have lost a client
        for (ClientThread t : clientThreads) {
            t.writeMessage(message);
        }
    }

    //checks at the login and on startgame if there are enough, but not to many players
    protected boolean checkPlayerCount(int arraySize){
        if(arraySize == ALLOWEDPLAYERS){
            return false;
        }
        return true;
    }

    // this method checks if client tried to sent LOGOUT message to exit
    public void removeClient(int id) {

        String clientDisconnected = "";
        // loop trough arrayList until the id has been found
        for (int i = 0; i < clientThreads.size(); ++i){
            ClientThread clientThread = clientThreads.get(i);
            if(clientThread.id == id){
                clientDisconnected = clientThread.getUsername();
                clientThreads.remove(i);
                break;
            }
        }
        Message message = new Message(Message.Type.CLIENTLOST, clientDisconnected);
        broadcast(message);
    }

    public synchronized String getPlayerByName(String name) {
        return playerNames.stream().filter(player -> player.equals(name)).toString();
    }

    public synchronized void addPlayerByName(String name){
        playerNames.add(name);
    }

    public synchronized void removePlayerByName(String name){
        playerNames.remove(name);
    }


    public synchronized ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public int getALLOWEDPLAYERS() {
        return ALLOWEDPLAYERS;
    }

    // needs to be optimized TODO
    public void closeAllThreads(){
        for ( ClientThread t : clientThreads) {

            Platform.exit();
            System.exit(0);
        }
    }
}
