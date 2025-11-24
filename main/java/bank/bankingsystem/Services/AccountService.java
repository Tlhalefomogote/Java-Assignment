package bank.bankingsystem.Services;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.models.*;

/**
 * Service interface for account-related business operations.
 */
public interface AccountService {
    /**
     * Opens a new account for a customer.
     * 
     * @param accountNumber the account number
     * @param customerId the customer ID
     * @param initialDeposit the initial deposit
     * @param type the account type
     * @param branch the branch name
     * @return the created account
     * @throws IllegalArgumentException if validation fails
     */
    Account openAccount(String accountNumber, Integer customerId, BigDecimal initialDeposit, 
                       AccountType type, String branch);
    
    /**
     * Deposits money into an account.
     * 
     * @param accountNumber the account number
     * @param amount the amount to deposit
     * @return the updated account
     * @throws IllegalArgumentException if account not found or invalid amount
     */
    Account deposit(String accountNumber, BigDecimal amount);
    
    /**
     * Withdraws money from an account.
     * 
     * @param accountNumber the account number
     * @param amount the amount to withdraw
     * @return the updated account
     * @throws IllegalArgumentException if account not found, not withdrawable, or insufficient funds
     */
    Account withdraw(String accountNumber, BigDecimal amount);
    
    /**
     * Applies monthly interest to an interest-bearing account.
     * 
     * @param accountNumber the account number
     * @return the interest amount applied
     * @throws IllegalArgumentException if account not found or not interest-bearing
     */
    BigDecimal applyMonthlyInterest(String accountNumber);
    
    /**
     * Finds an account by account number.
     * 
     * @param accountNumber the account number
     * @return the account, or null if not found
     */
    Account findByAccountNumber(String accountNumber);
    
    /**
     * Finds all accounts for a customer.
     * 
     * @param customerId the customer ID
     * @return a list of accounts
     */
    List<Account> findByCustomerId(Integer customerId);
    
    /**
     * Finds all accounts.
     * 
     * @return a list of all accounts
     */
    List<Account> findAll();
}

