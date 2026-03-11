package Service;

import DataTransferObjects.AccountCreatedDTO;
import DataTransferObjects.CardCreatedDTO;
import DataTransferObjects.UserCreatedDTO;
import Entity.Account;
import Entity.Card;
import Entity.User;
import Repository.AccountRepository;
import Repository.CardRepository;
import Repository.TransactionRepository;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public AdminService(AccountRepository accountRepository, CardRepository cardRepository,
                        TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserCreatedDTO onBoarding(String firstName, String lastName, String username, String email,
                                     BigDecimal currentBalance, String accountType,
                                     String cardPin) {
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username is already in use");
        }
        User onboardingUser = new User(firstName, lastName, username, email, User.ROLE.USER);
        if (accountType.equalsIgnoreCase("checking")) {
            Account onboardingAccount = new Account(currentBalance, onboardingUser, Account.ACCTYPE.CHECKING);
            onboardingUser.addAccount(onboardingAccount);
            Card onboardingCard = new Card(onboardingAccount, cardPin);
            onboardingAccount.addCard(onboardingCard);
            userRepository.save(onboardingUser);
            accountRepository.save(onboardingAccount);
            cardRepository.save(onboardingCard);
            UserCreatedDTO userCreatedDTOResult = new UserCreatedDTO(username,
                    onboardingAccount.getAccountNumber().toString(), onboardingCard.getCardNumber().toString(),
                    true, true);
            return userCreatedDTOResult;
        } else {
            Account savingsAccount = new Account(currentBalance, onboardingUser, Account.ACCTYPE.SAVINGS);
            onboardingUser.addAccount(savingsAccount);
            Card savingsCard = new Card(savingsAccount, cardPin);
            savingsAccount.addCard(savingsCard);
            userRepository.save(onboardingUser);
            accountRepository.save(savingsAccount);
            cardRepository.save(savingsCard);
            UserCreatedDTO userCreatedDTOResult = new UserCreatedDTO(username,
                    savingsAccount.getAccountNumber().toString(), savingsCard.getCardNumber().toString(),
                    true, true);
            return userCreatedDTOResult;
        }
    }


    @Transactional
    public AccountCreatedDTO newAccountForUser(String username, BigDecimal runningBalance,
                                               String accountType) {
        if (!userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User does not exist");
        }
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        if (accountType.equalsIgnoreCase("checking")){
            Account newAccount = new Account(runningBalance, optionalUser.get(), Account.ACCTYPE.CHECKING);
            User currentUser = optionalUser.get();
            currentUser.addAccount(newAccount);
            userRepository.save(currentUser);
            accountRepository.save(newAccount);
            AccountCreatedDTO accCreatedDTO = new AccountCreatedDTO(newAccount.getAccountNumber().toString(),
                    runningBalance, "checking", username);
            return accCreatedDTO;
        } else {
            Account savingsAccount = new Account(runningBalance, optionalUser.get(), Account.ACCTYPE.SAVINGS);
            User currentUser = optionalUser.get();
            currentUser.addAccount(savingsAccount);
            userRepository.save(currentUser);
            accountRepository.save(savingsAccount);
            AccountCreatedDTO accCreatedDTO = new AccountCreatedDTO(savingsAccount.getAccountNumber().toString(),
                    runningBalance, "savings", username);
            return accCreatedDTO;
        }
    }

    @Transactional
    public CardCreatedDTO newCardForAccount(UUID accountNumber, String cardPin) {
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Account does not exist");
        }
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (!optionalAccount.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }
        Account currentAccount = optionalAccount.get();
        User currentUser = currentAccount.getUser();
        Card freshCard = new Card(currentAccount, cardPin);
        currentAccount.addCard(freshCard);
        cardRepository.save(freshCard);
        accountRepository.save(currentAccount);
        CardCreatedDTO cardReport = new CardCreatedDTO(freshCard.getCardNumber().toString(), currentUser.getUsername(),
                currentUser.getFirstName(), currentUser.getLastName());
        return cardReport;
    }

    @Transactional
    public boolean freezeAccount(UUID accountNumber) {
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Account does not exist");
        }
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (!optionalAccount.isPresent()) {
            throw new IllegalStateException("Account not found");
        }
        Account currentAccount = optionalAccount.get();
        if (currentAccount.isActive() == false) {
            throw new IllegalStateException("Account is already not active");
        } else {
            currentAccount.setActive(false);
            accountRepository.save(currentAccount);
            return true;
        }
    }

    @Transactional
    public boolean unfreezeAccount(UUID accountNumber) {
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Account does not exist");
        }
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (!optionalAccount.isPresent()) {
            throw new IllegalStateException("Account not found");
        }
        Account currentAccount = optionalAccount.get();
        if (currentAccount.isActive() == true) {
            throw new IllegalStateException("Account is already active");
        } else {
            currentAccount.setActive(true);
            accountRepository.save(currentAccount);
            return true;
        }
    }
}
