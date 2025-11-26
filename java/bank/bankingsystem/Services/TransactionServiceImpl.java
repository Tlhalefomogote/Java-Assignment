package bank.bankingsystem.Services;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.DAO.TransactionDAO;
import bank.bankingsystem.models.*;

/**
 * Implementation of TransactionService.
 */
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;
    
    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    
    @Override
    public Transaction recordTransaction(Integer accountId, TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction(accountId, type, amount);
        Integer transactionId = transactionDAO.save(transaction);
        transaction.setId(transactionId);
        return transaction;
    }
    
    @Override
    public List<Transaction> findByAccountId(Integer accountId) {
        return transactionDAO.findByAccountId(accountId);
    }
    
    @Override
    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }
}

