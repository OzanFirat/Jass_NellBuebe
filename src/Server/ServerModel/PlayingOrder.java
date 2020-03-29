package Server.ServerModel;

import java.util.ArrayList;


public class PlayingOrder {
	public  ArrayList<Player> playersInInitialPlayingOrder;
	public int startingPlayer;
	public int currentPlayer;

	
	
	public PlayingOrder(ArrayList<Player>playersInInitialPlayingOrder, int startingPlayer) {
		
	}
	
	// create a playingOrder, no object needed for it
	public static PlayingOrder createOrder() {
		return null;
		
		
	}
	
	// create a new playingOrder beginning from the player who has won the last round
	public static PlayingOrder createOrderStartingFromPlayer() {
		return null;
			
	}
	
	public void moveToNextPlayer() {
		currentPlayer = currentPlayer +1;   //change to ++
	}
	
	// get current playingOrder
	public ArrayList<Player> getPlayerInOrder(){
		return playersInInitialPlayingOrder;
		
	}
	
	//get curretnPlayer
	public Player getCurrentPlayer() {
		return null;
	}
	
	// methods TODO
}
