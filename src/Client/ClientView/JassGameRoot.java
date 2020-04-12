package Client.ClientView;

import java.util.ArrayList;

import Client.ClientModel.ClientModel;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class JassGameRoot extends Pane {

    private ClientModel model;
    // ArrayList to place the cards in the middle
    public ArrayList<CardLabel> playedCards = new ArrayList<>();

    //Panes to add the elements needed to play
    Pane rootCards = new Pane();
    Pane cardsInMiddle = new Pane();
    Pane opponents = new Pane();

    //Elements to display the opponents
    public CardLabel oppCard;
    private ArrayList<ImageView> imgOpponents = new ArrayList<>();
    protected ArrayList<Label> lblOpponentName = new ArrayList<>();
    protected ArrayList<Pane> oppContainers = new ArrayList<>();

    //Elements to display the chat
    protected TextArea txtChat = new TextArea();
    protected TextField inputChat = new TextField();

    //Elements to display the current Trumpf
    protected Label lblTrumpf = new Label("Trumpf");
    protected ImageView imgTrumpf = new ImageView();
    protected VBox cntTrumpf = new VBox(10);

    //Elements to display the score
    protected TextArea txtScore = new TextArea();

    // ArrayList for the playable Cards
    public ArrayList<CardLabel> yourCards = new ArrayList<>();
    public double xStartPoint = 275;
    public double yStartingPoint = 770;
    public double xSpace = 106.25;

    // TEST Button to remove cards In Middle, and one to replace them
    public Button btnRemove = new Button("Remove");
    public Button btnRemake = new Button("Remake");

    public JassGameRoot(ClientModel model) {
        this.model = model;

        createYourCards();
        createOpponentLabel();
        createTrumpfElements();
        createScoreFrame();
        placeRemoveButton();
        placeCreateCardsInMiddleBtn();

        this.getChildren().addAll(rootCards, cardsInMiddle, opponents, cntTrumpf, btnRemake, btnRemove, txtScore);

    }

    public void createYourCards () {
        for (int i = 0; i < model.getGame().getYourPlayer().getHandCards().size(); i++){
            CardLabel c = new CardLabel(model.getGame().getYourPlayer().getHandCards().get(i));
            yourCards.add(c);
            c.setTranslateY(yStartingPoint);
            c.setTranslateX(xStartPoint + (i*xSpace));
        }
        rootCards.getChildren().addAll(yourCards);

    }

   /* public void createCardsInMiddle(){
        // Create the cards for the opponents
        for (int i = 0; i < model.getCardsInMiddle().size(); i++) {
            CardLabel c = new CardLabel(model.getCardsInMiddle().get(i));
            playedCards.add(c);
        }

        // Place the cards for the opponents
        // Card played from layer
        //playedCards.get(0).setTranslateX(700);
        // playedCards.get(0).setTranslateY(500);



        playedCards.get(1).setTranslateX(700);
        playedCards.get(1).setTranslateY(320);
        playedCards.get(1).setRotate(180);

        playedCards.get(0).setTranslateX(850);
        playedCards.get(0).setTranslateY(400);
        playedCards.get(0).setRotate(90);

        playedCards.get(2).setTranslateX(550);
        playedCards.get(2).setTranslateY(400);
        playedCards.get(2).setRotate(270);

        // playedCards.remove(0);

        cardsInMiddle.getChildren().addAll(playedCards);
    }

    */

    public void createOpponentLabel() {
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));

        for (int i = 0; i < 3; i++) {

            ImageView opp = new ImageView(back);
            opp.setPreserveRatio(true);
            opp.setFitWidth(100);

            imgOpponents.add(opp);
            Label oppName = new Label(model.getGame().getOpponentPlayers().get(i).getPlayerName());
            oppName.setMinWidth(opp.getFitWidth());

            lblOpponentName.add(oppName);
            VBox container = new VBox();
            container.getChildren().addAll(oppName, opp);
            oppContainers.add(container);
        }

        oppContainers.get(1).setTranslateX(700);
        oppContainers.get(1).setTranslateY(70);


        oppContainers.get(0).setTranslateX(1300);
        oppContainers.get(0).setTranslateY(400);
        oppContainers.get(0).setRotate(90);

        oppContainers.get(2).setTranslateX(100);
        oppContainers.get(2).setTranslateY(400);
        oppContainers.get(2).setRotate(270);

        opponents.getChildren().addAll(oppContainers);
    }

    public void createPlayer2(){
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        ImageView opp = new ImageView(back);
        opp.setFitHeight(160);
        opp.setFitWidth(100);

        Label oppName = new Label(model.getGame().getOpponentPlayers().get(0).getPlayerName());
        oppName.setMinWidth(opp.getFitWidth());


    }

    public void createPlayer3(){
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        ImageView opp = new ImageView(back);
        opp.setFitHeight(160);
        opp.setFitWidth(100);

        Label oppName = new Label(model.getGame().getOpponentPlayers().get(1).getPlayerName());
        oppName.setMinWidth(opp.getFitWidth());


    }

    public void createPlayer4(){
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        ImageView opp = new ImageView(back);
        opp.setFitHeight(160);
        opp.setFitWidth(100);

        Label oppName = new Label(model.getGame().getOpponentPlayers().get(2).getPlayerName());
        oppName.setMinWidth(opp.getFitWidth());


    }



    public void createTrumpfElements() {
        // Getting the image and label to display the trumpf done
        HBox imgContainer = new HBox();
        imgTrumpf = loadTrumpfImage();
        imgContainer.getChildren().add(imgTrumpf);
        imgContainer.getStyleClass().add("trumpf");
        lblTrumpf.setMinWidth(90);
        cntTrumpf.getChildren().addAll(lblTrumpf, imgContainer);
        cntTrumpf.setTranslateX(850);
        cntTrumpf.setTranslateY(70);
    }


    // TODO: Put Points in Listview - Styling
    public void createScoreFrame() {
        txtScore.setText("Score \nPlayer 1: \nPlayer2: \nPlayer3: \nPlayer 4:");
        txtScore.setTranslateX(1000);
        txtScore.setTranslateY(70);
        txtScore.setMaxWidth(200);
    }

    public void placeRemoveButton(){
        btnRemove.setTranslateX(100);
        btnRemove.setTranslateY(100);
    }

    public void placeCreateCardsInMiddleBtn(){
        btnRemake.setTranslateX(200);
        btnRemake.setTranslateY(100);
    }

    public void doAnimationPlayYourCard (CardLabel card) {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(1));
        animation.setNode(card);
        animation.setToX(700);
        animation.setToY(500);
        animation.play();
    }

    public void doAnimationPlayCard2 (CardLabel card){
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
        animation.setToY(500);
        animation.play();
    }

    public void removeCardsInMiddle () {
        for (CardLabel card : yourCards){
            if (card.getTranslateX() == 700 && card.getTranslateY() == 500) {
                rootCards.getChildren().remove(card);
            }
        }

        cardsInMiddle.getChildren().removeAll(playedCards);
    }

    public void setOpponentCards() {
        cardsInMiddle.getChildren().addAll(playedCards);
    }

    private ImageView loadTrumpfImage(){
        String fileName = model.getTrumpf().toString()+".png";
        Image trumpf = new Image(getClass().getClassLoader().getResourceAsStream("images/" + fileName));
        ImageView img = new ImageView();
        img.setImage(trumpf);
        img.setFitWidth(70);
        img.setFitHeight(70);
        return img;
    }


}
