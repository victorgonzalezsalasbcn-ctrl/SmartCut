package com.peluqueria.gestion_citas.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull(message = "Dime qué cliente es, que si no no sé a quién peinar")
    private Cliente cliente;

    // 
    @ManyToOne
    @JoinColumn(name = "id_peluquero")
    @NotNull(message = "Dime qué peluquero le va a atender")
    private Peluquero peluquero;
    // ------------------------------------

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    @NotNull(message = "Dime qué servicio quiere")
    private Servicio servicio;

    @Column(name = "fecha_hora")
    @NotNull(message = "Pon una fecha y hora, que si no no viene")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaHora;

    private String observaciones;

    public Cita() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Peluquero getPeluquero() { return peluquero; }
    public void setPeluquero(Peluquero peluquero) { this.peluquero = peluquero; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}