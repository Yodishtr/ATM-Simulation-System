package com.yodishtr.ATM.Simulator.Service;

import com.yodishtr.ATM.Simulator.Entity.Account;
import com.yodishtr.ATM.Simulator.Entity.Card;
import com.yodishtr.ATM.Simulator.Entity.Transaction;
import com.yodishtr.ATM.Simulator.Repository.AccountRepository;
import com.yodishtr.ATM.Simulator.Repository.CardRepository;
import com.yodishtr.ATM.Simulator.Repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public List<Transaction> getTransactionHistory(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardId(cardId);
        if (optionalCard.isEmpty()) {
            throw new NoSuchElementException("Card not found");
        }
        Account currentAccount = optionalCard.get().getAccount();
        List<Transaction> transactionList = transactionRepository.findByAccount(currentAccount);
        return transactionList;
    }


}
