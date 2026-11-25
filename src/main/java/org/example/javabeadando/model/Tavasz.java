package org.example.javabeadando.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tavasz")
public class Tavasz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "szalloda_az")
    private Szalloda szalloda;

    private LocalDate indulas;

    private Integer idotartam;

    private Integer ar;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Szalloda getSzalloda() { return szalloda; }
    public void setSzalloda(Szalloda szalloda) { this.szalloda = szalloda; }

    public LocalDate getIndulas() { return indulas; }
    public void setIndulas(LocalDate indulas) { this.indulas = indulas; }

    public Integer getIdotartam() { return idotartam; }
    public void setIdotartam(Integer idotartam) { this.idotartam = idotartam; }

    public Integer getAr() { return ar; }
    public void setAr(Integer ar) { this.ar = ar; }
}
