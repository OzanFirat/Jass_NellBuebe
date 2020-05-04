package Common;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class contains all message types that will be exchanged by the clients and server.
 * This class uses many Message subclasses through polymorphism.This will help us organize our codes better.
 * The Message class contains only messages which are standard for the game
 */

public class Message implements Serializable {

    // there are different type of messages sent by the clients
    // Receive Players connected to the server as a List
    // Message an ordinary text message in a chatroom for conversations
    // Clients can disconnect themselves from the server
    // Clients can connect themselves from the LoginWindow to the server
    // Server can accept the connections from the clients unit the max players are 4
    // Server can show a message if client loses connection to server
    // Server can announce if the Game has started
    // Server can give turns for the currentPlayer who has to play
    // Server can give action by a GameAction from Player
    // Server rejects startGame if numOfPlayers are too less

    public enum Type {WHOISIN, CHATMESSAGE, LOGOUT, LOGIN, LOGINREJECTED, LOGINACCEPTED,
                            TRUMPF, STARTGAME, YOURTURN, CARDPLAYED, ROUNDFINISHED, GAMEFINISHED}


    private Type type;
    private String message;
    private String userName;
    private String cardString;
    private volatile ArrayList<?> arrayList;
    private volatile String winnerName;
    private String currentPlayer;
    private volatile ArrayList<String> playerNames;
    private String lastPlayedCard = "";
    private String playerName;
    private int pointsOfRound;
    private String trumpf;

    // Messages for player entering the game
    public Message(Message.Type type, String message) {
        this.message = message;
        this.type = type;

        switch (type) {
            case WHOISIN:
                this.message = message;
                break;
            case LOGIN:
                this.userName = message;
                break;
            case LOGINACCEPTED:
                this.userName = message;
                break;
            case TRUMPF:
                this.trumpf = message;
            case YOURTURN:
                this.currentPlayer = message;
                break;
            case CARDPLAYED:
                this.cardString = message;
                break;
            case ROUNDFINISHED:
                this.winnerName = message;
                break;
            case GAMEFINISHED:
                this.winnerName = message;
        }
        this.message = message;
    }

    public Message (Type type){
        this.type = type;
    }

    public Message(Type type, ArrayList<String> playerNames){
        this.type = type;
        this.playerNames = playerNames;
    }

    public Message(Type type, String playerName, ArrayList<?> playerCards) {
        this.type = type;
        this.playerName = playerName;
        this.arrayList = playerCards;
    }

    public Message(Type type, String playerName, String cardString) {
        this.type = type;
        this.currentPlayer = playerName;
        this.cardString = cardString;
    }

    public Message(Type type, String playerName, int points) {
        this.type = type;
        this.playerName = playerName;
        this.pointsOfRound = points;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardString() {
        return cardString;
    }

    public void setCardString(String cardString) {
        this.cardString = cardString;
    }

    public ArrayList<?> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public String getLastPlayedCard() {
        return lastPlayedCard;
    }

    public void setLastPlayedCard(String lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPointsOfRound() {
        return pointsOfRound;
    }

    public void setPointsOfRound(int pointsOfRound) {
        this.pointsOfRound = pointsOfRound;
    }

    public String getTrumpf() {
        return trumpf;
    }

    public void setTrumpf(String trumpf) {
        this.trumpf = trumpf;
    }
}

