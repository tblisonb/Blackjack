package blackjack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Tanner Lisonbee
 */
public class Client extends Application implements Runnable, BlackjackConstants
{
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    
    @Override
    public void start(Stage primaryStage)
    {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-image: url(" + BACKGROUND + "); \n" +
                      "-fx-background-position: center center; \n" +
                      "-fx-background-repeat: stretch;");
        
        /*
        image.fitWidthProperty().bind(primaryStage.widthProperty());
        image.fitHeightProperty().bind(primaryStage.heightProperty());
        pane.setCenter(image);
        pane.setId("pane");
        */
        
        //StackPane root = new StackPane();
        //root.setId("pane");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        connectToServer();
    }
    
    private void connectToServer()
    {
        try
        {
            //new socket on port 8000, looking on local network for server
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        
        //new thread for connecting to server
        new Thread(this).start();
    }
    
    @Override
    public void run() 
    {
        try
        {
            //To-do ~ implement data passing on client side
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}