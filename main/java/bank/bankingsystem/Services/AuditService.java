package bank.bankingsystem.Services;

import java.util.List;
import bank.bankingsystem.models.AuditLog;

/**
 * Service interface for audit logging operations.
 */
public interface AuditService {
    /**
     * Logs an action to the audit log.
     * 
     * @param userId the user ID (can be null for system actions)
     * @param action the action description
     */
    void logAction(Integer userId, String action);
    
    /**
     * Retrieves all audit log entries.
     * 
     * @return a list of all audit log entries
     */
    List<AuditLog> getAllLogs();
    
    /**
     * Retrieves audit log entries for a specific user.
     * 
     * @param userId the user ID
     * @return a list of audit log entries
     */
    List<AuditLog> getLogsByUserId(Integer userId);
}

