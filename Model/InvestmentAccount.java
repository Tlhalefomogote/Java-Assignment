package model;

// Investment account - earns higher interest, allows deposit & withdrawal
public class InvestmentAccount extends Account
        implements InterestBearing, Depositable, Withdrawable {

    private static final double MONTHLY_RATE = 0.05; // 5%
    private static final double MIN_OPEN = 500.0;

    public InvestmentAccount(String accNo, Customer c, String branch, double opening) {
        super(accNo, c, branch, opening);
        if (opening < MIN_OPEN)
            throw new IllegalArgumentException("Minimum opening deposit for InvestmentAccount is BWP 500");
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * MONTHLY_RATE;
        balance += interest;
        System.out.printf("Investment interest. New balance: %.2f%n", interest, balance);
    }

    @Override
    public void monthlyProcess() {
        applyMonthlyInterest();
    }
}
