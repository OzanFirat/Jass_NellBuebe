package Client.View;

import Client.JassClient;
import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import Server.ClientThread;
import Server.ServerModel;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.security.auth.callback.ConfirmationCallback;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class GameView {
    private Stage gameStage;
    private ClientModel model;

    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator t = sl.getTranslator();


    // Root for the JassGame
    Pane rootJassGame;

    // ArrayList to place the cards in the middle
    public ArrayList<CardLabel> playedCards = new ArrayList<>();

    //Panes to add the elements needed to play
    Pane rootCards;
    Pane cardsPlayedByOpponents;
    ArrayList<Pane> oppPanes;
    Pane overlayNotYourTurn;
    Pane underlayYourCards;
    Pane underlayCardsInMiddle;
    Pane paneScoreTable;
    VBox boxTrumpfChoice;

    //Elements to display the opponents
    public Label lblPlayer2Name;
    public Label lblPlayer3Name;
    public Label lblPlayer4Name;
    public ImageView imvPlayer2;
    public ImageView imvPlayer3;
    public ImageView imvPlayer4;
    public Label lblWinner2;
    public Label lblWinner3;
    public Label lblWinner4;

    // Elements to display trumpf choice
    private ArrayList<TrumpfLabel> listTrumpfLabels;
    private Label titleChooseTrumpf;

    // ArrayList to handle the CardLabels (played Cards) of the opponents;
    public ArrayList<CardLabel> cardsPlayedByOpps;
    public CardLabel c0;
    public CardLabel c1;
    public CardLabel c2;
    public TextArea gameHistory;

    // ArrayList to save the rectangles of the overlay - needed to remove them when it's your turn
    private ArrayList<Rectangle> rectanglesOverlay;

    //Elements to display the current Trumpf
    protected Label lblTrumpf;
    protected ImageView imgTrumpf;
    protected VBox vBoxTrumpf;

    //Elements to display the score
    private TableView tvScoreTable;
    private TableColumn tvcName;
    private TableColumn tvcPoints;

    // ArrayList for the playable Cards
    public ArrayList<CardLabel> yourCards;
    public double xStartPoint = 315;
    public double yStartingPoint = 630;
    public double xSpaceCards = 5;

    // enum to define the style of the cards (DE/FR)
    private CardLabel.Style style;

    // some numbers to place & size the elements right
    private double xMiddle = 700;
    private double yMiddle = 400;
    private double cardWidth = 80;
    private double cardHeight = 130;
    private double spaceToEdge = 50;


    // lenght and width of the scene
    private final double sceneWidth = 1400;
    private final double sceneHeight = 800;

    //Define the image for the background
    private Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/background_1400x800.png"));

    // Elements to display the chatRoom
    public Button btnChatGame;

    // Elements to display gameRules
    public Label lblGameInstruction;

    public Button btngameCards;
    public Button btnMinorSuit;
    public Button btnTrump;
    public Button btnTopsDown;
    public Button btnBottomsUp;
    Group gameInstructions;

    //Elements to display the languageSetting
    public ChoiceBox<String> choiceBoxLanguageGameView;

    private Button button;


    public GameView(Stage gameStage, ClientModel model) {
        this.gameStage = gameStage;
        this.model = model;

        // set style of cards default to FR
        style = CardLabel.Style.FR;

        rootJassGame = new Pane();

        rootCards = new Pane();
        cardsPlayedByOpponents = new Pane();
        oppPanes = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Pane pane = new Pane();
            oppPanes.add(pane);
        }

        paneScoreTable = new Pane();
        overlayNotYourTurn = new Pane();

        createUnderlayYourCards();
        createUnderlayCardsInMiddle();
        createChoiceBoxLanguage();
        createGameInstructions();
        createChatButton();



        registerForShutDown();

        rootJassGame.getChildren().addAll(rootCards, cardsPlayedByOpponents, overlayNotYourTurn, paneScoreTable, gameInstructions);
        rootJassGame.getChildren().addAll(oppPanes);
        rootJassGame.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
        gameInstructions.toFront();

        Scene scene = new Scene(rootJassGame, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        gameStage.setScene(scene);
        gameStage.setTitle("Jass by NellBuebe");
        gameStage.setResizable(false);
    }


    public void start() {
        gameStage.show();
    }



    public void stop() {
        gameStage.hide();
    }

    public void addToRootJassGame(Node n) {
        rootJassGame.getChildren().add(n);
    }

    public void removeFromRootJassGame(Node n) {
        rootJassGame.getChildren().remove(n);
    }

    public void setStageTitle(String name) {
        gameStage.setTitle(t.getString("game.stage.title")+" "+name);
    }

    private void registerForShutDown() {
        gameStage.setOnCloseRequest(e -> {
            e.consume();
            showConfirmationCloseGame();
        });

        button = new Button("Close Game");
        button.setOnAction(e-> showConfirmationCloseGame());

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
    }




    public void createTrumpfChoice() {
        Platform.runLater( new Runnable() {
            public void run() {
                boxTrumpfChoice = new VBox(50);
                boxTrumpfChoice.setMinWidth(400);
                boxTrumpfChoice.setMinHeight(300);
                boxTrumpfChoice.setTranslateX(xMiddle - 200);
                boxTrumpfChoice.setTranslateY(yMiddle - 150);

                listTrumpfLabels = new ArrayList<>();

                TrumpfLabel hearts = new TrumpfLabel("hearts", style);
                TrumpfLabel clubs = new TrumpfLabel("clubs", style);
                TrumpfLabel diamonds = new TrumpfLabel("diamonds", style);
                TrumpfLabel spades = new TrumpfLabel("spades", style);

                listTrumpfLabels.add(hearts);
                listTrumpfLabels.add(clubs);
                listTrumpfLabels.add(diamonds);
                listTrumpfLabels.add(spades);

                HBox container = new HBox(20);

                container.setAlignment(Pos.CENTER);
                container.getChildren().addAll(listTrumpfLabels);

                titleChooseTrumpf = new Label("Choose Trumpf for the game");
                titleChooseTrumpf.getStyleClass().add("login-text");


                boxTrumpfChoice.getChildren().addAll(titleChooseTrumpf, container);
                boxTrumpfChoice.setAlignment(Pos.CENTER);
                boxTrumpfChoice.getStyleClass().add("trumpf-choice");

                rootJassGame.getChildren().add(boxTrumpfChoice);
            }
        });
    }

    // card placement
    public void createYourCards() {
        yourCards = new ArrayList<>();

        for (int i = 0; i < model.getYourCards().size(); i++){
            CardLabel c = new CardLabel(model.getYourCards().get(i).toString(), style);
            yourCards.add(c);
            c.setTranslateY(yStartingPoint);
            c.setTranslateX(xStartPoint + (i* xSpaceCards) +(i*cardWidth));
        }
        rootCards.getChildren().addAll(yourCards);
    }

    // creates an overlay to enable cards if it's not your turn
    public void createOverlayNotYourTurn() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                rectanglesOverlay = new ArrayList<>();

                for (int i = 0; i < model.getYourCards().size(); i++) {

                    Rectangle rect = new Rectangle(cardWidth + 1, cardHeight);
                    rect.setFill(Color.rgb(0, 0, 0, 0.5));
                    rectanglesOverlay.add(rect);
                    rect.setTranslateX(xStartPoint + (i* xSpaceCards) +(i*cardWidth));
                }
                overlayNotYourTurn.getChildren().addAll(rectanglesOverlay);
                overlayNotYourTurn.setTranslateY(yStartingPoint);

            }
        });
    }

    // add the overlay to the root
    public void showOverlayNotYourTurn() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!rootJassGame.getChildren().contains(overlayNotYourTurn)) {
                    rootJassGame.getChildren().add(overlayNotYourTurn);
                    gameInstructions.toFront();
                }
            }
        });
    }

    // method to remove the overlay when it's not your turn
    public void hideOverlayNotYourTurn() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                rootJassGame.getChildren().remove(overlayNotYourTurn);
            }
        });
    }

    public void createUnderlayYourCards() {
        underlayYourCards = new HBox(xSpaceCards);
        ArrayList<Rectangle> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Rectangle rect = new Rectangle(cardWidth, cardHeight);
            rect.setFill(Color.rgb(0, 0, 0, 0.5));
            list.add(rect);
        }
        underlayYourCards.getChildren().addAll(list);
        underlayYourCards.setTranslateX(xStartPoint);
        underlayYourCards.setTranslateY(yStartingPoint);
        rootJassGame.getChildren().add(underlayYourCards);

    }

    public void createUnderlayCardsInMiddle() {
        underlayCardsInMiddle = new Pane();
        ArrayList<Rectangle> listRectangle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Rectangle rect = new Rectangle(cardWidth, cardHeight);
            rect.setFill(Color.rgb(0, 0, 0, 0.5));
            listRectangle.add(rect);
        }
        listRectangle.get(0).setRotate(90);
        listRectangle.get(0).setTranslateX(xMiddle + 80);
        listRectangle.get(0).setTranslateY(yMiddle - cardWidth);

        listRectangle.get(1).setTranslateX(xMiddle - (cardWidth / 2));
        listRectangle.get(1).setTranslateY(yMiddle - cardHeight - 20);

        listRectangle.get(2).setRotate(270);
        listRectangle.get(2).setTranslateX(xMiddle - 160);
        listRectangle.get(2).setTranslateY(yMiddle - cardWidth);

        listRectangle.get(3).setTranslateX(xMiddle - (cardWidth / 2));
        listRectangle.get(3).setTranslateY(yMiddle + 10);

        underlayCardsInMiddle.getChildren().addAll(listRectangle);
        rootJassGame.getChildren().add(underlayCardsInMiddle);
    }

    public void createPlayer2() {

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/myAvatar_1.png"));
        imvPlayer2 = new ImageView(back);
        imvPlayer2.setPreserveRatio(true);
        imvPlayer2.setFitWidth(100);

        // Create the label to show player name
        lblPlayer2Name = new Label(model.getOppPlayerNames().get(0));
        lblPlayer2Name.setMinWidth(imvPlayer2.getFitWidth());
        lblPlayer2Name.getStyleClass().add("oppName");

        lblWinner2 = new Label("Winner");
        lblWinner2.getStyleClass().add("winner-text");
        lblWinner2.setVisible(false);
        lblWinner2.setMinWidth(imvPlayer2.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer2Name, imvPlayer2, lblWinner2);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(0).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(0).setRotate(90);
        oppPanes.get(0).setTranslateX(1400 - (spaceToEdge + cardHeight));
        oppPanes.get(0).setTranslateY(yMiddle - cardWidth);
    }

    public void createPlayer3() {

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/myAvatar_2.png"));
        imvPlayer3 = new ImageView(back);
        imvPlayer3.setPreserveRatio(true);
        imvPlayer3.setFitWidth(100);

        // Create the label to show player name
        lblPlayer3Name = new Label(model.getOppPlayerNames().get(1));
        lblPlayer3Name.setMinWidth(imvPlayer3.getFitWidth());
        lblPlayer3Name.getStyleClass().add("oppName");

        lblWinner3 = new Label("Winner");
        lblWinner3.getStyleClass().add("winner-text");
        lblWinner3.setVisible(false);
        lblWinner3.setMinWidth(imvPlayer3.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer3Name, imvPlayer3, lblWinner3);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(1).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(1).setTranslateX(xMiddle - 50);
        oppPanes.get(1).setTranslateY(spaceToEdge);
    }

    public void createPlayer4() {

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/myAvatar_3.png"));
        imvPlayer4 = new ImageView(back);
        imvPlayer4.setFitWidth(100);
        imvPlayer4.setPreserveRatio(true);

        // Create the label to show player name
        lblPlayer4Name = new Label(model.getOppPlayerNames().get(2));
        lblPlayer4Name.setMinWidth(imvPlayer4.getFitWidth());
        lblPlayer4Name.getStyleClass().add("oppName");

        lblWinner4 = new Label("Winner");
        lblWinner4.getStyleClass().add("winner-text");
        lblWinner4.setVisible(false);
        lblWinner4.setMinWidth(imvPlayer4.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer4Name, imvPlayer4, lblWinner4);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(2).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(2).setRotate(270);
        oppPanes.get(2).setTranslateX(spaceToEdge+20);
        oppPanes.get(2).setTranslateY(yMiddle - cardWidth);
    }

    public void updateCardsPlayedByOpps(String cardName, int indexOfCurrentPlayer) {
        // cardsPlayedByOpponents.getChildren().removeAll(cardsPlayedByOpps);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (cardsPlayedByOpps == null) {
                    cardsPlayedByOpps = new ArrayList<>(3);
                    cardsPlayedByOpps.add(null);
                    cardsPlayedByOpps.add(null);
                    cardsPlayedByOpps.add(null);

                }
                switch (indexOfCurrentPlayer) {
                    case 0:
                        // Place the card for the player on the right side
                        c0 = new CardLabel(cardName, style);
                        c0.setRotate(90);
                        c0.setTranslateX(xMiddle + 80);
                        c0.setTranslateY(yMiddle - cardWidth);

                        cardsPlayedByOpps.set(indexOfCurrentPlayer, c0);
                        cardsPlayedByOpponents.getChildren().add(c0);

                        System.out.println("Next cards to be played at right side");
                        break;
                    case 1:
                        // Place the card for the player on the top
                        // Place the card for the player on the right side
                        c1 = new CardLabel(cardName, style);

                        c1.setTranslateX(xMiddle - (cardWidth / 2));
                        c1.setTranslateY(yMiddle - cardHeight - 20);

                        cardsPlayedByOpps.set(indexOfCurrentPlayer, c1);
                        cardsPlayedByOpponents.getChildren().add(c1);
                        System.out.println("Next card to be played on top side");
                        break;
                    case 2:
                        c2 = new CardLabel(cardName, style);
                        c2.setRotate(270);
                        c2.setTranslateX(xMiddle - 160);
                        c2.setTranslateY(yMiddle - cardWidth);

                        cardsPlayedByOpps.set(indexOfCurrentPlayer, c2);
                        cardsPlayedByOpponents.getChildren().add(c2);
                        System.out.println("Next card to be played on left side");
                        break;
                }

                System.out.println("Card to be placed is: " + cardName);
            }
        });
    }

    public void createTrumpfElements() {
        vBoxTrumpf = new VBox(10);
        // Getting the image and label to display the trumpf done
        lblTrumpf = new Label(t.getString("game.lbl.trump"));
        lblTrumpf.setMinWidth(70);
        lblTrumpf.getStyleClass().add("login-text");

        HBox imgContainer = new HBox();

        imgTrumpf = loadTrumpfImage();
        imgContainer.getChildren().add(imgTrumpf);
        imgContainer.getStyleClass().add("trumpf");

        vBoxTrumpf.getChildren().addAll(lblTrumpf, imgContainer);
        vBoxTrumpf.setTranslateX(950);
        vBoxTrumpf.setTranslateY(spaceToEdge);
        rootJassGame.getChildren().add(vBoxTrumpf);
    }

    // TableView to show the score during the game
    public void createTableViewScore() {
        tvScoreTable = new TableView();
        tvcName = new TableColumn(t.getString("game.tableColumn.tvcName"));
        tvcPoints = new TableColumn(t.getString("game.tableColumn.tvcPoint"));
        tvScoreTable.getColumns().addAll(tvcName, tvcPoints);

        tvcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvcPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        tvScoreTable.setMaxHeight(141);
        tvScoreTable.setMaxWidth(160);

        tvScoreTable.setItems(model.getPlayerWithPoints());

        tvScoreTable.setTranslateX(1100);
        tvScoreTable.setTranslateY(spaceToEdge);

        // Create a rectangle to fill gap between frame and table
        Rectangle rect = new Rectangle(160, 141);
        rect.setTranslateX(1100);
        rect.setTranslateY(spaceToEdge);
        rect.setFill(Color.BLACK);

        paneScoreTable.getChildren().addAll(rect, tvScoreTable);
    }

    public void createGameHistory() {
        gameHistory = new TextArea("");
        gameHistory.setMaxWidth(300);
        gameHistory.setMaxHeight(150);
        gameHistory.setTranslateX(50);
        gameHistory.setTranslateY(50);
        gameHistory.setEditable(false);
        rootJassGame.getChildren().add(gameHistory);
    }

    public void doPlayYourCard(CardLabel card) {
        card.setTranslateX(xMiddle - (cardWidth / 2));
        card.setTranslateY(yMiddle + 10);
    }

    public void removeCardsInMiddle() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (CardLabel card : yourCards) {
                    if (card.getTranslateX() == xMiddle - (cardWidth / 2) && card.getTranslateY() == yMiddle + 10) {
                        rootCards.getChildren().remove(card);
                    }
                }

                cardsPlayedByOpponents.getChildren().removeAll(c0, c1, c2);
            }
        });
    }

    public void setOpponentCards() {
        cardsPlayedByOpponents.getChildren().addAll(playedCards);
    }

    private ImageView loadTrumpfImage() {
        String fileName = model.getTrumpf();
        if (style.equals(CardLabel.Style.DE)) {
            fileName += "_de";
        }
        fileName += ".png";
        Image trumpf = new Image(getClass().getClassLoader().getResourceAsStream("images/" + fileName));
        ImageView img = new ImageView();
        img.setImage(trumpf);
        img.setFitWidth(50);
        img.setFitHeight(50);
        return img;
    }

    public void showRoundWinner(String winnerName) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (winnerName.equals(model.getOppPlayerNames().get(0))) {
                    lblWinner2.setVisible(true);
                }
                if (winnerName.equals(model.getOppPlayerNames().get(1))) {
                    lblWinner3.setVisible(true);
                }
                if (winnerName.equals(model.getOppPlayerNames().get(2))) {
                    lblWinner4.setVisible(true);
                }
            }
        });

    }

    public void hideRoundWinner(String winnerName) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (winnerName.equals(model.getOppPlayerNames().get(0))) {
                    lblWinner2.setVisible(false);
                }
                if (winnerName.equals(model.getOppPlayerNames().get(1))) {
                    lblWinner3.setVisible(false);
                }
                if (winnerName.equals(model.getOppPlayerNames().get(2))) {
                    lblWinner4.setVisible(false);
                }
            }
        });

    }

    public void createChoiceBoxLanguage() {
        // defined languages
        choiceBoxLanguageGameView = new ChoiceBox<>();
        choiceBoxLanguageGameView.setValue("DE");
        choiceBoxLanguageGameView.getItems().add("EN");
        choiceBoxLanguageGameView.getItems().add("DE");
        choiceBoxLanguageGameView.setId("languageSetting");
        choiceBoxLanguageGameView.setTranslateX(1300);
        choiceBoxLanguageGameView.setTranslateY(725);

        choiceBoxLanguageGameView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                t = sl.getTranslator();
                updateGameViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                t = sl.getTranslator();
                updateGameViewTexts();
            }
        });
        rootJassGame.getChildren().add(choiceBoxLanguageGameView);
    }

    public void createChatButton() {
        // defined Chat-Elements in gameView
        btnChatGame = new Button(t.getString("game.button.chatroom"));
        btnChatGame.setId("chatButton");
        btnChatGame.setTranslateX(1100);
        btnChatGame.setTranslateY(725);
        rootJassGame.getChildren().add(btnChatGame);
    }

    public void createGameInstructions() {
        // defined Rules-Elements in gameView
        lblGameInstruction = new Label(t.getString("game.lbl.gameInstruction"));
        lblGameInstruction.setAlignment(Pos.CENTER);
        lblGameInstruction.setId("titleRules");
        lblGameInstruction.setTranslateX(60);
        lblGameInstruction.setTranslateY(605);

        btngameCards = new Button(t.getString("game.button.playingCards"));
        btngameCards.setId("rules");
        btngameCards.setTranslateX(60);
        btngameCards.setTranslateY(625);

        btnTrump = new Button(t.getString("game.button.trump"));
        btnTrump.setId("rules");
        btnTrump.setTranslateX(60);
        btnTrump.setTranslateY(650);

        btnMinorSuit = new Button(t.getString("game.button.minorSuit"));
        btnMinorSuit.setId("rules");
        btnMinorSuit.setTranslateX(60);
        btnMinorSuit.setTranslateY(675);

        btnTopsDown = new Button(t.getString("game.button.topsDown"));
        btnTopsDown.setId("rules");
        btnTopsDown.setTranslateX(60);
        btnTopsDown.setTranslateY(700);

        btnBottomsUp = new Button(t.getString("game.button.bottomsUp"));
        btnBottomsUp.setId("rules");
        btnBottomsUp.setTranslateX(60);
        btnBottomsUp.setTranslateY(725);

        gameInstructions = new Group(btngameCards, btnTrump, btnMinorSuit, btnBottomsUp, btnTopsDown, lblGameInstruction);
    }

    // needs to be optimized TODO
    public void showConfirmationCloseGame(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Leaving... :(");
        alert.setContentText("Are you sure u want to exit, game can't  be proceeded anymore");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    // methods for gameInstructions
    public void showPlayingCards() {
        if (choiceBoxLanguageGameView.getValue() == "EN") {
            Alert rules = new Alert(Alert.AlertType.NONE, "You can choose between a deck of German playing cards or a deck of French playing cards.\n" +
                    "\n" + "A deck of cards has 4 suits, each of which contains 9 playing cards. A total of 36 cards are used in the game. For every type of Jass game" +
                    " played on Jass.ch 3 × 3 cards are dealt, i.e. each player receives 9 cards.\n" +
                    "\n" + "The total value of all counters in the deck is 152 for Differenzler, Schieber and Coiffeur. Taking the last trick scores an additional 5 points, which means there are a total of 157 points to be won.");
            rules.setTitle("Playing Cards");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Grundlagen_eng.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        } else {
            Alert rules = new Alert(Alert.AlertType.NONE, "Sie können zwischen einem Kartenset mit deutschen Farben (deutsches Blatt) und einem Kartenset mit französischen Farben (französisches Blatt) wählen.\n" +
                    "\n" + "Ein Kartenset besteht aus 4 Farben und jede Farbe umfasst 9 Spielkarten. Insgesamt sind 36 Karten im Spiel. Bei jeder auf Jass.ch gespielten Jass-Art werden 3 × 3 Karten verteilt, d.h. jeder Spieler erhält 9 Karten.\n" +
                    "\n" + "Beim Differenzler, Schieber und Coiffeur ergibt das Punktetotal der Karten 152 Punkte und fünf Punkte für den letzten Stich. Insgesamt sind also 157 Punkte im Spiel.");
            rules.setTitle("Spielkarten");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Grundlagen_de.jpg"));

            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        }
    }

    public void showTrumpRule() {
        if (choiceBoxLanguageGameView.getValue() == "EN") {
            Alert rules = new Alert(Alert.AlertType.NONE, "Trump\n" +
                    "\n" +
                    "The suit declared trumps beats cards of all ranks in the minor suits. " +
                    "The ranking within the trump suit as well as the point values are shown in " +
                    "the adjoining table. A higher-ranked card beats a lower-ranked one.");
            rules.setTitle("Trump");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Trumpf_Rangfolge_Kartenwerte_eng.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        } else {
            Alert rules = new Alert(Alert.AlertType.NONE, "Trumpf\n" +
                    "\n" + "Wenn eine Farbe als Trumpffarbe angesagt ist, dann sticht die Trumpffarbe " +
                    "jeden Rang einer Nebenfarbe. Die Rangfolge innerhalb der Trumpffarbe sowie die Punktwerte " +
                    "gelten gemäss der nebenstehenden Abbildung. Eine höhere Karte sticht eine tiefere Karte.");
            rules.setTitle("Trumpf");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Trumpf_Rangfolge_Kartenwerte_de.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        }
    }

    public void showMinorSuitRule() {
        if (choiceBoxLanguageGameView.getValue() == "EN") {
            Alert rules = new Alert(Alert.AlertType.NONE, "Minor suit\n" +
                    "\n" +
                    "The adjoining table shows the rankings within the minor suits as well" +
                    " as the point values. A higher-ranked card beats a lower-ranked one.");
            rules.setTitle("Minor suit");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Nebenfarbe_Rangfolge_Kartenwerte_eng.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        } else {
            Alert rules = new Alert(Alert.AlertType.NONE, "Nebenfarbe\n" +
                    "\n" +
                    "Die Rangfolge innerhalb der Nebenfarben sowie die Punktwerte gelten gemäss der nebenstehenden Abbildung. Eine höhere Karte sticht eine tiefere Karte.");
            rules.setTitle("Nebenfarbe");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Nebenfarbe_Rangfolge_Kartenwerte_de.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        }
    }

    public void showTopsDownRule() {
        if (choiceBoxLanguageGameView.getValue() == "EN") {
            Alert rules = new Alert(Alert.AlertType.NONE, "Tops-down\n" +
                    "\n" +
                    "There is no trump suit in tops-down. The adjoining table shows the ranking as well as the point values. In tops-down, Ace is highest. A higher-ranked card also beats a lower-ranked card in both tops-down and bottoms-up.");
            rules.setTitle("Tops-down");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Obenabe_Rangfolge_Kartenwerte_eng.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        } else {
            Alert rules = new Alert(Alert.AlertType.NONE, "Obenabe\n" +
                    "\n" +
                    "Beim Obenabe gibt es keine Trumpffarbe. Die Rangfolge sowie die Punktwerte gelten gemäss der nebenstehenden Abbildung. Beim Obenabe ist das Ass die höchste Stechkarte. Auch beim Obenabe und Undenufe gilt, dass eine höhere Karte eine tiefere Karte sticht.");
            rules.setTitle("Obenabe");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Obenabe_Rangfolge_Kartenwerte_de.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        }
    }

    public void showBottomsUpRule() {
        if (choiceBoxLanguageGameView.getValue() == "EN") {
            Alert rules = new Alert(Alert.AlertType.NONE, "Bottoms-up\n" +
                    "\n" +
                    "There is no trump suit in bottoms-up. The adjoining table shows the ranking as well as the point values. In bottoms-up, Six is highest. A higher-ranked card also beats a lower-ranked card in both tops-down and bottoms-up.");
            rules.setTitle("Bottoms-up");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Udenufe_Rangfolge_Kartenwerte_eng.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        } else {
            Alert rules = new Alert(Alert.AlertType.NONE, "Udenufe\n" +
                    "\n" +
                    "Beim Undenufe gibt es keine Trumpffarbe. Die Rangfolge sowie die Punktwerte gelten gemäss der nebenstehenden Abbildung. Beim Undenufe ist hingegen die 6 die stärkste Stechkarte. Auch beim Obenabe und Undenufe gilt, dass eine höhere Karte eine tiefere Karte sticht.");
            rules.setTitle("Udenufe");
            Image pCards = new Image(getClass().getClassLoader().getResourceAsStream("images/Jass-Udenufe_Rangfolge_Kartenwerte_de.jpg"));
            ImageView pCardView = new ImageView(pCards);
            pCardView.setFitHeight(400);
            pCardView.setFitWidth(450);
            rules.setGraphic(pCardView);
            rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
            rules.showAndWait();
        }
    }

    protected void updateGameViewTexts() {
        // Menus
        btnChatGame.setText(t.getString("game.button.chatroom"));
        lblGameInstruction.setText(t.getString("game.lbl.gameInstruction"));
        btngameCards.setText(t.getString("game.button.playingCards"));
        btnTrump.setText(t.getString("game.button.trump"));
        btnMinorSuit.setText(t.getString("game.button.minorSuit"));
        btnTopsDown.setText(t.getString("game.button.topsDown"));
        btnBottomsUp.setText(t.getString("game.button.bottomsUp"));
        tvcName.setText(t.getString("game.tableColumn.tvcName"));
        tvcPoints.setText(t.getString("game.tableColumn.tvcPoint"));
        lblTrumpf.setText(t.getString("game.lbl.trump"));
    }

    public void showAlertGameFinished(String leader) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert gameFinished = new Alert(Alert.AlertType.INFORMATION);
                gameFinished.setTitle("The Game is finished");
                gameFinished.setHeaderText("All Cards are played, the current leader is "+leader);
                gameFinished.setContentText("The next game round is about to start");
                gameFinished.showAndWait();
            }
        });
    }

    public ArrayList<Pane> getOppPanes() {
        return oppPanes;
    }

    public void setOppPanes(ArrayList<Pane> oppPanes) {
        this.oppPanes = oppPanes;
    }

    public TableView getTvScoreTable() {
        return tvScoreTable;
    }

    public CardLabel.Style getStyle() {
        return style;
    }

    public void setStyle(CardLabel.Style style) {
        this.style = style;
    }

    public ArrayList<TrumpfLabel> getListTrumpfLabels() {
        return listTrumpfLabels;
    }

    public VBox getBoxTrumpfChoice() {
        return boxTrumpfChoice;
    }

    public VBox getvBoxTrumpf() {
        return vBoxTrumpf;
    }
}