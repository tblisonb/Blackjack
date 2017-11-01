package blackjack;

import javafx.application.Application;
import javafx.scene.Scene;
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
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(new Scene(new StackPane(), 300, 250));
        primaryStage.show();
    }
}
