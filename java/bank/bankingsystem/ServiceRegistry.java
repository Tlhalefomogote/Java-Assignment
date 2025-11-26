package bank.bankingsystem;

import bank.bankingsystem.DAO.*;
import bank.bankingsystem.Services.*;

/**
 * Registry to hold service and DAO instances for global access.
 * Acts as a simple Service Locator.
 */
public class ServiceRegistry {
    private static CustomerDAO customerDAO;
    private static AccountDAO accountDAO;
    private static TransactionDAO transactionDAO;
    private static UserDAO userDAO;
    private static AuditLogDAO auditLogDAO;
    
    private static TransactionService transactionService;
    private static AuditService auditService;
    private static CustomerService customerService;
    private static AccountService accountService;
    private static AuthService authService;

    public static void initialize(CustomerDAO customerDAO, AccountDAO accountDAO, TransactionDAO transactionDAO,
                                  UserDAO userDAO, AuditLogDAO auditLogDAO,
                                  TransactionService transactionService, AuditService auditService,
                                  CustomerService customerService, AccountService accountService,
                                  AuthService authService) {
        ServiceRegistry.customerDAO = customerDAO;
        ServiceRegistry.accountDAO = accountDAO;
        ServiceRegistry.transactionDAO = transactionDAO;
        ServiceRegistry.userDAO = userDAO;
        ServiceRegistry.auditLogDAO = auditLogDAO;
        ServiceRegistry.transactionService = transactionService;
        ServiceRegistry.auditService = auditService;
        ServiceRegistry.customerService = customerService;
        ServiceRegistry.accountService = accountService;
        ServiceRegistry.authService = authService;
    }

    public static CustomerDAO getCustomerDAO() { return customerDAO; }
    public static AccountDAO getAccountDAO() { return accountDAO; }
    public static TransactionDAO getTransactionDAO() { return transactionDAO; }
    public static UserDAO getUserDAO() { return userDAO; }
    public static AuditLogDAO getAuditLogDAO() { return auditLogDAO; }

    public static TransactionService getTransactionService() { return transactionService; }
    public static AuditService getAuditService() { return auditService; }
    public static CustomerService getCustomerService() { return customerService; }
    public static AccountService getAccountService() { return accountService; }
    public static AuthService getAuthService() { return authService; }
}
