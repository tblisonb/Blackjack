package blackjack;

import java.util.Random;

/**
 * @author Tanner Lisonbee, Dustin Howarth, David Boehmer, Richard Beck
 */
public class Deck {
    private Card card[] = new Card[52];
    private int size = 52;

    public Deck() {
        int sum = 0;
        Card car[] = new Card[52];
        Card test;
        Card.Suit suit[] = new Card.Suit[4];
        suit[0] = Card.Suit.HEARTS;
        suit[1] = Card.Suit.DIAMONDS;
        suit[2] = Card.Suit.SPADES;
        suit[3] = Card.Suit.CLUBS;
        
        Card.Number num[] = new Card.Number[13];
        num[0] = Card.Number.ACE;
        num[1] = Card.Number.TWO;
        num[2] = Card.Number.THREE;
        num[3] = Card.Number.FOUR;
        num[4] = Card.Number.FIVE;
        num[5] = Card.Number.SIX;
        num[6] = Card.Number.SEVEN;
        num[7] = Card.Number.EIGHT;
        num[8] = Card.Number.NINE;
        num[9] = Card.Number.TEN;
        num[10] = Card.Number.JACK;
        num[11] = Card.Number.QUEEN;
        num[12] = Card.Number.KING;
        
        for(int i =0; i < 4; i ++){
            for(int j = 0; j < 13; j++){
                test = new Card(suit[i],num[j]);
                car[sum] = test;
                sum++;
            }
        }
       
        shuffle(car);
        for(int i =0; i < size; i ++){
            System.out.println(car[i].getNumber()+" of " + car[i].getSuit());
        }
    }
    
    
    public void shuffle(Card card[]){
        Random rand  = new Random();
        Card car;
        int index  = 0;
        System.out.println(size);
        for(int i = 0; i < size; i++){
            System.out.println(i); 
            index = rand.nextInt(size);
            car = card[i];
            card[i] = card[index];
            card[index] = card[i];
        }
    }

    public Card draw(){
        Card car;
        car = card[size];
        size--;
        return car;
    }
}
