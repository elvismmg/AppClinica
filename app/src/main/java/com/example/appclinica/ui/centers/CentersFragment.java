package com.example.appclinica.ui.centers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclinica.R;
import com.example.appclinica.ui.models.CenterModel;

import java.util.ArrayList;
import java.util.List;

public class CentersFragment extends Fragment {

    private CentersViewModel centersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        centersViewModel =
                ViewModelProviders.of(this).get(CentersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_centers, container, false);
//        final TextView textView = root.findViewById(R.id.centers_row_teCenterName);
//        centersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        RecyclerView rvCenters = (RecyclerView)root.findViewById(R.id.centers_rvCenters);
        rvCenters.setLayoutManager(new LinearLayoutManager(getActivity()));
        CentersAdapter centersAdapter = new CentersAdapter(GetCenters(), getContext());
        centersAdapter.notifyDataSetChanged();
        rvCenters.setAdapter(centersAdapter);
        rvCenters.setItemAnimator(new DefaultItemAnimator());


        return root;
    }


    private List<CenterModel> GetCenters() {
        List<CenterModel> _list = new ArrayList<>();
        _list.add(new CenterModel("Sede Surco", "Av. Caminos del Inca 752, Santiago de Surco", "Lima", "-12.1177673", "-76.9918108"));
        _list.add(new CenterModel("Sede Monterrico", "Av. Primavera 2540, Monterrico", "Lima", "-12.1041753", "-76.9633614"));
        _list.add(new CenterModel("Sede Miraflores", "Av. Alfredo Benavides 503, Miraflores", "Lima", "-12.1254878", "-77.0283469"));
        _list.add(new CenterModel("Sede San Borja 1", "Av. Guardia Civil 421, San Borja", "Lima", "-12.1044037", "-77.0190864"));
        _list.add(new CenterModel("Sede San Borja 2", "Av. Javier Prado Este 4520, San Borja", "Lima", "-12.091353", "-77.0167166"));
        return _list;
    }
}