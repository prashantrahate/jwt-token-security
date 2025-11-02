package net.security;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import net.security.entities.AppUserEntity;
import net.security.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    // TODO: only for testing, remove it
    @Transactional
    @PostConstruct
    public void init() {
        AppUserEntity user = new AppUserEntity();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole("ROLE_ADMIN");
        user.setCreatedBy("system");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedBy("system");
        user.setUpdatedAt(LocalDateTime.now());

        appUserRepository.save(user);
    }
}