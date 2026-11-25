package org.example.javabeadando.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** A Kapcsolat űrlap adatai, szerver oldali validációval. */
public class KapcsolatForm {

    @NotBlank(message = "A név megadása kötelező.")
    @Size(min = 2, max = 100, message = "A név 2 és 100 karakter között legyen.")
    private String nev;

    @NotBlank(message = "Az e-mail cím megadása kötelező.")
    @Email(message = "Érvényes e-mail címet adj meg.")
    @Size(max = 150, message = "Az e-mail cím legfeljebb 150 karakter lehet.")
    private String email;

    @NotBlank(message = "A tárgy megadása kötelező.")
    @Size(min = 3, max = 150, message = "A tárgy 3 és 150 karakter között legyen.")
    private String targy;

    @NotBlank(message = "Az üzenet nem lehet üres.")
    @Size(min = 10, max = 2000, message = "Az üzenet 10 és 2000 karakter között legyen.")
    private String szoveg;

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTargy() { return targy; }
    public void setTargy(String targy) { this.targy = targy; }

    public String getSzoveg() { return szoveg; }
    public void setSzoveg(String szoveg) { this.szoveg = szoveg; }
}
