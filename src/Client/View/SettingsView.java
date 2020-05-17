package Client.View;

import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingsView {
    private Stage settingsStage;
    private ClientModel model;

    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator t = sl.getTranslator();

    private Pane rootSettings = new Pane();

    private GridPane grid;
    public ArrayList<ImageView> imvTrumpfIcons = new ArrayList<ImageView>(4);
    public Button btnStartGame;
    private Rectangle rect;
    public ArrayList<Button> btnChooseTrumpf = new ArrayList<Button>(4);

    public int xMiddle = 700;
    public int yMiddle = 400;

    private ChoiceBox cbMaxPoints;
    private Label lblChooseMaxPoints;
    private ChoiceBox cbCardStyle;
    private Label lblCardStyle;
    private Label lblTitle;

    // lenght and width of the scene
    private final double sceneWidth = 700;
    private final double sceneHeight = 400;

    //Define the image for the background
    private Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));

    public SettingsView(Stage settingsStage, ClientModel model) {
        this.settingsStage = settingsStage;
        this.model = model;

        createBackgroundImage();
        createGridPane();
        rootSettings.getChildren().add(grid);

        Scene scene = new Scene(rootSettings, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        settingsStage.setScene(scene);
        settingsStage.setTitle("Jass by NellBuebe - Settings");
    }

    public void start() {
        settingsStage.show();
    }

    public void stop(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                settingsStage.hide();
            }
        });
    }

    public void setSettingStageTitle() {
        settingsStage.setTitle(t.getString("settings.title")+" - " + model.getUserName());
    }

    private void createBackgroundImage() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/background_plain.png"));
        ImageView imvBackground = new ImageView(background);
        imvBackground.setFitHeight(400);
        imvBackground.setFitWidth(700);
        rootSettings.getChildren().add(imvBackground);
    }

    public void createGridPane() {
        grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        lblTitle = new Label("Settings");
        grid.add(lblTitle, 0, 0);
        lblTitle.getStyleClass().add("login-text");

        lblCardStyle = new Label(t.getString("lobby.lbl.CardStyle"));
        cbCardStyle = new ChoiceBox<>(FXCollections.observableArrayList("DE", "FR"));
        lblCardStyle.getStyleClass().add("login-text");

        grid.add(lblCardStyle, 0, 1);
        grid.add(cbCardStyle, 1, 1);

        lblChooseMaxPoints = new Label("Choose at how many points the game should end");
        lblChooseMaxPoints.getStyleClass().add("login-text");
        cbMaxPoints = new ChoiceBox<>(FXCollections.observableArrayList(100, 1000, 1500));

        grid.add(lblChooseMaxPoints, 0, 2);
        grid.add(cbMaxPoints, 1, 2);

        btnStartGame = new Button("Start Game");
        grid.add(btnStartGame, 1, 3);
    }




    public ChoiceBox getCbMaxPoints() {
        return cbMaxPoints;
    }

    public void setCbMaxPoints(ChoiceBox cbMaxPoints) {
        this.cbMaxPoints = cbMaxPoints;
    }

    public ChoiceBox getCbCardStyle() {
        return cbCardStyle;
    }

    public void setCbCardStyle(ChoiceBox cbCardStyle) {
        this.cbCardStyle = cbCardStyle;
    }
}
