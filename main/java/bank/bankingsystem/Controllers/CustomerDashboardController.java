package bank.bankingsystem.Controllers;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.models.*;

/**
 * Controller for customer dashboard operations.
 */
public class CustomerDashboardController {
    private final AccountController accountController;
    private final TransactionController transactionController;
    private final Integer customerId;
    
    public CustomerDashboardController(AccountController accountController,
                                      TransactionController transactionController,
                                      Integer customerId) {
        this.accountController = accountController;
        this.transactionController = transactionController;
        this.customerId = customerId;
    }
    
    public List<Account> getMyAccounts() {
        return accountController.findByCustomerId(customerId);
    }
    
    public Account openAccount(String accountNumber, BigDecimal initialDeposit, AccountType type, String branch) {
        return accountController.openAccount(accountNumber, customerId, initialDeposit, type, branch);
    }
    
    public Account deposit(String accountNumber, BigDecimal amount) {
        return accountController.deposit(accountNumber, amount);
    }
    
    public Account withdraw(String accountNumber, BigDecimal amount) {
        return accountController.withdraw(accountNumber, amount);
    }
    
    public BigDecimal applyInterest(String accountNumber) {
        return accountController.applyMonthlyInterest(accountNumber);
    }
    
    public List<Transaction> getMyTransactions(Integer accountId) {
        return transactionController.findByAccountId(accountId);
    }
}

