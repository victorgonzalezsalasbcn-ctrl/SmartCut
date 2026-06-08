package com.example.smartcut;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClienteActivity extends AppCompatActivity {

    // Mis inputs y el botón de la pantalla
    private EditText etNombre, etTelefono;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cliente);

        // Para que la flecha de arriba no me cierre la app entera
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nuevo Cliente");
        }

        // Engancho las variables con los ID del layout
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);

        // El listener para que cuando le dé, intente guardar
        btnGuardar.setOnClickListener(view -> {
            guardarCliente();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void guardarCliente() {
        // Quito espacios raros de lo que escribo
        String nombreStr = etNombre.getText().toString().trim();
        String telfStr = etTelefono.getText().toString().trim();

        // Pongo un email cualquiera para que la validación del VSCode no me eche atrás
        String emailTemporal = "cliente@correo.com";

        // Si falta el nombre o el teléfono paso de seguir
        if (nombreStr.isEmpty() || telfStr.isEmpty()) {
            Toast.makeText(this, "Tengo que rellenar los dos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // ID en null para que el MySQL le asigne el suyo solo
        Cliente nuevoCliente = new Cliente(null, nombreStr, telfStr, emailTemporal);

        // Lanzo la petición al servidor
        RetrofitClient.getApiService().insertarCliente(nuevoCliente).enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    // Si entra aquí es que ya está en la base de datos
                    Toast.makeText(AddClienteActivity.this, "¡Guardado!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Si sale esto es que el server me ha rechazado el JSON por algo
                    Log.e("DEPURACION", "Error del server: " + response.code());
                    Toast.makeText(AddClienteActivity.this, "Error " + response.code() + ": Mira el Logcat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                // Si sale esto es que la IP está mal o el server está apagado
                Log.e("DEPURACION", "Fallo de conexión: " + t.getMessage());
                Toast.makeText(AddClienteActivity.this, "No conecta con el server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}