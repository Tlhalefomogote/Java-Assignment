package bank.bankingsystem.Controllers;

import java.util.List;
import bank.bankingsystem.Services.CustomerService;
import bank.bankingsystem.models.Customer;

/**
 * Controller for customer management operations.
 */
public class CustomerController {
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    public Customer createIndividual(String firstName, String lastName, String address, String nationalId,
                                   boolean employed, String employerName, String employerAddress) {
        return customerService.createIndividual(firstName, lastName, address, nationalId,
                                                employed, employerName, employerAddress);
    }
    
    public Customer createCompany(String companyName, String address, String registrationNumber,
                                 boolean employed, String employerName, String employerAddress) {
        return customerService.createCompany(companyName, address, registrationNumber,
                                            employed, employerName, employerAddress);
    }
    
    public Customer findById(Integer id) {
        return customerService.findById(id);
    }
    
    public List<Customer> findAll() {
        return customerService.findAll();
    }
    
    public void update(Customer customer) {
        customerService.update(customer);
    }
    
    public void delete(Integer id) {
        customerService.delete(id);
    }
}

