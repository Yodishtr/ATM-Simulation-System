package DataTransferObjects;

import Entity.Card;

import java.math.BigDecimal;
import java.util.List;

public class AccountCreatedDTO {

    private String accountNumber;
    private BigDecimal currentBalance;
    private String accountType;
    private String username;

    public AccountCreatedDTO(String accountNumber, BigDecimal currentBalance, String accountType, String username) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        this.username = username;
    }

    @Override
    public String toString() {
        return "New Account: " + "account number= " + accountNumber + ", current balance = " + currentBalance +
                ", username = " + username;
    }
}
