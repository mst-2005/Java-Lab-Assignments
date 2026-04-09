public class SavingsAccount extends Account {
    private double minimumBalance;

    public SavingsAccount(String accountNumber, Customer customer, double balance, double minimumBalance) {
        super(accountNumber, customer, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount >= minimumBalance) {
            balance -= amount;
            System.out.println("Withdrawal successful!");
            return true;
        } else {
            System.out.println("Insufficient funds! Minimum balance requirement not met.");
            return false;
        }
    }
}
