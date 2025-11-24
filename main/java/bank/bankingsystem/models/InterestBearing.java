package bank.bankingsystem.models;

import java.math.BigDecimal;

/**
 * Interface for accounts that earn interest.
 */
public interface InterestBearing {
    /**
     * Applies monthly interest to the account and returns the interest amount applied.
     * 
     * @return the interest amount that was applied
     */
    BigDecimal applyMonthlyInterest();

    /**
     * Returns the monthly interest rate (e.g., 0.0005 for 0.05%).
     * 
     * @return the monthly interest rate as a BigDecimal
     */
    BigDecimal getInterestRate();
}
