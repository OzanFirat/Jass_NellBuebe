package Client.ClientModel;

import java.util.ArrayList;

public class Game {
	protected DeckOfCards deck;
    protected Card.Suit trumpf = null;
    protected ArrayList<Card> cardsInMiddle;
    protected boolean evaluated = true;

    protected Player yourPlayer;
    protected Player player2;
    protected Player player3;
    protected Player player4;
    protected ArrayList<Player> opponentPlayers;

    public Game(Card.Suit trumpf) {
        deck = new DeckOfCards();
        this.trumpf = trumpf;

        yourPlayer = new Player("Hans", 1);
        player2 = new Player("Fritz", 2);
        player3 = new Player("Sepp", 3);
        player4 = new Player("Johannes", 4);
        opponentPlayers = new ArrayList<Player>(3);

        // Put all player Objects in an ArrayList
        opponentPlayers.add(player2);
        opponentPlayers.add(player3);
        opponentPlayers.add(player4);

        dealCardsToPlayer();

    }

    // Distribute the deck to the player - every Player has an ArrayList filled with cards
    private void dealCardsToPlayer(){
        for (int i = 0; i < yourPlayer.getHANDSIZE(); i++) {
            yourPlayer.addCard(deck.dealCard());
        }

        for (int i = 0; i < player2.getHANDSIZE(); i++) {
            player2.addCard(deck.dealCard());
        }

        for (int i = 0; i < player3.getHANDSIZE(); i++) {
            player3.addCard(deck.dealCard());
        }

        for (int i = 0; i < player4.getHANDSIZE(); i++) {
            player4.addCard(deck.dealCard());
        }
    }
    
    private void makeRound(){
        Round round = new Round(trumpf);
        for (Player p : opponentPlayers){
            Turn turn = new Turn(p, p.getHandCards().get(0));
            round.addTurn(turn);
        }
    }

    public Player getYourPlayer() {
        return yourPlayer;
    }

    public ArrayList<Player> getOpponentPlayers() {
        return opponentPlayers;
    }



    public void setTrumpf(Card.Suit trumpf) {
        this.trumpf = trumpf;
    }
}
