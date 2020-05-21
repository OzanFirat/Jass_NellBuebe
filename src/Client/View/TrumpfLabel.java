package Client.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrumpfLabel extends Label {
    private String name;
    private boolean isTrumpf;

    public TrumpfLabel(String name, CardLabel.Style style, boolean isTrumpf) {
        super();
        this.name = name;
        this.isTrumpf = isTrumpf;

        String fileName = name;
        if (style.equals(CardLabel.Style.DE)) {
            fileName += "_de";
        }
        fileName += ".png";
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/" + fileName));
        ImageView imv = new ImageView(image);
        imv.setFitHeight(70);
        imv.setFitWidth(70);
        this.setGraphic(imv);
    }

    public String getName() {
        return name;
    }

    public boolean isTrumpf() {
        return isTrumpf;
    }

    public void setTrumpf(boolean trumpf) {
        isTrumpf = trumpf;
    }

}
