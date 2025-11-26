package bank.bankingsystem.models;

/**
 * Abstract base class representing a customer in the banking system.
 * Can be either an Individual or a Company.
 */
public abstract class Customer {
    private Integer id; // database ID
    protected String firstName;
    protected String lastName;
    protected String address;
    protected boolean employed;
    protected String employerName;
    protected String employerAddress;

    /**
     * Constructor for creating a customer with name and address.
     */
    public Customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.employed = false;
    }

    /**
     * Full constructor with employment details.
     */
    public Customer(String firstName, String lastName, String address, 
                   boolean employed, String employerName, String employerAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.employed = employed;
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    /**
     * Constructor for database loading (with ID).
     */
    public Customer(Integer id, String firstName, String lastName, String address,
                   boolean employed, String employerName, String employerAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.employed = employed;
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the full name of the customer.
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    @Override
    public String toString() {
        return getName() + " (" + address + ")";
    }
}


