package com.yodishtr.ATM.Simulator.Repository;

import com.yodishtr.ATM.Simulator.Entity.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardId(Long id);
    Optional<Card> findByCardNumber(UUID cardNumber);

    @EntityGraph(attributePaths = {"account"})
    Optional<Card> findWithAccountByCardId(Long id);

    @EntityGraph(attributePaths = {"account"})
    Optional<Card> findWithAccountByCardNumber(UUID cardNumber);
}
