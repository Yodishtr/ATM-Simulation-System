package Repository;

import Entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(UUID accountNumber);
    List<Account> findByAccountType(Account.ACCTYPE accountType);
    List<Account> findByBalance(BigDecimal balance);

    boolean existsById(Long id);
    boolean existsByAccountNumber(UUID accountNumber);
    boolean existsByAccountType(Account.ACCTYPE accountType);
    boolean existsByBalance(BigDecimal balance);

    @EntityGraph(attributePaths = {"user"})
    Optional<Account> findWithUserById(Long id);

    @EntityGraph(attributePaths = {"cardList"})
    Optional<Account> findWithCardListById(Long id);

    @EntityGraph(attributePaths = {"transactionList"})
    Optional<Account> findWithTransactionListById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Account> findWithUserByAccountNumber(UUID accountNumber);

    @EntityGraph(attributePaths = {"cardList"})
    Optional<Account> findWithCardListByAccountNumber(UUID accountNumber);

    @EntityGraph(attributePaths = {"transactionList"})
    Optional<Account> findWithTransactionListByAccountNumber(UUID accountNumber);
}
