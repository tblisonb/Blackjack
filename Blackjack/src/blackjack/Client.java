package blackjack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        GridPane root = new GridPane();
        //set and configure background
        root.setStyle("-fx-background-image: url(" + BACKGROUND + "); \n" +
                      "-fx-background-position: center center; \n" +
                      "-fx-background-repeat: stretch; \n" + 
                      "-fx-background-size: 1280 720;");
        
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(8);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        
        root.getColumnConstraints().add(new ColumnConstraints(250));
        root.getColumnConstraints().add(new ColumnConstraints(165));
        root.getColumnConstraints().add(new ColumnConstraints(165));
        root.getColumnConstraints().add(new ColumnConstraints(165));
        root.getColumnConstraints().add(new ColumnConstraints(350));
        root.getColumnConstraints().add(new ColumnConstraints(165));
        
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(650));
        
        Label betLabel = new Label("CURRENT BET: ");
        betLabel.setFont(Font.font("Times New Roman", 32));
        betLabel.setTextFill(Color.web("#FFD000"));
        
        Label creditsLabel = new Label("CREDITS AVAILABLE: ");
        creditsLabel.setFont(Font.font("Times New Roman", 32));
        creditsLabel.setTextFill(Color.web("#FFD000"));
        
        GridPane.setConstraints(betLabel, 0, 0);
        GridPane.setConstraints(creditsLabel, 4, 0);
        
        root.getChildren().addAll(betLabel, creditsLabel);
        
        /*
        image.fitWidthProperty().bind(primaryStage.widthProperty());
        image.fitHeightProperty().bind(primaryStage.heightProperty());
        pane.setCenter(image);
        pane.setId("pane");
        */
        
        //StackPane root = new StackPane();
        //root.setId("pane");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.setResizable(ALLOW_RESIZE);
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