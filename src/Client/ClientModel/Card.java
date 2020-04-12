package Client.ClientModel;

public class Card {
	public enum Suit { Clubs, Diamonds, Hearts, Spades;
        @Override
        public String toString() {
            String suit = "";
            switch (this) {
                case Clubs: suit = "clubs"; break;
                case Diamonds: suit = "diamonds"; break;
                case Hearts: suit = "hearts"; break;
                case Spades: suit = "spades"; break;
            }
            return suit;
        }
    };

    public enum Rank {Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
        @Override
        public String toString() {
            String str = "ace";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 12
            int ordinal = this.ordinal();
            if (ordinal == 0) {
                str = "six";
            } else if (ordinal == 1) {
                str ="seven";
            } else if (ordinal == 2) {
                str = "eight";
            } else if (ordinal == 3) {
                str = "nine";
            } else if (ordinal == 4) {
                str = "ten";
            } else if (ordinal == 5) { // Jack
                str = "jack";
            } else if (ordinal == 6) { // Queen
                str = "queen";
            } else if (ordinal == 7) { // King
                str = "king";
            }
            return str;
        }
    };

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return suit.toString() + rank.toString();
    }

}
