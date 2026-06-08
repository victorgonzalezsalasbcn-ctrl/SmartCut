package com.example.smartcut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionPeluqueroActivity extends AppCompatActivity {

    private RecyclerView rvPeluqueros;
    private boolean modoGestion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ocultar la barra de arriba fea de Android para que se vea Premium
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_seleccion_peluquero);

        modoGestion = getIntent().getBooleanExtra("MODO_GESTION", false);

        // Configurar el botón de volver atrás (el de la flecha/tijeras dorada)
        ImageButton btnVolver = findViewById(R.id.btnVolver);
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> onBackPressed());
        }

        TextView tvTituloCabecera = findViewById(R.id.tvTituloCabecera);
        if (tvTituloCabecera != null) {
            tvTituloCabecera.setText(modoGestion ? "GESTIÓN DE AGENDA" : "ELIGE TU EXPERTO");
        }

        rvPeluqueros = findViewById(R.id.rvPeluqueros);
        rvPeluqueros.setLayoutManager(new LinearLayoutManager(this));
        cargarPeluqueros();
    }

    private void cargarPeluqueros() {
        RetrofitClient.getApiService().obtenerPeluqueros().enqueue(new Callback<List<Peluquero>>() {
            @Override
            public void onResponse(Call<List<Peluquero>> call, Response<List<Peluquero>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // El adaptador se encarga de pintar las tarjetas oscuras
                    PeluqueroAdapter adapter = new PeluqueroAdapter(response.body(), peluquero -> {
                        Intent intent;
                        if (modoGestion) {
                            intent = new Intent(SeleccionPeluqueroActivity.this, AgendaActivity.class);
                        } else {
                            intent = new Intent(SeleccionPeluqueroActivity.this, SeleccionServicioActivity.class);
                        }
                        intent.putExtra("ID_PELUQUERO", peluquero.getId_peluquero());
                        intent.putExtra("peluquero", peluquero);
                        startActivity(intent);
                    });
                    rvPeluqueros.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Peluquero>> call, Throwable t) {
                Toast.makeText(SeleccionPeluqueroActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}