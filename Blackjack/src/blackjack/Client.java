package blackjack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
        StackPane root = new StackPane();
        GridPane grid = new GridPane();
        Pane buttonPane = new Pane(), fieldPane = new Pane();
        
        //set and configure background
        grid.setStyle("-fx-background-image: url(" + BACKGROUND + "); \n" +
                      "-fx-background-position: center center; \n" +
                      "-fx-background-repeat: stretch; \n" + 
                      "-fx-background-size: 1280 720;");
        //grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        //grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        
        //add column and row constraints to keep elements in the proper position
        grid.getColumnConstraints().add(new ColumnConstraints(235));
        grid.getColumnConstraints().add(new ColumnConstraints(75));
        grid.getColumnConstraints().add(new ColumnConstraints(250));
        grid.getColumnConstraints().add(new ColumnConstraints(250));
        grid.getColumnConstraints().add(new ColumnConstraints(345));
        grid.getColumnConstraints().add(new ColumnConstraints(75));
        grid.getRowConstraints().add(new RowConstraints(50));
        grid.getRowConstraints().add(new RowConstraints(650));
        
        //current bet label
        Label betLabel = new Label("CURRENT BET: ");
        betLabel.setFont(Font.font("Times New Roman", 32));
        betLabel.setTextFill(Color.web("#FFD000"));
        GridPane.setConstraints(betLabel, 0, 0);
        
        //current bet field
        TextField betField = new TextField();
        betField.setEditable(false);
        GridPane.setConstraints(betField, 1, 0);
        
        //credits available label
        Label creditsLabel = new Label("CREDITS AVAILABLE: ");
        creditsLabel.setFont(Font.font("Times New Roman", 32));
        creditsLabel.setTextFill(Color.web("#FFD000"));
        GridPane.setConstraints(creditsLabel, 4, 0);
        
        //credits available field
        TextField creditsField = new TextField();
        creditsField.setEditable(false);
        GridPane.setConstraints(creditsField, 5, 0);
        
        //stay button
        Button btnStay = new Button("STAY");
        btnStay.setLayoutX(440);
        btnStay.setLayoutY(472);
        btnStay.setFont(Font.font("Times New Roman", 16));
        btnStay.setOnAction((ActionEvent event) -> 
        {
            System.out.println("STAY");
        });
        
        //hit button
        Button btnHit = new Button("HIT");
        btnHit.setLayoutX(792);
        btnHit.setLayoutY(472);
        btnHit.setFont(Font.font("Times New Roman", 16));
        btnHit.setOnAction((ActionEvent event) -> 
        {
            System.out.println("HIT");
        });
        
        //player 1
        TextField player1Field = new TextField();
        player1Field.setLayoutX(162);
        player1Field.setLayoutY(555);
        player1Field.setEditable(false);
        player1Field.setPrefWidth(96);
        
        //player 2
        TextField player2Field = new TextField();
        player2Field.setLayoutX(282);
        player2Field.setLayoutY(555);
        player2Field.setEditable(false);
        player2Field.setPrefWidth(96);
        
        //player 3
        TextField player3Field = new TextField();
        player3Field.setLayoutX(914);
        player3Field.setLayoutY(555);
        player3Field.setEditable(false);
        player3Field.setPrefWidth(96);
        
        //player 4
        TextField player4Field = new TextField();
        player4Field.setLayoutX(1032);
        player4Field.setLayoutY(555);
        player4Field.setEditable(false);
        player4Field.setPrefWidth(96);
        
        buttonPane.getChildren().addAll(btnStay, btnHit);
        grid.getChildren().addAll(betLabel, creditsLabel, betField, creditsField);
        fieldPane.getChildren().addAll(player1Field, player2Field, player3Field, player4Field);
        root.getChildren().addAll(grid, buttonPane, fieldPane);
        
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