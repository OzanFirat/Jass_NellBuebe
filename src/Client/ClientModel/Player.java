package Client.ClientModel;

import java.util.ArrayList;

public class Player {
	private final int HANDSIZE = 9;
    private ArrayList<Card> handCards = new ArrayList<>(HANDSIZE);
    private String playerName;
    private int id; //Playernumber


    private int pointCounter;

    // creates player initially with 0 points
    public Player(String playerName, int id) {
        this.playerName = playerName;
        this.id = id;
        this.pointCounter = 0;
    }

    public void addCard(Card card) {
            handCards.add(card);
    }

    public void discard() {
        handCards.clear();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointCounter() {
        return pointCounter;
    }

    public void setPointCounter(int pointCounter) {
        this.pointCounter = pointCounter;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public int getHANDSIZE() {
        return HANDSIZE;
    }
}
