package bank.bankingsystem.DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import bank.bankingsystem.models.AuditLog;

/**
 * Implementation of AuditLogDAO for SQLite.
 */
public class AuditLogDAOImpl implements AuditLogDAO {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public Integer save(AuditLog auditLog) {
        String sql = """
            INSERT INTO audit_log (userId, action, timestamp)
            VALUES (?, ?, ?)
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (auditLog.getUserId() != null) {
                pstmt.setInt(1, auditLog.getUserId());
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setString(2, auditLog.getAction());
            pstmt.setString(3, auditLog.getTimestamp().format(FORMATTER));
            
            pstmt.executeUpdate();
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving audit log", e);
        }
        return null;
    }
    
    @Override
    public List<AuditLog> findAll() {
        String sql = "SELECT * FROM audit_log ORDER BY timestamp DESC";
        List<AuditLog> logs = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                logs.add(mapResultSetToAuditLog(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all audit logs", e);
        }
        return logs;
    }
    
    @Override
    public List<AuditLog> findByUserId(Integer userId) {
        String sql = "SELECT * FROM audit_log WHERE userId = ? ORDER BY timestamp DESC";
        List<AuditLog> logs = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding audit logs by user", e);
        }
        return logs;
    }
    
    private AuditLog mapResultSetToAuditLog(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        Integer userId = rs.getInt("userId");
        if (rs.wasNull()) {
            userId = null;
        }
        String action = rs.getString("action");
        LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"), FORMATTER);
        
        return new AuditLog(id, userId, action, timestamp);
    }
}

