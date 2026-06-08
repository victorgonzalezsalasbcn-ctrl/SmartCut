package com.peluqueria.gestion_citas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peluqueria.gestion_citas.model.Cita;
import com.peluqueria.gestion_citas.service.CitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/citas") 
@CrossOrigin(origins = "*")   
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.obtenerTodas();
    }

    // --- ESTE ES EL MÉTODO NUEVO PARA EL CALENDARIO ---
    @GetMapping("/ocupadas")
    public List<String> listarHorasOcupadas(
            @RequestParam Integer idPeluquero, 
            @RequestParam String fecha) { 
        
        // Convertimos el texto "2026-03-27" a fecha real de Java
        LocalDate dia = LocalDate.parse(fecha);
        
        // Le pedimos al Service que nos dé solo las horas (ej: ["10:00", "11:30"])
        return citaService.obtenerHorasOcupadas(idPeluquero, dia);
    }

    @PostMapping
    public Cita crearCita(@Valid @RequestBody Cita nuevaCita) {
        return citaService.guardar(nuevaCita);
    }

    @DeleteMapping("/{id}")
    public void borrarCita(@PathVariable Integer id) {
        citaService.eliminar(id);
    }
}