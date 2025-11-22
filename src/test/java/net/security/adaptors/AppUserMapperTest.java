package net.security.adaptors;

import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import net.security.model.UserRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppUserMapperTest {

  private final AppUserMapper userMapper = Mappers.getMapper(AppUserMapper.class);

  @Test
  void toEntityTest() {
    AppUser appUser = new AppUser("test@example.com", "testuser", UserRole.GUEST);
    AppUserEntity userEntity = userMapper.toEntity(appUser);
    assertNotNull(userEntity);
    assertEquals(appUser.getEmail(), userEntity.getEmail());
    assertEquals(appUser.getUsername(), userEntity.getUsername());
    assertEquals(appUser.getRole(), appUser.getRole());
  }

  @Test
  void toModelTest() {
    AppUserEntity userEntity = new AppUserEntity();
    userEntity.setId(1L);
    userEntity.setEmail("test@example.com");
    userEntity.setUsername("testuser");
    userEntity.setPassword("testpassword");
    userEntity.setRole("ROLE_USER");
    userEntity.setCreatedBy("system");
    userEntity.setCreatedAt(LocalDateTime.now());
    userEntity.setUpdatedBy("system");
    userEntity.setUpdatedAt(LocalDateTime.now());

    AppUser appUser = userMapper.toModel(userEntity);
    assertNotNull(appUser);
    assertEquals(appUser.getId(), userEntity.getId());
    assertEquals(userEntity.getEmail(), appUser.getEmail());
    assertEquals(userEntity.getUsername(), appUser.getUsername());
    assertEquals(userEntity.getRole(), appUser.getRole());
    assertEquals(userEntity.getCreatedBy(), appUser.getCreatedBy());
    assertEquals(userEntity.getCreatedAt(), appUser.getCreatedAt());
    assertEquals(userEntity.getUpdatedBy(), appUser.getUpdatedBy());
    assertEquals(userEntity.getUpdatedAt(), appUser.getUpdatedAt());
  }
}
