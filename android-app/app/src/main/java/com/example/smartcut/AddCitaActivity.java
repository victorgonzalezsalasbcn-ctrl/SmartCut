package com.example.smartcut;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCitaActivity extends AppCompatActivity {

    private Long idPeluquero, idServicio; // Añado el idServicio que también viene del Calendario
    private String fecha, hora;
    private EditText etBuscarCliente, etObservaciones;
    private TextView tvResumenCita, tvClienteSeleccionado;
    private RecyclerView rvResultadosBusqueda;
    private Cliente clienteSeleccionado;
    private List<Cliente> listaCompletaClientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Esta es la pantalla de finalizar reserva, uso mis colores oscuros y dorados */
        setContentView(R.layout.activity_add_cita);

        /* Saco los datos que me han pasado del calendario */
        idPeluquero = getIntent().getLongExtra("ID_PELUQUERO", -1L);
        idServicio = getIntent().getLongExtra("ID_SERVICIO", -1L); // Importante recuperarlo para la cita
        fecha = getIntent().getStringExtra("FECHA");
        hora = getIntent().getStringExtra("HORA");

        /* Localizo todos mis elementos del XML por su ID */
        tvResumenCita = findViewById(R.id.tvResumenCita);
        tvClienteSeleccionado = findViewById(R.id.tvClienteSeleccionado);
        etBuscarCliente = findViewById(R.id.etBuscarCliente);
        etObservaciones = findViewById(R.id.etObservaciones);
        rvResultadosBusqueda = findViewById(R.id.rvResultadosBusqueda);
        Button btnConfirmarCita = findViewById(R.id.btnConfirmarCita);

        /* Pongo el resumen arriba para que el usuario sepa qué está reservando */
        if (tvResumenCita != null) tvResumenCita.setText("Reserva: " + fecha + " a las " + hora);

        /* Traigo todos los clientes de mi API para poder buscarlos luego sin peticiones extra */
        obtenerClientesDeServidor();

        /* Cada vez que escribo en el buscador, filtro la lista de clientes automáticamente */
        etBuscarCliente.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarResultados(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        /* Al confirmar, si he elegido cliente, mando la cita a la DB y me largo al inicio de la app */
        btnConfirmarCita.setOnClickListener(v -> {
            if (clienteSeleccionado == null) {
                Toast.makeText(this, "Debo elegir un cliente de la lista para continuar", Toast.LENGTH_SHORT).show();
            } else {
                /* EJECUTO LA ACCIÓN: Guardo la cita en el servidor */
                guardarCitaEnServidor();
            }
        });
    }

    private void obtenerClientesDeServidor() {
        RetrofitClient.getApiService().obtenerClientes().enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCompletaClientes = response.body();
                }
            }
            @Override public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("MIFEC", "Error al traer clientes: " + t.getMessage());
            }
        });
    }

    private void filtrarResultados(String texto) {
        if (texto.isEmpty()) {
            rvResultadosBusqueda.setVisibility(View.GONE);
            return;
        }
        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : listaCompletaClientes) {
            if (c.getNombre().toLowerCase().contains(texto.toLowerCase()) || c.getTelefono().contains(texto)) {
                filtrados.add(c);
            }
        }

        if (!filtrados.isEmpty()) {
            rvResultadosBusqueda.setVisibility(View.VISIBLE);
            rvResultadosBusqueda.setLayoutManager(new LinearLayoutManager(this));
            /* Uso mi ClienteAdapter pasándole el comportamiento del click para seleccionar */
            ClienteAdapter adapter = new ClienteAdapter(filtrados, c -> {
                clienteSeleccionado = c;
                tvClienteSeleccionado.setText("Cita para: " + c.getNombre());
                etBuscarCliente.setText(c.getNombre());
                rvResultadosBusqueda.setVisibility(View.GONE);
            });
            rvResultadosBusqueda.setAdapter(adapter);
        } else {
            rvResultadosBusqueda.setVisibility(View.GONE);
        }
    }

    /* Método para enviar la cita final al servidor usando los datos recopilados */
    private void guardarCitaEnServidor() {
        Cita nuevaCita = new Cita();
        nuevaCita.setCliente(clienteSeleccionado);

        Peluquero p = new Peluquero(); p.setId(idPeluquero.intValue());
        nuevaCita.setPeluquero(p);

        Servicio s = new Servicio(); s.setId(idServicio.intValue());
        nuevaCita.setServicio(s);

        nuevaCita.setFechaHora(fecha + " " + hora);
        nuevaCita.setObservaciones(etObservaciones.getText().toString());

        RetrofitClient.getApiService().insertarCita(nuevaCita).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddCitaActivity.this, "Cita confirmada correctamente", Toast.LENGTH_SHORT).show();

                    /* Limpio el historial de pantallas y vuelvo a la pantalla principal */
                    Intent intent = new Intent(AddCitaActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddCitaActivity.this, "Error al guardar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MIFEC", "Error de red: " + t.getMessage());
                Toast.makeText(AddCitaActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}