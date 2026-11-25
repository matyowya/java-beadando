package org.example.javabeadando.config;

import org.example.javabeadando.model.AppUser;
import org.example.javabeadando.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!repository.existsByUsername("admin")) {
            repository.save(new AppUser("admin", passwordEncoder.encode("admin"), "ADMIN"));
        }
    }
}
