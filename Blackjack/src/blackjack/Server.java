/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import static blackjack.BlackjackConstants.MAX_PLAYER_COUNT;
import blackjack.Player.Move;
import blackjack.Player.State;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Tanner Lisonbee
 */
public class Server extends Application implements BlackjackConstants
{
    @Override
    public void start(Stage primaryStage)
    {
        StackPane root = new StackPane();
        TextArea log = new TextArea(); //server output
        log.setEditable(false); //output log shouldn't be editable
        root.getChildren().add(log);

        Scene scene = new Scene(root, 600, 400);
        //ensure server terminates when the window is closed
        primaryStage.setOnCloseRequest((WindowEvent event) -> 
        {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("Blackjack Server");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        try
        {
            serverStart(primaryStage, log);
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }
    
    public void serverStart(Stage primaryStage, TextArea log) throws IOException
    {
        //new socket on port 8000
        ServerSocket serverSocket = new ServerSocket(8000);
        log.appendText(new Date() + ": Server started at socket 8000\n");

        //new thread for polling for client sockets.
        new Thread(() -> 
        {
            int sessionNum = 1; //keeps track of what client is connected
            while (true) //runs indefinitely
            {
                List<Player> players = new LinkedList<>();
                List<ObjectInputStream> fromClient = new LinkedList<>();
                List<ObjectOutputStream> toClient = new LinkedList<>();
                try
                {
                    log.appendText(new Date() + ": Starting a thread for "
                            + "session #" + sessionNum + "\n");
                    new Thread().start();
                    HandleSession session = new HandleSession(sessionNum, log);

                    //poll for new client connection
                    for (int i = 0; i < MAX_PLAYER_COUNT; i++)
                    {
                        log.appendText(new Date() + ": Waiting for player "
                                + (i + 1) + " to join session #" + sessionNum
                                + "\n");

                        Socket socket = serverSocket.accept();
                        try
                        {
                            toClient.add(i, new ObjectOutputStream(socket.getOutputStream()));
                            toClient.get(i).flush();
                            fromClient.add(i, new ObjectInputStream(socket.getInputStream()));
                            
                            Player newPlayer = (Player)fromClient.get(i).readObject();
                            //newPlayer.setPlayerNum(i);
                            players.add(i, newPlayer);
                            System.out.println(players.get(i).getState());
                            
                          if(sessionNum == 5){
                          
                          }
                            //new Event(new EventType("Player Joined"));
                            log.appendText(new Date() + ": Player '" + newPlayer.getName()
                                    + "' has joined session #" + sessionNum + "\n");
                        } 
                        catch (IOException | ClassNotFoundException e)
                        {
                            System.err.println(e);
                        }

                        session.update(players, toClient, fromClient);
                        if (i == 0)
                            session.run();
                        session.broadcastPlayerData(players);
                    }
                    sessionNum++;
                } 
                catch (IOException e)
                {
                    System.err.println(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }//end of server while(true)
        }).start();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

/**
 *
 * @author Tanner Lisonbee
 */
class HandleSession implements Runnable, BlackjackConstants
{
    private List<Player> players;
    private final int sessionNum;
    private final TextArea log;
    private List<ObjectInputStream> fromClient;
    private List<ObjectOutputStream> toClient;
    public static Deck deck = new Deck();
    private int currentPlayerNum;
    private Dealer dealer;
   
    
    /**
     * @param socket client socket
     * @param sessionNum numerical id for client
     * @param log reference to the server log
     */
    public HandleSession(int sessionNum, TextArea log)
    {
        this.currentPlayerNum = 0;
        this.sessionNum = sessionNum;
        this.log = log;
        this.dealer = new Dealer();
    }

    @Override
    public void run()
    {
        new Thread(() ->
        {
            try
            {
                while (true)
                {
                    Object object = fromClient.get(currentPlayerNum).readObject();
                    players.set(currentPlayerNum, (Player) object);
                    //System.out.println(currentPlayerNum + " is: " + players.get(currentPlayerNum).getState());

                    if (players.get(currentPlayerNum).getMove() != Move.DEFAULT)
                    {
                        if (players.get(currentPlayerNum).getMove() == Move.HIT) 
                            hit(currentPlayerNum);
                        else if(players.get(currentPlayerNum).getMove() == Move.STAY)
                            stay(currentPlayerNum);       

                        System.out.println("List of names:");
                        for(Player p: players)
                        {
                            System.out.print(p.getName() + " - Cards: ");
                            for(Card c: p.getSecondHand()){
                                System.out.print(c.toString() + " ");
                            }
                            System.out.print(" - " + p.getState());
                            System.out.println();
                        }
                    }
                    System.out.println("broadcasting in loop");
                    broadcastPlayerData(players);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                System.err.println(e);
            }
        }).start();
    }
    
    public void broadcastPlayerData(List<Player> object) throws IOException, ClassNotFoundException
    {
        if (object == null) {
            return;
        }
        for (int i = 0; i < players.size(); i++) {
            //if(players.get(i).getState() == State.OFF && players.get(i+1) != null)
            //players.get(i+1).setState(State.ON);
            for (int j = 0; j < object.size(); j++) {

                try {

                    toClient.get(i).writeObject(object.get(j));
                    toClient.get(i).flush();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
    
    public void update(List<Player> players, List<ObjectOutputStream> toClient, List<ObjectInputStream> fromClient)
    {
        this.players = players;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

    public void playGame(List<Player> players) throws IOException, ClassNotFoundException 
    {
        int current = 0;
        System.out.println("hit");

        while (true) 
        {
            Object play = fromClient.get(current).readObject();
            play.getClass();
            //Player play1 = (Player) play;
            System.out.println(play.getClass());
            //players.set(current, (Player) play);

            if (players.get(current).getMove() == Move.HIT) {
                hit(current);
                current++;
            }
        }
    }
    
    public void hit(int playerid) throws IOException, ClassNotFoundException
    {
        players.get(playerid).addCardSecondHand(deck.draw());
        
        Random generate = new Random();
        if (getValue(players.get(playerid).getSecondHand()) > 21)
        {
            dealer.setHandValue(generate.nextInt(11) + 17);
            players.get(playerid).setDealerValue(dealer.getHandValue());
            players.get(playerid).setMessage("You busted.");
            lose(playerid);
            for (Card c : players.get(playerid).getSecondHand())
                deck.returnToDeck(c);
            players.get(playerid).setSecondHand(new ArrayList<>());
        }
        else if (getValue(players.get(playerid).getSecondHand()) == 21)
        {
            dealer.setHandValue(generate.nextInt(11) + 17);
            players.get(playerid).setDealerValue(dealer.getHandValue());
            players.get(playerid).setMessage("Blackjack! You win.");
            win(playerid);
            for (Card c : players.get(playerid).getSecondHand())
                deck.returnToDeck(c);
            players.get(playerid).setSecondHand(new ArrayList<>());
        }
        
        players.get(playerid).setMove(Move.DEFAULT);
        toClient.get(playerid).writeObject(players.get(playerid));
        toClient.get(playerid).flush();
        //System.out.println("draw: "+deck.draw().getSuit());
        //System.out.println("hand value " + getValue(players.get(playerid).getSecondHand()));
        players.get(playerid).setMessage("");
        players.get(playerid).setDealerValue(0);
    }
    
    public void stay(int playerid) throws IOException
    {
        Random generate = new Random();
        dealer.setHandValue(generate.nextInt(11) + 17);
        players.get(playerid).setDealerValue(dealer.getHandValue());
        System.out.println("--------------DEALER HAND VALUE: " + dealer.getHandValue());
        
        if (dealer.getHandValue() > 21)
        {
            players.get(playerid).setMessage("Dealer busted. You win!");
            win(playerid);
        }
        else if (getValue(players.get(playerid).getSecondHand()) > dealer.getHandValue())
        {
            players.get(playerid).setMessage("Your hand beats the dealer's. You win!");
            win(playerid);
        }
        else if (getValue(players.get(playerid).getSecondHand()) < dealer.getHandValue())
        {
            players.get(playerid).setMessage("Your hand is smaller than the dealer's You lose.");
            lose(playerid);
        }
            
        for (Card c : players.get(playerid).getSecondHand())
            deck.returnToDeck(c);
        
        players.get(playerid).setSecondHand(new ArrayList<>());
        players.get(playerid).setMove(Move.DEFAULT);
        toClient.get(playerid).writeObject(players.get(playerid));
        toClient.get(playerid).flush();
        
        players.get(playerid).setMessage("");
        players.get(playerid).setDealerValue(0);
    }
    
    public void win(int playerid)
    {
        players.get(playerid).addCredits(players.get(playerid).getBet());
        advancePlayer();
        /*int winner  = getValue(players.get(0).getSecondHand());
        int index = 0;
        Player play;
        for(int i =0; i < players.size(); i++)
        {
            if(getValue(players.get(i).getSecondHand()) > winner)
                winner = getValue(players.get(i).getSecondHand());
            index = 0;
        }
        players.get(index).addCredits(index);
        return index;*/
    }
    
    public void lose(int playerid)
    {
        players.get(playerid).addCredits(-(players.get(playerid).getBet()));
        advancePlayer();
    }
    
    private void advancePlayer()
    {
        players.get(currentPlayerNum).setState(State.OFF);
        currentPlayerNum = (++currentPlayerNum) % players.size();
        players.get(currentPlayerNum).setState(State.ON);
    }

    private int getValue(ArrayList<Card> hand)
    {
        int sum = 0;
        for (Card c : hand)
            sum += c.getNumber().getValue();
        return sum;
    }
}