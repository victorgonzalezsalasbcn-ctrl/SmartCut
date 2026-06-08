package com.peluqueria.gestion_citas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peluqueria.gestion_citas.model.Cita;
import com.peluqueria.gestion_citas.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    /**
     * Devuelve una lista de horas (strings) que ya están ocupadas por un peluquero.
     * Si una cita dura 60 min, devolverá dos franjas de 30 min.
     */
    public List<String> obtenerHorasOcupadas(Integer idPeluquero, LocalDate fecha) {
        // Buscamos las citas del peluquero para ese día
        
        List<Cita> citas = citaRepository.findByPeluqueroIdAndFechaHoraBetween(
            idPeluquero, 
            fecha.atStartOfDay(), 
            fecha.atTime(LocalTime.MAX)
        );

        List<String> horasBloqueadas = new ArrayList<>();

        for (Cita cita : citas) {
            LocalTime horaInicio = cita.getFechaHora().toLocalTime();
            
            Integer duracionTotal = cita.getServicio().getDuracionMin();

            // Calculamos cuántos bloques de 30 minutos ocupa el servicio
            int numBloques = duracionTotal / 30;

            for (int i = 0; i < numBloques; i++) {
                LocalTime bloqueOcupado = horaInicio.plusMinutes(i * 30);
                horasBloqueadas.add(bloqueOcupado.toString());
            }
        }

        return horasBloqueadas;
    }

    public Cita guardar(Cita cita) {
        
        return citaRepository.save(cita);
    }

    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }
}