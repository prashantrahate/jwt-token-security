package net.security.repositories;

import java.util.Optional;
import net.security.entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
  Optional<AppUserEntity> findByUsername(String username);

  Optional<AppUserEntity> findByEmail(String email);

  @Query(
      """
        SELECT new AppUserEntity(u.id)
        FROM AppUserEntity u
        WHERE u.username = :username OR u.email = :email
        """)
  Optional<AppUserEntity> findByUsernameOrEmail(
      @Param("username") String username, @Param("email") String email);
}
