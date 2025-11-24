package bank.bankingsystem.DAO;

import java.util.List;
import bank.bankingsystem.models.Customer;

/**
 * Data Access Object interface for Customer operations.
 */
public interface CustomerDAO {
    /**
     * Saves a customer to the database.
     * 
     * @param customer the customer to save
     * @return the generated customer ID
     */
    Integer save(Customer customer);
    
    /**
     * Finds a customer by ID.
     * 
     * @param id the customer ID
     * @return the customer, or null if not found
     */
    Customer findById(Integer id);
    
    /**
     * Finds all customers.
     * 
     * @return a list of all customers
     */
    List<Customer> findAll();
    
    /**
     * Updates a customer in the database.
     * 
     * @param customer the customer to update
     */
    void update(Customer customer);
    
    /**
     * Deletes a customer from the database.
     * 
     * @param id the customer ID to delete
     */
    void delete(Integer id);
}

