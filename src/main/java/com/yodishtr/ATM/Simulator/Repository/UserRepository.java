package com.yodishtr.ATM.Simulator.Repository;

import com.yodishtr.ATM.Simulator.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long id);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByUsername(String username);
    List<User> findByRole(User.ROLE role);

    @EntityGraph(attributePaths = {"accounts"})
    Page<User> findAll(Pageable pageable);

    boolean existsByUserId(Long id);
    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<User> findWithAccountsByUserId(Long id);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<User> findWithAccountsByUsername(String username);
}
