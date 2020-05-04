package Server;


public class Turn {
    private Player player;
    private Card card;

    public Turn (Player player, Card card){
        this.card = card;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
