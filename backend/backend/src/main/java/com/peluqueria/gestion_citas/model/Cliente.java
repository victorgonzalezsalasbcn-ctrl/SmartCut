package com.peluqueria.gestion_citas.model; 

import java.io.Serializable;

import jakarta.persistence.Column; // Importante: sin esto no mapea id_cliente
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes") // Mi tabla en MySQL se llama "clientes"
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_cliente") // OJO: En Java lo llamo "id", pero en mi DB es "id_cliente"
    private Integer id;

    private String nombre;
    private String telefono;
    private String email;

    // El constructor vacío es necesario para que Spring pueda crear objetos Cliente a partir del JSON que le llega en las peticiones POST/PUT
    public Cliente() {}

    // Crear objetos rápido con todos los datos
    public Cliente(Integer id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters estándar para que Spring gestione el JSON de la API
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Parche de compatibilidad para que Android no muera al buscar "id_cliente"
    public int getId_cliente() { 
        return (id != null) ? id : 0; 
    }
}