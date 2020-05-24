package Server;

import java.util.ArrayList;

public class Player {
    private final int HANDSIZE = 9;
    private ArrayList<Card> handCards = new ArrayList<>(HANDSIZE);
    private String playerName;
    private int id; //Playernumber


    private int pointCounter;

    // creates player initially with 0 points
    public Player(String playerName) {
        this.playerName = playerName;
        this.pointCounter = 0;
    }

    public ArrayList<String> blockCards(Round round){
        boolean sameSuitInHand = false;
        boolean trumpfPlayedOutOfColor = false;
        Card trumpfCardOutOfColor = null;
        Card.Suit trumpf = round.getTrumpf();
        Card firstCard = null;

        ArrayList<String> blockedCards = new ArrayList();

        // check if the player has the same suit in hand than the first card which was played - only if it's not the first turn of the round
        if (round.getTurns().size() != 0) {
            firstCard = round.getTurns().get(0).getCard();
            for (Card c : handCards) {
                if (c.getSuit() == firstCard.getSuit()) {
                    sameSuitInHand = true;
                }
            }
        }

        // check if there was a trumpf played out of color (not as first card)
        for (Turn t : round.getTurns()) {
            if (t.getCard().getSuit().equals(round.getTrumpf()) && round.getTurns().get(0).getCard().getSuit() != round.getTrumpf()) {
                trumpfPlayedOutOfColor = true;
                trumpfCardOutOfColor = t.getCard();
            }
        }



        for(Card c : handCards) {
            if (sameSuitInHand) {
                if (c.getSuit() != firstCard.getSuit() && c.getSuit() != trumpf) {
                    blockedCards.add(c.toString());
                }
            }
            if (trumpfPlayedOutOfColor) {
                if (c.getSuit().equals(trumpf)) {
                    if (c.getRank() == Card.Rank.Jack) {
                        // do nothing - jack can always be played
                    } else {
                        if (c.getRank() == Card.Rank.Nine) {
                            if (trumpfCardOutOfColor.getRank() == Card.Rank.Jack) {
                                blockedCards.add(c.toString()); // Nell is lower than jack so it can't be played
                            }
                        } else {
                            if (trumpfCardOutOfColor.getRank() == Card.Rank.Jack || trumpfCardOutOfColor.getRank() == Card.Rank.Nine) {
                                blockedCards.add(c.toString()); // Every possible card is lower than Jack and Nell and therefore can't be played
                            } else {
                                if (c.getRank().ordinal() < trumpfCardOutOfColor.getRank().ordinal()) {
                                    blockedCards.add(c.toString()); // block the card if it's lower than the trumpf played out of color
                                }
                            }
                        }
                    }
                }
            }
        }

        if(blockedCards.size()==0 || handCards.size() == 1){
            blockedCards = null;
        }
        return blockedCards;
    }

    public void addCard(Card card) {
        handCards.add(card);
    }

    public void addPointsToCounter(int points) {
        pointCounter += points;
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
