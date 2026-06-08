package com.peluqueria.gestion_citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peluqueria.gestion_citas.model.Peluquero;
import com.peluqueria.gestion_citas.repository.PeluqueroRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PeluqueroController {

    @Autowired
    private PeluqueroRepository peluqueroRepository;

    @GetMapping("/peluqueros")
    public List<Peluquero> listarPeluqueros() {
        return peluqueroRepository.findAll();
    }
}