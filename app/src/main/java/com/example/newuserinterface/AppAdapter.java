package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context context;
    private List<AppModel> appList;

    public AppAdapter(Context context, List<AppModel> appList) {
        this.context = context;
        this.appList = appList;
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppAdapter.ViewHolder holder, int position) {
        AppModel ingreso = appList.get(position);

        holder.txtPlaca.setText("Placa: " + ingreso.getPlaca());
        holder.txtTipoVehiculo.setText("Tipo: " + ingreso.getTipoVehiculo());
        holder.txtFechaIngreso.setText("Ingreso: " + ingreso.getFechaHoraIngreso());
        holder.txtCelda.setText("Celda: " + ingreso.getCelda());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPlaca, txtTipoVehiculo, txtFechaIngreso, txtCelda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlaca = itemView.findViewById(R.id.txtPlaca);
            txtTipoVehiculo = itemView.findViewById(R.id.txtTipoVehiculo);
            txtFechaIngreso = itemView.findViewById(R.id.txtFechaIngreso);
            txtCelda = itemView.findViewById(R.id.txtCelda);
        }
    }
}


