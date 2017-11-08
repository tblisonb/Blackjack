/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.Date;
import javafx.scene.control.TextArea;

/**
 *
 * @author Tanner Lisonbee
 */
public class HandleSession implements Runnable, BlackjackConstants
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
