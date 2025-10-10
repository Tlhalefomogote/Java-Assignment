public class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(String accountNumber, String branch, double balance) {
        super(accountNumber, balance, null, "Savings", "active", "2024-01-01", "2024-01-01", 0.05, "BWP", branch);

    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawals are not allowed from Savings Account.");
        }
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.0005; // 0.05%
        balance += interest;
        System.out.println("Savings interest added: BWP" + interest);
    }
    
}
