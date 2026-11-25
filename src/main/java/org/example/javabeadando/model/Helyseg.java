package org.example.javabeadando.model;

import jakarta.persistence.*;

@Entity
@Table(name = "helyseg")
public class Helyseg {

    @Id
    private Integer az;

    @Column(nullable = false)
    private String nev;

    @Column(nullable = false)
    private String orszag;

    public Integer getAz() { return az; }
    public void setAz(Integer az) { this.az = az; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getOrszag() { return orszag; }
    public void setOrszag(String orszag) { this.orszag = orszag; }
}
