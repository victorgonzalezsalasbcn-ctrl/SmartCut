package com.peluqueria.gestion_citas.model;



import java.math.BigDecimal;

import jakarta.persistence.Column; // Para los decimales
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity

@Table(name = "servicios")

public class Servicio {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_servicio") // El ID que tengo en la tabla servicios

    private Integer id;



    private String nombre;



    private BigDecimal precio; // Para que no baile 



    @Column(name = "duracion_min") // Los minutos que se tarda en hacer el servicio

    private Integer duracionMin;



    public Servicio() {}



    // Mis Getters y Setters de siempre para que Spring gestione el JSON de la API

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }

    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getDuracionMin() { return duracionMin; }

    public void setDuracionMin(Integer duracionMin) { this.duracionMin = duracionMin; }

}