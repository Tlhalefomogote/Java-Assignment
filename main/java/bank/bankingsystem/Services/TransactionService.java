package bank.bankingsystem.Services;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.models.Transaction;
import bank.bankingsystem.models.TransactionType;

/**
 * Service interface for transaction-related operations.
 */
public interface TransactionService {
    /**
     * Records a transaction in the database.
     * 
     * @param accountId the account ID
     * @param type the transaction type
     * @param amount the transaction amount
     * @return the created transaction
     */
    Transaction recordTransaction(Integer accountId, TransactionType type, BigDecimal amount);
    
    /**
     * Finds all transactions for an account.
     * 
     * @param accountId the account ID
     * @return a list of transactions
     */
    List<Transaction> findByAccountId(Integer accountId);
    
    /**
     * Finds all transactions.
     * 
     * @return a list of all transactions
     */
    List<Transaction> findAll();
}

