package lt.bit.products.ui.service.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsernameAndPassword(String username, String password);

  boolean existsByUsernameAndPassword(String username, String password);

  boolean existsByUsernameAndPasswordAndRole(String username, String password, UserRole role);
}
