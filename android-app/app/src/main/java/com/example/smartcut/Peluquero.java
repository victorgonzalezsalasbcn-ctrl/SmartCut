package com.example.smartcut;

import java.io.Serializable;

/**
 * Modelo de datos para los profesionales de la barbería.
 * Implemento Serializable para poder pasar el objeto completo entre pantallas
 * (de la lista de selección a la pantalla de horas) sin perder información.
 */
public class Peluquero implements Serializable {

    private Long id_peluquero; // Clave primaria que viene de la base de datos SQL
    private String nombre;
    private String especialidad; // Ej: "Especialista en degradados" o "Barba clásica"
    private String foto_url;    // Ruta de la imagen para el diseño Premium (diseño de tarjetas)

    // Constructor vacío requerido por Retrofit/Gson para la conversión de JSON
    public Peluquero() {
    }

    public Peluquero(Long id_peluquero, String nombre, String especialidad, String foto_url) {
        this.id_peluquero = id_peluquero;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.foto_url = foto_url;
    }

    // --- GETTERS Y SETTERS ---
    // "Los necesito para que el Adapter pueda pintar el nombre y la foto en la lista"

    public Long getId_peluquero() {
        return id_peluquero;
    }

    public void setId_peluquero(Long id_peluquero) {
        this.id_peluquero = id_peluquero;
    }

    // Añado este método para que CalendarioActivity pueda meter el ID sin problemas de nombres
    public void setId(Integer id) {
        this.id_peluquero = Long.valueOf(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    /**
     * y asegurar que estoy recibiendo el objeto correcto desde la API.
     */
    @Override
    public String toString() {
        return "Peluquero{" +
                "id=" + id_peluquero +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}