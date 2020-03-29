package Server.ServerModel;

import java.util.ArrayList;
import java.util.List;



public class Round {
	
	public Mode mode;
	public int roundNumber;
	public PlayingOrder playingOrder;
	public ArrayList<Turn> turn = new ArrayList<>();

	public Round(Mode mode, int roundNumber, PlayingOrder playingOrder) {
		this.mode = mode;
		this.roundNumber = roundNumber;
		this.playingOrder = playingOrder;

	}

	// create round with no object needed
	public static Round createRound(Mode gameMode, int roundNumber, PlayingOrder playingOrder) {
		return new Round(gameMode, roundNumber, playingOrder);
	}

	// take a turn and add it to ArrayList moves, set playingOrder to next player
	public void makeTurn(Turn turn) {

	}

	// getters and setters
	public int getRoundNumber() {
		return roundNumber;
	}

	public List<Turn> getTurn() {
		return turn;
	}

	public PlayingOrder getPlayingOrder() {
		return playingOrder;
	}

	public Mode getMode() {
		return mode;
	}

}
