package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newuserinterface.Cliente;
import com.example.newuserinterface.Vehiculo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder> {

    private final List<Cliente> listaClientes;

    public ReporteAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ReporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reporte_cliente, parent, false);
        return new ReporteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);
        Context context = holder.itemView.getContext();

        holder.txtNombreCliente.setText("Nombre: " + cliente.getNombres() + " " + cliente.getApellidos());
        holder.txtPlacaCliente.setText("Placa: " + cliente.getPlaca());
        holder.txtTipoVehiculoCliente.setText("Tipo: " + cliente.getTipoVehiculo());

        List<Vehiculo> registros = Vehiculo.find(Vehiculo.class, "placa = ?", cliente.getPlaca());

        if (!registros.isEmpty()) {
            Vehiculo ultimo = registros.get(registros.size() - 1);

            // Formato de fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            String fechaIngreso = sdf.format(new Date(ultimo.getFechaIngreso()));
            holder.txtFechaIngreso.setText("Último ingreso: " + fechaIngreso);

            if (ultimo.getFechaSalida() > 0) {
                String fechaSalida = sdf.format(new Date(ultimo.getFechaSalida()));
                holder.txtFechaSalida.setText("Última salida: " + fechaSalida);

                long tiempoEnMilis = ultimo.getFechaSalida() - ultimo.getFechaIngreso();
                long horas = Math.max(1, tiempoEnMilis / (1000 * 60 * 60)); // mínimo 1 hora

                int valor = cliente.isEsFrecuente() ? 60000 : (int) (horas * 500);

                holder.txtEstado.setText("Valor a pagar: $" + valor);
                holder.txtEstado.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            } else {
                holder.txtFechaSalida.setText("Última salida: ---");
                holder.txtEstado.setText("Estado: En parqueadero");
                holder.txtEstado.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            }

        } else {
            holder.txtFechaIngreso.setText("Último ingreso: ---");
            holder.txtFechaSalida.setText("Última salida: ---");
            holder.txtEstado.setText("Estado: Sin registros");
            holder.txtEstado.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        }
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ReporteViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreCliente, txtPlacaCliente, txtTipoVehiculoCliente,
                txtFechaIngreso, txtFechaSalida, txtEstado;

        public ReporteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreCliente = itemView.findViewById(R.id.txtNombreCliente);
            txtPlacaCliente = itemView.findViewById(R.id.txtPlacaCliente);
            txtTipoVehiculoCliente = itemView.findViewById(R.id.txtTipoVehiculoCliente);
            txtFechaIngreso = itemView.findViewById(R.id.txtFechaIngresoCliente);
            txtFechaSalida = itemView.findViewById(R.id.txtFechaSalidaCliente);
            txtEstado = itemView.findViewById(R.id.txtEstadoCliente);
        }
    }
}


