package org.example.javabeadando.api;

import jakarta.validation.Valid;
import org.example.javabeadando.dto.SzallodaForm;
import org.example.javabeadando.model.Szalloda;
import org.example.javabeadando.repository.HelysegRepository;
import org.example.javabeadando.repository.SzallodaRepository;
import org.example.javabeadando.repository.TavaszRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/szallodak")
public class SzallodaApiController {

    private final SzallodaRepository szallodaRepository;
    private final HelysegRepository helysegRepository;
    private final TavaszRepository tavaszRepository;

    public SzallodaApiController(SzallodaRepository szallodaRepository,
                                 HelysegRepository helysegRepository,
                                 TavaszRepository tavaszRepository) {
        this.szallodaRepository = szallodaRepository;
        this.helysegRepository = helysegRepository;
        this.tavaszRepository = tavaszRepository;
    }

    @GetMapping
    public List<SzallodaDto> lista() {
        return szallodaRepository.mindenHelyseggel().stream().map(SzallodaDto::of).toList();
    }

    @GetMapping("/{az}")
    public ResponseEntity<?> egy(@PathVariable String az) {
        return szallodaRepository.findById(az)
                .<ResponseEntity<?>>map(s -> ResponseEntity.ok(SzallodaDto.of(s)))
                .orElseGet(() -> hiba(HttpStatus.NOT_FOUND, "Nincs ilyen szálloda: " + az));
    }

    @PostMapping
    public ResponseEntity<?> letrehoz(@Valid @RequestBody SzallodaForm form) {
        if (szallodaRepository.existsById(form.getAz())) {
            return hiba(HttpStatus.CONFLICT, "Ez a kód már foglalt: " + form.getAz());
        }
        if (!helysegRepository.existsById(form.getHelysegAz())) {
            return hiba(HttpStatus.BAD_REQUEST, "Nincs ilyen helység: " + form.getHelysegAz());
        }
        Szalloda s = new Szalloda();
        s.setAz(form.getAz());
        ment(s, form);
        return ResponseEntity.status(HttpStatus.CREATED).body(SzallodaDto.of(s));
    }

    @PutMapping("/{az}")
    public ResponseEntity<?> modosit(@PathVariable String az, @Valid @RequestBody SzallodaForm form) {
        Szalloda s = szallodaRepository.findById(az).orElse(null);
        if (s == null) {
            return hiba(HttpStatus.NOT_FOUND, "Nincs ilyen szálloda: " + az);
        }
        if (!helysegRepository.existsById(form.getHelysegAz())) {
            return hiba(HttpStatus.BAD_REQUEST, "Nincs ilyen helység: " + form.getHelysegAz());
        }
        ment(s, form);
        return ResponseEntity.ok(SzallodaDto.of(s));
    }

    @DeleteMapping("/{az}")
    public ResponseEntity<?> torol(@PathVariable String az) {
        Szalloda s = szallodaRepository.findById(az).orElse(null);
        if (s == null) {
            return hiba(HttpStatus.NOT_FOUND, "Nincs ilyen szálloda: " + az);
        }
        if (tavaszRepository.existsBySzallodaAz(az)) {
            return hiba(HttpStatus.CONFLICT, "Nem törölhető, mert vannak hozzá ajánlatok: " + az);
        }
        szallodaRepository.delete(s);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacioHiba(MethodArgumentNotValidException e) {
        Map<String, String> mezok = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> mezok.putIfAbsent(f.getField(), f.getDefaultMessage()));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("hiba", "Érvénytelen adatok");
        body.put("mezok", mezok);
        return ResponseEntity.badRequest().body(body);
    }

    private void ment(Szalloda s, SzallodaForm form) {
        s.setNev(form.getNev().trim());
        s.setBesorolas(form.getBesorolas());
        s.setHelyseg(helysegRepository.findById(form.getHelysegAz()).orElseThrow());
        s.setTengerpartTav(form.getTengerpartTav());
        s.setRepterTav(form.getRepterTav());
        s.setFelpanzio(form.isFelpanzio());
        szallodaRepository.save(s);
    }

    private ResponseEntity<?> hiba(HttpStatus status, String uzenet) {
        return ResponseEntity.status(status).body(Map.of("hiba", uzenet));
    }
}
