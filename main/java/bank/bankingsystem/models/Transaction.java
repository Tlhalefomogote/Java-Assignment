package bank.bankingsystem.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a financial transaction (deposit, withdrawal, or interest).
 */
public class Transaction {
    private Integer id;
    private Integer accountId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    
    public Transaction() {
    }
    
    public Transaction(Integer accountId, TransactionType type, BigDecimal amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
    
    public Transaction(Integer id, Integer accountId, TransactionType type, BigDecimal amount, LocalDateTime timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "Transaction{id=" + id + ", accountId=" + accountId + ", type=" + type + 
               ", amount=" + amount + ", timestamp=" + timestamp + "}";
    }
}

