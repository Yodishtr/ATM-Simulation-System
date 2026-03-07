package Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    public enum TRANSACTION_TYPE {
        DEPOSIT,
        WITHDRAWAL,
        INQUIRY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TRANSACTION_TYPE type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private BigDecimal runningBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Account account;

    public Transaction() {}

    public Transaction(BigDecimal amount, TRANSACTION_TYPE type, LocalDateTime timestamp, BigDecimal runningBalance,
                       Account account) {
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.runningBalance = runningBalance;
        this.account = account;
    }

    // Getters
    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TRANSACTION_TYPE getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getRunningBalance() {
        return runningBalance;
    }

    public Account getAccount() {
        return account;
    }

    // Setters
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TRANSACTION_TYPE type) {
        this.type = type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setRunningBalance(BigDecimal runningBalance) {
        this.runningBalance = runningBalance;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
