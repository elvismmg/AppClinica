package com.example.appclinica.ui.report;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclinica.MainActivity;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.registry.RegistryPaso2Fragment;

import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.MyViewHolder> {

    private List<Citas> citasList;
    //private Bundle mBundle;
    private Fragment fragment1;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView especialidad, estado, fecha, tipo;

        public MyViewHolder(View view) {
            super(view);
            especialidad = (TextView) view.findViewById(R.id.especialidad);
            estado = (TextView) view.findViewById(R.id.estado);
            fecha = (TextView) view.findViewById(R.id.fecha);
            tipo = (TextView) view.findViewById(R.id.tipo);
        }
    }

    public CitasAdapter(List<Citas> citasList) {
        this.citasList = citasList;
    }
    public CitasAdapter(Context mContext, List<Citas> citasList, ReportFragment fragment1) {
        this.citasList = citasList;
        this.mContext  = mContext;
        this.fragment1 = fragment1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citas_fila, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Citas citas = citasList.get(position);
        holder.especialidad.setText(citas.getEspecialidad());
        holder.estado.setText(citas.getEstado());
        holder.fecha.setText(citas.getFecha());
        holder.tipo.setText(citas.getTipo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_report, new DetailFragment()).addToBackStack(null).commit();



                //AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                FragmentTransaction transaction = fragment1.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_report, new DetailFragment() );
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(view.getContext(),citas.getEspecialidad(),Toast.LENGTH_SHORT).show();


            }


        });

    }

    @Override
    public int getItemCount() {
        return citasList.size();
    }



}