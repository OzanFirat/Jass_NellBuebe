package Client.ClientView;

import Client.ClientModel.Card;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardLabel extends Label {
	Card card;

    public CardLabel(Card c){
        super();
        String fileName = cardToFileName(c);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/" + fileName));
        ImageView imv = new ImageView(image);
        imv.setFitHeight(120);
        imv.setFitWidth(75);
        this.setGraphic(imv);
        this.card = c;
    }

   /*  public void setCard(Card card) {
        if (card != null) {
            String fileName = cardToFileName(card);
            Image image = new Image("file:images/" + fileName);
        }
    }*/

   public Card getCard(){
       return card;
   }

    private String cardToFileName(Card card) {
        String rank = card.getRank().toString();
        String suit = card.getSuit().toString();
        return suit + "_" + rank + ".jpg";
    }

}
