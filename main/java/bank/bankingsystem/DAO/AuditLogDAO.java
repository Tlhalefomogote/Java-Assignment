package bank.bankingsystem.DAO;

import java.util.List;
import bank.bankingsystem.models.AuditLog;

/**
 * Data Access Object interface for AuditLog operations.
 */
public interface AuditLogDAO {
    /**
     * Saves an audit log entry to the database.
     * 
     * @param auditLog the audit log entry to save
     * @return the generated audit log ID
     */
    Integer save(AuditLog auditLog);
    
    /**
     * Finds all audit log entries.
     * 
     * @return a list of all audit log entries
     */
    List<AuditLog> findAll();
    
    /**
     * Finds audit log entries by user ID.
     * 
     * @param userId the user ID
     * @return a list of audit log entries for the user
     */
    List<AuditLog> findByUserId(Integer userId);
}

