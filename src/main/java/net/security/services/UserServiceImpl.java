package net.security.services;

import net.security.mappers.AppUserMapper;
import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import net.security.model.UserRoleCreate;
import net.security.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;

    public UserServiceImpl(AppUserMapper appUserMapper, AppUserRepository appUserRepository) {
        this.appUserMapper = appUserMapper;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public AppUser addUserRoles(Long id, UserRoleCreate userRoleCreate) {
        AppUserEntity appUserEntity = appUserRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        // Add roles into entity
        appUserMapper.updateRolesEntityFromModel(userRoleCreate, appUserEntity);
        // Add audit fields
        appUserEntity.setUpdatedBy("SYSTEM");
        appUserEntity.setUpdatedAt(LocalDateTime.now());
        // Save user, this will all roles into roles table
        AppUserEntity savedAppUser = appUserRepository.save(appUserEntity);

        return appUserMapper.toModel(savedAppUser);
    }
}
