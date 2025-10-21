package model;

import java.util.ArrayList;
import java.util.List;

// Customer class - holds personal info and list of accounts
public class Customer {
    private String firstName;
    private String lastName;
    private String address;
    private List<Account> accounts;

    public Customer(String f, String l, String a) {
        firstName = f;
        lastName = l;
        address = a;
        accounts = new ArrayList<>();
    }

    public String getFullName() { return firstName + " " + lastName; }
    public String getAddress()  { return address; }

    public void addAccount(Account acc) { accounts.add(acc); }
    public List<Account> getAccounts()  { return accounts; }
}
