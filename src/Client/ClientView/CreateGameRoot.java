package Client.ClientView;

import java.util.ArrayList;

import Client.ClientModel.ClientModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreateGameRoot extends Pane {
	private ClientModel model;
    public HBox cntChooseTrumpf;
    public ArrayList<ImageView> imvTrumpfIcons = new ArrayList<ImageView>(4);
    public Button btnStartGame;
    private Rectangle rect;
    public ArrayList<Button> btnChooseTrumpf = new ArrayList<Button>(4);



    public CreateGameRoot(ClientModel model) {
        this.model = model;

        rect = createBackgroundRect();
        cntChooseTrumpf = createChooseTrumpf();
        btnStartGame = createBtnStartGame();

        this.getChildren().addAll(rect, cntChooseTrumpf, btnStartGame);
    }


    private Rectangle createBackgroundRect(){
        Rectangle rect = new Rectangle(750, 500);
        rect.setTranslateY(250);
        rect.setTranslateX(375);
        return rect;
    }

    private Button createBtnStartGame(){
        Button btn = new Button("Start");
        btn.setTranslateX(1000);
        btn.setTranslateY(700);
        return btn;
    }

    public HBox createChooseTrumpf(){
        HBox box = new HBox(10);
        for (int i = 0; i < 4; i++){
            ImageView imv = new ImageView();
            imvTrumpfIcons.add(imv);

            Button btn = new Button();
            btn.setGraphic(imvTrumpfIcons.get(i));
            btnChooseTrumpf.add(btn);
        }
        loadTrumpfImages();

        for (ImageView imv : imvTrumpfIcons) {
            imv.setFitWidth(70);
            imv.setFitHeight(70);
        }



        box.setTranslateX(550);
        box.setTranslateY(475);
        box.getChildren().addAll(btnChooseTrumpf);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void loadTrumpfImages(){
        Image imgClubs = new Image(getClass().getClassLoader().getResourceAsStream("images/clubs.png"));
        Image imgDiamonds = new Image(getClass().getClassLoader().getResourceAsStream("images/diamonds.png"));
        Image imgHearts = new Image(getClass().getClassLoader().getResourceAsStream("images/hearts.png"));
        Image imgSpades = new Image(getClass().getClassLoader().getResourceAsStream("images/spades.png"));

        imvTrumpfIcons.get(0).setImage(imgClubs);
        imvTrumpfIcons.get(1).setImage(imgHearts);
        imvTrumpfIcons.get(2).setImage(imgDiamonds);
        imvTrumpfIcons.get(3).setImage(imgSpades);
    }
}
