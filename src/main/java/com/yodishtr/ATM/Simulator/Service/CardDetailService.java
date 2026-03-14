package com.yodishtr.ATM.Simulator.Service;

import com.yodishtr.ATM.Simulator.Entity.Account;
import com.yodishtr.ATM.Simulator.Entity.Card;
import com.yodishtr.ATM.Simulator.Entity.User;
import com.yodishtr.ATM.Simulator.Repository.AccountRepository;
import com.yodishtr.ATM.Simulator.Repository.CardRepository;
import com.yodishtr.ATM.Simulator.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardDetailService implements UserDetailsService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public CardDetailService(CardRepository cardRepository, UserRepository userRepository,
                             AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Card> optionalCard = cardRepository.findWithAccountByCardNumber(UUID.fromString(username));
        if (optionalCard.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        Card card = optionalCard.get();
        Optional<Account> optionalAccount = accountRepository.findWithUserByAccountNumber(card.getAccount().
                getAccountNumber());
        if (optionalAccount.isEmpty()){
            throw new UsernameNotFoundException(card.getAccount().getAccountNumber().toString());
        }
        User user = optionalAccount.get().getUser();
        return new CardDetail(optionalCard.get(), user);
    }
}
