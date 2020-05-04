package Client.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardLabel extends Label {
    private String cardName;
    public enum Style {DE, FR};

    public CardLabel(String cardName, Style style){
        super();
        this.cardName = cardName;
        String fileName = cardName;
        if (style.equals(Style.DE)) {
            fileName += "_de";
        }
        fileName += ".jpg";
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/" + fileName));
        ImageView imv = new ImageView(image);
        imv.setFitHeight(130);
        imv.setFitWidth(81.25);
        this.setGraphic(imv);
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /*  public void setCard(Card card) {
        if (card != null) {
            String fileName = cardToFileName(card);
            Image image = new Image("file:images/" + fileName);
        }
    }*/


    /*
    private String cardToFileName(Card card) {
        String rank = card.getRank().toString();
        String suit = card.getSuit().toString();
        return suit + "_" + rank + ".jpg";
    }

     */

}
