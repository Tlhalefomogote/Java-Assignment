package bank.bankingsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bank.bankingsystem.models.*;
import bank.bankingsystem.ServiceRegistry;
import bank.bankingsystem.Services.AccountServiceImpl;
import bank.bankingsystem.Services.CustomerServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * FXML Controller for the customer dashboard view.
 */
public class CustomerDashboardFXMLController {
    @FXML
    private TabPane tabPane;
    
    // My Accounts Tab
    @FXML
    private Tab accountsTab;
    @FXML
    private TableView<Account> accountsTable;
    @FXML
    private TableColumn<Account, String> accountNumberCol;
    @FXML
    private TableColumn<Account, AccountType> accountTypeCol;
    @FXML
    private TableColumn<Account, BigDecimal> accountBalanceCol;
    @FXML
    private ComboBox<AccountType> newAccountTypeCombo;
    @FXML
    private TextField newAccountInitialDepositField;
    @FXML
    private TextField newAccountBranchField;
    @FXML
    private Button openAccountButton;
    @FXML
    private Button logoutButton;
    
    // Transactions Tab
    @FXML
    private Tab transactionsTab;
    @FXML
    private ComboBox<Account> transactionAccountCombo;
    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, Integer> transactionIdCol;
    @FXML
    private TableColumn<Transaction, TransactionType> transactionTypeCol;
    @FXML
    private TableColumn<Transaction, BigDecimal> transactionAmountCol;
    @FXML
    private TableColumn<Transaction, String> transactionTimestampCol;
    @FXML
    private Button refreshTransactionsButton;
    
    // Deposit/Withdraw Tab
    @FXML
    private Tab operationsTab;
    @FXML
    private ComboBox<Account> operationAccountCombo;
    @FXML
    private TextField depositAmountField;
    @FXML
    private Button depositButton;
    @FXML
    private TextField withdrawAmountField;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button applyInterestButton;
    @FXML
    private Label accountBalanceLabel;
    
    private User currentUser;
    private CustomerDashboardController dashboardController;
    
    @FXML
    public void initialize() {
        // FXML calls this automatically - set up tabs but don't load data yet
        setupAccountsTab();
        setupTransactionsTab();
        setupOperationsTab();
        
        logoutButton.setOnAction(e -> handleLogout());
    }
    
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        
        // Set user context in services
        if (currentUser != null && currentUser.getRole() == UserRole.CUSTOMER) {
            ((AccountServiceImpl) ServiceRegistry.getAccountService()).setCurrentUserId(currentUser.getId());
            ((CustomerServiceImpl) ServiceRegistry.getCustomerService()).setCurrentUserId(currentUser.getId());
        }
        
