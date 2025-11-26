package bank.bankingsystem.Controllers;

import java.util.List;
import bank.bankingsystem.Services.TransactionService;
import bank.bankingsystem.models.Transaction;

/**
 * Controller for transaction viewing operations.
 */
public class TransactionController {
    private final TransactionService transactionService;
    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    public List<Transaction> findByAccountId(Integer accountId) {
        return transactionService.findByAccountId(accountId);
    }
    
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }
}

