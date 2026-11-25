package org.example.javabeadando.service;

import org.example.javabeadando.model.AppUser;
import org.example.javabeadando.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Új, sima felhasználó (USER szerep). Hibánál a hibaüzenetet adja vissza, sikernél null-t. */
    public String register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return "A felhasználónév és a jelszó nem lehet üres.";
        }
        if (repository.existsByUsername(username.trim())) {
            return "Ez a felhasználónév már foglalt.";
        }
        repository.save(new AppUser(username.trim(), passwordEncoder.encode(password), "USER"));
        return null;
    }
}
