package com.example.appclinica.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    private ReportViewModel reportViewModel;
    private List<Citas> citasList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CitasAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        final TextView textView = root.findViewById(R.id.text_report);
        reportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        //mAdapter = new CitasAdapter(citasList);
        mAdapter = new CitasAdapter(getContext(), citasList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareCitasData();

        return root;
    }

    private void prepareCitasData() {
        Citas cita;
        cita = new Citas("Medicina Interna", "Reservado", "02/09/2020", "TeleConsulta");
        citasList.add(cita);
        cita = new Citas("Psicología", "Finalizado", "27/08/2020", "Presencial");
        citasList.add(cita);
        cita = new Citas("Psicología", "Anulado", "26/08/2020", "Presencial");
        citasList.add(cita);
        cita = new Citas("Medicina Interna", "Finalizado", "02/07/2020", "Presencial");
        citasList.add(cita);
        cita = new Citas("Medicina Interna", "Finalizado", "02/06/2020", "TeleConsulta");
        citasList.add(cita);
        cita = new Citas("Medicina Interna", "Finalizado", "02/05/2020", "TeleConsulta");
        citasList.add(cita);

        mAdapter.notifyDataSetChanged();
    }

}