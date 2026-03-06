package Service;

import Entity.Card;
import Repository.AccountRepository;
import Repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public AccountService(AccountRepository accountRepository, CardRepository cardRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public BigDecimal getBalance(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findWithAccountById(cardId);
        if (optionalCard.isEmpty()){
            throw new IllegalStateException("Card not found");
        }
        BigDecimal currentAccount = optionalCard.get().getAccount().getBalance();
        return currentAccount;
    }
}
