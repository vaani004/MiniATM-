import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dashboard {

    private static double balance = 5000;
    private static ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public static void showDashboard(Stage primaryStage) {
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount ₹");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button checkBalanceBtn = new Button("Check Balance");
        Button changePINBtn = new Button("Change PIN");
        Button historyBtn = new Button("Transaction History");
        Button logoutBtn = new Button("Logout");

        Label clockLabel = new Label();
        clockLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        updateClock(clockLabel);  // Start real-time clock

        depositBtn.setOnAction(e -> {
            String input = amountField.getText();
            if (isValidAmount(input)) {
                double amt = Double.parseDouble(input);
                balance += amt;
                transactions.add(new Transaction("Deposit", amt, balance));
                showAlert("Deposit", "₹" + amt + " has been deposited.");
                amountField.clear();
            } else {
                showAlert("Invalid Input", "Please enter a valid positive number.");
            }
        });

        withdrawBtn.setOnAction(e -> {
            String input = amountField.getText();
            if (isValidAmount(input)) {
                double amt = Double.parseDouble(input);
                if (balance >= amt) {
                    balance -= amt;
                    transactions.add(new Transaction("Withdraw", amt, balance));
                    showAlert("Withdraw", "₹" + amt + " has been withdrawn.");
                    amountField.clear();
                } else {
                    showAlert("Withdraw Failed", "Insufficient Balance.");
                }
            } else {
                showAlert("Invalid Input", "Please enter a valid positive number.");
            }
        });

        checkBalanceBtn.setOnAction(e -> {
            showAlert("Current Balance", "Your current balance is ₹" + balance);
        });

        changePINBtn.setOnAction(e -> {
            ChangePIN.showChangePINWindow();
        });

        historyBtn.setOnAction(e -> showTransactionHistory());

        logoutBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Logout Confirmation");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to logout?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(yes, no);

            confirm.showAndWait().ifPresent(response -> {
                if (response == yes) {
                    LoginPage login = new LoginPage();
                    try {
                        login.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });

        VBox vbox = new VBox(15, amountField, depositBtn, withdrawBtn, checkBalanceBtn, changePINBtn, historyBtn, logoutBtn, clockLabel);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #eaf7ff;");
        vbox.getStyleClass().add("atm-dashboard");

        Scene scene = new Scene(vbox, 370, 400);
        scene.getStylesheets().add("styles.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("ATM Dashboard");
        primaryStage.show();
    }

    private static boolean isValidAmount(String input) {
        try {
            double value = Double.parseDouble(input);
            return value > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @SuppressWarnings("unchecked")
    private static void showTransactionHistory() {
        Stage historyStage = new Stage();
        historyStage.setTitle("Transaction History");

        TableView<Transaction> table = new TableView<>();
        table.setItems(transactions);

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date & Time");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(150);

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount (₹)");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, Double> balanceCol = new TableColumn<>("Balance (₹)");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        table.getColumns().addAll(dateCol, typeCol, amountCol, balanceCol);

        VBox vbox = new VBox(table);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, 500, 300);

        historyStage.setScene(scene);
        historyStage.show();
    }

    private static void updateClock(Label clockLabel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            clockLabel.setText("🕒 " + LocalDateTime.now().format(formatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
}
