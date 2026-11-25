package org.example.javabeadando.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzenet")
public class Uzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nev;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String targy;

    @Column(nullable = false, length = 2000)
    private String szoveg;

    private String felhasznalo;

    @Column(nullable = false)
    private LocalDateTime kuldesIdeje;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTargy() { return targy; }
    public void setTargy(String targy) { this.targy = targy; }

    public String getSzoveg() { return szoveg; }
    public void setSzoveg(String szoveg) { this.szoveg = szoveg; }

    public String getFelhasznalo() { return felhasznalo; }
    public void setFelhasznalo(String felhasznalo) { this.felhasznalo = felhasznalo; }

    public LocalDateTime getKuldesIdeje() { return kuldesIdeje; }
    public void setKuldesIdeje(LocalDateTime kuldesIdeje) { this.kuldesIdeje = kuldesIdeje; }
}
