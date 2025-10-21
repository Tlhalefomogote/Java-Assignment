package model;

// Savings account - earns small interest, no withdrawals allowed
public class SavingsAccount extends Account implements InterestBearing, Depositable {
    private static final double MONTHLY_RATE = 0.0005; // 0.05%

    public SavingsAccount(String accNo, Customer c, String branch, double opening) {
        super(accNo, c, branch, opening);
    }

    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawals not allowed on Savings Account.");
        return false;
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * MONTHLY_RATE;
        balance += interest;
        System.out.printf("Savings interest ", interest, balance);
    }

    @Override
    public void monthlyProcess() {
        applyMonthlyInterest();
    }
}
