package bank.bankingsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import bank.bankingsystem.models.*;
import bank.bankingsystem.ServiceRegistry;

import java.math.BigDecimal;
import java.util.List;

/**
 * FXML Controller for the admin dashboard view.
 */
public class AdminDashboardController {
    @FXML
    private TabPane tabPane;
    
    // Customer Management Tab
    @FXML
    private Tab customersTab;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;
    @FXML
    private TableColumn<Customer, Boolean> customerEmployedCol;
    @FXML
    private TextField customerFirstNameField;
    @FXML
    private TextField customerLastNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private ComboBox<String> customerTypeCombo;
    @FXML
    private Label customerIdLabel;
    @FXML
    private TextField customerNationalIdField;
    @FXML
    private TextField customerUsernameField;
    @FXML
    private PasswordField customerPasswordField;
    @FXML
    private CheckBox customerEmployedCheck;
    @FXML
    private TextField customerEmployerNameField;
    @FXML
    private TextField customerEmployerAddressField;
    @FXML
    private Button createCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button logoutButton;
    
    // Account Management Tab
    @FXML
    private Tab accountsTab;
    @FXML
    private TableView<Account> accountsTable;
    @FXML
    private TableColumn<Account, String> accountNumberCol;
    @FXML
    private TableColumn<Account, String> accountOwnerCol;
    @FXML
    private TableColumn<Account, AccountType> accountTypeCol;
    @FXML
    private TableColumn<Account, BigDecimal> accountBalanceCol;
    @FXML
    private ComboBox<Customer> accountCustomerCombo;
    @FXML
    private ComboBox<AccountType> accountTypeCombo;
    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField accountInitialDepositField;
    @FXML
    private TextField accountBranchField;
    @FXML
    private Button createAccountButton;
    
    // Audit Log Tab
    @FXML
    private Tab auditLogTab;
    @FXML
    private TableView<AuditLog> auditLogTable;
    @FXML
    private TableColumn<AuditLog, Integer> auditIdCol;
    @FXML
    private TableColumn<AuditLog, Integer> auditUserIdCol;
    @FXML
    private TableColumn<AuditLog, String> auditActionCol;
    @FXML
    private TableColumn<AuditLog, String> auditTimestampCol;
    
    private User currentUser;
    private AdminController adminController;
    
