package net.security.services;

import jakarta.validation.Valid;
import net.security.model.AppUser;
import net.security.model.AppUserCreate;
import net.security.model.LoginRequest;
import net.security.model.Token;

public interface AuthService {
    AppUser registerUser(AppUserCreate user);
    Token generateToken(LoginRequest user);
}
