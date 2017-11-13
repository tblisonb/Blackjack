package blackjack;

/**
 * @author Tanner Lisonbee
 */
public class Card 
{
    public enum Suit { HEARTS, SPADES, CLUBS, DIAMONDS }
    public enum Number 
    { 
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);
        
        private final int value;
        Number (int value) 
        {
            this.value = value;
        }
        
        int getValue()
        {
            return value;
        }
    }
    
    private Suit suit;
    private Number number;
    private int unicode;
    
    public Card(Suit suit, Number number)
    {
        this.suit = suit;
        this.number = number;
        switch (suit) 
        {
            case SPADES:
                unicode = 0x1F0A0;
                break;
            case HEARTS:
                unicode = 0x1F0B0;
                break;
            case DIAMONDS:
                unicode = 0x1F0C0;
                break;
            default:
                unicode = 0x1F0D0;
        }
        unicode += number.value;
    }

    public Suit getSuit() 
    {
        return suit;
    }

    public Number getNumber() 
    {
        return number;
    }
}
