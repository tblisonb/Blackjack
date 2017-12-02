package blackjack;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Tanner Lisonbee, Dustin Howarth, David Boehmer, Richard Beck
 */
public class Deck 
{
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
     
    }
    
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public Card draw()
    {
        if(getSize() < 3)
            this.cards = (new Deck()).cards;
        return cards.remove();
    }
    
    public void returnToDeck(Card card)
    {
        cards.addLast(card);
    }
    public int getSize(){
        return cards.size();
    }
}
