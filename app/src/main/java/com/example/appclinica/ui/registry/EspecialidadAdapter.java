package com.example.appclinica.ui.registry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appclinica.ui.dao.Especialidad;
import com.example.appclinica.ui.dao.Sede;

import java.util.List;

public class EspecialidadAdapter extends ArrayAdapter<Especialidad> {

    private Context context;
    private List<Especialidad> listEspecialidad;

    public EspecialidadAdapter(Context context, int textViewResourceId, List<Especialidad> listEspecialidad) {
        super(context, textViewResourceId, listEspecialidad);
        this.context = context;
        this.listEspecialidad = listEspecialidad;
    }

    public int getCount(){
        return listEspecialidad.size();
    }

    public Especialidad getItem(int position){
        return listEspecialidad.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        //label.setTypeface(null, Typeface.BOLD);
        label.setText(listEspecialidad.get(position).getNombre());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        label.setText(listEspecialidad.get(position).getNombre());
        return label;
    }

}
