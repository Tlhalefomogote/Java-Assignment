package bank.bankingsystem.models;

/**
 * Represents a company customer in the banking system.
 */
public class Company extends Customer {
    private String registrationNumber;

    /**
     * Constructor for creating a company customer.
     */
    public Company(String companyName, String address, String registrationNumber) {
        super(companyName, "", address); // company name as firstName, empty lastName
        this.registrationNumber = registrationNumber;
    }

    /**
     * Constructor with employment details (for company branches/divisions).
     */
    public Company(String companyName, String address, String registrationNumber,
                  boolean employed, String employerName, String employerAddress) {
        super(companyName, "", address, employed, employerName, employerAddress);
        this.registrationNumber = registrationNumber;
    }

    /**
     * Constructor for database loading.
     */
    public Company(Integer id, String companyName, String address, String registrationNumber,
                  boolean employed, String employerName, String employerAddress) {
        super(id, companyName, "", address, employed, employerName, employerAddress);
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String getName() {
        return getFirstName(); // For companies, firstName is the company name
    }
}

