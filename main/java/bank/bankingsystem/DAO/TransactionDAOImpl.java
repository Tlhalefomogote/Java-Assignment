package bank.bankingsystem.DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import bank.bankingsystem.models.Transaction;
import bank.bankingsystem.models.TransactionType;

/**
 * Implementation of TransactionDAO for SQLite.
 */
public class TransactionDAOImpl implements TransactionDAO {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public Integer save(Transaction transaction) {
        String sql = """
            INSERT INTO transactions (account_id, type, amount, timestamp)
            VALUES (?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setString(2, transaction.getType().name());
            pstmt.setString(3, transaction.getAmount().toPlainString());
            pstmt.setString(4, transaction.getTimestamp().format(FORMATTER));
            
            pstmt.executeUpdate();
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction", e);
        }
        return null;
    }
    
    @Override
    public Transaction findById(Integer id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding transaction", e);
        }
        return null;
    }
    
    @Override
    public List<Transaction> findByAccountId(Integer accountId) {
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions by account", e);
        }
        return transactions;
    }
    
    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions ORDER BY timestamp DESC";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all transactions", e);
        }
        return transactions;
    }
    
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Integer accountId = rs.getInt("account_id");
        TransactionType type = TransactionType.valueOf(rs.getString("type"));
        BigDecimal amount = new BigDecimal(rs.getString("amount"));
        LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"), FORMATTER);
        
        return new Transaction(id, accountId, type, amount, timestamp);
    }
}

