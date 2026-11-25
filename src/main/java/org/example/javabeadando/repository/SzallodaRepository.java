package org.example.javabeadando.repository;

import org.example.javabeadando.model.Szalloda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SzallodaRepository extends JpaRepository<Szalloda, String> {

    @Query("""
            select s.nev as nev, s.besorolas as besorolas, s.felpanzio as felpanzio,
                   h.nev as helyseg, h.orszag as orszag,
                   count(t) as ajanlatokSzama, min(t.ar) as legolcsobb
            from Tavasz t join t.szalloda s join s.helyseg h
            group by s.nev, s.besorolas, s.felpanzio, h.nev, h.orszag
            order by h.orszag, h.nev, s.nev
            """)
    List<SzallodaOsszesito> osszesito();

    interface SzallodaOsszesito {
        String getNev();
        Integer getBesorolas();
        Boolean getFelpanzio();
        String getHelyseg();
        String getOrszag();
        Long getAjanlatokSzama();
        Integer getLegolcsobb();
    }
}
