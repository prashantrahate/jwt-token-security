package net.security.adaptors;

import net.security.configurations.GlobalMapperConfig;
import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface AppUserMapper {

  AppUserEntity toEntity(AppUser appUser);

  AppUser toModel(AppUserEntity appUserEntity);
}
