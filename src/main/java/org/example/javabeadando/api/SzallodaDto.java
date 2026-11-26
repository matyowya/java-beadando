package org.example.javabeadando.api;

import org.example.javabeadando.model.Szalloda;

public record SzallodaDto(String az, String nev, Integer besorolas, Integer helysegAz, String helyseg,
                          String orszag, Integer tengerpartTav, Integer repterTav, Boolean felpanzio) {

    public static SzallodaDto of(Szalloda s) {
        return new SzallodaDto(s.getAz(), s.getNev(), s.getBesorolas(), s.getHelyseg().getAz(),
                s.getHelyseg().getNev(), s.getHelyseg().getOrszag(), s.getTengerpartTav(),
                s.getRepterTav(), s.getFelpanzio());
    }
}
