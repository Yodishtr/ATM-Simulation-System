package com.yodishtr.ATM.Simulator.Repository;

import com.yodishtr.ATM.Simulator.Entity.Account;
import com.yodishtr.ATM.Simulator.Entity.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(Long id);
    List<Transaction> findByAmount(BigDecimal amount);
    List<Transaction> findByType(Transaction.TRANSACTION_TYPE type);
    List<Transaction> findByTimestamp(LocalDateTime timestamp);
    List<Transaction> findByRunningBalance(BigDecimal runningBalance);
    List<Transaction> findByAccount(Account account);

    @EntityGraph(attributePaths = {"account"})
    Optional<Transaction> findWithAccountByTransactionId(Long id);
}
