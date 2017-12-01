package blackjack;

import java.util.ArrayList;

/**
 * @author Tanner Lisonbee
 */
public class Dealer 
{
    private ArrayList<Card> hand1;
    private int handValue;
    
    public Dealer()
    {
        this.hand1 = new ArrayList<>();
    }
    
    public ArrayList<Card> getFirstHand()
    {
        return hand1;
    }
    
    public void setFirstHand(ArrayList<Card> hand)
    {
        this.hand1 = hand;
    }
    
    public void addCardFirstHand(Card card)
    {
        this.hand1.add(card);
    }
    
    public void setHandValue(int value)
    {
        this.handValue = value;
    }
    
    public int getHandValue()
    {
        return handValue;
    }
}