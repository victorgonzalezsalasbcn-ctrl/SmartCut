package com.peluqueria.gestion_citas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "peluqueros")
public class Peluquero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_peluquero;
    private String nombre;
    private String especialidad;

    public Peluquero() {}

    public Integer getId_peluquero() { return id_peluquero; }
    public void setId_peluquero(Integer id_peluquero) { this.id_peluquero = id_peluquero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}