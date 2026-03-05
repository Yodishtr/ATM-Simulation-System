package Repository;

import Entity.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findById(Long id);
    List<Transaction> findByAmount(BigDecimal amount);
    List<Transaction> findByType(Transaction.TRANSACTION_TYPE type);
    List<Transaction> findByTimestamp(LocalDateTime timestamp);
    List<Transaction> findByRunningBalance(BigDecimal runningBalance);

    @EntityGraph(attributePaths = {"account"})
    Optional<Transaction> findWithAccountById(Long id);
}
