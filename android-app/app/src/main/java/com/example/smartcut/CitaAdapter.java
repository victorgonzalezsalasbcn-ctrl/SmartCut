package com.example.smartcut;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.ViewHolder> {

    private List<String> franjasHorarias;
    private List<Cita> citasReales;

    public CitaAdapter(List<String> franjasHorarias, List<Cita> citasReales) {
        this.franjasHorarias = franjasHorarias;
        this.citasReales = citasReales;
    }

    public void actualizarCitas(List<Cita> nuevasCitas) {
        this.citasReales = nuevasCitas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String horaFranja = franjasHorarias.get(position);
        holder.txtHoraCita.setText(horaFranja);

        Cita citaEncontrada = null;
        for (Cita c : citasReales) {
            if (c.getFechaHora() != null && c.getFechaHora().contains(horaFranja)) {
                citaEncontrada = c;
                break;
            }
        }

        if (citaEncontrada != null) {
            String nombre = (citaEncontrada.getCliente() != null) ? citaEncontrada.getCliente().getNombre() : "Ocupado";
            holder.txtNombreClienteCita.setText(nombre);
            holder.txtNombreClienteCita.setTextColor(Color.WHITE);
            holder.itemView.setBackgroundColor(Color.parseColor("#44FF0000"));
        } else {
            holder.txtNombreClienteCita.setText("Disponible");
            holder.txtNombreClienteCita.setTextColor(Color.GRAY);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        // OBLIGATORIO: franjasHorarias.size() para que salgan las 24 filas
        return franjasHorarias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHoraCita, txtNombreClienteCita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // IDs EXACTOS DE TU XML
            txtHoraCita = itemView.findViewById(R.id.txtHoraCita);
            txtNombreClienteCita = itemView.findViewById(R.id.txtNombreClienteCita);
        }
    }
}