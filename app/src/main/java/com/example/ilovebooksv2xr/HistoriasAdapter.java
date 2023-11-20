package com.example.ilovebooksv2xr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class HistoriasAdapter extends ArrayAdapter<Historias> {

    private List<Historias> historiasList;
    private Context context;

    public HistoriasAdapter(Context context, List<Historias> historiasList) {
        super(context, 0, historiasList);
        this.context = context;
        this.historiasList = historiasList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
        }

        Historias currentHistoria = historiasList.get(position);

        TextView titleTextView = listItemView.findViewById(android.R.id.text1);
        titleTextView.setText(currentHistoria.getTitulo());

        return listItemView;
    }
}

