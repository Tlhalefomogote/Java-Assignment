package bank.bankingsystem.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Cheque account that allows deposits and withdrawals.
 * Can ONLY be opened for employed customers (requires company name and address).
 */
public class ChequeAccount extends Account implements Withdrawable {

    /**
     * Constructor for creating a new cheque account.
     * 
     * @param accountNumber the account number
     * @param owner the customer who owns the account (must be employed)
     * @param initialDeposit the initial deposit
     * @param branch the branch where the account is opened
     * @throws IllegalArgumentException if customer is not employed or missing employment details
     */
    public ChequeAccount(String accountNumber, Customer owner, BigDecimal initialDeposit, String branch) {
        super(accountNumber, owner, initialDeposit, AccountType.CHEQUE, branch);
        
        // Business rule: ChequeAccount requires employment
        if (!owner.isEmployed()) {
            throw new IllegalArgumentException("ChequeAccount can only be opened for employed customers");
        }
        if (owner.getEmployerName() == null || owner.getEmployerName().trim().isEmpty()) {
            throw new IllegalArgumentException("ChequeAccount requires employer name");
        }
        if (owner.getEmployerAddress() == null || owner.getEmployerAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("ChequeAccount requires employer address");
        }
    }

    /**
     * Constructor for database loading (bypasses validation).
     */
    public ChequeAccount(Integer id, String accountNumber, Customer owner, BigDecimal balance, String branch) {
        super(id, accountNumber, owner, balance, AccountType.CHEQUE, branch);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getAvailableBalance() {
        return balance;
    }

    @Override
    public BigDecimal getWithdrawalLimit() {
        return balance;
    }
}


