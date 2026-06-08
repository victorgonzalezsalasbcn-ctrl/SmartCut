package com.example.smartcut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;
    private OnClienteClickListener listener;

    /* Interfaz para gestionar el click desde el Activity */
    public interface OnClienteClickListener {
        void onClienteClick(Cliente cliente);
    }

    /* Constructor que pide la lista y el gestor de clicks */
    public ClienteAdapter(List<Cliente> listaClientes, OnClienteClickListener listener) {
        this.listaClientes = listaClientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Inflamos el diseño de la fila individual del cliente */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);

        /* Pintamos los datos en los TextViews */
        holder.txtNombre.setText(cliente.getNombre());
        holder.txtTelefono.setText(cliente.getTelefono());

        /* Si el usuario toca la fila, avisamos a la pantalla que corresponda */
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClienteClick(cliente);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClientes != null ? listaClientes.size() : 0;
    }

    /* Clase interna para localizar los elementos del XML item_cliente */
    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTelefono;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            /* IDs sincronizados con tu archivo item_cliente.xml */
            txtNombre = itemView.findViewById(R.id.txtNombreCliente);
            txtTelefono = itemView.findViewById(R.id.txtTelefonoCliente);
        }
    }
}