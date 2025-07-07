@SuppressWarnings("unchecked")
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String date;
    private String type;
    private double amount;
    private double balance;

    public Transaction(String type, double amount, double balance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public String getDate() { return date; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
}
