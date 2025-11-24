package bank.bankingsystem.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Abstract base class for all account types in the banking system.
 * Uses BigDecimal for precise monetary calculations.
 */
public abstract class Account {
    private Integer id; // database ID
    protected String accountNumber;
    protected BigDecimal balance;
    protected Customer owner;
    protected AccountType type;
    protected String branch;

    /**
     * Constructor for creating a new account.
     */
    public Account(String accountNumber, Customer owner, BigDecimal initialDeposit, AccountType type, String branch) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialDeposit.setScale(2, RoundingMode.HALF_UP);
        this.type = type;
        this.branch = branch;
    }

    /**
     * Constructor for database loading.
     */
    public Account(Integer id, String accountNumber, Customer owner, BigDecimal balance, AccountType type, String branch) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.type = type;
        this.branch = branch;
    }

    /**
     * Deposits money into the account.
     * 
     * @param amount the amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public AccountType getType() {
        return type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return owner.getName() + "'s " + type + " Account " + accountNumber + ": Balance = BWP " + balance;
    }
}

