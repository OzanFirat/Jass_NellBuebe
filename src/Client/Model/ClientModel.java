package Client.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import  javafx.scene.image.Image;

import java.util.ArrayList;

public class ClientModel {

    private String userName;
    private int indexCurrentPlayer;
    private int playerIndex;
    private ArrayList<String> playerNames;
    private ArrayList<String> oppPlayerNames;
    private ArrayList<?> yourCards;
    private String cardName;
    private ObservableList<PlayerScoreTuple> playerWithPoints;
    private String trumpf;


    public ClientModel(){
        playerNames = new ArrayList<>(4);
        oppPlayerNames = new ArrayList<>(3);
        yourCards = new ArrayList<>(9);
        playerWithPoints = FXCollections.observableArrayList();
    }

    public void fillOppPlayerList() {
        int indexOfUser = 0;
        String userNameLocal = "";
        for (String s : playerNames){
            if (s.equals(userName)) {
                userNameLocal = s;
                indexOfUser = playerNames.indexOf(s);
            }
        }
        playerNames.remove(userNameLocal);
        for (String s : playerNames){
            oppPlayerNames.add(s);
        }
        playerNames.add(indexOfUser, userNameLocal);

        switch(indexOfUser){
            case 1:
                String s1 = oppPlayerNames.get(0);
                String s2 = oppPlayerNames.get(1);
                String s3 = oppPlayerNames.get(2);
                oppPlayerNames.set(0, s2);
                oppPlayerNames.set(1, s3);
                oppPlayerNames.set(2, s1);
                break;
            case 2:
                s1 = oppPlayerNames.get(0);
                s2 = oppPlayerNames.get(1);
                s3 = oppPlayerNames.get(2);
                oppPlayerNames.set(0, s3);
                oppPlayerNames.set(1, s1);
                oppPlayerNames.set(2, s2);
                break;
        }
        System.out.println("Reordering done");
    }

    public void fillPlayerWithPoints() {
        for (String s : playerNames) {
            PlayerScoreTuple p = new PlayerScoreTuple(s, 0);
            playerWithPoints.add(p);
        }
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    public void setIndexCurrentPlayer(int indexCurrentPlayer) {
        this.indexCurrentPlayer = indexCurrentPlayer;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void addPlayernameToList(String playerName) {
        playerNames.add(playerName);
    }

    public ArrayList<?> getYourCards() {
        return yourCards;
    }

    public void setYourCards(ArrayList<?> yourCards) {
        this.yourCards = yourCards;
    }

    public ArrayList<String> getOppPlayerNames() {
        return oppPlayerNames;
    }

    public void setOppPlayerNames(ArrayList<String> oppPlayerNames) {
        this.oppPlayerNames = oppPlayerNames;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public ObservableList<PlayerScoreTuple> getPlayerWithPoints() {
        return playerWithPoints;
    }

    public void setPlayerWithPoints(ObservableList<PlayerScoreTuple> playerWithPoints) {
        this.playerWithPoints = playerWithPoints;
    }

    public String getTrumpf() {
        return trumpf;
    }

    public void setTrumpf(String trumpf) {
        this.trumpf = trumpf;
    }
}
