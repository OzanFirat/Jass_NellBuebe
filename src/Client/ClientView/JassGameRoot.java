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
    Pane player2 = new Pane();
    Pane player3 = new Pane();
    Pane player4 = new Pane();

    //Elements to display the opponents
    public ArrayList<CardLabel> oppPlayer2Cards = new ArrayList<>();
    public ArrayList<CardLabel> oppPlayer3Cards = new ArrayList<>();
    public ArrayList<CardLabel> oppPlayer4Cards = new ArrayList<>();
    public Label lblPlayer2Name = null;
    public Label lblPlayer3Name = null;
    public Label lblPlayer4Name = null;
    public ImageView imvPlayer2 = null;
    public ImageView imvPlayer3 = null;
    public ImageView imvPlayer4 = null;
    
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
    public double xStartPoint = 342.5;
    public double yStartingPoint = 630;
    public double xSpace = 80;

    // TEST Button to remove cards In Middle, and one to replace them
    public Button btnRemove = new Button("Remove");
    public Button btnRemake = new Button("Remake");
    
    private double xMiddle = 700;
    private double yMiddle = 400;
    private double cardWidth = 75;
    private double cardHeight = 120;
    private double spaceToEdge = 50;

    public JassGameRoot(ClientModel model) {
        this.model = model;

        createYourCards();
        createPlayer2();
        createPlayer3();
        createPlayer4();
        createTrumpfElements();
        createScoreFrame();
        placeRemoveButton();
        placeCreateCardsInMiddleBtn();

        this.getChildren().addAll(rootCards, cardsInMiddle, player2, player3, player4, cntTrumpf, btnRemake, btnRemove, txtScore);

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

    

    public void createPlayer2(){
        // Create the stack of cards to play
        for (int i = 0; i < model.getGame().getOpponentPlayers().get(0).getHandCards().size(); i++){
            CardLabel card = new CardLabel(model.getGame().getOpponentPlayers().get(0).getHandCards().get(i));
            oppPlayer2Cards.add(card);
        }

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        imvPlayer2 = new ImageView(back);
        imvPlayer2.setFitHeight(cardHeight);
        imvPlayer2.setFitWidth(cardWidth);

        // Create the label to show player name
        lblPlayer2Name = new Label(model.getGame().getOpponentPlayers().get(0).getPlayerName());
        lblPlayer2Name.setMinWidth(imvPlayer2.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer2Name, imvPlayer2);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane
        player2.getChildren().addAll(oppPlayer2Cards);
        player2.getChildren().add(container);

        // Place the pane at the right place
        player2.setRotate(90);
        player2.setTranslateX(1400 - (spaceToEdge + cardHeight));
        player2.setTranslateY(yMiddle - cardWidth);
        
    }

    public void createPlayer3(){
        // Create the stack of cards to play
        for (int i = 0; i < model.getGame().getOpponentPlayers().get(1).getHandCards().size(); i++){
            CardLabel card = new CardLabel(model.getGame().getOpponentPlayers().get(1).getHandCards().get(i));
            oppPlayer3Cards.add(card);
        }

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        imvPlayer3 = new ImageView(back);
        imvPlayer3.setFitHeight(cardHeight);
        imvPlayer3.setFitWidth(cardWidth);

        // Create the label to show player name
        lblPlayer3Name = new Label(model.getGame().getOpponentPlayers().get(1).getPlayerName());
        lblPlayer3Name.setMinWidth(imvPlayer3.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer3Name, imvPlayer3);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane
        player3.getChildren().addAll(oppPlayer3Cards);
        player3.getChildren().add(container);

        // Place the pane at the right place
        player3.setTranslateX(xMiddle - (cardWidth/2));
        player3.setTranslateY(spaceToEdge);
    }

    public void createPlayer4(){
        // Create the stack of cards to play
        for (int i = 0; i < model.getGame().getOpponentPlayers().get(2).getHandCards().size(); i++){
            CardLabel card = new CardLabel(model.getGame().getOpponentPlayers().get(2).getHandCards().get(i));
            oppPlayer4Cards.add(card);
        }

        // Create the image (back of card) to display player
        Image back = new Image(getClass().getClassLoader().getResourceAsStream("images/back.jpg"));
        imvPlayer4 = new ImageView(back);
        imvPlayer4.setFitHeight(cardHeight);
        imvPlayer4.setFitWidth(cardWidth);

        // Create the label to show player name
        lblPlayer4Name = new Label(model.getGame().getOpponentPlayers().get(2).getPlayerName());
        lblPlayer4Name.setMinWidth(imvPlayer4.getFitWidth());

        // Create the container to add back and player name
        VBox container = new VBox();
        container.getChildren().addAll(lblPlayer4Name, imvPlayer4);

        // Add the ArrayList Cardlabel and the container (name and image) to the pane
        player4.getChildren().addAll(oppPlayer4Cards);
        player4.getChildren().add(container);

        // Place the pane at the right place
        player4.setRotate(270);
        player4.setTranslateX(spaceToEdge+20);
        player4.setTranslateY(yMiddle - cardWidth);
        
    }



    public void createTrumpfElements() {
        // Getting the image and label to display the trumpf done
        HBox imgContainer = new HBox();
        imgTrumpf = loadTrumpfImage();
        imgContainer.getChildren().add(imgTrumpf);
        imgContainer.getStyleClass().add("trumpf");
        lblTrumpf.setMinWidth(70);
        cntTrumpf.getChildren().addAll(lblTrumpf, imgContainer);
        cntTrumpf.setTranslateX(xMiddle + cardWidth*2);
        cntTrumpf.setTranslateY(spaceToEdge);
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
        animation.setToX(xMiddle - (cardWidth/2));
        animation.setToY(yMiddle + 10);
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
        animation.setToY(yMiddle-10);
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
        img.setFitWidth(50);
        img.setFitHeight(50);
        return img;
    }


}
