package com.example.appclinica.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appclinica.R;
import com.example.appclinica.ui.user.ChangeUserFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        Bundle bundle = getArguments();


        SimpleDateFormat fechaNacimientoParse = new SimpleDateFormat("yyyy-MM-dd");
        int edad = 0;
        try {
            Date fechaNacimiento = fechaNacimientoParse.parse(bundle.getString("pacienteFechaNacimiento"));

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaNacimiento);
            edad = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);

            fechaNacimiento.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        TextView etGenero = (TextView) root.findViewById(R.id.home_etGenero);
        TextView etEdad = (TextView) root.findViewById(R.id.home_etEdad);
        TextView etPeso = (TextView) root.findViewById(R.id.home_etPeso);
        TextView etAltura = (TextView) root.findViewById(R.id.home_etAltura);
        TextView etTipoSangre = (TextView) root.findViewById(R.id.home_etTipoSangre);

        String genero = "";
        if(bundle.getString("pacienteGenero") == "M") {
            genero ="Masculino";
        }
        else {
            genero ="Femenino";
        }

        etGenero.setText(genero);
        etEdad.setText(String.valueOf(edad));
        etPeso.setText(String.valueOf(bundle.getDouble("pacientePeso")));
        etAltura.setText(String.valueOf(bundle.getDouble("pacienteAltura")));
        etTipoSangre.setText(bundle.getString("pacienteTipoSangre"));


        Button btnMyInfo = (Button) root.findViewById(R.id.btnMyInfo);
        btnMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.layout_home, new ChangeUserFragment()).commit();
            }
        });

        return root;
    }

    //    public void misDatosOnClick(View view) {
//        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.replace(R.id.layout_home, new ChangeUserFragment()).commit();
//
//    }
}