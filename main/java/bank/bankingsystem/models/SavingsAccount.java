package bank.bankingsystem.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Savings account that allows deposits but NO withdrawals.
 * Earns monthly interest at 0.05%.
 */
public class SavingsAccount extends Account implements InterestBearing {

    private static final BigDecimal MIN_INITIAL_DEPOSIT = new BigDecimal("50.00");
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.0005"); // 0.05% monthly

    /**
     * Constructor for creating a new savings account.
     * 
     * @param accNumber the account number
     * @param owner the customer who owns the account
     * @param initialDeposit the initial deposit (must be >= BWP 50.00)
     * @param branch the branch where the account is opened
     * @throws IllegalArgumentException if initial deposit is less than minimum
     */
    public SavingsAccount(String accNumber, Customer owner, BigDecimal initialDeposit, String branch) {
        super(accNumber, owner, initialDeposit, AccountType.SAVINGS, branch);
        if (initialDeposit.compareTo(MIN_INITIAL_DEPOSIT) < 0) {
            throw new IllegalArgumentException("SavingsAccount requires a minimum initial deposit of BWP " + MIN_INITIAL_DEPOSIT);
        }
    }

    /**
     * Constructor for database loading.
     */
    public SavingsAccount(Integer id, String accNumber, Customer owner, BigDecimal balance, String branch) {
        super(id, accNumber, owner, balance, AccountType.SAVINGS, branch);
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = balance.multiply(MONTHLY_RATE).setScale(2, RoundingMode.HALF_UP);
        balance = balance.add(interest).setScale(2, RoundingMode.HALF_UP);
        return interest;
    }

    @Override
    public BigDecimal getInterestRate() {
        return MONTHLY_RATE;
    }
}

