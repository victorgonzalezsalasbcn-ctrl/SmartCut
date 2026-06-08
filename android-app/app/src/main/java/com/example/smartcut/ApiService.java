package com.example.smartcut;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // --- CLIENTES ---
    @GET("api/clientes")
    Call<List<Cliente>> obtenerClientes();

    @POST("api/clientes")
    Call<Cliente> insertarCliente(@Body Cliente cliente);


    // --- PELUQUEROS ---
    @GET("api/peluqueros")
    Call<List<Peluquero>> obtenerPeluqueros();


    // --- SERVICIOS ---
    @GET("api/servicios")
    Call<List<Servicio>> obtenerServicios();


    // --- CITAS ---
    @GET("api/citas")
    Call<List<Cita>> obtenerCitas();

    // Este es el que uso en el CalendarioActivity para confirmar
    @POST("api/citas")
    Call<Void> insertarCita(@Body Cita cita);

    // Para filtrar disponibilidad en el servidor directamente
    @GET("api/citas/ocupadas")
    Call<List<String>> obtenerHorasOcupadas(
            @Query("idPeluquero") Long idPeluquero,
            @Query("fecha") String fecha
    );
}