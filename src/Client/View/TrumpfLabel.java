package Client.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrumpfLabel extends Label {
    private String name;

    public TrumpfLabel(String name, CardLabel.Style style) {
        super();
        this.name = name;

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

}
