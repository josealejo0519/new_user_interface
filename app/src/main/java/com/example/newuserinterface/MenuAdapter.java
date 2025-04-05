package com.example.newuserinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<AppModel> {

    public MenuAdapter(Context context) {
        super(context, 0, getData());
    }

    // Datos de ejemplo
    private static ArrayList<AppModel> getData() {
        ArrayList<AppModel> list = new ArrayList<>();
        list.add(new AppModel(R.drawable.fondo22, "Explora Rutas", "", ""));
        list.add(new AppModel(R.drawable.fondo22, "Tu Moto", "", ""));
        list.add(new AppModel(R.drawable.fondo22, "Comunidad Motera", "", ""));
        return list;
    }

    static class ViewHolder {
        ImageView image;
        TextView textTitle;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        AppModel model = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_menu, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.textTitle = convertView.findViewById(R.id.textTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        assert model != null;
        holder.image.setImageResource(model.getImage());
        holder.textTitle.setText(model.getName());

        return convertView;
    }
}
