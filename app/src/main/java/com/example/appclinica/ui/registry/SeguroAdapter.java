package com.example.appclinica.ui.registry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appclinica.ui.dao.Sede;
import com.example.appclinica.ui.dao.Seguro;

import java.util.List;

public class SeguroAdapter extends ArrayAdapter<Seguro> {

    private Context context;
    private List<Seguro> listSeguro;

    public SeguroAdapter(Context context, int textViewResourceId, List<Seguro> listSeguro) {
        super(context, textViewResourceId, listSeguro);
        this.context = context;
        this.listSeguro = listSeguro;
    }

    public int getCount(){
        return listSeguro.size();
    }

    public Seguro getItem(int position){
        return listSeguro.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        //label.setTypeface(null, Typeface.BOLD);
        label.setText(listSeguro.get(position).getNombre());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(18);
        label.setText(listSeguro.get(position).getNombre());
        return label;
    }

}
