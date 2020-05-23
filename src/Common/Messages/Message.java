package Common.Messages;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class contains all message types that will be exchanged by the clients and server.
 * The Message class contains only messages which are standard for the game
 */

public class Message implements Serializable {

    // there are different type of messages sent by the clients
    // Receive Players connected to the server as a List
    // Message an ordinary text message in a chatroom for conversations
    // Clients can disconnect themselves from the server TODO
    // Clients can connect themselves from the LoginWindow to the server
    // Server can accept the connections from the clients unit the max players are 4
    // Server can show a message if client loses connection to server TODO
    // Server can announce if the Game has started
    // Server can give turns for the currentPlayer who has to play
    // Server can give action by a GameAction from Player
    // Server rejects startGame if players are too less

    public enum Type {WHOISIN, CHATMESSAGE, LOGOUT, LOGIN, LOGINREJECTED, LOGINACCEPTED,DEALCARDS,TRUMPF,CLIENTLOST,EXITGAME,
        STARTGAME, YOURTURN, CARDPLAYED, ROUNDFINISHED, GAMEFINISHED, STARTGAMEREJECTED, LOGINREJECTEDTOOMANYPLAYERS, MAXPOINTSREACHED, BACKTOLOBBY}

    private Type type;
    private String message;
    private String userName;
    private String cardString;
    private volatile ArrayList<?> arrayList;
    private String winnerName;
    private String currentPlayer;
    private volatile ArrayList<String> playerNames;
    private String playerName;
    private int pointsOfRound;
    private String trumpf;
    private int maxPoints;
    private int playerCount;
    private volatile ArrayList<String> illegalCards;
    private String gameType;


    // Messages for player entering the game
    public Message(Message.Type type, String message) {
        this.message = message;
        this.type = type;

        switch (type) {
            case LOGOUT:
                this.playerName = message;
                break;
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
                this.playerName = message;
                break;
            case STARTGAME:
                this.playerName = message;
            case CARDPLAYED:
                this.cardString = message;
                break;
            case ROUNDFINISHED:
                this.winnerName = message;
                break;
            case GAMEFINISHED:
                this.winnerName = message;
                break;
            case MAXPOINTSREACHED:
                this.winnerName = message;
                break;
            case BACKTOLOBBY:
                this.userName = message;
                break;
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

        switch (type) {
            case DEALCARDS:
                this.arrayList = playerCards;
                break;
            case YOURTURN:
                illegalCards = (ArrayList<String>) playerCards;
                break;
        }
    }


    public Message(Type type, String playerName, String cardString) {
        this.type = type;

        switch (type) {
            case CARDPLAYED:
                this.currentPlayer = playerName;
                this.cardString = cardString;
                break;
            case TRUMPF:
                this.trumpf = playerName;
                this.gameType = cardString;
                break;
        }
    }


    // message for score
    public Message(Type type, String playerName, int points) {
        this.type = type;
        this.playerName = playerName;
        this.pointsOfRound = points;

        switch (type) {
            case STARTGAME:
                this.maxPoints = points;
                break;
            case ROUNDFINISHED:
                this.pointsOfRound = points;
                break;
        }
    }

    // message for too less plasyers
    public Message(Message.Type type, int value){
        this.type = type; ;

        switch(type){
            case STARTGAMEREJECTED:
                this.playerCount = value;
                break;
            case STARTGAME:
                this.maxPoints = value;
                break;
        }
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


    public String getTrumpf(){
        return trumpf;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setTrumpf(){
        this.trumpf = trumpf;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public ArrayList<String> getIllegalCards() {
        return illegalCards;
    }

    public void setIllegalCards(ArrayList<String> illegalCards) {
        this.illegalCards = illegalCards;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

}
