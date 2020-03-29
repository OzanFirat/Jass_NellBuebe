package Server.ServerModel;

public class Game {
	
	protected Round round;
	protected Player player;
	protected DeckOfCards deckOfcards;
	protected Mode mode;
	protected PlayingOrder playingOrder;
	protected Score score;
	
	public Game() {
		
	}
	
	
	// Getters and Setters
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public DeckOfCards getDeckOfcards() {
		return deckOfcards;
	}

	public void setDeckOfcards(DeckOfCards deckOfcards) {
		this.deckOfcards = deckOfcards;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public PlayingOrder getPlayingOrder() {
		return playingOrder;
	}

	public void setPlayingOrder(PlayingOrder playingOrder) {
		this.playingOrder = playingOrder;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	
	public void startNextRound() {
		
	}
	
	private void makeTurn() {
		
	}
	
	/*
	private void calculateMatchBonus() {
		
	}
	*/
	
	private void updateRoundResult() {
		
	}
	
	private Round createNextRound() {
		return round;
		
	}
	 
	
}
