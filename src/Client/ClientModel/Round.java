package Client.ClientModel;

import java.util.ArrayList;

public class Round {
	 	public ArrayList<Turn> turns = new ArrayList<>();
	    public Player winner;
	    public static int id;
	    public Card.Suit trumpf;
	    public int totalPoints;

	    public Round(Card.Suit trumpf) {
	        winner = null;
	        id++;
	        this.trumpf = trumpf;
	    }

	    public void addTurn(Turn turn){
	        turns.add(turn);
	    }

	    // TODO Method to evaluate the winner of the round
	    // Gives back the player who wins the round
	    public Player evaluate(){
	        return winner;
	    }


	    // Count the points of the round - TODO go through players to see who's the winner and call method - probably done in game
	    public int countPoints() {
	        for (Turn t : turns) {
	            if (t.getCard().getSuit().equals(trumpf) && t.getCard().getSuit().equals(Card.Rank.Jack)) {
	                totalPoints += t.getCard().getRank().setAndGetValueTrumpf(20);
	            } else {
	                if (t.getCard().getSuit().equals(trumpf) && t.getCard().getSuit().equals(Card.Rank.Nine)) {
	                    totalPoints += t.getCard().getRank().setAndGetValueTrumpf(14);
	                } else {
	                    totalPoints += t.getCard().getRank().getValueinPoints();
	                }
	            }
	        }
	        return totalPoints;
	    }

}
