package com.peluqueria.gestion_citas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peluqueria.gestion_citas.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {


    // Usamos c.peluquero.id porque en tu Cita.java el objeto se llama "peluquero"
    @Query("SELECT c FROM Cita c WHERE c.peluquero.id = :idPeluquero AND c.fechaHora BETWEEN :inicio AND :fin")
    List<Cita> findByPeluqueroIdAndFechaHoraBetween(
        @Param("idPeluquero") Integer idPeluquero, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fin") LocalDateTime fin
    );
}