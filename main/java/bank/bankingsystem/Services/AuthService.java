package bank.bankingsystem.Services;

import bank.bankingsystem.models.User;
import bank.bankingsystem.models.UserRole;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {
    /**
     * Authenticates a user with username and password.
     * 
     * @param username the username
     * @param password the password
     * @return the authenticated user, or null if authentication fails
     */
    User authenticate(String username, String password);
    
    /**
     * Creates a new user account.
     * 
     * @param username the username
     * @param password the password
     * @param role the user role
     * @param customerId the customer ID (null for admin)
     * @return the created user
     * @throws IllegalArgumentException if username already exists
     */
    User createUser(String username, String password, UserRole role, Integer customerId);
}

