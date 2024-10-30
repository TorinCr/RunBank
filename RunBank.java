import java.util.*;
import java.io.*;

/**
 * Abstract base class representing a bank account.
 * Provides basic functionality for managing account balance and transactions.
 * 
 * @author Torin Crudup, Brayan Mendoza
 * @version 1.0
 */
abstract class Account {
    /** The current balance in the account */
    protected double balance;
    
    /** The unique account number */
    protected int accountNumber;

    /**
     * Constructs a new account with initial balance and account number.
     * 
     * @param initialBalance The initial amount of money in the account
     * @param accountNumber The unique identifier for this account
     */

    public Account(double initialBalance, int accountNumber) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the current balance of the account.
     * 
     * @return The current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the account number.
     * 
     * @return The account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Deposits money into the account.
     * 
     * @param amount The amount of money to deposit
     */
    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Deposit of $" + amount + " successful.");
    }

    /**
     * Withdraws money from the account if sufficient funds are available.
     * 
     * @param amount The amount of money to withdraw
     */
    public void withdraw(double amount) {
        if (canWithdraw(amount)) {
            this.balance -= amount;
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            handleInsufficientFunds();
        }
    }

    /**
     * Transfers money from this account to another account.
     * 
     * @param toAccount The destination account
     * @param amount The amount of money to transfer
     */
    public void transfer(Account toAccount, double amount) {
        if (canWithdraw(amount)) {
            this.balance -= amount;
            toAccount.deposit(amount);
            System.out.println("Transfer of $" + amount + " successful.");
        } else {
            handleInsufficientFunds();
        }
    }

    /**
     * Checks if the account has sufficient funds for a withdrawal.
     * 
     * @param amount The amount to check against the balance
     * @return true if withdrawal is allowed, false otherwise
     */
    protected boolean canWithdraw(double amount) {
        return amount <= this.balance;
    }

    /**
     * Handles the case when insufficient funds are available for a transaction.
     */
    protected void handleInsufficientFunds() {
        System.out.println("Insufficient funds for withdrawal.");
    }
}

/**
 * Represents a checking account.
 * Basic account type with standard withdrawal and deposit functionality.
 */
class Checking extends Account {
    /**
     * Creates a new checking account.
     * 
     * @param initialBalance The initial amount of money in the account
     * @param accountNumber The unique identifier for this account
     */
    public Checking(double initialBalance, int accountNumber) {
        super(initialBalance, accountNumber);
    }
}

/**
 * Represents a savings account.
 * Includes monthly withdrawal limits and special handling for exceeded limits.
 */
class Savings extends Account {
    /** Maximum number of withdrawals allowed per month */
    private static final int MONTHLY_WITHDRAWAL_LIMIT = 6;
    
    /** Current count of withdrawals made this month */
    private int withdrawalsThisMonth = 0;

    /**
     * Creates a new savings account.
     * 
     * @param initialBalance The initial amount of money in the account
     * @param accountNumber The unique identifier for this account
     */
    public Savings(double initialBalance, int accountNumber) {
        super(initialBalance, accountNumber);
    }

    /**
     * Checks if withdrawal is allowed based on both balance and monthly limit.
     * 
     * @param amount The amount to withdraw
     * @return true if withdrawal is allowed, false otherwise
     */
    
    @Override
    protected boolean canWithdraw(double amount) {
        return super.canWithdraw(amount) && withdrawalsThisMonth < MONTHLY_WITHDRAWAL_LIMIT;
    }

    /**
     * Handles insufficient funds or exceeded withdrawal limit cases.
     */
    @Override
    protected void handleInsufficientFunds() {
        if (withdrawalsThisMonth >= MONTHLY_WITHDRAWAL_LIMIT) {
            System.out.println("Monthly withdrawal limit exceeded.");
        } else {
            super.handleInsufficientFunds();
        }
    }

