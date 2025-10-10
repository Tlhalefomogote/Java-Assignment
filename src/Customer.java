import java.util.ArrayList;
import java.util.List;

public class customer {
    private String firstName;
    private String lastName;
    private String address;
    private List<account> accounts;

    public customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(account account) {
        accounts.add(account);
        System.out.println("Account added successfully.");
    }

    public void removeAccount(account account) {
        if (accounts.remove(account)) {
            System.out.println("Account removed successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
}
