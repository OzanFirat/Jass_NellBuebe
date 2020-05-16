package Client.View;

import Client.Model.ClientModel;
import Client.Model.PlayerScoreTuple;
import Common.ServiceLocator;
import Common.Translator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public Pane root;
    public Pane rootTable;

    public TableView tvScoreTable;
    public TableColumn tvcName;
    public TableColumn tvcPoints;



    public Button btnExit;
    private Image background ;
    private ImageView imvBackground;

    public Label title;
    public Label lblResult;
    public Label lblWinner;
    public Label lblWinnerName;

    // lenght and width of the scene
    private final double sceneWidth = 900;
    private final double sceneHeight = 517;

    //Elements to display the languageSetting
    public ChoiceBox<String> choiceBoxLanguageGameOverView;

    public GameOverView(Stage gameOverStage, ClientModel model) {
        this.gameOverStage = gameOverStage;
        this.model = model;

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        btnExit = new Button (t.getString("gameOver.btn.Exit"));
        title = new Label (t.getString("gameOver.lbl.Title"));
        lblResult = new Label(t.getString("gameOver.lbl.Results"));

        // defined languages
        choiceBoxLanguageGameOverView = new ChoiceBox<>();
        choiceBoxLanguageGameOverView.setValue("DE");
        choiceBoxLanguageGameOverView.getItems().add("EN");
        choiceBoxLanguageGameOverView.getItems().add("DE");

        root = new Pane();
        createBackgroundImage();
        placeLabels();
        placeButton();

        rootTable = new Pane();
        createTableView();
        root.getChildren().add(rootTable);

        choiceBoxLanguageGameOverView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                updateGameOverViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                updateGameOverViewTexts();
            }
        });

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
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        title.setTranslateX(600);
        title.setTranslateY(100);
        title.getStyleClass().add("login-text");

        lblResult.setTranslateX(600);
        lblResult.setTranslateY(220);
        lblResult.getStyleClass().add("login-text");

        lblWinner = new Label(t.getString("gameOver.lbl.Winner"));
        lblWinner.setTranslateX(600);
        lblWinner.setTranslateY(160);
        lblWinner.getStyleClass().add("login-text");


        lblWinnerName = new Label();
        lblWinnerName.setTranslateX(680);
        lblWinnerName.setTranslateY(160);
        lblWinnerName.getStyleClass().add("login-text");

        root.getChildren().addAll(lblResult, title, lblWinner,lblWinnerName);
    }

    private void placeButton() {
        btnExit.setTranslateX(720);
        btnExit.setTranslateY(420);

        choiceBoxLanguageGameOverView.setTranslateX(600);
        choiceBoxLanguageGameOverView.setTranslateY(420);

        root.getChildren().addAll(btnExit,choiceBoxLanguageGameOverView);
    }

    public void createTableView() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        tvScoreTable = new TableView();
        tvcName = new TableColumn(t.getString("gameOver.tableView.Name"));
        tvcPoints = new TableColumn(t.getString("gameOver.tableView.Points"));
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
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        lblWinner.setText(t.getString("gameOver.lbl.Winner"));
        lblWinnerName.setText(""+ winnerName);
    }

    protected void updateGameOverViewTexts() {

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        btnExit.setText(t.getString("gameOver.btn.Exit"));
        title.setText(t.getString("gameOver.lbl.Title"));
        lblResult.setText(t.getString("gameOver.lbl.Results"));

        lblWinner.setText(t.getString("gameOver.lbl.Winner"));


        tvcName.setText(t.getString("gameOver.tableView.Name"));
        tvcPoints.setText(t.getString("gameOver.tableView.Points"));

    }
}
