package blackjack;

import java.util.ArrayList;

/**
 * @author Tanner Lisonbee
 */
public class Dealer 
{
    private ArrayList<Card> hand1;
    
    public Dealer()
    {
        this.hand1 = new ArrayList<>();
    }
    
    public ArrayList<Card> getHand()
    {
        return (ArrayList<Card>)hand1.clone();
    }
}

class Player extends Dealer
{
    private ArrayList<Card> hand2;
    private int credits;
    
    public Player()
    {
        this.hand2 = new ArrayList<>();
        credits = 500;
    }
    
    public ArrayList<Card> getSecondHand()
    {
        return (ArrayList<Card>)hand2.clone();
    }
}