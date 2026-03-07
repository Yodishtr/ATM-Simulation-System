package Service;

import Entity.Account;
import Entity.Card;
import Entity.Transaction;
import Repository.AccountRepository;
import Repository.CardRepository;
import Repository.TransactionRepository;
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
        Optional<Card> optionalCard = cardRepository.findWithAccountById(cardId);
        if (optionalCard.isEmpty()) {
            throw new NoSuchElementException("Card not found");
        }
        Account currentAccount = optionalCard.get().getAccount();
        List<Transaction> transactionList = transactionRepository.findByAccount(currentAccount);
        return transactionList;
    }


}
