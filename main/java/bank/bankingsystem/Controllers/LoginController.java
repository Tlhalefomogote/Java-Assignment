package bank.bankingsystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import bank.bankingsystem.models.User;
import bank.bankingsystem.models.UserRole;

import java.io.IOException;

/**
 * Controller for the login view.
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    private AuthController authController;
    
    public void setAuthController(AuthController authController) {
        this.authController = authController;
    }
    
    @FXML
    private void handleLogin() {
        if (authController == null) {
            showAlert(Alert.AlertType.ERROR, "System Error", "Authentication service not initialized. Please restart the application.");
            System.err.println("ERROR: authController is null in LoginController");
            return;
        }
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter both username and password.");
            return;
        }
        
        User user = authController.login(username, password);
        
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            return;
        }
        
        // Set user context in services for audit logging
        if (user.getRole() == UserRole.ADMIN) {
            loadAdminDashboard(user);
        } else {
            loadCustomerDashboard(user);
        }
    }
    
    private void loadAdminDashboard(User user) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bank/bankingsystem/views/admin-dashboard.fxml"));
            Parent root = loader.load();
            
            AdminDashboardController controller = loader.getController();
            controller.setCurrentUser(user);
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Banking System - Admin Dashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load admin dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadCustomerDashboard(User user) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bank/bankingsystem/views/customer-dashboard.fxml"));
            Parent root = loader.load();
            
            CustomerDashboardFXMLController controller = loader.getController();
            controller.setCurrentUser(user);
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Banking System - Customer Dashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customer dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

