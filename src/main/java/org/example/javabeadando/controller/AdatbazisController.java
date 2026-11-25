package org.example.javabeadando.controller;

import org.example.javabeadando.model.Tavasz;
import org.example.javabeadando.repository.HelysegRepository;
import org.example.javabeadando.repository.SzallodaRepository;
import org.example.javabeadando.repository.TavaszRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdatbazisController {

    private final HelysegRepository helysegRepository;
    private final SzallodaRepository szallodaRepository;
    private final TavaszRepository tavaszRepository;

    public AdatbazisController(HelysegRepository helysegRepository,
                               SzallodaRepository szallodaRepository,
                               TavaszRepository tavaszRepository) {
        this.helysegRepository = helysegRepository;
        this.szallodaRepository = szallodaRepository;
        this.tavaszRepository = tavaszRepository;
    }

    @GetMapping("/adatbazis")
    public String adatbazis(@RequestParam(required = false) String orszag,
                            @RequestParam(required = false) Integer helyseg,
                            @RequestParam(required = false) Integer idotartam,
                            Model model) {
        if (orszag != null && orszag.isBlank()) {
            orszag = null;
        }

        List<Tavasz> ajanlatok = tavaszRepository.keres(orszag, helyseg, idotartam);

        model.addAttribute("helysegekSzama", helysegRepository.count());
        model.addAttribute("szallodakSzama", szallodaRepository.count());
        model.addAttribute("ajanlatokSzama", tavaszRepository.count());

        model.addAttribute("osszesito", szallodaRepository.osszesito());

        model.addAttribute("orszagok", helysegRepository.orszagok());
        model.addAttribute("helysegek", helysegRepository.findAllByOrderByOrszagAscNevAsc());
        model.addAttribute("ajanlatok", ajanlatok);

        model.addAttribute("valasztottOrszag", orszag);
        model.addAttribute("valasztottHelyseg", helyseg);
        model.addAttribute("valasztottIdotartam", idotartam);
        return "adatbazis";
    }
}
