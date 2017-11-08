package blackjack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

class Player extends Dealer
{
    private ArrayList<Card> hand2;
    private Socket socket;
    private int credits;
    
    public Player(Socket socket)
    {
        this.socket = socket;
        this.hand2 = new ArrayList<>();
        credits = 500;
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
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public int getCredits()
    {
        return credits;
    }
    
    public void addCredits(int credits)
    {
        this.credits += credits;
    }
}