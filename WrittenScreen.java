import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("💳 Welcome to Mini ATM");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #2b2b2b;");

        StackPane root = new StackPane(welcomeLabel);
        root.setStyle("-fx-background-color: #dff6ff;");
        Scene scene = new Scene(root, 400, 250);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome");
        primaryStage.show();

        // Automatically go to LoginPage after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            LoginPage login = new LoginPage();
            try {
                login.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

