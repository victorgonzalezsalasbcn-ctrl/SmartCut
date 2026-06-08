package com.example.smartcut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioActivity extends AppCompatActivity {

    private Long idPeluquero, idServicio;
    private String fechaSeleccionada;
    private RecyclerView rvHoras;
    private HoraAdapter horaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_calendario);

        idPeluquero = getIntent().getLongExtra("ID_PELUQUERO", -1L);
        idServicio = getIntent().getLongExtra("ID_SERVICIO", -1L);

        ImageButton btnVolver = findViewById(R.id.btnVolverCalendario);
        CalendarView calendarView = findViewById(R.id.calendarView);
        rvHoras = findViewById(R.id.rvHoras);

        btnVolver.setOnClickListener(v -> finish());
        rvHoras.setLayoutManager(new GridLayoutManager(this, 3));

        // Inicializo con la fecha de hoy para que no salga vacío al entrar
        java.util.Calendar c = java.util.Calendar.getInstance();
        actualizarFechaSeleccionada(c.get(java.util.Calendar.YEAR), c.get(java.util.Calendar.MONTH), c.get(java.util.Calendar.DAY_OF_MONTH));

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            actualizarFechaSeleccionada(year, month, dayOfMonth);
            obtenerCitasYMostrarHorarios();
        });

        obtenerCitasYMostrarHorarios();
    }

    private void actualizarFechaSeleccionada(int year, int month, int dayOfMonth) {
        String mes = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
        String dia = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
        fechaSeleccionada = year + "-" + mes + "-" + dia;
    }

    private void obtenerCitasYMostrarHorarios() {
        Log.d("MIFEC", "Pidiendo horas ocupadas para el peluquero " + idPeluquero + " el día " + fechaSeleccionada);

        // Llamo a la nueva ruta /api/citas/ocupadas
        RetrofitClient.getApiService().obtenerHorasOcupadas(idPeluquero, fechaSeleccionada)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        List<String> horasOcupadas = (response.isSuccessful() && response.body() != null)
                                ? response.body() : new ArrayList<>();

                        Log.d("MIFEC", "Horas ocupadas recibidas: " + horasOcupadas.size());
                        generarHorariosYMostrar(horasOcupadas);
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Log.e("MIFEC", "Fallo de conexión: " + t.getMessage());
                        generarHorariosYMostrar(new ArrayList<>());
                    }
                });
    }

    private void generarHorariosYMostrar(List<String> ocupadas) {
        List<String> listaHoras = new ArrayList<>();
        String[] tramos = {"09", "10", "11", "12", "13", "16", "17", "18", "19", "20"};
        for (String h : tramos) {
            listaHoras.add(h + ":00");
            listaHoras.add(h + ":30");
        }

        horaAdapter = new HoraAdapter(listaHoras, ocupadas, hora -> {
            Intent intent = new Intent(CalendarioActivity.this, AddCitaActivity.class);
            intent.putExtra("ID_PELUQUERO", idPeluquero);
            intent.putExtra("ID_SERVICIO", idServicio);
            intent.putExtra("FECHA", fechaSeleccionada);
            intent.putExtra("HORA", hora + ":00");
            startActivity(intent);
        });

        rvHoras.setAdapter(horaAdapter);
    }
}