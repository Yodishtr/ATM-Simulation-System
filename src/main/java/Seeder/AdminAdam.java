package Seeder;

import Entity.Account;
import Entity.Card;
import Entity.User;
import Repository.AccountRepository;
import Repository.CardRepository;
import Repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AdminAdam implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CardRepository cardRepository;

    public AdminAdam(UserRepository userRepository, AccountRepository accountRepository,
                     BCryptPasswordEncoder bCryptPasswordEncoder, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByRole(User.ROLE.ADMIN).size() == 0){
            User admin = new User("BigMan", "Yessai", "erling",
                    "bomboclat@gmail.com", User.ROLE.ADMIN);
            Account account = new Account(BigDecimal.valueOf(100000000), admin, Account.ACCTYPE.SAVINGS);
            String cardPin = "1234";
            String encodedCardPin = bCryptPasswordEncoder.encode(cardPin);
            Card card = new Card(account, encodedCardPin);
            userRepository.save(admin);
            accountRepository.save(account);
            cardRepository.save(card);
            System.out.println(card.getCardNumber());
            System.out.println(encodedCardPin);
        }
    }
}
