package com.example.smartcut;

import java.io.Serializable;

// Clase para la gestión de clientes en el sistema
public class Cliente implements Serializable {
    private Integer id;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente() {}

    // Constructor de 4 parámetros para evitar el error en AddClienteActivity
    public Cliente(Integer id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Métodos para mantener la compatibilidad con el resto del proyecto
    public void setId_cliente(int id_cliente) { this.id = id_cliente; }
    public int getId_cliente() { return (id != null) ? id : 0; }
}