package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// Bank class manages customers and their accounts
public class Bank {
    private String name;
    private Map<String, Customer> customers;
    private AtomicInteger accCounter;

    // Constructor
    public Bank(String name) {
        this.name = name;
        this.customers = new HashMap<>();
        this.accCounter = new AtomicInteger(1000);
    }

    // Create a new customer and add to bank
    public Customer createCustomer(String first, String last, String address) {
        Customer c = new Customer(first, last, address);
        customers.put(c.getFullName(), c);
        System.out.println("Customer created: " + c.getFullName());
        return c;
    }

    // Generate new account number
    private String nextAccNo() {
        return "ACC" + accCounter.getAndIncrement();
    }

    // Open a Savings account
    public SavingsAccount openSavings(Customer c, String branch, double opening) {
        SavingsAccount s = new SavingsAccount(nextAccNo(), c, branch, opening);
        c.addAccount(s);
        System.out.println("Opened SavingsAccount for " + c.getFullName());
        return s;
    }

    // Open an Investment account
    public InvestmentAccount openInvestment(Customer c, String branch, double opening) {
        InvestmentAccount i = new InvestmentAccount(nextAccNo(), c, branch, opening);
        c.addAccount(i);
        System.out.println("Opened InvestmentAccount for " + c.getFullName());
        return i;
    }

    // Open a Cheque account
    public ChequeAccount openCheque(Customer c, String branch, double opening, Company comp) {
        ChequeAccount ch = new ChequeAccount(nextAccNo(), c, branch, opening, comp);
        c.addAccount(ch);
        System.out.println("Opened ChequeAccount for " + c.getFullName());
        return ch;
    }

    // Apply interest or run monthly updates for all accounts
    public void runMonthlyProcesses() {
        System.out.println("\n--- Running Monthly Processes ---");
        for (model.Customer c : customers.values()) {
            for (model.Account a : c.getAccounts()) {
                a.monthlyProcess();
            }
        }
    }

    // Optional: get all customers in the bank
    public Collection<model.Customer> getCustomers() {
        return customers.values();
    }

    // Display simple summary
    public void displaySummary() {
        System.out.println("\n==== Bank Summary for " + name + " ====");
        for (model.Customer c : customers.values()) {
            System.out.println("Customer: " + c.getFullName());
            for (model.Account a : c.getAccounts()) {
                System.out.println("   " + a);
            }
        }
    }
}

