package blackjack;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Tanner Lisonbee, Dustin Howarth, David Boehmer, Richard Beck
 */
public class Deck 
{
    private final int MAX_SIZE = 52;
    private LinkedList<Card> cards;

    public Deck() 
    {
        cards = new LinkedList<>();       
        Card.Suit suit[] = {Card.Suit.HEARTS, Card.Suit.DIAMONDS, Card.Suit.SPADES, 
                            Card.Suit.CLUBS};   
        Card.Number num[] = {Card.Number.ACE, Card.Number.TWO, Card.Number.THREE,
                             Card.Number.FOUR, Card.Number.FIVE, Card.Number.SIX,
                             Card.Number.SEVEN, Card.Number.EIGHT, Card.Number.NINE,
                             Card.Number.TEN, Card.Number.JACK, Card.Number.QUEEN,
                             Card.Number.KING};
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 13; j++)
                cards.add(new Card(suit[i],num[j]));
       
        shuffle();
        
        /*
        for(int i =0; i < size; i ++){
            System.out.println(car[i].getNumber()+" of " + car[i].getSuit());
        }
        */
    }
    
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public Card draw()
    {
        return cards.remove();
    }
    
    public void returnToDeck(Card card)
    {
        cards.addLast(card);
    }
}
