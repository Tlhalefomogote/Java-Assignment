package bank.bankingsystem.DAO;

import bank.bankingsystem.models.User;

/**
 * Data Access Object interface for User operations.
 */
public interface UserDAO {
    /**
     * Saves a user to the database.
     * 
     * @param user the user to save
     * @return the generated user ID
     */
    Integer save(User user);
    
    /**
     * Finds a user by ID.
     * 
     * @param id the user ID
     * @return the user, or null if not found
     */
    User findById(Integer id);
    
    /**
     * Finds a user by username.
     * 
     * @param username the username
     * @return the user, or null if not found
     */
    User findByUsername(String username);
}

