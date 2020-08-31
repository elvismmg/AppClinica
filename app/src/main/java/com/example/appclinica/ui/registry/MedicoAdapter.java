package com.example.appclinica.ui.registry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.Medico;
import com.example.appclinica.ui.report.DetailFragment;
import com.example.appclinica.ui.report.ReportFragment;

import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MyViewHolder> {

    private List<Medico> medicoList;
    private LayoutInflater inflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView gridIcon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtMedico);
            gridIcon = (ImageView) view.findViewById(R.id.imgMedico);
        }
    }

    public MedicoAdapter(List<Medico> medicoList) {
        this.medicoList = medicoList;
    }
    public MedicoAdapter(Context ctx, List<Medico> medicoList) {
        this.medicoList = medicoList;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medico_fila, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Medico medico = medicoList.get(position);

        holder.title.setText(medico.getNombre());
        holder.gridIcon.setImageResource(medico.getImagen());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),medico.getNombre(),Toast.LENGTH_SHORT).show();
            }


        });

    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }



}