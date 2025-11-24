package bank.bankingsystem.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import bank.bankingsystem.Services.PasswordHasher;

/**
 * Initializes the SQLite database schema for the banking system.
 */
public class DatabaseInitializer {
    
    /**
     * Initializes the database schema, creating all required tables.
     * 
     * @throws SQLException if a database error occurs
     */
    public static void initializeDatabase() throws SQLException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");
            
            // Create users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    passwordHash TEXT NOT NULL,
                    role TEXT NOT NULL CHECK(role IN ('ADMIN', 'CUSTOMER')),
                    customerId INTEGER,
                    FOREIGN KEY (customerId) REFERENCES customers(id) ON DELETE CASCADE
                )
            """);
            
            // Create customers table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName TEXT NOT NULL,
                    lastName TEXT,
                    address TEXT NOT NULL,
                    employed INTEGER NOT NULL DEFAULT 0,
                    employerName TEXT,
                    employerAddress TEXT,
                    customerType TEXT NOT NULL CHECK(customerType IN ('INDIVIDUAL', 'COMPANY')),
                    nationalId TEXT,
                    registrationNumber TEXT
                )
            """);
            
            // Create accounts table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    accountNumber TEXT UNIQUE NOT NULL,
                    customer_id INTEGER NOT NULL,
                    type TEXT NOT NULL CHECK(type IN ('SAVINGS', 'INVESTMENT', 'CHEQUE')),
                    balance TEXT NOT NULL,
                    branch TEXT NOT NULL,
                    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
                )
            """);
            
            // Create transactions table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_id INTEGER NOT NULL,
                    type TEXT NOT NULL CHECK(type IN ('DEPOSIT', 'WITHDRAWAL', 'INTEREST')),
                    amount TEXT NOT NULL,
                    timestamp TEXT NOT NULL,
                    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
                )
            """);
            
            // Create audit_log table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS audit_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    userId INTEGER,
                    action TEXT NOT NULL,
                    timestamp TEXT NOT NULL,
                    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE SET NULL
                )
            """);
            
            // Create indexes for better performance
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_accounts_customer ON accounts(customer_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_accounts_number ON accounts(accountNumber)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transactions_account ON transactions(account_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_audit_user ON audit_log(userId)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_users_username ON users(username)");
            
            // Insert default admin user (password: admin)
            try {
                String adminHash = PasswordHasher.hashPassword("admin");
                stmt.execute("""
                    INSERT OR IGNORE INTO users (username, passwordHash, role, customerId)
                    VALUES ('admin', '%s', 'ADMIN', NULL)
                """.formatted(adminHash));
            } catch (Exception e) {
                // Admin user might already exist, ignore
            }
        }
        // Connection automatically closed by try-with-resources
    }
}

