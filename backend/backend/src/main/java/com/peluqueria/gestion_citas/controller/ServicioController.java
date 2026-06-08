package com.peluqueria.gestion_citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peluqueria.gestion_citas.model.Servicio;
import com.peluqueria.gestion_citas.repository.ServicioRepository;

@RestController
@RequestMapping("/api/servicios") 
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio nuevoServicio) {
        return servicioRepository.save(nuevoServicio);
    }
}