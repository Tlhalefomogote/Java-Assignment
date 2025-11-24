package bank.bankingsystem.models;

import java.math.BigDecimal;

/**
 * Factory class for creating Account instances based on account type.
 */
public class AccountFactory {
    
    /**
     * Creates a new account instance based on the account type.
     * 
     * @param accountNumber the account number
     * @param owner the customer who owns the account
     * @param initialDeposit the initial deposit
     * @param type the account type
     * @param branch the branch where the account is opened
     * @return a new Account instance
     */
    public static Account createAccount(String accountNumber, Customer owner, 
                                       BigDecimal initialDeposit, AccountType type, String branch) {
        return switch (type) {
            case SAVINGS -> new SavingsAccount(accountNumber, owner, initialDeposit, branch);
            case INVESTMENT -> new InvestmentAccount(accountNumber, owner, initialDeposit, branch);
            case CHEQUE -> new ChequeAccount(accountNumber, owner, initialDeposit, branch);
        };
    }
    
    /**
     * Creates an account instance from database data (bypasses validation).
     * 
     * @param id the account ID
     * @param accountNumber the account number
     * @param owner the customer who owns the account
     * @param balance the current balance
     * @param type the account type
     * @param branch the branch
     * @return an Account instance
     */
    public static Account createAccount(Integer id, String accountNumber, Customer owner,
                                       BigDecimal balance, AccountType type, String branch) {
        return switch (type) {
            case SAVINGS -> new SavingsAccount(id, accountNumber, owner, balance, branch);
            case INVESTMENT -> new InvestmentAccount(id, accountNumber, owner, balance, branch);
            case CHEQUE -> new ChequeAccount(id, accountNumber, owner, balance, branch);
        };
    }
}

