public class LoanAccount extends Account {
    private double loanAmount;
    private double interestRate;

    public LoanAccount(String accountNumber, Customer customer, double loanAmount, double interestRate) {
        super(accountNumber, customer, -loanAmount);
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be greater than zero.");
        }
        balance += amount;
        loanAmount -= amount;
        System.out.println("Loan repayment successful!");
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        balance -= amount;
        loanAmount += amount;
        System.out.println("Additional loan amount withdrawn successfully!");
        return true;
    }

    public void calculateInterest() {
        double interest = loanAmount * (interestRate / 100);
        balance -= interest;
        System.out.println("Interest of " + interest + " applied. New balance: " + balance);
    }
}
