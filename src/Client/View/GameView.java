package Client.View;

import Client.Model.ClientModel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameView {
    private Stage gameStage;
    private ClientModel model;

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
    public ArrayList<CardLabel> yourCards = new ArrayList<>(9);
    public double xStartPoint = 314.375;
    public double yStartingPoint = 630;
    public double xSpace = 85.25;

    // enum to define the style of the cards (DE/FR)
    private CardLabel.Style style;

    // some numbers to place & size the elements right
    private double xMiddle = 700;
    private double yMiddle = 400;
    private double cardWidth = 81.25;
    private double cardHeight = 130;
    private double spaceToEdge = 50;

    // lenght and width of the scene
    private final double sceneWidth = 1400;
    private final double sceneHeight = 800;

    //Define the image for the background
    private Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/background_1400x800.png"));

    public GameView(Stage gameStage, ClientModel model) {
        this.gameStage = gameStage;
        this.model = model;

        // set style of cards default to FR
        style = CardLabel.Style.FR;

        rootJassGame = new Pane();

        rootCards = new Pane();
        cardsPlayedByOpponents = new Pane();
        oppPanes = new ArrayList<>(3);
        for (int i = 0; i < 3; i++){
            Pane pane = new Pane();
            oppPanes.add(pane);
        }

        paneScoreTable = new Pane();

        vBoxTrumpf = new VBox(10);
        overlayNotYourTurn = new Pane();

        createUnderlayYourCards();
        createUnderlayCardsInMiddle();

        rootJassGame.getChildren().addAll(rootCards, vBoxTrumpf, cardsPlayedByOpponents, overlayNotYourTurn, paneScoreTable);
        rootJassGame.getChildren().addAll(oppPanes);
        rootJassGame.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));

        Scene scene = new Scene(rootJassGame, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        gameStage.setScene(scene);
        gameStage.setTitle("Jass by NellBuebe");
    }

    public void start(){
        gameStage.show();
    }

    public void stop(){
        gameStage.hide();
    }

    public void addToRootJassGame(Node n) {
        rootJassGame.getChildren().add(n);
    }

    public void removeFromRootJassGame(Node n) {
        rootJassGame.getChildren().remove(n);
    }

    public void createYourCards () {
        for (int i = 0; i < model.getYourCards().size(); i++){
            CardLabel c = new CardLabel(model.getYourCards().get(i).toString(), style);
            yourCards.add(c);
            c.setTranslateY(yStartingPoint);
            c.setTranslateX(xStartPoint + (i*xSpace));
        }
        rootCards.getChildren().addAll(yourCards);
    }

    // creates an overlay to enable cards if it's not your turn
    public void createOverlayNotYourTurn() {


        Platform.runLater(new Runnable(){

            @Override
            public void run() {

                rectanglesOverlay = new ArrayList<>();

                    for (int i = 0; i < model.getYourCards().size(); i++) {
                        Rectangle rect = new Rectangle(cardWidth, cardHeight);
                        rect.setTranslateY(yStartingPoint);
                        rect.setTranslateX(xStartPoint + (i * xSpace));
                        rect.setFill(Color.rgb(0, 0, 0, 0.5));
                        rectanglesOverlay.add(rect);
                    }
                overlayNotYourTurn.getChildren().addAll(rectanglesOverlay);
            }
        });
    }

    // add the overlay to the root
    public void showOverlayNotYourTurn() {
        Platform.runLater( new Runnable() {
            @Override
            public void run() {
                if(!rootJassGame.getChildren().contains(overlayNotYourTurn)) {
                    rootJassGame.getChildren().add(overlayNotYourTurn);
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
        underlayYourCards = new Pane();
        ArrayList<Rectangle> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Rectangle rect = new Rectangle(cardWidth, cardHeight);
            rect.setTranslateY(yStartingPoint);
            rect.setTranslateX(xStartPoint + (i * xSpace));
            rect.setFill(Color.rgb(0, 0, 0, 0.5));
            list.add(rect);
        }
        underlayYourCards.getChildren().addAll(list);
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

        listRectangle.get(1).setTranslateX(xMiddle - (cardWidth/2));
        listRectangle.get(1).setTranslateY(yMiddle - cardHeight - 20);

        listRectangle.get(2).setRotate(270);
        listRectangle.get(2).setTranslateX(xMiddle - 160);
        listRectangle.get(2).setTranslateY(yMiddle - cardWidth);

        listRectangle.get(3).setTranslateX(xMiddle - (cardWidth/2));
        listRectangle.get(3).setTranslateY(yMiddle + 10);

        underlayCardsInMiddle.getChildren().addAll(listRectangle);
        rootJassGame.getChildren().add(underlayCardsInMiddle);
    }

    public void createPlayer2(){

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
        lblWinner2.setRotate(90);
        lblWinner2.setTranslateX(1100);
        lblWinner2.setTranslateY(yMiddle - (cardWidth /2));
        lblWinner2.getStyleClass().add("login-text");
        lblWinner2.setVisible(false);

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer2Name, imvPlayer2);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(0).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(0).setRotate(90);
        oppPanes.get(0).setTranslateX(1400 - (spaceToEdge + cardHeight));
        oppPanes.get(0).setTranslateY(yMiddle - cardWidth);
    }

    public void createPlayer3(){

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
        lblWinner3.setTranslateY(180);
        lblWinner3.setTranslateX(xMiddle - 25);
        lblWinner3.getStyleClass().add("login-text");
        lblWinner3.setVisible(false);

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer3Name, imvPlayer3);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(1).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(1).setTranslateX(xMiddle - 50);
        oppPanes.get(1).setTranslateY(spaceToEdge);
    }

    public void createPlayer4(){

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
        lblWinner4.setTranslateX(180);
        lblWinner4.setTranslateY(yMiddle- (cardWidth/2));
        lblWinner4.setRotate(270);
        lblWinner4.getStyleClass().add("login-text");
        lblWinner4.setVisible(false);

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer4Name, imvPlayer4);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane

        oppPanes.get(2).getChildren().add(container);

        // Place the pane at the right place
        oppPanes.get(2).setRotate(270);
        oppPanes.get(2).setTranslateX(spaceToEdge+20);
        oppPanes.get(2).setTranslateY(yMiddle - cardWidth);
    }

    public void updateCardsPlayedByOpps (String cardName, int indexOfCurrentPlayer) {
        // cardsPlayedByOpponents.getChildren().removeAll(cardsPlayedByOpps);
        Platform.runLater( new Runnable() {

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

                        c1.setTranslateX(xMiddle - (cardWidth/2));
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

                System.out.println("Card to be placed is: "+cardName);
            }
        });
    }



    public void createTrumpfElements() {
        // Getting the image and label to display the trumpf done
        lblTrumpf = new Label("Trumpf");
        lblTrumpf.setMinWidth(70);
        lblTrumpf.getStyleClass().add("login-text");

        HBox imgContainer = new HBox();

        imgTrumpf = loadTrumpfImage();
        imgContainer.getChildren().add(imgTrumpf);
        imgContainer.getStyleClass().add("trumpf");

        vBoxTrumpf.getChildren().addAll(lblTrumpf, imgContainer);
        vBoxTrumpf.setTranslateX(950);
        vBoxTrumpf.setTranslateY(spaceToEdge);
    }


    // TableView to show the score during the game
   public void createTableViewScore() {
       tvScoreTable = new TableView();
       tvcName = new TableColumn("PlayerName");
       tvcPoints = new TableColumn("Points");
       tvScoreTable.getColumns().addAll(tvcName, tvcPoints);

       tvcName.setCellValueFactory(new PropertyValueFactory<>("name"));
       tvcPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

       tvScoreTable.setMaxHeight(140);
       tvScoreTable.setMaxWidth(155);

       tvScoreTable.setItems(model.getPlayerWithPoints());

       tvScoreTable.setTranslateX(1100);
       tvScoreTable.setTranslateY(spaceToEdge);

       // Create a rectangle to fill gap between frame and table
       Rectangle rect = new Rectangle(155, 140);
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



    public void doAnimationPlayYourCard (CardLabel card) {
        card.setTranslateX(xMiddle - (cardWidth/2));
        card.setTranslateY(yMiddle + 10);

        /*
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(1));
        animation.setNode(card);
        animation.setToX();
        animation.setToY(yMiddle + 10);
        animation.play();
         */
    }

    public void doAnimationPlayCard2 (CardLabel card) {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(1));
        animation.setNode(card);
        animation.setToX(850);
        animation.play();
    }

    public void doAnimationPlayCard3 (CardLabel card) {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(1));
        animation.setNode(card);
        animation.setToY(yMiddle-10);
        animation.play();
    }

    public void removeCardsInMiddle () {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                for (CardLabel card : yourCards){
                    if (card.getTranslateX() == xMiddle - (cardWidth/2) && card.getTranslateY() == yMiddle + 10) {
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

    private ImageView loadTrumpfImage(){
        String fileName = model.getTrumpf();
        if (style.equals(CardLabel.Style.DE)){
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
        Platform.runLater( new Runnable() {
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
        Platform.runLater( new Runnable() {
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

    public Pane getRootCards() {
        return rootCards;
    }

    public void setRootCards(Pane rootCards) {
        this.rootCards = rootCards;
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
}
