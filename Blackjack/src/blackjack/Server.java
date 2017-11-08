/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
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
public class Server extends Application implements BlackjackConstants
{
    private Player[] players = new Player[MAX_PLAYER_COUNT];
    
    @Override
    public void start(Stage primaryStage) 
    {
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
                    try
                    {
                        //poll for new client connection
                        for (int i = 0; i < MAX_PLAYER_COUNT; i++)
                        {
                            log.appendText(new Date() + ": Waiting for player " +
                                (i + 1) + " to join session #" + sessionNum + 
                                "\n");
                            
                            players[i].setSocket(serverSocket.accept());
                            try
                            {
                                DataInputStream reader = new DataInputStream(players[i].getSocket().getInputStream());
                                String name = reader.readUTF();
                                if (name == null || name.isEmpty())
                                    name = "Anonymous_" + i;
                                log.appendText(new Date() + ": Player '" + name + 
                                    "' has joined session #" + sessionNum + "\n");
                            }
                            catch (IOException e)
                            {
                                System.err.println(e);
                            }
                        }
                        log.appendText(new Date() + ": Starting a thread for " + 
                                "session #" + sessionNum + "\n");

                        HandleSession task = new HandleSession(players, sessionNum++, log);
                    }
                    catch (IOException e)
                    {
                        System.err.println(e);
                    }
                }
            }).start();
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
