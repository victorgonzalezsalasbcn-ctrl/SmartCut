package com.peluqueria.gestion_citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peluqueria.gestion_citas.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}