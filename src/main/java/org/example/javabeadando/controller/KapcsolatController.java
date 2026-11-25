package org.example.javabeadando.controller;

import jakarta.validation.Valid;
import org.example.javabeadando.dto.KapcsolatForm;
import org.example.javabeadando.model.Uzenet;
import org.example.javabeadando.repository.UzenetRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class KapcsolatController {

    private final UzenetRepository uzenetRepository;

    public KapcsolatController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/kapcsolat")
    public String urlap(Model model) {
        model.addAttribute("kapcsolatForm", new KapcsolatForm());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String kuldes(@Valid @ModelAttribute("kapcsolatForm") KapcsolatForm form,
                         BindingResult hibak,
                         Authentication authentication) {
        // Szerver oldali validáció: hiba esetén az űrlap újra, a hibaüzenetekkel
        if (hibak.hasErrors()) {
            return "kapcsolat";
        }

        Uzenet uzenet = new Uzenet();
        uzenet.setNev(form.getNev().trim());
        uzenet.setEmail(form.getEmail().trim());
        uzenet.setTargy(form.getTargy().trim());
        uzenet.setSzoveg(form.getSzoveg().trim());
        uzenet.setKuldesIdeje(LocalDateTime.now());
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            uzenet.setFelhasznalo(authentication.getName());
        }
        uzenetRepository.save(uzenet);

        return "redirect:/kapcsolat?elkuldve";
    }
}
