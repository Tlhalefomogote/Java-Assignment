package bank.bankingsystem.Services;

import java.util.List;
import bank.bankingsystem.DAO.AuditLogDAO;
import bank.bankingsystem.models.AuditLog;

/**
 * Implementation of AuditService.
 */
public class AuditServiceImpl implements AuditService {
    private final AuditLogDAO auditLogDAO;
    
    public AuditServiceImpl(AuditLogDAO auditLogDAO) {
        this.auditLogDAO = auditLogDAO;
    }
    
    @Override
    public void logAction(Integer userId, String action) {
        AuditLog auditLog = new AuditLog(userId, action);
        auditLogDAO.save(auditLog);
    }
    
    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogDAO.findAll();
    }
    
    @Override
    public List<AuditLog> getLogsByUserId(Integer userId) {
        return auditLogDAO.findByUserId(userId);
    }
}

