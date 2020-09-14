package com.example.appclinica.ui.registry;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.Sede;

import java.util.ArrayList;
import java.util.List;

public class SedeAdapter extends ArrayAdapter<Sede> {

    private Context context;
    private List<Sede> listSedes;

    public SedeAdapter(Context context, int textViewResourceId, List<Sede> listSedes) {
        super(context, textViewResourceId, listSedes);
        this.context = context;
        this.listSedes = listSedes;
    }

    public int getCount(){
        return listSedes.size();
    }

    public Sede getItem(int position){
        return listSedes.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        //label.setTypeface(null, Typeface.BOLD);
        label.setText(listSedes.get(position).getNombre());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        label.setText(listSedes.get(position).getNombre());
        return label;
    }

}
