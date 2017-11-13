package blackjack;

import java.io.Serializable;
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
}

class Player extends Dealer implements Serializable
{
    public enum State 
    { 
        HIT, STAY;
    }
    
    private ArrayList<Card> hand2;
    private int credits;
    private State state;
    private String name;
    
    public Player(String name)
    {
        this.hand2 = new ArrayList<>();
        credits = 500;
        this.name = name;
    }
    
    public ArrayList<Card> getSecondHand()
    {
        return (ArrayList<Card>)hand2.clone();
    }
    
    public void setSecondHand(ArrayList<Card> hand)
    {
        this.hand2 = hand;
    }
    
    public void addCardSecondHand(Card card)
    {
        hand2.add(card);
    }
    
    public int getCredits()
    {
        return credits;
    }
    
    public void addCredits(int credits)
    {
        this.credits += credits;
    }
    
    public void setState(State state)
    {
        this.state = state;
    }
    
    public String getName()
    {
        return name;
    }
}
