package bank.bankingsystem.Services;

import java.util.Random;

/**
 * Utility class for generating unique account numbers.
 */
public class AccountNumberGenerator {
    private static final Random random = new Random();
    
    /**
     * Generates a unique account number.
     * Format: ACC followed by 8 digits.
     * 
     * @return a new account number
     */
    public static String generate() {
        int number = 10000000 + random.nextInt(90000000);
        return "ACC" + number;
    }
    
    /**
     * Generates an account number with a custom prefix.
     * 
     * @param prefix the prefix (e.g., "SAV", "INV", "CHQ")
     * @return a new account number
     */
    public static String generate(String prefix) {
        int number = 10000000 + random.nextInt(90000000);
        return prefix + number;
    }
}

