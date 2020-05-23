package Client.View;

import Client.JassClient;
import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class GameOverView {
    private Stage gameOverStage;
    private ClientModel model;

    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator t = sl.getTranslator();

    private Scene sceneGameOver;

    public Pane root;
    public Pane rootTable;

    public TableView tvScoreTable;
    public TableColumn tvcName;
    public TableColumn tvcPoints;



    public Button btnExit;
    public Button btnBackToLobby;

    private Image background ;
    private ImageView imvBackground;

    public Label title;
    public Label lblResult;
    public Label lblWinner;
    public Label lblWinnerName;

    // lenght and width of the scene
    private final double sceneWidth = 1000;
    private final double sceneHeight = 517;

    //Elements to display the languageSetting
    public ChoiceBox<String> choiceBoxLanguageGameOverView;

    public GameOverView(Stage gameOverStage, ClientModel model) {
        this.gameOverStage = gameOverStage;
        this.model = model;

        btnExit = new Button (t.getString("gameOver.btn.Exit"));
        title = new Label (t.getString("gameOver.lbl.Title"));
        lblResult = new Label(t.getString("gameOver.lbl.Results"));
        btnBackToLobby = new Button("Back to Lobby");

        // defined languages
        choiceBoxLanguageGameOverView = new ChoiceBox<>();
        choiceBoxLanguageGameOverView.setValue("DE");
        choiceBoxLanguageGameOverView.getItems().add("EN");
        choiceBoxLanguageGameOverView.getItems().add("DE");

        root = new Pane();
        createBackgroundImage();
        createLabels();
        placeButton();

        rootTable = new Pane();
        createTableView();
        root.getChildren().add(rootTable);

        choiceBoxLanguageGameOverView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                t = sl.getTranslator();
                updateGameOverViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                t = sl.getTranslator();
                updateGameOverViewTexts();
            }
        });

        sceneGameOver = new Scene(root, sceneWidth, sceneHeight);
        sceneGameOver.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        gameOverStage.setScene(sceneGameOver);
        gameOverStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png")));

    }

    public void start() {
        gameOverStage.show();
    }

    public void stop() {
        gameOverStage.hide();
    }

    public void setGameOverStageTitle() {
        gameOverStage.setTitle(t.getString("gameOver.stage.title") +" "+model.getUserName());
    }

    private void createBackgroundImage() {
        background = new Image(getClass().getClassLoader().getResourceAsStream("images/login_background_medium.jpg"));
        imvBackground = new ImageView(background);
        imvBackground.setFitHeight(sceneHeight);
        imvBackground.setFitWidth(sceneWidth);
        root.getChildren().add(imvBackground);
    }

    private void createLabels() {
        title = new Label (t.getString("gameOver.lbl.Title"));
        title.setTranslateX(600);
        title.setTranslateY(100);
        title.getStyleClass().add("login-text");

        lblResult = new Label(t.getString("gameOver.lbl.Results"));
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
        btnExit.getStyleClass().add("basic-button");
        btnBackToLobby.getStyleClass().add("basic-button");

        btnExit.setTranslateX(750);
        btnExit.setTranslateY(420);

        btnBackToLobby.setTranslateX(600);
        btnBackToLobby.setTranslateY(420);

        choiceBoxLanguageGameOverView.setTranslateX(750);
        choiceBoxLanguageGameOverView.setTranslateY(220);

        root.getChildren().addAll(btnExit,choiceBoxLanguageGameOverView, btnBackToLobby);
    }

    public void createTableView() {

        tvScoreTable = new TableView();
        tvcName = new TableColumn(t.getString("gameOver.tableView.Name"));
        tvcPoints = new TableColumn(t.getString("gameOver.tableView.Points"));
        tvScoreTable.getColumns().addAll(tvcName, tvcPoints);

        tvcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvcPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        tvScoreTable.setMaxHeight(142);
        tvScoreTable.setMaxWidth(160);

        tvScoreTable.setItems(model.getPlayerWithPoints());

        tvScoreTable.setTranslateX(600);
        tvScoreTable.setTranslateY(260);

        rootTable.getChildren().add(tvScoreTable);
    }

    public void setWinner(String winnerName) {
        lblWinner.setText(t.getString("gameOver.lbl.Winner"));
        lblWinnerName.setText(""+ winnerName);
    }

    public void showAlertGameOver(String winner) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert gameOver = new Alert(Alert.AlertType.INFORMATION);
                gameOver.setTitle("Game Over");
                gameOver.setHeaderText("The game is over - Max Points are reached");
                gameOver.setContentText("Click OK to go to Game Over window");
                Optional<ButtonType> result = gameOver.showAndWait();
                if (result.isPresent()) {
                    JassClient.mainProgram.getGameController().changeToGameOverScene(winner);
                }
            }
        });
    }

    protected void updateGameOverViewTexts() {

        btnExit.setText(t.getString("gameOver.btn.Exit"));
        title.setText(t.getString("gameOver.lbl.Title"));
        lblResult.setText(t.getString("gameOver.lbl.Results"));

        lblWinner.setText(t.getString("gameOver.lbl.Winner"));


        tvcName.setText(t.getString("gameOver.tableView.Name"));
        tvcPoints.setText(t.getString("gameOver.tableView.Points"));

    }
}
