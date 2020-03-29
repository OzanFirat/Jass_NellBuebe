package Server.ServerModel;

import java.util.ArrayList;


public class Player {
	
public static int HANDSIZE= 9;
	


	public static int points;
	public String name;
	public ArrayList<Card> cardsInHand;
	public ArrayList<Card> cardsWon;		//cards that were won in the game, needed to count points later on
	public boolean winner = false;          //to determine who has won the round 
	
	public Player() { 
	// TODO constructor
	}
	
	
	// Getters and Setters up to line 69
	public static int getHandsize() { 
		return HANDSIZE;
	}

	public static void setHandsize(int handsize) {
		HANDSIZE = handsize;
	}


	public static int getPoints() {
		return points;
	}


	public static void setPoints(int points) {
		Player.points = points;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Card> getCardsInHand() {
		return cardsInHand;
	}


	public void setCardsInHand(ArrayList<Card> cardsInHand) {
		this.cardsInHand = cardsInHand;
	}


	public ArrayList<Card> getCardsWon() {
		return cardsWon;
	}


	public void setCardsWon(ArrayList<Card> cardsWon) {
		this.cardsWon = cardsWon;
	}
	
	
	// needed to deal cards, add a single card to ArrayList CardsInHand
	public void addCardToHand() { 
		
	}
	
	// method for playing a card - probably in controller because of buttonClick on Card in Hand
	public void playCard() { 
		
	}
	
	// method for collecting cards won from the middle
	public void collectCardsWon() {		
		
	}
	

}
