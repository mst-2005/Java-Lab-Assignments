import java.util.Date;

public class Transaction {
    private int transactionID;
    private int accountNumber;
    private String type;
    private double amount;
    private Date date;

    public Transaction(int transactionID, int accountNumber, String type, double amount) {
        this.transactionID = transactionID;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    public String getTransactionDetails() {
        return "ID: " + transactionID + ", Account: " + accountNumber + 
               ", Type: " + type + ", Amount: " + amount + ", Date: " + date;
    }
}
