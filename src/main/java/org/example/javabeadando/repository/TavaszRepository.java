package org.example.javabeadando.repository;

import org.example.javabeadando.model.Tavasz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TavaszRepository extends JpaRepository<Tavasz, Long> {

    @Query("""
            select t from Tavasz t
            join fetch t.szalloda s
            join fetch s.helyseg h
            where (:orszag is null or h.orszag = :orszag)
              and (:helysegAz is null or h.az = :helysegAz)
              and (:idotartam is null or t.idotartam = :idotartam)
            order by t.indulas, s.nev, t.idotartam
            """)
    List<Tavasz> keres(@Param("orszag") String orszag,
                       @Param("helysegAz") Integer helysegAz,
                       @Param("idotartam") Integer idotartam);
}
