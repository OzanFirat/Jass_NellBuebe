package Server.ServerModel;


public class Turn {
	
	public Player player;
	public Card playedCard;
	public boolean winner = false;
	
	
	public Turn(Player player, Card playedCard) {
		this.player = player;
		this.playedCard = playedCard;
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public Card getPlayedCard() {
		return playedCard;
	}
	
	
	// method for checking winner
	public void checkWinner() {
		
			
	}
		
		
	//TODO

}
