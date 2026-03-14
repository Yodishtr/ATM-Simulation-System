package com.yodishtr.ATM.Simulator.DataTransferObjects;

import com.yodishtr.ATM.Simulator.Entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class LoggedInAccountInfo {

    private BigDecimal balance;
    private UUID accountNumber;
    private List<Transaction> transactions;
    private UUID cardNumber;

    public LoggedInAccountInfo(BigDecimal balance, UUID accountNumber, List<Transaction> transactions,
                               UUID cardNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.transactions = transactions;
        this.cardNumber = cardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public UUID getCardNumber() {
        return cardNumber;
    }
}