    /**
     * Processes a withdrawal and updates the monthly withdrawal counter.
     * 
     * @param amount The amount to withdraw
     */
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        if (canWithdraw(amount)) {
            withdrawalsThisMonth++;
        }
    }
}

/**
 * Represents a credit account.
 * Includes credit limit functionality allowing withdrawals beyond the balance up to the credit limit.
 */
class Credit extends Account {
    /** The maximum amount that can be borrowed */
    private double creditLimit;

    /**
     * Creates a new credit account.
     * 
     * @param initialBalance The initial amount of money in the account
     * @param accountNumber The unique identifier for this account
     * @param creditLimit The maximum amount that can be borrowed
     */
    public Credit(double initialBalance, int accountNumber, double creditLimit) {
        super(initialBalance, accountNumber);
        this.creditLimit = creditLimit;
    }

    /**
     * Checks if withdrawal is allowed based on balance plus available credit.
     * 
     * @param amount The amount to withdraw
     * @return true if withdrawal is allowed, false otherwise
     */
    @Override
    protected boolean canWithdraw(double amount) {
        return amount <= (this.balance + creditLimit);
    }

    /**
     * Handles the case when a withdrawal would exceed the credit limit.
     */
    @Override
    protected void handleInsufficientFunds() {
        System.out.println("Credit limit exceeded.");
    }
}

/**
 * Represents a person in the banking system
 */

class Person{
    private String name;
    protected HashMap<Integer, Account> accounts;

    /**
     * Creates a new person
     * @param name The person's name
     */

    public Person(String name){
        this.name = name;
        this.accounts = new HashMap<>();
    }

/**
     * Adds an account to the person's account list
     * @param account The account to add
     */

    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(), account);
    }

    /**
     * Retrieves an account by its number
     * @param accountNumber The account number to look up
     * @return The account if found, null otherwise
     */

    public Account getAccount(int accountNumber){
        return accounts.get(accountNumber);
    }

    /**
     * Gets the person's name
     * @return The person's name
     */

    public String getName(){
        return name;
    }
}

/**
 * Represents a bank customer, extends Person with additional banking functionality
 * Manages customer accounts and provides banking operations like deposits, withdrawals, and transfers.
 */

class Customer extends Person{
    /** The customer's unique identification number */
    private String id;

    /** The customer's date of birth */
    private String dob;

    /** The customer's mailing address */
    private String address;

    /** The customer's contact phone number */
    private String phone;

    /**
     * Creates a new customer
     * @param name The customer's name
     */

    public Customer(String name){
        super(name);
        this.accounts = new HashMap<>();
    }

    /**
     * Sets the customer's personal details
     * @param id The customer's ID
     * @param dob The customer's date of birth
     * @param address The customer's address
     * @param phone The customer's phone number
     */

    public void setCustomerDetails(String id, String dob, String address, String phone) {
        this.id = id;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Gets the customer's ID.
     * @return The customer's ID
     */
    public String getId() { return id; }

    /**
     * Gets the customer's date of birth.
     * @return The customer's date of birth
     */
    public String getDob() { return dob; }

    /**
     * Gets the customer's address.
     * @return The customer's address
     */
    public String getAddress() { return address; }

    /**
     * Gets the customer's phone number.
     * @return The customer's phone number
     */
    public String getPhone() { return phone; }

    /**
     * Displays all accounts owned by the customer and logs the inquiry
     */

    public void displayAllAccounts(){
        System.out.println("Accounts for " + this.getName() + ":");
        for (Account account : accounts.values()){
            String message = this.getName() + " made a balance inquiry on " + getAccountType(account) + "-" + account.getAccountNumber() + ". Balance: $" + account.getBalance();
            System.out.println("Account Number: " + account.getAccountNumber() + " | Balance: $" + account.getBalance());
            RunBank.logTransaction(message);
        }
    }

    /**
     * Determines the type of account
     * @param account The account to check
     * @return A string representing the account type (Checking, Savings, or Credit)
     */

    private String getAccountType(Account account) {
        if (account instanceof Checking) return "Checking";
        if (account instanceof Savings) return "Savings";
        if (account instanceof Credit) return "Credit";
        return "Account";
    }

    /**
     * Processes a deposit transaction
     * @param accountNumber The account number for the deposit
     * @param amount The amount to deposit
     */

    public void deposit(int accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            logTransaction("deposited", account, amount);
            RunBank.updateCustomerDataFile();
        } else {
            System.out.println("Account not found.");
        }
    }

