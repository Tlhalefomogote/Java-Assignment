package model;

// Interface for accounts that allow withdrawals
public interface Withdrawable {
    boolean withdraw(double amount);
}
