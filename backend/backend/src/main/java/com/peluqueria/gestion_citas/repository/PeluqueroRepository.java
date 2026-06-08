package com.peluqueria.gestion_citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peluqueria.gestion_citas.model.Peluquero;

@Repository
public interface PeluqueroRepository extends JpaRepository<Peluquero, Integer> {
}