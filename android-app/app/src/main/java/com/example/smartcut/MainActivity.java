package com.example.smartcut;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVerAgenda, btnIrAAddCliente, btnIrAAddCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazo los botones de la pantalla principal
        btnVerAgenda = findViewById(R.id.btnVerAgenda);
        btnIrAAddCliente = findViewById(R.id.btnIrAAddCliente);
        btnIrAAddCita = findViewById(R.id.btnIrAAddCita);

        // --- LÓGICA DE NAVEGACIÓN ---

        // Para ver la agenda, primero elijo qué peluquero quiero consultar
        btnVerAgenda.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SeleccionPeluqueroActivity.class);
            intent.putExtra("MODO_GESTION", true); // Truco para que la pantalla sepa que solo quiero ver citas
            startActivity(intent);
        });

        // Registrar un cliente nuevo en la base de datos
        btnIrAAddCliente.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddClienteActivity.class));
        });

        // Flujo para crear una cita desde cero (el buscador/reserva)
        btnIrAAddCita.setOnClickListener(v -> {
            // Aquí empiezo el proceso de reserva de toda la vida
            Intent intent = new Intent(MainActivity.this, SeleccionPeluqueroActivity.class);
            intent.putExtra("MODO_GESTION", false);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Paso de refrescar listas aquí para que la app vaya más fluida y no pete
    }
}