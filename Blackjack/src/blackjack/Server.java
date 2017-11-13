/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import static blackjack.BlackjackConstants.MAX_PLAYER_COUNT;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
                    List<String> names = new LinkedList<>();
                    List<ObjectInputStream> fromClient = new LinkedList<>();
                    List<ObjectOutputStream> toClient = new LinkedList<>();
                    try
                    {
                        //poll for new client connection
                        for (int i = 0; i < MAX_PLAYER_COUNT; i++)
                        {
                            log.appendText(new Date() + ": Waiting for player "
                                    + (i + 1) + " to join session #" + sessionNum
                                    + "\n");

                            Socket socket = serverSocket.accept();
                            players.add(i, new Player());
                            try
                            {
                                toClient.add(i, new ObjectOutputStream(socket.getOutputStream()));
                                toClient.get(i).flush();
                                fromClient.add(i, new ObjectInputStream(socket.getInputStream()));
                                names.add(i, fromClient.get(i).readUTF());
                                if (names.get(i) == null || names.get(i).isEmpty())
                                {
                                    names.set(i, "Anonymous_" + i);
                                }
                                //new Event(new EventType("Player Joined"));
                                log.appendText(new Date() + ": Player '" + names.get(i)
                                        + "' has joined session #" + sessionNum + "\n");
                            } 
                            catch (IOException e)
                            {
                                System.err.println(e);
                            }
                            
                            if (i == 0)
                            {
                                log.appendText(new Date() + ": Starting a thread for "
                                + "session #" + sessionNum + "\n");
                            }
                            
                            new Thread().start();
                            HandleSession session = new HandleSession(players, names, toClient, fromClient, sessionNum, log);
                            session.broadcastClients(names);
                            session.broadcastClients(players.get(i));
                        }
                        sessionNum++;
                    } 
                    catch (IOException e)
                    {
                        System.err.println(e);
                    }
                }
            }).start();
        } 
        catch (IOException e)
        {
            System.err.println(e);
        }
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
    private TextArea log;
    private List<String> names;
    private List<ObjectInputStream> fromClient;
    private List<ObjectOutputStream> toClient;
    private Deck deck = new Deck();
    
    /**
     * @param socket client socket
     * @param sessionNum numerical id for client
     * @param log reference to the server log
     */
    public HandleSession(List<Player> players, List<String> names, List<ObjectOutputStream>  toClient, List<ObjectInputStream> fromClient, int sessionNum, TextArea log)
    {
        this.players = players;
        this.names = names;
        this.toClient = toClient;
        this.fromClient = fromClient;
        this.sessionNum = sessionNum;
        this.log = log;
    }

    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < names.size(); i++)
            {
                toClient.get(i).writeObject(names);
                toClient.get(i).flush();
            }
        } 
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
    public void broadcastClients(Object object)
    {
        if (object == null)
            return;
        for (int i = 0; i < players.size(); i++)
            try
            {
                toClient.get(i).writeObject(object);
                toClient.get(i).flush();
            }
            catch (IOException e)
            {
                System.err.println(e);
            }
    }

    public void hit(int playerid) throws IOException
    {
        players.get(playerid).addCardFirstHand(deck.draw());
        if (getValue(players.get(playerid).getFirstHand()) > 21)
        {

        }
    }

    public int getValue(ArrayList<Card> hand)
    {
        int sum = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            sum += hand.remove(i).getNumber().getValue();
        }
        return sum;
    }
}