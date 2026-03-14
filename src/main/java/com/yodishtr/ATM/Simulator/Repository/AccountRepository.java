package com.yodishtr.ATM.Simulator.Repository;

import com.yodishtr.ATM.Simulator.Entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(Long id);
    Optional<Account> findByAccountNumber(UUID accountNumber);
    List<Account> findByAccType(Account.ACCTYPE accountType);
    List<Account> findByBalance(BigDecimal balance);

    boolean existsByAccountId(Long id);
    boolean existsByAccountNumber(UUID accountNumber);
    boolean existsByAccType(Account.ACCTYPE accountType);
    boolean existsByBalance(BigDecimal balance);

    @EntityGraph(attributePaths = {"user"})
    Optional<Account> findWithUserByAccountId(Long id);

    @EntityGraph(attributePaths = {"cardList"})
    Optional<Account> findWithCardListByAccountId(Long id);

    @EntityGraph(attributePaths = {"transactionList"})
    Optional<Account> findWithTransactionListByAccountId(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Account> findWithUserByAccountNumber(UUID accountNumber);

    @EntityGraph(attributePaths = {"cardList"})
    Optional<Account> findWithCardListByAccountNumber(UUID accountNumber);

    @EntityGraph(attributePaths = {"transactionList"})
    Optional<Account> findWithTransactionListByAccountNumber(UUID accountNumber);
}
