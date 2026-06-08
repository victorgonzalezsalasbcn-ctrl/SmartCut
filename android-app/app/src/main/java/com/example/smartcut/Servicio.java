package com.example.smartcut;

import java.io.Serializable;

public class Servicio implements Serializable {
    private Integer id;
    private String nombre;
    private double precio;
    private Integer duracionMin;

    public Servicio() {}

    public Servicio(Integer id, String nombre, double precio, Integer duracionMin) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionMin = duracionMin;
    }

    // Getters y Setters para que el Adapter y la Activity no peten
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Integer getDuracionMin() { return duracionMin; }
    public void setDuracionMin(Integer duracionMin) { this.duracionMin = duracionMin; }
}