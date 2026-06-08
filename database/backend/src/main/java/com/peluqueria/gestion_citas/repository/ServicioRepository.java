package com.peluqueria.gestion_citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peluqueria.gestion_citas.model.Servicio;

// Hablo con la tabla servicios usando el ID de tipo Integer
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}