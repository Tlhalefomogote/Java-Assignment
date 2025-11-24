package bank.bankingsystem.Controllers;

import bank.bankingsystem.Services.AuthService;
import bank.bankingsystem.models.User;
import bank.bankingsystem.models.UserRole;

/**
 * Controller for authentication operations.
 */
public class AuthController {
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * Attempts to authenticate a user.
     * 
     * @param username the username
     * @param password the password
     * @return the authenticated user, or null if authentication fails
     */
    public User login(String username, String password) {
        return authService.authenticate(username, password);
    }
    
    /**
     * Creates a new user account.
     * 
     * @param username the username
     * @param password the password
     * @param role the user role
     * @param customerId the customer ID (null for admin)
     * @return the created user
     */
    public User createUser(String username, String password, UserRole role, Integer customerId) {
        return authService.createUser(username, password, role, customerId);
    }
}

