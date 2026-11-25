package org.example.javabeadando.controller;

import org.example.javabeadando.repository.AppUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final AppUserRepository repository;

    public AdminController(AppUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", repository.findAll());
        return "admin";
    }
}