    /**
     * Processes a withdrawal transaction
     * @param accountNumber The account number for the withdrawal
     * @param amount The amount to withdraw
     */

    public void withdraw(int accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            String message = this.getName() + " withdrew $" + amount + " from " + accountNumber + "New Balance: ";

            RunBank.logTransaction(message);
            RunBank.updateCustomerDataFile();
        } else {
            System.out.println("Account not found.");
        }
    }

    /**
     * Processes a transfer between accounts
     * @param fromAccountNumber Source account number
     * @param toAccountNumber Destination account number
     * @param amount Amount to transfer
     */

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) {
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);
            
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
            String message = this.getName() + " transferred $" + amount + " from " + getAccountType(fromAccount) + "-" + fromAccountNumber + " to " + getAccountType(toAccount) + "-" + toAccountNumber + ". " +getAccountType(fromAccount) + "-" + fromAccountNumber + " Balance: $" + fromAccount.getBalance() + ". " + getAccountType(toAccount) + "-" + toAccountNumber + " Balance: $" + toAccount.getBalance();

            RunBank.logTransaction(message);
            RunBank.updateCustomerDataFile();
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    /**
     * Processes a payment to another customer
     * @param recipient The recipient customer
     * @param fromAccountNumber Source account number
     * @param toAccountNumber Destination account number
     * @param amount Amount to pay
     */

    public void payOther(Customer recipient, int fromAccountNumber, int toAccountNumber, double amount) {
        Account fromAccount = this.getAccount(fromAccountNumber);
        Account recipientAccount = recipient.getAccount(toAccountNumber);
            
        if (fromAccount != null && recipientAccount != null) {
            fromAccount.transfer(recipientAccount, amount);
            String senderMessage = this.getName() + " paid " + recipient.getName() + " $" + amount + " from " + getAccountType(fromAccount) + "-" + fromAccountNumber + ". New Balance: $" + fromAccount.getBalance();

        RunBank.logTransaction(senderMessage);
        
        String recipientMessage = recipient.getName() + " received $" + amount + " from " + this.getName() + ". New Balance: $" + recipientAccount.getBalance();

        RunBank.logTransaction(recipientMessage);
        RunBank.updateCustomerDataFile();

        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    /**
     * Logs a transaction to the transaction log
     * @param action The type of transaction
     * @param account The account involved
     * @param amount The transaction amount
     */

    public void logTransaction(String action, Account account, double amount){
        String logMessage;
        if (action.equals("deposited")) {
            logMessage = this.getName() + " deposited $" + amount + " to " + getAccountType(account) + "-" + account.getAccountNumber() + ". New Balance: $" + account.getBalance();
        }
        else if (action.equals("withdrew")) {
            logMessage = this.getName() + " withdrew $" + amount + " from " + getAccountType(account) + "-" + account.getAccountNumber() + ". New Balance: $" + account.getBalance();
        }
        else if (action.equals("transferred")) {
            logMessage = this.getName() + " transferred $" + amount + " from " + getAccountType(account) + "-" + account.getAccountNumber() + ". New Balance: $" + account.getBalance();
        }
        else if (action.startsWith("paid to")) {
            logMessage = this.getName() + " " + action + " $" + amount + " from " + getAccountType(account) + "-" + account.getAccountNumber() + ". New Balance: $" + account.getBalance();
        }
        else {
            logMessage = this.getName() + " " + action + " $" + amount + " from " + getAccountType(account) + "-" + account.getAccountNumber() + ". New Balance: $" + account.getBalance();
    }
    System.out.println(logMessage);
    RunBank.logTransaction(logMessage);
    }
}


public class RunBank{
    /** Map of customer names to Customer objects */
    private static HashMap<String, Customer> customers = new HashMap<>();

    /** List of all transaction logs */
    private static ArrayList<String> transactionLog = new ArrayList<>();

    /** Name of the customer data CSV file */
    private static final String CSV_FILE_NAME = "CS 3331 - Bank Users.csv";
    
    /** Name of the transaction log file */
    private static final String log_file_name = "transaction_log.txt";

    /**
     * Main method that runs the banking system.
     * Provides a menu-driven interface for customers and bank managers.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){ 
        String fileName = "CS 3331 - Bank Users.csv";

        readCustomerDataFromFile(fileName);

        Scanner scanner = new Scanner(System.in);
        
        while(true){
            System.out.println("Are you an individual person or a bank manager?");
            System.out.println("1.) Individual Person");
            System.out.println("2.) Bank Manager");
            System.out.println("Type 'EXIT' to quit.");

            String userType = scanner.nextLine().trim();

            if (userType.equalsIgnoreCase("EXIT")) {
                break;
            }

            if (userType.equals("1")) {
                handleIndividualPerson(scanner);
            } else if (userType.equals("2")) {
                handleBankManager(scanner);
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }
    
    /**
     * Handles the individual person menu and operations
     * @param scanner Scanner for user input
     */

    private static void handleIndividualPerson(Scanner scanner){
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine().trim();
        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine().trim();
        
        String fullName = firstName + " " + lastName;
        Customer customer = customers.get(fullName);
    
        if (customer != null){
            while(true){
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. View all accounts");
                System.out.println("2. Deposit money");
                System.out.println("3. Withdraw money");
                System.out.println("4. Transfer between accounts");
                System.out.println("5. Pay someone");
                System.out.println("6. Exit");
    
                String choice = scanner.nextLine().trim();
    
                try{
                    switch (choice){
                        case "1":
                            customer.displayAllAccounts();
                            break;
                        
                        case "2":
                            System.out.println("Enter account number:");
                            int depositAccount = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println("Enter amount to deposit:");
                            double depositAmount = Double.parseDouble(scanner.nextLine().trim());
                            customer.deposit(depositAccount, depositAmount);
                            break;
                            
                        case "3":
                            System.out.println("Enter account number:");
                            int withdrawAccount = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println("Enter amount to withdraw:");
                            double withdrawAmount = Double.parseDouble(scanner.nextLine().trim());
                            customer.withdraw(withdrawAccount, withdrawAmount);
                            break;
                            
                        case "4":
                            System.out.println("Enter source account number:");
                            int fromAccount = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println("Enter destination account number:");
                            int toAccount = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println("Enter amount to transfer:");
                            double transferAmount = Double.parseDouble(scanner.nextLine().trim());
                            customer.transfer(fromAccount, toAccount, transferAmount);
                            break;
                            
                        case "5":
                            System.out.println("Enter recipient's first name:");
                            String recipientFirstName = scanner.nextLine().trim();
                            System.out.println("Enter recipient's last name:");
                            String recipientLastName = scanner.nextLine().trim();
                            
                            String recipientName = recipientFirstName + " " + recipientLastName;
                            Customer recipient = customers.get(recipientName);
                            
                            if (recipient != null) {
                                System.out.println("Enter your account number:");
                                int payFromAccount = Integer.parseInt(scanner.nextLine().trim());
                                System.out.println("Enter recipient's account number:");
                                int payToAccount = Integer.parseInt(scanner.nextLine().trim());
                                System.out.println("Enter amount to pay:");
                                double payAmount = Double.parseDouble(scanner.nextLine().trim());
                                
                                customer.payOther(recipient, payFromAccount, payToAccount, payAmount);
                            } else {
                                System.out.println("Recipient not found");
                            }
                            break;
    
                        case "6":
                            return;
    
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Invalid input. Please enter valid numbers.");
                }
            }
        } else {
            System.out.println("No accounts found for "+fullName);
        }
    }

    /**
     * Handles the bank manager menu and operations
     * @param scanner Scanner for user input
     */

    private static void handleBankManager(Scanner scanner) {
        System.out.println("Would you like to inquire about an account by:");
        System.out.println("1.) Name");
        System.out.println("2.) Type/Number");
        
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
            System.out.println("Whose account would you like to inquire about?");
            String accountHolderName = scanner.nextLine().trim();
            Customer customer = customers.get(accountHolderName);
            
            if (customer != null) {
                customer.displayAllAccounts();
            } else {
                System.out.println("No accounts found for " + accountHolderName);
            }
        } else if (choice.equals("2")) {
            System.out.println("What is the account type? (Checking/Savings)");
            String accountType = scanner.nextLine().trim().toLowerCase();
            System.out.println("What is the account number?");
            int accountNumber = Integer.parseInt(scanner.nextLine().trim());
            
            for (Customer customer : customers.values()) {
                Account account = customer.getAccount(accountNumber);
                if (account != null && (accountType.equals("checking") && account instanceof Checking || 
                    accountType.equals("savings") && account instanceof Savings)) {
                    System.out.println("Account holder: " + customer.getName());
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Balance: $" + account.getBalance());
                    return;
                }
            }
            System.out.println("Account not found.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Appends a transaction message to the log file
     * @param message The message to append
     */

    public static void logTransaction(String message){
        transactionLog.add(message);
        appendToLogFile(message);
    }

    /**
     * Appends a transaction message to the log file
     * @param message The message to append
     */

    private static void appendToLogFile(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(log_file_name, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    /**
     * Saves all transaction logs to a file
     * @param fileName The name of the file to save to
     */
    public static void saveLogsToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String log : transactionLog) {
                bw.write(log);
                bw.newLine();
            }
            System.out.println("Transaction log saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to the log file: " + e.getMessage());
        }
    }
    
    /**
     * Parses a CSV line into individual fields
     * @param line The CSV line to parse
     * @return List of fields from the CSV line
     */

private static List<String> parseCSVLine(String line) {
    List<String> fields = new ArrayList<>();
    StringBuilder currentField = new StringBuilder();
    boolean inQuotes = false;
    
    for (char c : line.toCharArray()) {
        if (c == '"') {
            inQuotes = !inQuotes;
            currentField.append(c);
        } else if (c == ',' && !inQuotes) {
            fields.add(currentField.toString());
            currentField.setLength(0);
        } else {
            currentField.append(c);
        }
    }
    
    fields.add(currentField.toString());
    
    while (fields.size() < 13) {
        fields.add("");
    }
    
    return fields;
}

private static ArrayList<String> originalFileLines = new ArrayList<>();

    /**
     * Reads customer data from the CSV file
     * @param fileName The name of the file to read from
     */
public static void readCustomerDataFromFile(String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        originalFileLines.clear();
        customers.clear();
        
        String headerLine = br.readLine();
        originalFileLines.add(headerLine);
        
        String line;
        while ((line = br.readLine()) != null) {
            originalFileLines.add(line);
            
            List<String> data = parseCSVLine(line);
            
            if (data.size() >= 13) {
                String id = data.get(0).trim();
                String firstName = data.get(1).trim();
                String lastName = data.get(2).trim();
                String dob = data.get(3).trim();
                String address = data.get(4).trim().replace("\"", "");
                String phone = data.get(5).trim();
                
                String fullName = firstName + " " + lastName;
                Customer customer = new Customer(fullName);
                customer.setCustomerDetails(id, dob, address, phone);
                
                try {
                    String checkingNum = data.get(6).trim();
                    String checkingBal = data.get(7).trim();
                    if (!checkingNum.isEmpty() && !checkingBal.isEmpty()) {
                        int checkingNumber = Integer.parseInt(checkingNum);
                        double checkingBalance = Double.parseDouble(checkingBal);
                        customer.addAccount(new Checking(checkingBalance, checkingNumber));
                    }
                    
                    String savingsNum = data.get(8).trim();
                    String savingsBal = data.get(9).trim();
                    if (!savingsNum.isEmpty() && !savingsBal.isEmpty()) {
                        int savingsNumber = Integer.parseInt(savingsNum);
                        double savingsBalance = Double.parseDouble(savingsBal);
                        customer.addAccount(new Savings(savingsBalance, savingsNumber));
                    }
                    
                    String creditNum = data.get(10).trim();
                    String creditMax = data.get(11).trim();
                    String creditBal = data.get(12).trim();
                    if (!creditNum.isEmpty() && !creditMax.isEmpty() && !creditBal.isEmpty()) {
                        int creditNumber = Integer.parseInt(creditNum);
                        double creditLimit = Double.parseDouble(creditMax);
                        double creditBalance = Double.parseDouble(creditBal);
                        customer.addAccount(new Credit(creditBalance, creditNumber, creditLimit));
                    }
                    
                    customers.put(fullName, customer);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing account data for customer " + fullName);
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Customer data loaded successfully.");
    } catch (IOException e) {
        System.out.println("Error reading from file: " + e.getMessage());
    }
}

    /**
     * Updates the customer data file with current account information
     */

public static void updateCustomerDataFile() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_NAME))) {
        bw.write(originalFileLines.get(0));
        bw.newLine();
        
        for (int i = 1; i < originalFileLines.size(); i++) {
            String originalLine = originalFileLines.get(i);
            String[] data = originalLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            
            StringBuilder newLine = new StringBuilder();
            String firstName = data[1].trim();
            String lastName = data[2].trim();
            String fullName = firstName + " " + lastName;
            Customer customer = customers.get(fullName);
            
            if (customer != null) {
                for (int j = 0; j < 6; j++) {
                    newLine.append(data[j]).append(",");
                }
                
                if (!data[6].trim().isEmpty()) {
                    int checkingNum = Integer.parseInt(data[6].trim());
                    Account checkingAccount = customer.getAccount(checkingNum);
                    newLine.append(data[6]).append(",");
                    
                    if (checkingAccount != null) {
                        newLine.append(String.format("%.2f", checkingAccount.getBalance()));
                    } else {
                        newLine.append(data[7]);
                    }
                } else {
                    newLine.append(data[6]).append(",").append(data[7]);
                }
                newLine.append(",");
                
                if (!data[8].trim().isEmpty()) {
                    int savingsNum = Integer.parseInt(data[8].trim());
                    Account savingsAccount = customer.getAccount(savingsNum);
                    newLine.append(data[8]).append(",");
                    
                    if (savingsAccount != null) {
                        newLine.append(String.format("%.2f", savingsAccount.getBalance()));
                    } else {
                        newLine.append(data[9]);
                    }
                } else {
                    newLine.append(data[8]).append(",").append(data[9]);
                }
                newLine.append(",");
                
                if (!data[10].trim().isEmpty()) {
                    int creditNum = Integer.parseInt(data[10].trim());
                    Account creditAccount = customer.getAccount(creditNum);
                    newLine.append(data[10]).append(",");
                    newLine.append(data[11]).append(",");
                    
                    if (creditAccount != null) {
                        newLine.append(String.format("%.2f", creditAccount.getBalance()));
                    } else {
                        newLine.append(data[12]);
                    }
                } else {
                    newLine.append(data[10]).append(",")
                           .append(data[11]).append(",")
                           .append(data[12]);
                }
                
                bw.write(newLine.toString());
            } else {
                bw.write(originalLine);
            }
            bw.newLine();
        }
        
        System.out.println("Customer data updated successfully.");
    } catch (IOException e) {
        System.out.println("Error updating customer data file: " + e.getMessage());
        }
    }
}