        // Refresh data now that we have the user
        refreshAccountsTable();
    }
    
    private CustomerDashboardController getDashboardController() {
        if (dashboardController == null && currentUser != null) {
            try {
                AccountController accountController = new AccountController(ServiceRegistry.getAccountService());
                TransactionController transactionController = new TransactionController(ServiceRegistry.getTransactionService());
                dashboardController = new CustomerDashboardController(accountController, transactionController, currentUser.getCustomerId());
            } catch (Exception e) {
                System.err.println("ERROR creating CustomerDashboardController: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize customer dashboard controller", e);
            }
        }
        return dashboardController;
    }
    
    private void setupAccountsTab() {
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        
        newAccountTypeCombo.setItems(FXCollections.observableArrayList(AccountType.values()));
        
        refreshAccountsTable();
        
        openAccountButton.setOnAction(e -> openAccount());
    }
    
    private void setupTransactionsTab() {
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactionTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        transactionAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionTimestampCol.setCellValueFactory(cellData -> {
            Transaction t = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(t.getTimestamp().toString());
        });
        
        refreshTransactionsButton.setOnAction(e -> refreshTransactions());
    }
    
    private void setupOperationsTab() {
        depositButton.setOnAction(e -> deposit());
        withdrawButton.setOnAction(e -> withdraw());
        applyInterestButton.setOnAction(e -> applyInterest());
        
        operationAccountCombo.setOnAction(e -> updateBalanceLabel());
    }
    
    private void refreshAccountsTable() {
        CustomerDashboardController controller = getDashboardController();
        if (controller != null) {
            List<Account> accounts = controller.getMyAccounts();
            accountsTable.setItems(FXCollections.observableArrayList(accounts));
            
            ObservableList<Account> accountList = FXCollections.observableArrayList(accounts);
            transactionAccountCombo.setItems(accountList);
            operationAccountCombo.setItems(accountList);
        }
    }
    
    private void refreshTransactions() {
        Account selected = transactionAccountCombo.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an account.");
            return;
        }
        
        CustomerDashboardController controller = getDashboardController();
        if (controller != null) {
            List<Transaction> transactions = controller.getMyTransactions(selected.getId());
            transactionsTable.setItems(FXCollections.observableArrayList(transactions));
        }
    }
    
    private void openAccount() {
        try {
            AccountType type = newAccountTypeCombo.getSelectionModel().getSelectedItem();
            String depositStr = newAccountInitialDepositField.getText().trim();
            String branch = newAccountBranchField.getText().trim();
            
            if (type == null || depositStr.isEmpty() || branch.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }
            
            // Check if customer is trying to open a Cheque account
            if (type == AccountType.CHEQUE) {
                // Get customer details
                Customer customer = ServiceRegistry.getCustomerService().findById(currentUser.getCustomerId());
                if (customer == null || !customer.isEmployed()) {
                    // Prompt for employment details
                    if (!promptForEmploymentDetails(customer)) {
                        return; // User cancelled or failed to provide details
                    }
                }
            }
            
            BigDecimal initialDeposit = new BigDecimal(depositStr);
            
            // Auto-generate account number
            String accountNumber = bank.bankingsystem.Services.AccountNumberGenerator.generate();
            
            CustomerDashboardController controller = getDashboardController();
            if (controller != null) {
                controller.openAccount(accountNumber, initialDeposit, type, branch);
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                    "Account opened successfully.\nAccount Number: " + accountNumber);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Controller not initialized.");
            }
            clearNewAccountFields();
            refreshAccountsTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open account: " + e.getMessage());
        }
    }
    
    private boolean promptForEmploymentDetails(Customer customer) {
        // Create a dialog to collect employment information
        javafx.scene.control.Dialog<javafx.util.Pair<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Employment Details Required");
        dialog.setHeaderText("Cheque accounts require employment information.\nPlease provide your employment details:");
        
        // Set the button types
        javafx.scene.control.ButtonType submitButtonType = new javafx.scene.control.ButtonType("Submit", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, javafx.scene.control.ButtonType.CANCEL);
        
        // Create the employment fields
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        javafx.scene.control.TextField employerNameField = new javafx.scene.control.TextField();
        employerNameField.setPromptText("Employer Name");
        javafx.scene.control.TextField employerAddressField = new javafx.scene.control.TextField();
        employerAddressField.setPromptText("Employer Address");
        
        grid.add(new javafx.scene.control.Label("Employer Name:"), 0, 0);
        grid.add(employerNameField, 1, 0);
        grid.add(new javafx.scene.control.Label("Employer Address:"), 0, 1);
        grid.add(employerAddressField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        
        // Request focus on the employer name field by default
        javafx.application.Platform.runLater(() -> employerNameField.requestFocus());
        
        // Convert the result when the submit button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return new javafx.util.Pair<>(employerNameField.getText(), employerAddressField.getText());
            }
            return null;
        });
        
        java.util.Optional<javafx.util.Pair<String, String>> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            String employerName = result.get().getKey().trim();
            String employerAddress = result.get().getValue().trim();
            
            if (employerName.isEmpty() || employerAddress.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Both employer name and address are required.");
                return false;
            }
            
            try {
                // Update customer with employment details
                customer.setEmployed(true);
                customer.setEmployerName(employerName);
                customer.setEmployerAddress(employerAddress);
                ServiceRegistry.getCustomerService().update(customer);
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employment details updated successfully.");
                return true;
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update employment details: " + e.getMessage());
                return false;
            }
        }
        
        return false; // User cancelled
    }
    
    private void deposit() {
        try {
            Account account = operationAccountCombo.getSelectionModel().getSelectedItem();
            if (account == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an account.");
                return;
            }
            
            String amountStr = depositAmountField.getText().trim();
            if (amountStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter an amount.");
                return;
            }
            
            BigDecimal amount = new BigDecimal(amountStr);
            CustomerDashboardController controller = getDashboardController();
            if (controller != null) {
                controller.deposit(account.getAccountNumber(), amount);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit successful.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Controller not initialized.");
            }
            depositAmountField.clear();
            refreshAccountsTable();
            updateBalanceLabel();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to deposit: " + e.getMessage());
        }
    }
    
    private void withdraw() {
        try {
            Account account = operationAccountCombo.getSelectionModel().getSelectedItem();
            if (account == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an account.");
                return;
            }
            
            String amountStr = withdrawAmountField.getText().trim();
            if (amountStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter an amount.");
                return;
            }
            
            BigDecimal amount = new BigDecimal(amountStr);
            CustomerDashboardController controller = getDashboardController();
            if (controller != null) {
                controller.withdraw(account.getAccountNumber(), amount);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Controller not initialized.");
            }
            withdrawAmountField.clear();
            refreshAccountsTable();
            updateBalanceLabel();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to withdraw: " + e.getMessage());
        }
    }
    
    private void applyInterest() {
        try {
            Account account = operationAccountCombo.getSelectionModel().getSelectedItem();
            if (account == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an account.");
                return;
            }
            
            CustomerDashboardController controller = getDashboardController();
            if (controller == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Controller not initialized.");
                return;
            }
            
            BigDecimal interest = controller.applyInterest(account.getAccountNumber());
            
            showAlert(Alert.AlertType.INFORMATION, "Success", 
                "Interest of BWP " + interest + " applied successfully.");
            refreshAccountsTable();
            updateBalanceLabel();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to apply interest: " + e.getMessage());
        }
    }
    
    private void updateBalanceLabel() {
        Account account = operationAccountCombo.getSelectionModel().getSelectedItem();
        if (account != null) {
            accountBalanceLabel.setText("Current Balance: BWP " + account.getBalance());
        } else {
            accountBalanceLabel.setText("Current Balance: --");
        }
    }
    
    private void clearNewAccountFields() {
        newAccountInitialDepositField.clear();
        newAccountBranchField.clear();
        newAccountTypeCombo.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleLogout() {
        try {
            // Load Login FXML
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/bank/bankingsystem/views/login.fxml"));
            javafx.scene.Parent root = loader.load();
            
            // Get current stage
            javafx.stage.Stage stage = (javafx.stage.Stage) logoutButton.getScene().getWindow();
            
            // Initialize login controller
            LoginController loginController = loader.getController();
            loginController.setAuthController(new AuthController(ServiceRegistry.getAuthService()));
            
            // Set scene
            stage.setScene(new javafx.scene.Scene(root, 400, 300));
            stage.setTitle("Banking System - Login");
        } catch (java.io.IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

