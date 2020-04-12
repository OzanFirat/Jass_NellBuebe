package Client.ClientModel;

import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
	 public final int NUMOFCARDS = 36;
	 public ArrayList<Card> deckOfCards = new ArrayList<>();

	 public DeckOfCards() {
	      shuffle();
	 }


	 private void shuffle() {
		 deckOfCards.clear(); //Clearing the Deck

	     for(Card.Suit suit : Card.Suit.values()) {
	    	 for(Card.Rank rank : Card.Rank.values()) {
	    		 Card card = new Card(suit, rank);
	    		 deckOfCards.add(card);

	    	 }
	     }

	     Collections.shuffle(deckOfCards);
	 }


	 // takes the first Card of the Deck and returns it.
	 public Card dealCard() {
	     Card card = (deckOfCards.size() > 0) ? deckOfCards.remove(deckOfCards.size()-1): null;
	     return card;
	 }
}
