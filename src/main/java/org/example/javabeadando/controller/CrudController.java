package org.example.javabeadando.controller;

import jakarta.validation.Valid;
import org.example.javabeadando.dto.SzallodaForm;
import org.example.javabeadando.model.Szalloda;
import org.example.javabeadando.repository.HelysegRepository;
import org.example.javabeadando.repository.SzallodaRepository;
import org.example.javabeadando.repository.TavaszRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/crud")
public class CrudController {

    private final SzallodaRepository szallodaRepository;
    private final HelysegRepository helysegRepository;
    private final TavaszRepository tavaszRepository;

    public CrudController(SzallodaRepository szallodaRepository,
                          HelysegRepository helysegRepository,
                          TavaszRepository tavaszRepository) {
        this.szallodaRepository = szallodaRepository;
        this.helysegRepository = helysegRepository;
        this.tavaszRepository = tavaszRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("szallodak", szallodaRepository.mindenHelyseggel());
        return "crud";
    }

    @GetMapping("/uj")
    public String ujUrlap(Model model) {
        return urlap(model, new SzallodaForm(), false);
    }

    @PostMapping("/uj")
    public String letrehoz(@Valid @ModelAttribute("szallodaForm") SzallodaForm form,
                           BindingResult hibak,
                           Model model,
                           RedirectAttributes ra) {
        if (!hibak.hasFieldErrors("az") && szallodaRepository.existsById(form.getAz())) {
            hibak.rejectValue("az", "foglalt", "Ez a kód már foglalt.");
        }
        if (hibak.hasErrors()) {
            return urlap(model, form, false);
        }
        Szalloda szalloda = new Szalloda();
        szalloda.setAz(form.getAz());
        ment(szalloda, form);
        ra.addFlashAttribute("uzenet", "A szálloda létrehozva: " + szalloda.getNev());
        return "redirect:/crud";
    }

    @GetMapping("/{az}/szerkeszt")
    public String szerkesztUrlap(@PathVariable String az, Model model, RedirectAttributes ra) {
        Szalloda s = szallodaRepository.findById(az).orElse(null);
        if (s == null) {
            ra.addFlashAttribute("hiba", "Nincs ilyen szálloda.");
            return "redirect:/crud";
        }
        SzallodaForm form = new SzallodaForm();
        form.setAz(s.getAz());
        form.setNev(s.getNev());
        form.setBesorolas(s.getBesorolas());
        form.setHelysegAz(s.getHelyseg().getAz());
        form.setTengerpartTav(s.getTengerpartTav());
        form.setRepterTav(s.getRepterTav());
        form.setFelpanzio(Boolean.TRUE.equals(s.getFelpanzio()));
        return urlap(model, form, true);
    }

    @PostMapping("/{az}/szerkeszt")
    public String modosit(@PathVariable String az,
                          @Valid @ModelAttribute("szallodaForm") SzallodaForm form,
                          BindingResult hibak,
                          Model model,
                          RedirectAttributes ra) {
        Szalloda szalloda = szallodaRepository.findById(az).orElse(null);
        if (szalloda == null) {
            ra.addFlashAttribute("hiba", "Nincs ilyen szálloda.");
            return "redirect:/crud";
        }
        form.setAz(az);
        if (hibak.hasErrors()) {
            return urlap(model, form, true);
        }
        ment(szalloda, form);
        ra.addFlashAttribute("uzenet", "A szálloda módosítva: " + szalloda.getNev());
        return "redirect:/crud";
    }

    @PostMapping("/{az}/torol")
    public String torol(@PathVariable String az, RedirectAttributes ra) {
        Szalloda szalloda = szallodaRepository.findById(az).orElse(null);
        if (szalloda == null) {
            ra.addFlashAttribute("hiba", "Nincs ilyen szálloda.");
        } else if (tavaszRepository.existsBySzallodaAz(az)) {
            ra.addFlashAttribute("hiba", "A(z) " + szalloda.getNev() + " nem törölhető, mert vannak hozzá ajánlatok.");
        } else {
            szallodaRepository.delete(szalloda);
            ra.addFlashAttribute("uzenet", "A szálloda törölve: " + szalloda.getNev());
        }
        return "redirect:/crud";
    }

    private void ment(Szalloda szalloda, SzallodaForm form) {
        szalloda.setNev(form.getNev().trim());
        szalloda.setBesorolas(form.getBesorolas());
        szalloda.setHelyseg(helysegRepository.getReferenceById(form.getHelysegAz()));
        szalloda.setTengerpartTav(form.getTengerpartTav());
        szalloda.setRepterTav(form.getRepterTav());
        szalloda.setFelpanzio(form.isFelpanzio());
        szallodaRepository.save(szalloda);
    }

    private String urlap(Model model, SzallodaForm form, boolean szerkesztes) {
        model.addAttribute("szallodaForm", form);
        model.addAttribute("szerkesztes", szerkesztes);
        model.addAttribute("helysegek", helysegRepository.findAllByOrderByOrszagAscNevAsc());
        return "crud-urlap";
    }
}
