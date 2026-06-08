package com.example.smartcut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    private List<Servicio> lista;
    private OnServicioClickListener listener;

    public interface OnServicioClickListener { void onServicioClick(Servicio s); }

    public ServicioAdapter(List<Servicio> lista, OnServicioClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Servicio s = lista.get(position);
        holder.txtNombre.setText(s.getNombre());
        holder.txtPrecio.setText(s.getPrecio() + "€");

        // Paso el objeto seleccionado al listener para que la Activity sepa cuál es
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onServicioClick(s);
        });
    }

    @Override
    public int getItemCount() { return lista != null ? lista.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreServicio);
            txtPrecio = itemView.findViewById(R.id.txtPrecioServicio);
        }
    }
}