    @FXML
    public void initialize() {
        // FXML calls this automatically - set up tabs but don't load data yet
        setupCustomersTab();
        setupAccountsTab();
        setupAuditLogTab();
    }
    
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        // Refresh data now that we have the user
        refreshCustomersTable();
        refreshAccountsTable();
        refreshAuditLogTable();
    }
    
    private AdminController getAdminController() {
        if (adminController == null) {
            try {
                CustomerController customerController = new CustomerController(ServiceRegistry.getCustomerService());
                AccountController accountController = new AccountController(ServiceRegistry.getAccountService());
                TransactionController transactionController = new TransactionController(ServiceRegistry.getTransactionService());
                adminController = new AdminController(customerController, accountController, transactionController, ServiceRegistry.getAuditService());
            } catch (Exception e) {
                System.err.println("ERROR creating AdminController: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize admin controller", e);
            }
        }
        return adminController;
    }
    
    private void setupCustomersTab() {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(cellData -> {
            Customer c = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(c.getName());
        });
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerEmployedCol.setCellValueFactory(new PropertyValueFactory<>("employed"));
        
        // Setup customer type combo
        customerTypeCombo.setItems(FXCollections.observableArrayList("Individual", "Company"));
        customerTypeCombo.setValue("Individual");
        customerTypeCombo.setOnAction(e -> updateCustomerTypeFields());
        
        refreshCustomersTable();
        
        createCustomerButton.setOnAction(e -> createCustomer());
        deleteCustomerButton.setOnAction(e -> deleteCustomer());
        logoutButton.setOnAction(e -> handleLogout());
    }
    
    private void updateCustomerTypeFields() {
        String type = customerTypeCombo.getValue();
        if ("Company".equals(type)) {
            customerIdLabel.setText("Registration Number:");
        } else {
            customerIdLabel.setText("National ID:");
        }
    }
    
    private void setupAccountsTab() {
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accountOwnerCol.setCellValueFactory(cellData -> {
            Account a = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(a.getOwner().getName());
        });
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        
        accountTypeCombo.setItems(FXCollections.observableArrayList(AccountType.values()));
        refreshAccountsTable();
        refreshAccountCustomerCombo();
        
        createAccountButton.setOnAction(e -> createAccount());
    }
    
    private void setupAuditLogTab() {
        auditIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        auditUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        auditActionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
        auditTimestampCol.setCellValueFactory(cellData -> {
            AuditLog log = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(log.getTimestamp().toString());
        });
        
        refreshAuditLogTable();
    }
    
    private void refreshCustomersTable() {
        List<Customer> customers = getAdminController().getCustomerController().findAll();
        customersTable.setItems(FXCollections.observableArrayList(customers));
        refreshAccountCustomerCombo();
    }
    
    private void refreshAccountsTable() {
        List<Account> accounts = getAdminController().getAccountController().findAll();
        accountsTable.setItems(FXCollections.observableArrayList(accounts));
    }
    
    private void refreshAccountCustomerCombo() {
        List<Customer> customers = getAdminController().getCustomerController().findAll();
        accountCustomerCombo.setItems(FXCollections.observableArrayList(customers));
    }
    
    private void refreshAuditLogTable() {
        List<AuditLog> logs = getAdminController().getAuditLogs();
        auditLogTable.setItems(FXCollections.observableArrayList(logs));
    }
    
    private void createCustomer() {
        try {
            String customerType = customerTypeCombo.getValue();
            String firstName = customerFirstNameField.getText().trim();
            String lastName = customerLastNameField.getText().trim();
            String address = customerAddressField.getText().trim();
            String idNumber = customerNationalIdField.getText().trim();
            String username = customerUsernameField.getText().trim();
            String password = customerPasswordField.getText().trim();
            boolean employed = customerEmployedCheck.isSelected();
            String employerName = customerEmployerNameField.getText().trim();
            String employerAddress = customerEmployerAddressField.getText().trim();
            
            // Validation
            if (customerType == null || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || idNumber.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields.");
                return;
            }
            
            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Username and password are required.");
                return;
            }
            
            if (employed && (employerName.isEmpty() || employerAddress.isEmpty())) {
                showAlert(Alert.AlertType.ERROR, "Error", "Employer name and address are required for employed customers.");
                return;
            }
            
            // Create customer
            Customer customer;
            if ("Individual".equals(customerType)) {
                customer = getAdminController().getCustomerController().createIndividual(
                    firstName, lastName, address, idNumber, employed, employerName, employerAddress);
            } else {
                // For companies, firstName field contains the company name
                customer = getAdminController().getCustomerController().createCompany(
                    firstName, address, idNumber, employed, employerName, employerAddress);
            }
            
            // Create user account
            ServiceRegistry.getAuthService().createUser(username, password, UserRole.CUSTOMER, customer.getId());
            
            showAlert(Alert.AlertType.INFORMATION, "Success", 
                "Customer created successfully. They can now login with username: " + username);
            clearCustomerFields();
            refreshCustomersTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create customer: " + e.getMessage());
        }
    }
    
    private void deleteCustomer() {
        Customer selected = customersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to delete.");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Customer");
        confirm.setContentText("Are you sure you want to delete " + selected.getName() + "?");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                getAdminController().getCustomerController().delete(selected.getId());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully.");
                refreshCustomersTable();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete customer: " + e.getMessage());
            }
        }
    }
    
    private void createAccount() {
        try {
            Customer customer = accountCustomerCombo.getSelectionModel().getSelectedItem();
            AccountType type = accountTypeCombo.getSelectionModel().getSelectedItem();
            String accountNumber = accountNumberField.getText().trim();
            String depositStr = accountInitialDepositField.getText().trim();
            String branch = accountBranchField.getText().trim();
            
            if (customer == null || type == null || accountNumber.isEmpty() || depositStr.isEmpty() || branch.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }
            
            BigDecimal initialDeposit = new BigDecimal(depositStr);
            
            getAdminController().getAccountController().openAccount(
                accountNumber, customer.getId(), initialDeposit, type, branch);
            
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully.");
            clearAccountFields();
            refreshAccountsTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account: " + e.getMessage());
        }
    }
    
    private void clearCustomerFields() {
        customerTypeCombo.setValue("Individual");
        customerFirstNameField.clear();
        customerLastNameField.clear();
        customerAddressField.clear();
        customerNationalIdField.clear();
        customerUsernameField.clear();
        customerPasswordField.clear();
        customerEmployedCheck.setSelected(false);
        customerEmployerNameField.clear();
        customerEmployerAddressField.clear();
    }
    
    private void clearAccountFields() {
        accountNumberField.clear();
        accountInitialDepositField.clear();
        accountBranchField.clear();
        accountCustomerCombo.getSelectionModel().clearSelection();
        accountTypeCombo.getSelectionModel().clearSelection();
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

