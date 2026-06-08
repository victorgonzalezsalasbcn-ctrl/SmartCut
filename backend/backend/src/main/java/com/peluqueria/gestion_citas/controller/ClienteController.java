package com.peluqueria.gestion_citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Importante para que las reglas de arriba funcionen
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peluqueria.gestion_citas.model.Cliente;
import com.peluqueria.gestion_citas.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // IMPORTANTE
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping 
    public Cliente crearCliente(@Valid @RequestBody Cliente nuevoCliente) {
        return clienteRepository.save(nuevoCliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Integer id, @Valid @RequestBody Cliente datosNuevos) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                cliente.setNombre(datosNuevos.getNombre());
                cliente.setTelefono(datosNuevos.getTelefono());
                cliente.setEmail(datosNuevos.getEmail());
                return clienteRepository.save(cliente);
            })
            .orElseGet(() -> {
                datosNuevos.setId(id);
                return clienteRepository.save(datosNuevos);
            });
    }

    @DeleteMapping("/{id}")
    public void borrarCliente(@PathVariable Integer id) {
        clienteRepository.deleteById(id);
    }
}