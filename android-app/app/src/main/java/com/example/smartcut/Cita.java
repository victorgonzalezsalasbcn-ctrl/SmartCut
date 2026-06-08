package com.example.smartcut;

import java.io.Serializable;

// Implemento Serializable para poder pasar el objeto cita entre pantallas
public class Cita implements Serializable {
    private Integer id;
    private Cliente cliente;
    private Peluquero peluquero;
    private Servicio servicio;
    private String fechaHora;
    private String observaciones;

    public Cita() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Peluquero getPeluquero() { return peluquero; }
    public void setPeluquero(Peluquero peluquero) { this.peluquero = peluquero; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // El nombre que pide mi servidor para que no me de error de mapeo
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    // Compatibilidad por si el adaptador o algún logcat viejo me pide el nombre con guion bajo
    public String getFecha_hora() { return fechaHora; }
    public void setFecha_hora(String fecha_hora) { this.fechaHora = fecha_hora; }
}