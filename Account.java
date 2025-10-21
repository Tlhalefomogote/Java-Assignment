package model;

// Parent class for all accounts
public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer owner;

    public Account(String accountNumber, Customer owner, String branch, double openingBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.branch = branch;
        this.balance = openingBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }

    // basic deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited BWP " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Amount must be positive.");
        }
    }

    // basic withdrawal
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew BWP " + amount + ". New balance: " + balance);
            return true;
        }
        System.out.println("Invalid or insufficient funds.");
        return false;
    }

    public void monthlyProcess() { } // for subclasses

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", branch='" + branch + '\'' +
                '}';
    }
}

