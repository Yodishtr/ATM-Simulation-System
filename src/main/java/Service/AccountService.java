package Service;

import DataTransferObjects.LoggedInAccountInfo;
import Entity.Account;
import Entity.Card;
import Entity.Transaction;
import Repository.AccountRepository;
import Repository.CardRepository;
import Repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, CardRepository cardRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public LoggedInAccountInfo accountInfo(UUID cardNumber) {
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardNumber(cardNumber);
        if (optionalCard.isEmpty()) {
            throw new NoSuchElementException("Card not found");
        }
        Card currentCard = optionalCard.get();
        BigDecimal runningBalance = currentCard.getAccount().getBalance();
        UUID accountNumber = currentCard.getAccount().getAccountNumber();
        List<Transaction> transactionList = currentCard.getAccount().getTransactionList();
        return new LoggedInAccountInfo(runningBalance, accountNumber, transactionList, cardNumber);
    }

    @Transactional
    public BigDecimal getBalance(UUID cardNumber) {
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardNumber(cardNumber);
        if (optionalCard.isEmpty()){
            throw new NoSuchElementException("Card not found");
        }
        BigDecimal currentAccountBalance = optionalCard.get().getAccount().getBalance();
        Transaction balanceInquiryTransaction = new Transaction(BigDecimal.ZERO, Transaction.TRANSACTION_TYPE.INQUIRY,
                LocalDateTime.now(), currentAccountBalance, optionalCard.get().getAccount());
        transactionRepository.save(balanceInquiryTransaction);
        return currentAccountBalance;
    }

    @Transactional
    public void deposit(UUID cardNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardNumber(cardNumber);
        if (optionalCard.isEmpty()) {
            throw new NoSuchElementException("Card not found");
        }
        Account currentAccount = optionalCard.get().getAccount();
        if (currentAccount.isActive() == false) {
            throw new IllegalStateException("Current account is not active");
        }
        currentAccount.setBalance(currentAccount.getBalance().add(amount));
        Transaction currentTransaction = new Transaction(amount, Transaction.TRANSACTION_TYPE.DEPOSIT,
                LocalDateTime.now(), currentAccount.getBalance(), currentAccount);
        transactionRepository.save(currentTransaction);
        accountRepository.save(currentAccount);
    }

    @Transactional
    public void withdraw(UUID cardNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardNumber(cardNumber);
        if (optionalCard.isEmpty()) {
            throw new NoSuchElementException("Card not found");
        }
        Account currentAccount = optionalCard.get().getAccount();
        if (currentAccount.isActive() == false) {
            throw new IllegalStateException("Current account is not active");
        }
        BigDecimal currentAccountBalance = currentAccount.getBalance();
        if (amount.compareTo(currentAccountBalance) > 0) {
            throw new IllegalArgumentException("Amount cannot be greater than account balance");
        }
        currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
        Transaction currentTransaction = new Transaction(amount, Transaction.TRANSACTION_TYPE.WITHDRAWAL,
                LocalDateTime.now(), currentAccount.getBalance(), currentAccount);
        transactionRepository.save(currentTransaction);
        accountRepository.save(currentAccount);
    }
}
