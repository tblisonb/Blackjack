package blackjack;

import java.io.Serializable;
import java.util.ArrayList;

class Player extends Dealer implements Serializable
{
    public enum State 
    { 
        DEFAULT, HIT, STAY;
    }
    
    private ArrayList<Card> hand2;
    private int credits;
    private State state;
    private String name;
    
    public Player(String name)
    {
        this.hand2 = new ArrayList<>();
        this.credits = 500;
        this.name = name;
        this.state = State.DEFAULT;
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