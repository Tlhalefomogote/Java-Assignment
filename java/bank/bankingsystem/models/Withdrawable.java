package bank.bankingsystem.models;

import java.math.BigDecimal;

/**
 * Interface for accounts that support withdrawals.
 * Implemented by InvestmentAccount and ChequeAccount.
 * SavingsAccount does NOT implement this interface (withdrawals not allowed).
 */
public interface Withdrawable {
    /**
     * Withdraws money from the account.
     * 
     * @param amount the amount to withdraw (must be positive and <= available balance)
     * @throws IllegalArgumentException if amount is invalid or insufficient funds
     */
    void withdraw(BigDecimal amount);
    
    /**
     * Gets the available balance for withdrawal.
     * 
     * @return the available balance
     */
    BigDecimal getAvailableBalance();
    
    /**
     * Gets the maximum withdrawal limit for this account.
     * 
     * @return the withdrawal limit
     */
    BigDecimal getWithdrawalLimit();
}

