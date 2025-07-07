import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter your 4-digit PIN:");
        PasswordField pinField = new PasswordField();
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String enteredPIN = pinField.getText();
            if (enteredPIN.equals(ChangePIN.currentPIN)) {
                messageLabel.setText("Access granted ✅");
                Dashboard.showDashboard(primaryStage); // go to dashboard
            } else {
                messageLabel.setText("Wrong PIN ❌");
            }
        });

        VBox root = new VBox(15, label, pinField, loginButton, messageLabel);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f4ff;");

        Scene scene = new Scene(root, 350, 250);
        primaryStage.setTitle("ATM Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
