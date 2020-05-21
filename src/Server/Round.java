package Server;



import java.util.ArrayList;

public class Round {
    public ArrayList<Turn> turns = new ArrayList<>();
    public Player winner;
    public static int id;
    public Card.Suit trumpf;
    public int totalPoints;
    public enum EVALUATIONTYPE {TRUMPF, TOPDOWN, BOTTOMUP}
    private EVALUATIONTYPE evaluationtype;

    public Round(Card.Suit trumpf, EVALUATIONTYPE type) {
        winner = null;
        id++;
        this.trumpf = trumpf;
        this.evaluationtype = type;
    }

    public void addTurn(Turn turn){
        turns.add(turn);
    }

    public Player getWinner(){
        return winner;
    }


    // Gives back the player who wins the round
    public Player evaluateRound() {
        Turn winnerTurn = turns.get(0);

        switch (evaluationtype) {
            case TOPDOWN:
                for (Turn t : turns) {
                    if (t.getCard().getSuit() == winnerTurn.getCard().getSuit()) {
                        if (t.getCard().getRank().ordinal() > winnerTurn.getCard().getRank().ordinal()) {
                            winnerTurn = t;
                        }
                    }
                }
                break;
            case BOTTOMUP:
                for (Turn t : turns) {
                    if (t.getCard().getSuit() == winnerTurn.getCard().getSuit()) {
                        if (t.getCard().getRank().ordinal() < winnerTurn.getCard().getRank().ordinal()) {
                            winnerTurn = t;
                        }
                    }
                }
                break;
            case TRUMPF:
                Card.Rank nell = Card.Rank.Nine;
                Card.Rank buur = Card.Rank.Jack;

                for (Turn t : turns) {
                    if (t.getCard().getSuit() == trumpf) {
                        if (t.getCard().getRank() == buur) {
                            winnerTurn = t;
                        } else if (winnerTurn.getCard().getSuit() == trumpf) {
                            if (winnerTurn.getCard().getRank() == buur) {
                                // do nothing - winner stays the same
                            } else if (winnerTurn.getCard().getRank() == nell) {
                                // do nothing - winner stays the same
                            } else if (t.getCard().getRank() == nell) {
                                winnerTurn = t;
                            } else if (t.getCard().getRank().ordinal() > winnerTurn.getCard().getRank().ordinal()) {
                                winnerTurn = t;
                            }
                        } else
                            winnerTurn = t;
                    } else {
                        if (winnerTurn.getCard().getSuit() == trumpf) {
                            // do nothing
                        } else if (winnerTurn.getCard().getSuit() == t.getCard().getSuit()) {
                            if (t.getCard().getRank().ordinal() > winnerTurn.getCard().getRank().ordinal()) {
                                winnerTurn = t;
                            }
                        }
                    }
                }
                break;
        }
        winner = winnerTurn.getPlayer();
        return winnerTurn.getPlayer();
    }


    // method to count the points of the round - called in gameModel
    public int countPoints() {
        int pointsForEight = 8;
        switch (evaluationtype) {
            case TOPDOWN:
                for (Turn t : turns) {
                    if (t.getCard().getRank() == Card.Rank.Eight) {
                        totalPoints += pointsForEight;
                    } else {
                        totalPoints += t.getCard().getRank().getValueinPoints();
                    }
                }
                break;
            case BOTTOMUP:
                int pointsForSix = 11;
                int pointsForAce = 0;
                for (Turn t : turns) {
                    if (t.getCard().getRank() == Card.Rank.Eight) {
                        totalPoints += pointsForEight;
                    } else {
                        if (t.getCard().getRank() == Card.Rank.Six) {
                            totalPoints += pointsForSix;
                        } else {
                            if (t.getCard().getRank() == Card.Rank.Ace) {
                                totalPoints += pointsForAce;
                            } else {
                                totalPoints += t.getCard().getRank().getValueinPoints();
                            }
                        }
                    }
                }
                break;
            case TRUMPF:
                int pointBuurTrumpf = 20;
                int pointNellTrumpf = 14;
                for (Turn t : turns) {
                    if (t.getCard().getSuit().equals(trumpf) && t.getCard().getRank().equals(Card.Rank.Jack)) {
                        totalPoints += pointBuurTrumpf;
                    } else {
                        if (t.getCard().getSuit().equals(trumpf) && t.getCard().getRank().equals(Card.Rank.Nine)) {
                            totalPoints += pointNellTrumpf;
                        } else {
                            totalPoints += t.getCard().getRank().getValueinPoints();
                        }
                    }
                }
                break;
        }
        return totalPoints;
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public void setTurns(ArrayList<Turn> turns) {
        this.turns = turns;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Round.id = id;
    }

    public Card.Suit getTrumpf() {
        return trumpf;
    }

    public void setTrumpf(Card.Suit trumpf) {
        this.trumpf = trumpf;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
