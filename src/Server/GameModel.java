package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameModel {
    private static GameModel gameModel;

    private int numOfPlayers = 4;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int indexOfCurrentPlayer;
    private Round currentRound;
    private DeckOfCards deck;
    private Player winnerOfGame;
    private Player currentWinner;
    private ArrayList<Player> playingOrder;
    private int roundCounter;
    private int currentRoundPoints;
    private Card.Suit trumpf;
    private ArrayList<Round> allPlayedRounds;
    private int maxPoints;
    private Player startPlayer;
    private int indexOfStartPlayer;
    private int allCardsPlayedCounter;

    public static GameModel getGameModel() {
        if (gameModel == null){
            gameModel = new GameModel();
        }
        return gameModel;
    }

    public GameModel() {
        players = new ArrayList<>(4);
        currentPlayer = null;
        currentRound = null;
        allPlayedRounds = new ArrayList<>(9);
        indexOfCurrentPlayer = 0;
        deck = new DeckOfCards();
    }

    public void addPlayer(String playerName, int id){
        if (players.size() < 4) {
            Player p = new Player(playerName, id);
            players.add(p);
            currentPlayer = players.get(0);
        }
    }

    public void setRandomStartPlayer() {
        Random rand = new Random();
        int determiner = rand.nextInt(4);
        currentPlayer = players.get(determiner);
        startPlayer = currentPlayer;
        indexOfStartPlayer = determiner;
    }

    public boolean checkIfFourPlayers(){
        boolean check = false;
        if (players.size() == 4) {
            check = true;
        }
        return check;
    }

    public void dealCardsToPlayer() {
        if (allCardsPlayedCounter != 0) {
            deck = new DeckOfCards();
        }
        for (Player p : players) {
            for (int i = 0; i < currentPlayer.getHANDSIZE(); i++) {
                p.addCard(deck.dealCard());
            }
            Collections.sort(p.getHandCards());
        }
    }

    public void putPlayersInOrder() {
        playingOrder = new ArrayList<>(4);
        for (Player p : players) {
            playingOrder.add(p);
        }

        Player p1 = players.get(0);
        Player p2 = players.get(1);
        Player p3 = players.get(2);
        Player p4 = players.get(3);

        // set currentWinner if null to avoid NullPointerException - currentPlayer gets overridden after first round so this has no effect on game results
        if (currentWinner == null) {
            currentWinner = currentPlayer;
        }

        switch (currentWinner.getId()) {
            case 2:
                playingOrder.set(0, p2);
                playingOrder.set(1, p3);
                playingOrder.set(2, p4);
                playingOrder.set(3, p1);
                break;
            case 3:
                playingOrder.set(0, p3);
                playingOrder.set(1, p4);
                playingOrder.set(2, p1);
                playingOrder.set(3, p2);
                break;
            case 4:
                playingOrder.set(0, p4);
                playingOrder.set(1, p1);
                playingOrder.set(2, p2);
                playingOrder.set(3, p3);
                break;
        }
        currentPlayer = playingOrder.get(0);
    }

    public void setStartPlayer(){
        currentPlayer = players.get(0);
    }

    public void createRound(){
        if (currentRound != null) {
            allPlayedRounds.add(currentRound);
        }
        currentRound = new Round(trumpf);
        roundCounter++;
    }

    public void setTrumpf(String trumpf) {
        switch (trumpf) {
            case "hearts":
                this.trumpf = Card.Suit.Hearts;
                break;
            case "clubs":
                this.trumpf = Card.Suit.Clubs;
                break;
            case "diamonds":
                this.trumpf = Card.Suit.Diamonds;
                break;
            case "spades":
                this.trumpf = Card.Suit.Spades;
                break;
        }
    }

    public void moveToNextStartPlayer() {
        if (indexOfStartPlayer == 3) {
            indexOfStartPlayer = 0;
        } else {
            indexOfStartPlayer++;
        }
        currentPlayer = players.get(indexOfStartPlayer);
    }

    public void moveToNextPlayer() {
        if (indexOfCurrentPlayer == 3) {
            indexOfCurrentPlayer = 0;
        } else {
            indexOfCurrentPlayer++;
        }
        currentPlayer = playingOrder.get(indexOfCurrentPlayer);
    }

    public boolean checkIfRoundFull() {
        boolean full = false;
        if (currentRound.getTurns().size() == numOfPlayers){
            full = true;
        }
        return full;
    }

    public void evaluateCurrentRound() {
        int lastRoundBonus = 5;
        int numOfRoundsTotal = 9;
        currentWinner = currentRound.evaluateRound();
        currentRoundPoints = currentRound.countPoints();
        if (roundCounter == numOfRoundsTotal) {
            currentRoundPoints += lastRoundBonus;
        }
        currentWinner.addPointsToCounter(currentRoundPoints);
    }

    public void addTurnToCurrentRound(String cardString) {
        Turn t;

        for (Card c : currentPlayer.getHandCards()) {
            if (c.toString().equals(cardString)) {
                t = new Turn(currentPlayer, c);
                currentRound.addTurn(t);
            }
        }

        // remove the played card from the hand of the current player
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(currentPlayer)) {
                for (int y = 0; y < players.get(i).getHandCards().size(); y++){
                    if (players.get(i).getHandCards().get(y).toString().equals(cardString)){
                        players.get(i).getHandCards().remove(y);
                    }
                }
            }
        }
    }

    public boolean checkIfGameFinished() {
        boolean finished = false;
        if (playingOrder.get(3).getHandCards().size() == 0){
            finished = true;
        }
        allCardsPlayedCounter++;
        return finished;
    }

    public String getWinnerofGame() {
        Player winner = players.get(0);
        for (Player p : players) {
            if (p.getPointCounter() > winner.getPointCounter()) {
                winner = p;
            }
        }
        String winnerName = winner.getPlayerName();
        return winnerName;
    }

    public boolean checkIfMaxPointsReached() {
        boolean check = false;
        for (Player p : players) {
            if (p.getPointCounter() >= maxPoints) {
                check = true;
            }
        }
        return check;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public DeckOfCards getDeck() {
        return deck;
    }

    public void setDeck(DeckOfCards deck) {
        this.deck = deck;
    }

    public static void setGameModel(GameModel gameModel) {
        GameModel.gameModel = gameModel;
    }

    public int getIndexOfCurrentPlayer() {
        return indexOfCurrentPlayer;
    }

    public void setIndexOfCurrentPlayer(int indexOfCurrentPlayer) {
        this.indexOfCurrentPlayer = indexOfCurrentPlayer;
    }

    public Player getWinnerOfGame() {
        return winnerOfGame;
    }

    public void setWinnerOfGame(Player winnerOfGame) {
        this.winnerOfGame = winnerOfGame;
    }

    public Player getCurrentWinner() {
        return currentWinner;
    }

    public void setCurrentWinner(Player currentWinner) {
        this.currentWinner = currentWinner;
    }

    public ArrayList<Player> getPlayingOrder() {
        return playingOrder;
    }

    public void setPlayingOrder(ArrayList<Player> playingOrder) {
        this.playingOrder = playingOrder;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
    }

    public int getCurrentRoundPoints() {
        return currentRoundPoints;
    }

    public void setCurrentRoundPoints(int currentRoundPoints) {
        this.currentRoundPoints = currentRoundPoints;
    }

    public Card.Suit getTrumpf() {
        return trumpf;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
