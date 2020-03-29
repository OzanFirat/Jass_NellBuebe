package Server.ServerModel;

import java.util.ArrayList;
import java.util.Collections;


public class DeckOfCards {
	
	public final int NUMOFCARDS = 36;
	public ArrayList<Card> deckOfCards;
	
	public DeckOfCards(ArrayList<Card> cards) {
		this.deckOfCards = new ArrayList<>(NUMOFCARDS);
		
		for(Card.Suit suit : Card.Suit.values()) {
			for(Card.Rank rank : Card.Rank.values()) {
				Card card = new Card();
				deckOfCards.add(card);
				
			}	
		}
		Collections.shuffle(deckOfCards); 	
	}
	
	// player calls method and gets card from deck
	public void getCardfromDeck() {
		
	}	
}

