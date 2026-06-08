package com.example.smartcut;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HoraAdapter extends RecyclerView.Adapter<HoraAdapter.ViewHolder> {

    private List<String> horas;
    private List<String> horasOcupadas; // Ahora recibo directamente los strings de horas pilladas
    private OnHoraClickListener listener;

    public interface OnHoraClickListener { void onHoraClick(String hora); }

    // Constructor para la nueva lógica: comparamos String con String directamente
    public HoraAdapter(List<String> horas, List<String> horasOcupadas, OnHoraClickListener listener) {
        this.horas = horas;
        this.horasOcupadas = horasOcupadas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hora, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String horaActual = horas.get(position); // Ejemplo: "09:30"
        holder.txtHora.setText(horaActual);

        // Si la hora que estoy pintando está en la "lista negra" que viene del servidor...
        boolean ocupada = horasOcupadas != null && horasOcupadas.contains(horaActual);

        if (ocupada) {
            // Bloqueo total si el peluquero está ocupado
            holder.txtHora.setTextColor(Color.RED);
            holder.itemView.setAlpha(0.3f);
            holder.itemView.setOnClickListener(null);
            Log.d("MIFEC", "Pintando en ROJO por estar ocupada: " + horaActual);
        } else {
            // Mi dorado SmartCut de siempre para las libres
            holder.txtHora.setTextColor(Color.parseColor("#D4AF37"));
            holder.itemView.setAlpha(1.0f);
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onHoraClick(horaActual);
            });
        }
    }

    @Override
    public int getItemCount() {
        return horas != null ? horas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHora;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHora = itemView.findViewById(R.id.txtHoraItem);
        }
    }
}