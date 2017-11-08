package blackjack;

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
    
    public Player()
    {
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
        
    }
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
}