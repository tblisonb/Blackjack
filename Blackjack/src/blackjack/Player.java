package blackjack;

import java.io.Serializable;
import java.util.ArrayList;

class Player extends Dealer implements Serializable
{
    private ArrayList<Card> hand2;
    private int credits, currentBet, dealerValue;
    private State state;
    private Move move;
    private final String name;
    //private int playerNum;
    private long ID;
    private String message;
    private long timeStamp;
    
    public enum State 
    { 
        ON, OFF;
    }
    
    public enum Move
    {
        HIT, STAY, DEFAULT
    }
    
    public Player(String name)
    {
        this.hand2 = new ArrayList<>();
        this.credits = 500;
        this.currentBet = 0;
        this.name = name;
        this.state = State.ON;
        this.move = Move.DEFAULT;
        //this.playerNum = -1;
        ID = System.currentTimeMillis();
        this.message = "";
        this.dealerValue = 0;
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
    
     public State getState()
    {
        return state;
    }
    
    public String getName()
    {
        return name;
    }
    /*
    public void setPlayerNum(int num)
    {
        this.playerNum = num;
    }
    
    public int getPlayerNum()
    {
        return playerNum;
    }
*/
    public void setMove(Move move) 
    {
        this.move = move;
    }
    
    public Move getMove() 
    {
        return move;
    }
    
    public long getID()
    {
        return ID;
    }
    
    public void setBet(int amount)
    {
        this.currentBet = amount;
    }
    
    public int getBet()
    {
        return currentBet;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }

    public int getDealerValue()
    {
        return dealerValue;
    }

    public void setDealerValue(int dealerValue)
    {
        this.dealerValue = dealerValue;
    }
    
    public void setTimeStamp(){
        timeStamp = System.currentTimeMillis();
    }
    
    public long getTimeStamp(){
        return timeStamp;
    }
}
