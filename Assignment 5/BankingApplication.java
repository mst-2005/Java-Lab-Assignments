import java.util.*;

public class BankingApplication {
    private static List<Account> accounts = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Customer");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Check Balance");
            System.out.println("7. Display Consolidated Information");
            System.out.println("8. Exit");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createCustomer(scanner);
                        break;
                    case 2:
                        createAccount(scanner);
                        break;
                    case 3:
                        deposit(scanner);
                        break;
                    case 4:
                        withdraw(scanner);
                        break;
                    case 5:
                        transfer(scanner);
                        break;
                    case 6:
                        checkBalance(scanner);
                        break;
                    case 7:
                        displayConsolidatedInformation();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numerical value.");
                scanner.nextLine(); // Clear the invalid input from the scanner buffer
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void createCustomer(Scanner scanner) {
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer address:");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, address);
        customers.add(customer);
        System.out.println("Customer created successfully!");
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        Customer customer = findCustomer(name);
        
        if (customer == null) {
            System.out.println("Customer not found! Please create a customer first.");
            return;
        }

        System.out.println("Enter your account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Select Account Type (1 for Savings, 2 for Loan):");
        int type = scanner.nextInt();
        scanner.nextLine();
        
        if (type == 1) {
            System.out.println("Enter initial balance:");
            double balance = scanner.nextDouble();
            System.out.println("Enter minimum balance:");
            double minimumBalance = scanner.nextDouble();
            scanner.nextLine();
            
            Account account = new SavingsAccount(accountNumber, customer, balance, minimumBalance);
            accounts.add(account);
            System.out.println("Savings Account created successfully!");
        } else if (type == 2) {
            System.out.println("Enter loan amount:");
            double loanAmount = scanner.nextDouble();
            System.out.println("Enter interest rate:");
            double interestRate = scanner.nextDouble();
            scanner.nextLine();

            Account account = new LoanAccount(accountNumber, customer, loanAmount, interestRate);
            accounts.add(account);
            System.out.println("Loan Account created successfully!");
        } else {
            System.out.println("Invalid Account Type.");
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter amount to deposit:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter amount to withdraw:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.println("Enter source account number:");
        String sourceAccountNumber = scanner.nextLine();
        System.out.println("Enter destination account number:");
        String destinationAccountNumber = scanner.nextLine();
        System.out.println("Enter amount to transfer:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account sourceAccount = findAccount(sourceAccountNumber);
        Account destinationAccount = findAccount(destinationAccountNumber);

        if (sourceAccount != null && destinationAccount != null) {
            if (sourceAccount.withdraw(amount)) {
                destinationAccount.deposit(amount);
                System.out.println("Transfer completed successfully!");
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

    private static void checkBalance(Scanner scanner) {
        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();

        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void displayConsolidatedInformation() {
        for (Customer customer : customers) {
            System.out.println("--------------------------------------------------");
            System.out.println("Customer Info: " + customer);
            System.out.println("Accounts:");
            boolean hasAccounts = false;
            for (Account account : accounts) {
                if (account.getCustomer().equals(customer)) {
                    hasAccounts = true;
                    System.out.println(" - Account No: " + account.getAccountNumber() +
                            ", Type: " + account.getClass().getSimpleName() +
                            ", Balance: " + account.getBalance());
                }
            }
            if (!hasAccounts) {
                System.out.println(" - No accounts found.");
            }
        }
        System.out.println("--------------------------------------------------");
    }

    private static Customer findCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
