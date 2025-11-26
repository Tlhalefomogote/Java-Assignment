package bank.bankingsystem.models;

/**
 * Represents an individual customer (person) in the banking system.
 */
public class Individual extends Customer {
    private String nationalId;

    /**
     * Constructor for creating an individual customer.
     */
    public Individual(String firstName, String lastName, String address, String nationalId) {
        super(firstName, lastName, address);
        this.nationalId = nationalId;
    }

    /**
     * Constructor with employment details.
     */
    public Individual(String firstName, String lastName, String address, String nationalId,
                    boolean employed, String employerName, String employerAddress) {
        super(firstName, lastName, address, employed, employerName, employerAddress);
        this.nationalId = nationalId;
    }

    /**
     * Constructor for database loading.
     */
    public Individual(Integer id, String firstName, String lastName, String address, String nationalId,
                    boolean employed, String employerName, String employerAddress) {
        super(id, firstName, lastName, address, employed, employerName, employerAddress);
        this.nationalId = nationalId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}

