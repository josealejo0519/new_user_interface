package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends ArrayAdapter<AppModel> {

    public AppAdapter(Context context, List<AppModel> apps) {
        super(context, 0, getData());
    }

    // Generar lista de ejemplo (puedes reemplazar con API o DB)
    private static ArrayList<AppModel> getData() {
        ArrayList<AppModel> list = new ArrayList<>();
        list.add(new AppModel(R.drawable.fondo22, "Ducati Adventure", "Enduro", "Explora nuevos caminos con esta bestia del off-road."));
        list.add(new AppModel(R.drawable.fondo22, "BMW GS 1200", "Dual Sport", "Viaja sin límites con la icónica GS."));
        list.add(new AppModel(R.drawable.fondo22, "Yamaha Ténéré", "Adventure", "Ligera, potente y lista para el desierto."));
        return list;
    }

    // ViewHolder para optimizar el rendimiento
    static class ViewHolder {
        ImageView image;
        TextView textName;
        TextView textRights;
        TextView textSummary;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppModel app = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_app, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.textName = convertView.findViewById(R.id.textName);
            holder.textRights = convertView.findViewById(R.id.textRights);
            holder.textSummary = convertView.findViewById(R.id.textSummary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Asignar datos
        holder.image.setImageResource(app.getImage());
        holder.textName.setText(app.getName());
        holder.textRights.setText(app.getRights());
        holder.textSummary.setText(app.getSummary());

        return convertView;
    }
}
