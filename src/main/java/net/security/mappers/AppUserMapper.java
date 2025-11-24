package net.security.mappers;

import net.security.configurations.GlobalMapperConfig;
import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import net.security.model.AppUserCreate;
import net.security.model.UserRoleCreate;
import org.mapstruct.*;

@Mapper(config = GlobalMapperConfig.class)
public interface AppUserMapper {

  AppUserEntity toEntity(AppUser appUser);

  AppUserEntity toEntity(AppUserCreate appUserCreate);

  AppUser toModel(AppUserEntity appUserEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromModel(AppUser appUser, @MappingTarget AppUserEntity appUserEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateRolesEntityFromModel(
      UserRoleCreate userRoleCreate, @MappingTarget AppUserEntity appUserEntity);
}
