package bank.bankingsystem.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Investment account that allows deposits and withdrawals.
 * Requires minimum initial deposit of BWP 500.00.
 * Earns monthly interest at 5%.
 */
public class InvestmentAccount extends Account implements Withdrawable, InterestBearing {

    private static final BigDecimal MIN_INITIAL_DEPOSIT = new BigDecimal("500.00");
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.05"); // 5% monthly

    /**
     * Constructor for creating a new investment account.
     * 
     * @param accNumber the account number
     * @param owner the customer who owns the account
     * @param initialDeposit the initial deposit (must be >= BWP 500.00)
     * @param branch the branch where the account is opened
     * @throws IllegalArgumentException if initial deposit is less than minimum
     */
    public InvestmentAccount(String accNumber, Customer owner, BigDecimal initialDeposit, String branch) {
        super(accNumber, owner, initialDeposit, AccountType.INVESTMENT, branch);
        if (initialDeposit.compareTo(MIN_INITIAL_DEPOSIT) < 0) {
            throw new IllegalArgumentException("InvestmentAccount requires a minimum initial deposit of BWP " + MIN_INITIAL_DEPOSIT);
        }
    }

    /**
     * Constructor for database loading.
     */
    public InvestmentAccount(Integer id, String accNumber, Customer owner, BigDecimal balance, String branch) {
        super(id, accNumber, owner, balance, AccountType.INVESTMENT, branch);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getAvailableBalance() {
        return balance;
    }

    @Override
    public BigDecimal getWithdrawalLimit() {
        return balance; // can withdraw up to current balance
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


