package com.example.smartcut;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView rvCitas;
    private CitaAdapter adapter;
    private Long idPeluquero;
    private String nombrePeluquero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_agenda);

        // 1. Recuperar datos del Intent
        idPeluquero = getIntent().getLongExtra("ID_PELUQUERO", -1L);
        nombrePeluquero = getIntent().getStringExtra("NOMBRE_PELUQUERO");

        // 2. CONFIGURACIÓN DEL BOTÓN VOLVER (ID: btnVolverAgenda)
        ImageButton btnVolver = findViewById(R.id.btnVolverAgenda);
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> finish()); // Cierra la actividad actual
        }

        // 3. Título (ID: tvTituloAgenda)
        TextView tvTitulo = findViewById(R.id.tvTituloAgenda);
        if (tvTitulo != null && nombrePeluquero != null) {
            tvTitulo.setText("AGENDA: " + nombrePeluquero.toUpperCase());
        }

        // 4. Configurar RecyclerView (ID: rvCitasAgenda)
        rvCitas = findViewById(R.id.rvCitasAgenda);
        rvCitas.setLayoutManager(new LinearLayoutManager(this));

        // 5. Generar las 24 franjas de 30 min (9:00 a 20:30)
        List<String> franjas = new ArrayList<>();
        for (int h = 9; h <= 20; h++) {
            String horaFormato = (h < 10) ? "0" + h : "" + h;
            franjas.add(horaFormato + ":00");
            franjas.add(horaFormato + ":30");
        }

        // 6. Inicializar Adaptador (Pasamos las 24 franjas)
        adapter = new CitaAdapter(franjas, new ArrayList<>());
        rvCitas.setAdapter(adapter);

        cargarCitasDelPeluquero();
    }

    private void cargarCitasDelPeluquero() {
        // Obtenemos la fecha de hoy en el formato que usa tu base de datos (yyyy-MM-dd)
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        String hoy = sdf.format(new java.util.Date());

        RetrofitClient.getApiService().obtenerCitas().enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cita> ocupadas = response.body().stream()
                            .filter(c -> {
                                // Filtro 1: Que sea el peluquero correcto
                                boolean esMismoPeluquero = c.getPeluquero() != null &&
                                        c.getPeluquero().getId_peluquero().equals(idPeluquero);

                                // Filtro 2: Que la fecha de la cita empiece por la fecha de hoy
                                // Ejemplo: Si la cita es "2026-04-19 11:30:00", empieza por "2026-04-19"
                                boolean esHoy = c.getFechaHora() != null &&
                                        c.getFechaHora().startsWith(hoy);

                                return esMismoPeluquero && esHoy;
                            })
                            .collect(Collectors.toList());

                    adapter.actualizarCitas(ocupadas);
                }
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
                Toast.makeText(AgendaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}