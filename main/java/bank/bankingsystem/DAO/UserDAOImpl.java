package bank.bankingsystem.DAO;

import java.sql.*;
import bank.bankingsystem.models.User;
import bank.bankingsystem.models.UserRole;

/**
 * Implementation of UserDAO for SQLite.
 */
public class UserDAOImpl implements UserDAO {
    
    @Override
    public Integer save(User user) {
        String sql = """
            INSERT INTO users (username, passwordHash, role, customerId)
            VALUES (?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole().name());
            if (user.getCustomerId() != null) {
                pstmt.setInt(4, user.getCustomerId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            
            pstmt.executeUpdate();
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
        return null;
    }
    
    @Override
    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user", e);
        }
        return null;
    }
    
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
        return null;
    }
    
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("passwordHash");
        UserRole role = UserRole.valueOf(rs.getString("role"));
        Integer customerId = rs.getInt("customerId");
        if (rs.wasNull()) {
            customerId = null;
        }
        
        return new User(id, username, passwordHash, role, customerId);
    }
}

