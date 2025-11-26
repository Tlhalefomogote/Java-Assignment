package bank.bankingsystem.Controllers;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.Services.AccountService;
import bank.bankingsystem.models.*;

/**
 * Controller for account operations.
 */
public class AccountController {
    private final AccountService accountService;
    
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public Account openAccount(String accountNumber, Integer customerId, BigDecimal initialDeposit,
                              AccountType type, String branch) {
        return accountService.openAccount(accountNumber, customerId, initialDeposit, type, branch);
    }
    
    public Account deposit(String accountNumber, BigDecimal amount) {
        return accountService.deposit(accountNumber, amount);
    }
    
    public Account withdraw(String accountNumber, BigDecimal amount) {
        return accountService.withdraw(accountNumber, amount);
    }
    
    public BigDecimal applyMonthlyInterest(String accountNumber) {
        return accountService.applyMonthlyInterest(accountNumber);
    }
    
    public Account findByAccountNumber(String accountNumber) {
        return accountService.findByAccountNumber(accountNumber);
    }
    
    public List<Account> findByCustomerId(Integer customerId) {
        return accountService.findByCustomerId(customerId);
    }
    
    public List<Account> findAll() {
        return accountService.findAll();
    }
}

