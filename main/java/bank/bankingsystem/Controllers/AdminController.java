package bank.bankingsystem.Controllers;

import java.util.List;
import bank.bankingsystem.Services.AuditService;
import bank.bankingsystem.models.AuditLog;

/**
 * Controller for admin-specific operations.
 */
public class AdminController {
    private final CustomerController customerController;
    private final AccountController accountController;
    private final TransactionController transactionController;
    private final AuditService auditService;
    
    public AdminController(CustomerController customerController, AccountController accountController,
                          TransactionController transactionController, AuditService auditService) {
        this.customerController = customerController;
        this.accountController = accountController;
        this.transactionController = transactionController;
        this.auditService = auditService;
    }
    
    public CustomerController getCustomerController() {
        return customerController;
    }
    
    public AccountController getAccountController() {
        return accountController;
    }
    
    public TransactionController getTransactionController() {
        return transactionController;
    }
    
    public List<AuditLog> getAuditLogs() {
        return auditService.getAllLogs();
    }
}

