package blackjack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Tanner Lisonbee
 */
public class Client extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        BorderPane pane = new BorderPane();
        ImageView image = new ImageView("images/Blackjack.jpg");
        
        image.fitWidthProperty().bind(primaryStage.widthProperty());
        image.fitHeightProperty().bind(primaryStage.heightProperty());
        pane.setCenter(image);
        pane.setId("pane");
        
        //StackPane root = new StackPane();
        //root.setId("pane");
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add("images/style.css");
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
