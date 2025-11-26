package bank.bankingsystem.DAO;

import java.util.List;
import bank.bankingsystem.models.Account;

/**
 * Data Access Object interface for Account operations.
 */
public interface AccountDAO {
    /**
     * Saves an account to the database.
     * 
     * @param account the account to save
     * @return the generated account ID
     */
    Integer save(Account account);
    
    /**
     * Finds an account by ID.
     * 
     * @param id the account ID
     * @return the account, or null if not found
     */
    Account findById(Integer id);
    
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
     * @return a list of accounts for the customer
     */
    List<Account> findByCustomerId(Integer customerId);
    
    /**
     * Finds all accounts.
     * 
     * @return a list of all accounts
     */
    List<Account> findAll();
    
    /**
     * Updates an account in the database.
     * 
     * @param account the account to update
     */
    void update(Account account);
    
    /**
     * Deletes an account from the database.
     * 
     * @param id the account ID to delete
     */
    void delete(Integer id);
}

