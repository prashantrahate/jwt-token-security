package net.security.services;

import jakarta.validation.Valid;
import net.security.model.AppUser;
import net.security.model.LoginRequest;
import net.security.model.Token;

public interface AuthService {
    AppUser registerUser(AppUser user);
    Token generateToken(LoginRequest user);
}
