package net.security.controller;

import net.security.model.AppUser;
import net.security.model.UserRoleCreate;
import net.security.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: only admin should be able to call this and also add caching for token
    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public AppUser addUserRole(@PathVariable Long id, @RequestBody UserRoleCreate userRoleCreate) {
        return userService.addUserRoles(id, userRoleCreate);
    }
}
