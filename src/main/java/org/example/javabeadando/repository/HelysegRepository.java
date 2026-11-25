package org.example.javabeadando.repository;

import org.example.javabeadando.model.Helyseg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HelysegRepository extends JpaRepository<Helyseg, Integer> {

    List<Helyseg> findAllByOrderByOrszagAscNevAsc();

    @Query("select distinct h.orszag from Helyseg h order by h.orszag")
    List<String> orszagok();
}
