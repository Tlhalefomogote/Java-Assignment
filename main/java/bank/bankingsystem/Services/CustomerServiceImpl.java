package bank.bankingsystem.Services;

import java.util.List;
import bank.bankingsystem.DAO.CustomerDAO;
import bank.bankingsystem.models.*;

/**
 * Implementation of CustomerService with business logic.
 */
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;
    private final AuditService auditService;
    private Integer currentUserId;
    
    public CustomerServiceImpl(CustomerDAO customerDAO, AuditService auditService) {
        this.customerDAO = customerDAO;
        this.auditService = auditService;
    }
    
    public void setCurrentUserId(Integer userId) {
        this.currentUserId = userId;
    }
    
    @Override
    public Customer createIndividual(String firstName, String lastName, String address, String nationalId,
                                    boolean employed, String employerName, String employerAddress) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (nationalId == null || nationalId.trim().isEmpty()) {
            throw new IllegalArgumentException("National ID is required");
        }
        
        Individual customer = new Individual(firstName, lastName, address, nationalId,
                                            employed, employerName, employerAddress);
        
        Integer customerId = customerDAO.save(customer);
        customer.setId(customerId);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Created individual customer: " + firstName + " " + lastName + " (ID: " + customerId + ")");
        
        return customer;
    }
    
    @Override
    public Customer createCompany(String companyName, String address, String registrationNumber,
                                 boolean employed, String employerName, String employerAddress) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number is required");
        }
        
        Company customer = new Company(companyName, address, registrationNumber,
                                      employed, employerName, employerAddress);
        
        Integer customerId = customerDAO.save(customer);
        customer.setId(customerId);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Created company customer: " + companyName + " (ID: " + customerId + ")");
        
        return customer;
    }
    
    @Override
    public Customer findById(Integer id) {
        return customerDAO.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }
    
    @Override
    public void update(Customer customer) {
        customerDAO.update(customer);
        
        // Audit log
        auditService.logAction(currentUserId, 
            "Updated customer: " + customer.getName() + " (ID: " + customer.getId() + ")");
    }
    
    @Override
    public void delete(Integer id) {
        Customer customer = customerDAO.findById(id);
        if (customer != null) {
            customerDAO.delete(id);
            
            // Audit log
            auditService.logAction(currentUserId, 
                "Deleted customer: " + customer.getName() + " (ID: " + id + ")");
        }
    }
}

