package com.example.appclinica.ui.registry;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appclinica.R;

public class RegistryPaso3Fragment extends Fragment {

    private RegistryPaso3ViewModel mViewModel;

    public static RegistryPaso3Fragment newInstance() {
        return new RegistryPaso3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso3, container, false);

        final Button button1 = root.findViewById(R.id.btnRegresar3);
        final Button button2 = root.findViewById(R.id.btnContinuar3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_registry_paso1, new RegistryPaso2Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_registry_paso1, new RegistryPaso4Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryPaso3ViewModel.class);
        // TODO: Use the ViewModel
    }

}