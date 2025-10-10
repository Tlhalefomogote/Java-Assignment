public class InvestmentAccount extends Account implements InterestBearing {
    public InvestmentAccount(String accountNumber,String branch, double balance) {
        super(accountNumber, balance, null, "Investment", "active", "2024-01-01", "2024-01-01", 0.05, "BWP", branch);
        if (initialDeposit >= 500) {
            deposit(initialDeposit);
        } else {
            System.out.println("Investment Account requires a minimum of BWP500 to open.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn BWP" + amount + " from Investment Account.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.05; // 5%
        balance += interest;
        System.out.println("Investment interest added: BWP" + interest);
    }
}   