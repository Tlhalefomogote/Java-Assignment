package bank.bankingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import bank.bankingsystem.DAO.*;
import bank.bankingsystem.Services.*;
import bank.bankingsystem.Controllers.*;

import java.io.IOException;

/**
 * Main JavaFX application for the Banking System.
 * Implements proper layered architecture: Views → Controllers → Services → DAOs → Database
 */
public class BankApp extends Application {
    
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
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database
            DatabaseInitializer.initializeDatabase();
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        // Initialize DAOs
        customerDAO = new CustomerDAOImpl();
        accountDAO = new AccountDAOImpl(customerDAO);
        transactionDAO = new TransactionDAOImpl();
        userDAO = new UserDAOImpl();
        auditLogDAO = new AuditLogDAOImpl();
        
        // Initialize Services
        transactionService = new TransactionServiceImpl(transactionDAO);
        auditService = new AuditServiceImpl(auditLogDAO);
        customerService = new CustomerServiceImpl(customerDAO, auditService);
        accountService = new AccountServiceImpl(accountDAO, customerDAO, transactionService, auditService);
        authService = new AuthServiceImpl(userDAO);
        
        // Initialize Controllers
        AuthController authController = new AuthController(authService);
        
        // Store services in a simple way for controllers to access
        ServiceRegistry.initialize(
            customerDAO, accountDAO, transactionDAO, userDAO, auditLogDAO,
            transactionService, auditService, customerService, accountService, authService
        );
        
        try {
            // Load login FXML
            java.net.URL fxmlUrl = getClass().getResource("/bank/bankingsystem/views/login.fxml");
            if (fxmlUrl == null) {
                System.err.println("ERROR: Cannot find login.fxml resource!");
                System.err.println("Expected path: /bank/bankingsystem/views/login.fxml");
                System.err.println("Please ensure the file exists in src/main/resources/bank/bankingsystem/views/");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene loginScene = new Scene(loader.load(), 400, 300);
            
            // Initialize login controller
            LoginController loginController = loader.getController();
            if (loginController == null) {
                System.err.println("ERROR: LoginController is null after loading FXML!");
                return;
            }
            
            loginController.setAuthController(authController);
            
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Banking System - Login");
            primaryStage.show();
            
            System.out.println("Application started successfully!");
        } catch (IOException e) {
            System.err.println("Failed to load login view: " + e.getMessage());
            e.printStackTrace();
            System.err.println("========================================");
            System.err.println("FAILED TO LOAD APPLICATION");
            System.err.println("Error: " + e.getMessage());
            System.err.println("========================================");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            // Can't show dialog if JavaFX failed to initialize
            System.err.println("========================================");
            System.err.println("APPLICATION FAILED TO START");
            System.err.println("Error: " + e.getMessage());
            System.err.println("========================================");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
