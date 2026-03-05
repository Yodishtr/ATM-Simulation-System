package Repository;

import Entity.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findById(Long id);
    Optional<Card> findByCardNumber(UUID cardNumber);

    @EntityGraph(attributePaths = {"account"})
    Optional<Card> findWithAccountById(Long id);

    @EntityGraph(attributePaths = {"account"})
    Optional<Card> findWithAccountByCardNumber(UUID cardNumber);
}
