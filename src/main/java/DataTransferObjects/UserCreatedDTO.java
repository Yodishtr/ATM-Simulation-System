package DataTransferObjects;

public class UserCreatedDTO {

    private String username;
    private String accountNumber;
    private String cardNumber;
    private boolean accountActive;
    private boolean cardActive;

    public UserCreatedDTO(String username, String accountNumber, String cardnumber, boolean accountActive, boolean cardActive) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.cardNumber = cardnumber;
        this.accountActive = accountActive;
        this.cardActive = cardActive;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public boolean isCardActive() {
        return cardActive;
    }

    @Override
    public String toString() {
        return "New User{ " + "username: " + username + ", account number: " + accountNumber +
                ", card number: " + cardNumber + ", account active: " + accountActive + ", card active: " + cardActive +
                " }";
    }
}
