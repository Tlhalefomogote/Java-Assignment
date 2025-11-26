package bank.bankingsystem.DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bank.bankingsystem.models.*;

/**
 * Implementation of AccountDAO for SQLite.
 */
public class AccountDAOImpl implements AccountDAO {
    private final CustomerDAO customerDAO;
    
    public AccountDAOImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    
    @Override
    public Integer save(Account account) {
        String sql = """
            INSERT INTO accounts (accountNumber, customer_id, type, balance, branch)
            VALUES (?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setInt(2, account.getOwner().getId());
            pstmt.setString(3, account.getType().name());
            pstmt.setString(4, account.getBalance().toPlainString());
            pstmt.setString(5, account.getBranch());
            
            pstmt.executeUpdate();
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account", e);
        }
        return null;
    }
    
    @Override
    public Account findById(Integer id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account", e);
        }
        return null;
    }
    
    @Override
    public Account findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE accountNumber = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by number", e);
        }
        return null;
    }
    
    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY id";
        List<Account> accounts = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding accounts by customer", e);
        }
        return accounts;
    }
    
    @Override
    public List<Account> findAll() {
        String sql = "SELECT * FROM accounts ORDER BY id";
        List<Account> accounts = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all accounts", e);
        }
        return accounts;
    }
    
    @Override
    public void update(Account account) {
        String sql = """
            UPDATE accounts 
            SET balance = ?, branch = ?
            WHERE id = ?
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getBalance().toPlainString());
            pstmt.setString(2, account.getBranch());
            pstmt.setInt(3, account.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account", e);
        }
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account", e);
        }
    }
    
    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String accountNumber = rs.getString("accountNumber");
        Integer customerId = rs.getInt("customer_id");
        AccountType type = AccountType.valueOf(rs.getString("type"));
        BigDecimal balance = new BigDecimal(rs.getString("balance"));
        String branch = rs.getString("branch");
        
        Customer owner = customerDAO.findById(customerId);
        if (owner == null) {
            throw new RuntimeException("Customer not found for account: " + accountNumber);
        }
        
        return AccountFactory.createAccount(id, accountNumber, owner, balance, type, branch);
    }
}

