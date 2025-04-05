package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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

        holder.textCategory.setText(model.getCategory());
        holder.textName.setText(model.getName());
        holder.textSummary.setText(model.getSummary());

        return convertView;
    }
}
