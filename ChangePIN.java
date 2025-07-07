import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangePIN {

    public static String currentPIN = "1234"; // default

    public static void showChangePINWindow() {
        Stage window = new Stage();
        window.setTitle("Change PIN");

        PasswordField oldPIN = new PasswordField();
        oldPIN.setPromptText("Enter old PIN");

        PasswordField newPIN = new PasswordField();
        newPIN.setPromptText("Enter new PIN");

        PasswordField confirmPIN = new PasswordField();
        confirmPIN.setPromptText("Confirm new PIN");

        Label message = new Label();
        Button changeButton = new Button("Update PIN");

        changeButton.setOnAction(e -> {
            if (!oldPIN.getText().equals(currentPIN)) {
                message.setText("❌ Old PIN is incorrect");
            } else if (!newPIN.getText().equals(confirmPIN.getText())) {
                message.setText("❌ New PINs do not match");
            } else if (newPIN.getText().length() != 4) {
                message.setText("❌ PIN must be 4 digits");
            } else {
                currentPIN = newPIN.getText();
                message.setText("✅ PIN updated successfully");
            }
        });

        VBox layout = new VBox(10, oldPIN, newPIN, confirmPIN, changeButton, message);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #fffdf3;");
        Scene scene = new Scene(layout, 300, 250);

        window.setScene(scene);
        window.show();
    }
}
