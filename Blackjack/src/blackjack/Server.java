/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import static blackjack.BlackjackConstants.MAX_PLAYER_COUNT;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
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
public class Server extends Application implements BlackjackConstants {

    private Player[] players = new Player[MAX_PLAYER_COUNT];
    Deck deck = new Deck();

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        TextArea log = new TextArea(); //server output
        log.setEditable(false); //output log shouldn't be editable
        root.getChildren().add(log);

        Scene scene = new Scene(root, 600, 400);
        //ensure server terminates when the window is closed
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("Blackjack Server");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            //new socket on port 8000
            ServerSocket serverSocket = new ServerSocket(8000);
            log.appendText(new Date() + ": Server started at socket 8000\n");

            //new thread for polling for client sockets.
            new Thread(()
                    -> {
                int sessionNum = 1; //keeps track of what client is connected
                while (true) //runs indefinitely
                {
                    try {
                        //poll for new client connection
                        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
                            log.appendText(new Date() + ": Waiting for player "
                                    + (i + 1) + " to join session #" + sessionNum
                                    + "\n");

                            Socket socket = serverSocket.accept();
                            players[i] = new Player(socket);
                            try {
                                DataInputStream reader = new DataInputStream(players[i].getSocket().getInputStream());
                                String name = reader.readUTF();
                                if (name == null || name.isEmpty()) {
                                    name = "Anonymous_" + i;
                                }
                                log.appendText(new Date() + ": Player '" + name
                                        + "' has joined session #" + sessionNum + "\n");
                            } catch (IOException e) {
                                System.err.println(e);
                            }
                        }
                        log.appendText(new Date() + ": Starting a thread for "
                                + "session #" + sessionNum + "\n");

                        HandleSession task = new HandleSession(players, sessionNum++, log);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }).start();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void hit(int playerid) {
        players[playerid].addCardFirstHand(deck.draw());
        if(getValue(players[playerid].getFirstHand()) > 21){
        
        }
            

    }

    public int getValue(ArrayList<Card> hand) {
        int sum = 0;
        for (int i = 0; i < hand.size(); i++) {
            sum += hand.remove(i).getNumber().value;

        }
        return sum;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 *
 * @author Tanner Lisonbee
 */
class HandleSession implements Runnable, BlackjackConstants
{
    private Player[] players;
    private final int sessionNum;
    private TextArea log;
    
    private DataInputStream[] fromClient;
    private DataOutputStream[] toClient;
    
    /**
     * @param socket client socket
     * @param sessionNum numerical id for client
     * @param log reference to the server log
     */
    public HandleSession(Player[] players, int sessionNum, TextArea log)
    {
        this.players = players;
        this.sessionNum = sessionNum;
        this.log = log;
    }
    
    @Override
    public void run() 
    {
        try
        {
            for (int i = 0; i < MAX_PLAYER_COUNT; i++)
            {
                fromClient[i] = new DataInputStream(players[i].getSocket().getInputStream());
                toClient[i] = new DataOutputStream(players[i].getSocket().getOutputStream());
            }
            
            while (true) //runs indefinitely
            {
                //To-do ~ Implement data passing on server-side
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}