package org.example.javabeadando.controller;

import org.example.javabeadando.repository.TavaszRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiagramController {

    private final TavaszRepository tavaszRepository;

    public DiagramController(TavaszRepository tavaszRepository) {
        this.tavaszRepository = tavaszRepository;
    }

    @GetMapping("/diagram")
    public String diagram(Model model) {
        Map<String, Long> nyolcNap = new LinkedHashMap<>();
        Map<String, Long> tizenotNap = new LinkedHashMap<>();

        for (TavaszRepository.AtlagAr sor : tavaszRepository.atlagArak()) {
            long ar = Math.round(sor.getAtlagAr());
            nyolcNap.putIfAbsent(sor.getSzalloda(), null);
            tizenotNap.putIfAbsent(sor.getSzalloda(), null);
            if (sor.getIdotartam() == 8) {
                nyolcNap.put(sor.getSzalloda(), ar);
            } else if (sor.getIdotartam() == 15) {
                tizenotNap.put(sor.getSzalloda(), ar);
            }
        }

        model.addAttribute("szallodak", new ArrayList<>(nyolcNap.keySet()));
        model.addAttribute("nyolcNap", new ArrayList<>(nyolcNap.values()));
        model.addAttribute("tizenotNap", new ArrayList<>(tizenotNap.values()));
        return "diagram";
    }
}
