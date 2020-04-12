package Client.ClientModel;

/**
 * 
 * @author ozanf
 *
 */

public class ClientModel {
	 protected Game game;
	    protected Card.Suit trumpf = null;

	    public ClientModel() {

	    }

	    public void createGame() {
	        game = new Game(trumpf);
	    }
	    public Game getGame() {
	        return game;
	    }

	    public Card.Suit getTrumpf() {
	        return trumpf;
	    }

	    public void setTrumpf(Card.Suit trumpf){
	        this.trumpf = trumpf;
	    }
	

}
