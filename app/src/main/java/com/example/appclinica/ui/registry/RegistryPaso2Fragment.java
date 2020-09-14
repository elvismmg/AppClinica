package com.example.appclinica.ui.registry;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.Medico;
import com.example.appclinica.ui.report.CitasAdapter;
import com.example.appclinica.ui.report.ReportViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistryPaso2Fragment extends Fragment {

    private RegistryPaso2ViewModel mViewModel;
    private List<Medico> medicoList = new ArrayList<>();
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private MedicoAdapter mAdapter1;
    private MedicoAdapter mAdapter2;
    private NavController navController;

    public static RegistryPaso2Fragment newInstance() {
        return new RegistryPaso2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso2, container, false);

        navController = NavHostFragment.findNavController(this);

        //--Asignamos las propiedades del control
        TabHost pestaña = (TabHost)root.findViewById(R.id.tabHost);
        pestaña.setup();

        //Pestaña 1
        TabHost.TabSpec spec = pestaña.newTabSpec("Pestaña Uno");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Médico");
        pestaña.addTab(spec);

        //Pestaña 2
        spec = pestaña.newTabSpec("Pestaña Dos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Fecha");
        pestaña.addTab(spec);

        final Button button1 = root.findViewById(R.id.btnRegresar2);
        final Button button2 = root.findViewById(R.id.btnContinuar2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry3);
            }
        });

        recyclerView1 = (RecyclerView) root.findViewById(R.id.recycler_view_medico1);
        mAdapter1 = new MedicoAdapter(this.getContext(), medicoList, this);
        //recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_view_medico2);
        //mAdapter2 = new MedicoAdapter(this.getContext(), medicoList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(gridLayoutManager);
        //recyclerView1.setItemAnimator(new DefaultItemAnimator());
        //recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView1.setAdapter(mAdapter1);

        /*recyclerView2.setLayoutManager(gridLayoutManager);
        //recyclerView2.setItemAnimator(new DefaultItemAnimator());
        //recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView2.setAdapter(mAdapter2);*/

        prepareMedicosData();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryPaso2ViewModel.class);
        // TODO: Use the ViewModel
    }

    private void prepareMedicosData() {
        Medico medico;
        medico = new Medico("Dr. Martin de Porras", R.drawable.doctor_male);
        medicoList.add(medico);
        medico = new Medico("Dra. María Teresa De Calcuta", R.drawable.doctor_male);
        medicoList.add(medico);
        mAdapter1.notifyDataSetChanged();
        //mAdapter2.notifyDataSetChanged();
    }

}