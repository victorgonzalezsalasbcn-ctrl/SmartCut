package com.example.smartcut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionServicioActivity extends AppCompatActivity {

    private RecyclerView rvServicios;
    private ServicioAdapter adapter;
    private List<Servicio> listaServicios = new ArrayList<>();
    private Long idPeluquero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_seleccion_servicio);

        // Pillo el ID del peluquero que viene de la pantalla anterior
        idPeluquero = getIntent().getLongExtra("ID_PELUQUERO", -1L);
        Log.d("SMARTCUT_DEBUG", "ID Peluquero recibido: " + idPeluquero);

        ImageButton btnVolver = findViewById(R.id.btnVolver);
        if (btnVolver != null) btnVolver.setOnClickListener(v -> finish());

        rvServicios = findViewById(R.id.rvServicios);
        rvServicios.setLayoutManager(new LinearLayoutManager(this));

        // CONFIGURACIÓN DEL CLIC: "Si clico, quiero ir a la siguiente pantalla"
        adapter = new ServicioAdapter(listaServicios, servicio -> {
            Log.d("SMARTCUT_DEBUG", "HAS CLICADO EN: " + servicio.getNombre() + " (ID: " + servicio.getId() + ")");

            if (servicio.getId() != null) {
                Intent i = new Intent(SeleccionServicioActivity.this, CalendarioActivity.class);
                i.putExtra("ID_PELUQUERO", idPeluquero);
                i.putExtra("ID_SERVICIO", Long.valueOf(servicio.getId()));
                startActivity(i);
                Log.d("SMARTCUT_DEBUG", "Intent lanzado hacia CalendarioActivity");
            } else {
                Log.e("SMARTCUT_DEBUG", "El ID del servicio es NULO, por eso no cambia de pantalla");
                Toast.makeText(this, "Error: Servicio sin ID", Toast.LENGTH_SHORT).show();
            }
        });

        rvServicios.setAdapter(adapter);

        cargarServiciosDesdeMySQL();
    }

    private void cargarServiciosDesdeMySQL() {
        RetrofitClient.getApiService().obtenerServicios().enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaServicios.clear();
                    listaServicios.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("SMARTCUT_DEBUG", "Servicios pintados en lista: " + listaServicios.size());
                } else {
                    Log.e("SMARTCUT_DEBUG", "Error de respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                Log.e("SMARTCUT_DEBUG", "Error de red: " + t.getMessage());
            }
        });
    }
}