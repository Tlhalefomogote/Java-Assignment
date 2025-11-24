package bank.bankingsystem.models;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry for tracking system actions.
 */
public class AuditLog {
    private Integer id;
    private Integer userId;
    private String action;
    private LocalDateTime timestamp;
    
    public AuditLog() {
    }
    
    public AuditLog(Integer userId, String action) {
        this.userId = userId;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }
    
    public AuditLog(Integer id, Integer userId, String action, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "AuditLog{id=" + id + ", userId=" + userId + ", action='" + action + 
               "', timestamp=" + timestamp + "}";
    }
}

