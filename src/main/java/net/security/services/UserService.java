package net.security.services;

import net.security.model.AppUser;
import net.security.model.UserRole;
import net.security.model.UserRoleCreate;

import java.util.List;

public interface UserService {
    AppUser addUserRoles(Long id, UserRoleCreate userRoleCreate);
}
