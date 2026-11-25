package org.example.javabeadando.repository;

import org.example.javabeadando.model.Uzenet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UzenetRepository extends JpaRepository<Uzenet, Long> {

    List<Uzenet> findAllByOrderByKuldesIdejeDesc();
}
