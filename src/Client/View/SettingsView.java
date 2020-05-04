package Client.View;

import Client.Model.ClientModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingsView {
    private Stage settingsStage;
    private ClientModel model;

    private Pane rootSettings = new Pane();

    public HBox cntChooseTrumpf;
    public ArrayList<ImageView> imvTrumpfIcons = new ArrayList<ImageView>(4);
    public Button btnStartGame;
    private Rectangle rect;
    public ArrayList<Button> btnChooseTrumpf = new ArrayList<Button>(4);

    public int xMiddle = 700;
    public int yMiddle = 400;

    // lenght and width of the scene
    private final double sceneWidth = 1400;
    private final double sceneHeight = 800;

    //Define the image for the background
    private Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/background_1400x800.png"));

    public SettingsView(Stage settingsStage, ClientModel model) {
        this.settingsStage = settingsStage;
        this.model = model;

        rect = createBackgroundRect();
        cntChooseTrumpf = createChooseTrumpf();
        btnStartGame = createBtnStartGame();

        rootSettings.getChildren().addAll(rect, cntChooseTrumpf, btnStartGame);
        rootSettings.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));

        Scene scene = new Scene(rootSettings, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        settingsStage.setScene(scene);
        settingsStage.setTitle("Jass by NellBuebe - Settings");
    }

    public void start() {
        settingsStage.show();
    }

    public void stop(){
        settingsStage.hide();
    }

    private Rectangle createBackgroundRect(){
        Rectangle rect = new Rectangle(xMiddle, yMiddle);
        rect.setTranslateY(yMiddle - (yMiddle/2));
        rect.setTranslateX(xMiddle - (xMiddle/2));
        return rect;
    }

    private Button createBtnStartGame(){
        Button btn = new Button("Start");
        btn.setTranslateX(xMiddle + 200);
        btn.setTranslateY(yMiddle + 150);
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



        box.setTranslateX(500);
        box.setTranslateY(365);
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
