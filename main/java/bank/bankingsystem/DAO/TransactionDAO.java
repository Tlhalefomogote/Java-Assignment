package bank.bankingsystem.DAO;

import java.util.List;
import bank.bankingsystem.models.Transaction;

/**
 * Data Access Object interface for Transaction operations.
 */
public interface TransactionDAO {
    /**
     * Saves a transaction to the database.
     * 
     * @param transaction the transaction to save
     * @return the generated transaction ID
     */
    Integer save(Transaction transaction);
    
    /**
     * Finds a transaction by ID.
     * 
     * @param id the transaction ID
     * @return the transaction, or null if not found
     */
    Transaction findById(Integer id);
    
    /**
     * Finds all transactions for an account.
     * 
     * @param accountId the account ID
     * @return a list of transactions for the account
     */
    List<Transaction> findByAccountId(Integer accountId);
    
    /**
     * Finds all transactions.
     * 
     * @return a list of all transactions
     */
    List<Transaction> findAll();
}

