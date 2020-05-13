package Client.View;

import Client.Model.ClientModel;
import Client.Model.PlayerScoreTuple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class GameOverView {
    private Stage gameOverStage;
    private ClientModel model;

    private Scene sceneGameOver;

    private Pane root;
    private Pane rootTable;

    private TableView tvScoreTable;
    private TableColumn tvcName;
    private TableColumn tvcPoints;

    private Button btnExit = new Button("Exit");
    private Image background ;
    private ImageView imvBackground;

    private Label title = new Label("The game is over");
    private Label lblResult = new Label("Results");
    private Label lblWinner;

    // lenght and width of the scene
    private final double sceneWidth = 900;
    private final double sceneHeight = 517;

    public GameOverView(Stage gameOverStage, ClientModel model) {
        this.gameOverStage = gameOverStage;
        this.model = model;

        root = new Pane();
        createBackgroundImage();
        placeLabels();
        placeButton();

        rootTable = new Pane();
        createTableView();
        root.getChildren().add(rootTable);
        sceneGameOver = new Scene(root, sceneWidth, sceneHeight);
        sceneGameOver.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        gameOverStage.setScene(sceneGameOver);

    }

    private void createBackgroundImage() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));
        imvBackground = new ImageView(background);
        imvBackground.setFitHeight(sceneHeight);
        imvBackground.setFitWidth(sceneWidth);
        root.getChildren().add(imvBackground);
    }

    private void placeLabels() {
        title.setTranslateX(600);
        title.setTranslateY(100);
        title.getStyleClass().add("login-text");

        lblResult.setTranslateX(600);
        lblResult.setTranslateY(220);
        lblResult.getStyleClass().add("login-text");

        lblWinner = new Label("Winner: ");
        lblWinner.setTranslateX(600);
        lblWinner.setTranslateY(160);
        lblWinner.getStyleClass().add("login-text");
        root.getChildren().addAll(lblResult, title, lblWinner);
    }

    private void placeButton() {
        btnExit.setTranslateX(720);
        btnExit.setTranslateY(420);
        root.getChildren().add(btnExit);
    }

    public void createTableView() {


        tvScoreTable = new TableView();
        tvcName = new TableColumn("PlayerName");
        tvcPoints = new TableColumn("Points");
        tvScoreTable.getColumns().addAll(tvcName, tvcPoints);

        tvcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvcPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        tvScoreTable.setMaxHeight(140);
        tvScoreTable.setMaxWidth(155);


        tvScoreTable.setItems(model.getPlayerWithPoints());

        tvScoreTable.setTranslateX(600);
        tvScoreTable.setTranslateY(250);

        rootTable.getChildren().add(tvScoreTable);
    }

    /*private void findWinner() {
        PlayerScoreTuple winner = model.getPlayerWithPoints().get(0);
        for (PlayerScoreTuple pst : model.getPlayerWithPoints()) {
            winner = pst.comparePoints(winner);
        }
        lblWinner.setText("Winner: "+winner.getName().toString());
    }


     */


    public void start() {
        gameOverStage.show();
    }

    public void stop() {
        gameOverStage.hide();
    }

    public void setWinner(String winnerName) {
        lblWinner.setText("Winner: " + winnerName);
    }
}
