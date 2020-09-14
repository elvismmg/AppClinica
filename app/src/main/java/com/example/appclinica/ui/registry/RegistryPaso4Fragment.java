package com.example.appclinica.ui.registry;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appclinica.R;

public class RegistryPaso4Fragment extends Fragment {

    private RegistryPaso4ViewModel mViewModel;
    private NavController navController;

    public static RegistryPaso4Fragment newInstance() {
        return new RegistryPaso4Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso4, container, false);

        navController = NavHostFragment.findNavController(this);

        final Button button1 = root.findViewById(R.id.btnRegresar4);
        final Button button2 = root.findViewById(R.id.btnContinuar4);
        final Button button3 = root.findViewById(R.id.btnPago);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry3);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry);

                Toast.makeText(view.getContext(),"Cita Reservada correctamente",Toast.LENGTH_LONG).show();

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Pasarela de Pagos en Proceso...",Toast.LENGTH_LONG).show();
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryPaso4ViewModel.class);
        // TODO: Use the ViewModel
    }

}