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
import android.widget.TextView;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CitaMemory;

public class RegistryPaso3Fragment extends Fragment {

    private RegistryPaso3ViewModel mViewModel;
    private NavController navController;

    private static CitaMemory citaMemory;

    public static RegistryPaso3Fragment newInstance() {
        return new RegistryPaso3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso3, container, false);

        navController = NavHostFragment.findNavController(this);

        final Bundle datosRecuperados = getArguments();
        citaMemory = RegistryPaso1Fragment.recuperarDatos(datosRecuperados);

        TextView txtPaciente1 = (TextView) root.findViewById(R.id.txtPaciente1);
        TextView txtEspecialidad1 = (TextView) root.findViewById(R.id.txtEspecialidad1);
        TextView txtMedico1 = (TextView) root.findViewById(R.id.txtMedico1);
        TextView txtSede1 = (TextView) root.findViewById(R.id.txtSede1);
        TextView txtConsultorio1 = (TextView) root.findViewById(R.id.txtConsultorio1);
        TextView txtFecha1 = (TextView) root.findViewById(R.id.txtFecha1);
        TextView txtHora1 = (TextView) root.findViewById(R.id.txtHora1);
        TextView txtTipo1 = (TextView) root.findViewById(R.id.txtTipo1);

        txtPaciente1.setText(citaMemory.getPacienteNombre());
        txtEspecialidad1.setText(citaMemory.getEspecialidadT());
        txtMedico1.setText(citaMemory.getNombreMedico());
        txtSede1.setText(citaMemory.getSedeT());
        txtConsultorio1.setText(citaMemory.getConsultorioT());
        txtFecha1.setText(citaMemory.getFecha());
        txtHora1.setText(citaMemory.getHora());
        txtTipo1.setText(citaMemory.getTipoCitaT());

        final Button button1 = root.findViewById(R.id.btnRegresar3);
        final Button button2 = root.findViewById(R.id.btnContinuar3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry2, datosRecuperados);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDatosMemory(view);
                Bundle datosAEnviar = new Bundle();
                RegistryPaso1Fragment.enviarDatos(datosAEnviar, citaMemory);
                navController.navigate(R.id.bottom_registry4, datosAEnviar);
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

    private void saveDatosMemory(View root){

        citaMemory.setImagen1("");
        citaMemory.setImagen2("");
        citaMemory.setImagen3("");

    }

}