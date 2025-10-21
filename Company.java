package model;

// Represents an employer/company for cheque accounts
public class Company {
    private String name;
    private String address;

    public Company(String n, String a) {
        name = n;
        address = a;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}