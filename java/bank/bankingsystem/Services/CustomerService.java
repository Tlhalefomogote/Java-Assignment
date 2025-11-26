package bank.bankingsystem.Services;

import java.util.List;
import bank.bankingsystem.models.Customer;

/**
 * Service interface for customer-related business operations.
 */
public interface CustomerService {
    /**
     * Creates a new individual customer.
     * 
     * @param firstName the first name
     * @param lastName the last name
     * @param address the address
     * @param nationalId the national ID
     * @param employed whether the customer is employed
     * @param employerName the employer name (if employed)
     * @param employerAddress the employer address (if employed)
     * @return the created customer
     */
    Customer createIndividual(String firstName, String lastName, String address, String nationalId,
                             boolean employed, String employerName, String employerAddress);
    
    /**
     * Creates a new company customer.
     * 
     * @param companyName the company name
     * @param address the address
     * @param registrationNumber the registration number
     * @param employed whether the company is employed (has parent company)
     * @param employerName the parent company name (if applicable)
     * @param employerAddress the parent company address (if applicable)
     * @return the created customer
     */
    Customer createCompany(String companyName, String address, String registrationNumber,
                          boolean employed, String employerName, String employerAddress);
    
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
     * Updates a customer.
     * 
     * @param customer the customer to update
     */
    void update(Customer customer);
    
    /**
     * Deletes a customer.
     * 
     * @param id the customer ID
     */
    void delete(Integer id);
}

