package bank.bankingsystem.Services;

import java.math.BigDecimal;
import java.util.List;
import bank.bankingsystem.DAO.*;
import bank.bankingsystem.models.*;

/**
 * Implementation of AccountService with business logic.
 */
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;
    private final CustomerDAO customerDAO;
    private final TransactionService transactionService;
    private final AuditService auditService;
    private Integer currentUserId;
    
    public AccountServiceImpl(AccountDAO accountDAO, CustomerDAO customerDAO,
                             TransactionService transactionService, AuditService auditService) {
        this.accountDAO = accountDAO;
        this.customerDAO = customerDAO;
        this.transactionService = transactionService;
        this.auditService = auditService;
    }
    
    public void setCurrentUserId(Integer userId) {
        this.currentUserId = userId;
    }
    
    @Override
    public Account openAccount(String accountNumber, Integer customerId, BigDecimal initialDeposit,
                              AccountType type, String branch) {
        // Validate customer exists
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        
        // Validate account number is unique
        if (accountDAO.findByAccountNumber(accountNumber) != null) {
            throw new IllegalArgumentException("Account number already exists");
        }
        
        // Validate initial deposit
        if (initialDeposit == null || initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit must be non-negative");
        }
        
        // Create account using factory
        Account account = AccountFactory.createAccount(accountNumber, customer, initialDeposit, type, branch);
        
        // Save to database
        Integer accountId = accountDAO.save(account);
        account.setId(accountId);
        
        // Log initial deposit as transaction
        if (initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            transactionService.recordTransaction(accountId, TransactionType.DEPOSIT, initialDeposit);
        }
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Opened " + type + " account " + accountNumber + " for customer " + customerId);
        
        return account;
    }
    
    @Override
    public Account deposit(String accountNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Account account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        
        account.deposit(amount);
        accountDAO.update(account);
        
        // Log transaction
        transactionService.recordTransaction(account.getId(), TransactionType.DEPOSIT, amount);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Deposited BWP " + amount + " to account " + accountNumber);
        
        return account;
    }
    
    @Override
    public Account withdraw(String accountNumber, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        Account account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        
        // Business rule: Only withdrawable accounts can withdraw
        if (!(account instanceof Withdrawable)) {
            throw new IllegalArgumentException("Account type does not support withdrawals");
        }
        
        Withdrawable withdrawableAccount = (Withdrawable) account;
        withdrawableAccount.withdraw(amount);
        accountDAO.update(account);
        
        // Log transaction
        transactionService.recordTransaction(account.getId(), TransactionType.WITHDRAWAL, amount);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Withdrew BWP " + amount + " from account " + accountNumber);
        
        return account;
    }
    
    @Override
    public BigDecimal applyMonthlyInterest(String accountNumber) {
        Account account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        
        // Business rule: Only interest-bearing accounts can earn interest
        if (!(account instanceof InterestBearing)) {
            throw new IllegalArgumentException("Account type does not earn interest");
        }
        
        InterestBearing interestAccount = (InterestBearing) account;
        BigDecimal interest = interestAccount.applyMonthlyInterest();
        accountDAO.update(account);
        
        // Log interest as transaction
        transactionService.recordTransaction(account.getId(), TransactionType.INTEREST, interest);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Applied monthly interest BWP " + interest + " to account " + accountNumber);
        
        return interest;
    }
    
    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountDAO.findByAccountNumber(accountNumber);
    }
    
    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        return accountDAO.findByCustomerId(customerId);
    }
    
    @Override
    public List<Account> findAll() {
        return accountDAO.findAll();
    }
}

