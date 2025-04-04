package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<AppModel> {

    public CategoryAdapter(Context context) {
        super(context, 0, getData());
    }

    private static ArrayList<AppModel> getData() {
        ArrayList<AppModel> list = new ArrayList<>();
        list.add(new AppModel(0, "Apps de Rutas", "GPS", "Explora mapas y caminos de aventura."));
        list.add(new AppModel(0, "Accesorios", "Mecánica", "Todo lo que tu moto necesita."));
        list.add(new AppModel(0, "Viajes Moteros", "Turismo", "Planes para salir a rodar."));
        return list;
    }

    static class ViewHolder {
        TextView textCategory;
        TextView textName;
        TextView textSummary;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppModel model = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_category, parent, false);
            holder = new ViewHolder();
            holder.textCategory = convertView.findViewById(R.id.textCategory);
            holder.textName = convertView.findViewById(R.id.textName);
            holder.textSummary = convertView.findViewById(R.id.textSummary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textCategory.setText(model.getRights()); // usamos "rights" como categoría
        holder.textName.setText(model.getName());
        holder.textSummary.setText(model.getSummary());

        return convertView;
    }
}
