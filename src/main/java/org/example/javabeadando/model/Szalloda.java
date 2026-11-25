package org.example.javabeadando.model;

import jakarta.persistence.*;

@Entity
@Table(name = "szalloda")
public class Szalloda {

    @Id
    @Column(length = 2)
    private String az;

    @Column(nullable = false)
    private String nev;

    private Integer besorolas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "helyseg_az")
    private Helyseg helyseg;

    private Integer tengerpartTav;

    private Integer repterTav;

    private Boolean felpanzio;

    public String getAz() { return az; }
    public void setAz(String az) { this.az = az; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public Integer getBesorolas() { return besorolas; }
    public void setBesorolas(Integer besorolas) { this.besorolas = besorolas; }

    public Helyseg getHelyseg() { return helyseg; }
    public void setHelyseg(Helyseg helyseg) { this.helyseg = helyseg; }

    public Integer getTengerpartTav() { return tengerpartTav; }
    public void setTengerpartTav(Integer tengerpartTav) { this.tengerpartTav = tengerpartTav; }

    public Integer getRepterTav() { return repterTav; }
    public void setRepterTav(Integer repterTav) { this.repterTav = repterTav; }

    public Boolean getFelpanzio() { return felpanzio; }
    public void setFelpanzio(Boolean felpanzio) { this.felpanzio = felpanzio; }
}
