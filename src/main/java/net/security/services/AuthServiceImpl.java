package net.security.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.security.adaptors.AppUserMapper;
import net.security.entities.AppUserEntity;
import net.security.model.AppUser;
import net.security.model.AppUserCreate;
import net.security.model.LoginRequest;
import net.security.model.Token;
import net.security.repositories.AppUserRepository;
import net.security.utils.JwtUtil;
import org.springframework.cache.annotation.Cacheable;
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
  public AppUser registerUser(AppUserCreate user) {
    if (appUserRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
      throw new RuntimeException(
          "Username or email already exists. Please choose a different username or email.");
    }

    // Create an entity from input object via mapper
    AppUserEntity userEntity = appUserMapper.toEntity(user);
    // Set audit fields to entity
    userEntity.setCreatedBy(user.getUsername());
    userEntity.setCreatedAt(LocalDateTime.now());
    // Update password in encoded format
    userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

    // Save user into DB
    AppUserEntity savedUser = appUserRepository.save(userEntity);
    log.info("User registered: {}", userEntity.getUsername());

    // Create a model again from DB and return
      return appUserMapper.toModel(savedUser);
  }

  @Cacheable(value = "tokenCache", key = "#user.username")
  @Override
  public Token generateToken(LoginRequest user) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    return jwtUtil.generateToken(user.getUsername());
  }
}
