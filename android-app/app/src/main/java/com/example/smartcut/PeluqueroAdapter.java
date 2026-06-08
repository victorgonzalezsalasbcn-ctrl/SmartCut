package com.example.smartcut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adaptador para gestionar la lista de barberos en el RecyclerView.
 */
public class PeluqueroAdapter extends RecyclerView.Adapter<PeluqueroAdapter.ViewHolder> {

    private List<Peluquero> lista;
    private OnItemClickListener listener;

    // Interface para comunicar el clic desde el adaptador hacia la Activity
    public interface OnItemClickListener {
        void onItemClick(Peluquero peluquero);
    }

    public PeluqueroAdapter(List<Peluquero> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño de la tarjeta premium (item_peluquero.xml)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peluquero, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Peluquero p = lista.get(position);

        // Mapeamos los datos del modelo a la vista
        holder.nombre.setText(p.getNombre());

        // "Si el peluquero tiene especialidad, la mostramos; si no, ponemos un texto por defecto"
        if (p.getEspecialidad() != null && !p.getEspecialidad().isEmpty()) {
            holder.especialidad.setText(p.getEspecialidad());
        } else {
            holder.especialidad.setText("Barbero Senior");
        }

        // Lógica de la inicial
        if (p.getNombre() != null && !p.getNombre().isEmpty()) {
            holder.inicial.setText(p.getNombre().substring(0, 1).toUpperCase());
        }

        // Listener para que al clicar la tarjeta pase a la siguiente pantalla
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, inicial, especialidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombrePeluquero);
            inicial = itemView.findViewById(R.id.txtInicialPeluquero);

            especialidad = itemView.findViewById(R.id.txtEspecialidadPeluquero);
        }
    }
}