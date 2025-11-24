package bank.bankingsystem.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing SQLite database connections.
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:banking_system.db";
    private static DatabaseConnection instance;
    
    private DatabaseConnection() {
        // Private constructor for singleton
    }
    
    /**
     * Gets the singleton instance of DatabaseConnection.
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Gets a connection to the SQLite database.
     * 
     * @return a Connection object
     * @throws SQLException if a database error occurs
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    /**
     * Gets the database URL.
     */
    public String getDbUrl() {
        return DB_URL;
    }
}

