package net.security.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.security.adaptors.AppUserMapper;
import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import net.security.model.LoginRequest;
import net.security.model.Token;
import net.security.repositories.AppUserRepository;
import net.security.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final AppUserRepository appUserRepository;
  private final AppUserMapper appUserMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthServiceImpl(
          AuthenticationManager authenticationManager,
          AppUserRepository appUserRepository,
          AppUserMapper appUserMapper,
          PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.appUserRepository = appUserRepository;
    this.appUserMapper = appUserMapper;
    this.passwordEncoder = passwordEncoder;
      this.jwtUtil = jwtUtil;
  }

  @Transactional
  @Override
  public AppUser registerUser(AppUser user) {
    if (appUserRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
      throw new RuntimeException(
          "Username or email already exists. Please choose a different username or email.");
    }

    AppUserEntity userEntity = appUserMapper.toEntity(user);
    userEntity.setId(null); // Do not use id for registration, otherwise it will cause conflicts.
    userEntity.setCreatedBy("user");
    userEntity.setUpdatedBy("user");
    userEntity.setCreatedAt(LocalDateTime.now());
    userEntity.setUpdatedAt(LocalDateTime.now());
    userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

    AppUserEntity savedUser = appUserRepository.save(userEntity);
    log.info("User registered: {}", userEntity.getUsername());

    AppUser registeredUser = appUserMapper.toModel(savedUser);
    registeredUser.setPassword("***");

    return registeredUser;
  }

  @Override
  public Token generateToken(LoginRequest user) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    return jwtUtil.generateToken(user.getUsername());
  }
}
