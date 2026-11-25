package org.example.javabeadando.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SzallodaForm {

    @NotBlank(message = "A kód megadása kötelező.")
    @Pattern(regexp = "[A-Z]{2}", message = "A kód pontosan 2 nagybetű legyen (pl. AB).")
    private String az;

    @NotBlank(message = "A név megadása kötelező.")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet.")
    private String nev;

    @NotNull(message = "A besorolás megadása kötelező.")
    @Min(value = 1, message = "A besorolás 1 és 5 között legyen.")
    @Max(value = 5, message = "A besorolás 1 és 5 között legyen.")
    private Integer besorolas;

    @NotNull(message = "Válassz helységet.")
    private Integer helysegAz;

    @NotNull(message = "A tengerpart távolság megadása kötelező.")
    @Min(value = 0, message = "A távolság nem lehet negatív.")
    private Integer tengerpartTav;

    @NotNull(message = "A reptér távolság megadása kötelező.")
    @Min(value = 0, message = "A távolság nem lehet negatív.")
    private Integer repterTav;

    private boolean felpanzio;

    public String getAz() { return az; }
    public void setAz(String az) { this.az = az; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public Integer getBesorolas() { return besorolas; }
    public void setBesorolas(Integer besorolas) { this.besorolas = besorolas; }

    public Integer getHelysegAz() { return helysegAz; }
    public void setHelysegAz(Integer helysegAz) { this.helysegAz = helysegAz; }

    public Integer getTengerpartTav() { return tengerpartTav; }
    public void setTengerpartTav(Integer tengerpartTav) { this.tengerpartTav = tengerpartTav; }

    public Integer getRepterTav() { return repterTav; }
    public void setRepterTav(Integer repterTav) { this.repterTav = repterTav; }

    public boolean isFelpanzio() { return felpanzio; }
    public void setFelpanzio(boolean felpanzio) { this.felpanzio = felpanzio; }
}
