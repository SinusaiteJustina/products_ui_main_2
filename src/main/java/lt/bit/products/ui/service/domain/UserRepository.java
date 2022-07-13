package lt.bit.products.ui.service.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsernameAndPassword(String username, String password);

  boolean existsByUsernameAndPassword(String username, String password);

  boolean existsByUsernameAndPasswordAndRole(String username, String password, UserRole role);

  @Modifying
  @Query("update UserEntity u set u.loggedInAt = ?1")
  void updateLastLoginTime(LocalDateTime localDateTime);

}
