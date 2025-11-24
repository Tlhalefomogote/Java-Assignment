package bank.bankingsystem.Services;

import bank.bankingsystem.DAO.UserDAO;
import bank.bankingsystem.models.User;
import bank.bankingsystem.models.UserRole;

/**
 * Implementation of AuthService with password hashing.
 */
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    
    public AuthServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        
        if (PasswordHasher.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        
        return null;
    }
    
    @Override
    public User createUser(String username, String password, UserRole role, Integer customerId) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role is required");
        }
        
        // Check if username already exists
        if (userDAO.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        String passwordHash = PasswordHasher.hashPassword(password);
        User user = new User(username, passwordHash, role);
        user.setCustomerId(customerId);
        
        Integer userId = userDAO.save(user);
        user.setId(userId);
        
        return user;
    }
}

