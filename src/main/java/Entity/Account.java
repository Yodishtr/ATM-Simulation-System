package Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Account {

    public enum ACCTYPE{
        CHECKING,
        SAVINGS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private UUID accountNumber = UUID.randomUUID();

    @Column(columnDefinition = "DECIMAL(10,2) default '0.00'")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ACCTYPE accType;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList;

    public Account() {}

    // Getters
    public Long getAccountId() {
        return accountId;
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public ACCTYPE getAccType() {
        return accType;
    }

    public boolean isActive() {
        return active;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    // Setters
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountNumber(UUID accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccType(ACCTYPE accType) {
        this.accType = accType;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }


}
