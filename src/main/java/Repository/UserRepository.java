package Repository;

import Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByUsername(String username);
    List<User> findByRole(User.ROLE role);

    boolean existsById(Long id);
    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<User> findWithAccountsById(Long id);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<User> findWithAccountsByUsername(String username);
}
