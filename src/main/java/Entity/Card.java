package Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false, unique = true)
    private UUID cardNumber = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private String pin;

    public Card() {}

    public Card(Account account, String pin) {
        this.account = account;
        this.pin = pin;
    }

    // Getters
    public Long getCardId() {
        return cardId;
    }

    public UUID getCardNumber() {
        return cardNumber;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isActive() {
        return active;
    }

    public String getPin() {
        return pin;
    }

    // Setters
    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCardNumber(UUID cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
