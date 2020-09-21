package com.example.appclinica.ui.registry;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CitaMemory;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.Medico;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistryPaso4Fragment extends Fragment {

    private RegistryPaso4ViewModel mViewModel;
    private NavController navController;

    private static CitaMemory citaMemory;

    public static RegistryPaso4Fragment newInstance() {
        return new RegistryPaso4Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso4, container, false);

        navController = NavHostFragment.findNavController(this);

        final Bundle datosRecuperados = getArguments();
        citaMemory = RegistryPaso1Fragment.recuperarDatos(datosRecuperados);

        final Button button1 = root.findViewById(R.id.btnRegresar4);
        final Button button2 = root.findViewById(R.id.btnContinuar4);
        final Button button3 = root.findViewById(R.id.btnPago);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry3, datosRecuperados);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareMedicosData1(view);
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

    private void prepareMedicosData1(View root) {

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        String seguro = "";
        seguro = citaMemory.getSeguro();
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("IdMedico", citaMemory.getMedico());
            jsonobject.put("IdPaciente", citaMemory.getPaciente());
            jsonobject.put("IdSede", citaMemory.getSede());
            jsonobject.put("IdConsultorio", citaMemory.getConsultorio());
            jsonobject.put("IdEmpresaSeguro", seguro);
            jsonobject.put("Numero", "");
            jsonobject.put("Tipo", citaMemory.getTipoCita());
            jsonobject.put("IdProgramacionAtencion", citaMemory.getHorario());
            jsonobject.put("Fecha", citaMemory.getFecha());
            jsonobject.put("Hora", citaMemory.getHora());
            jsonobject.put("ImagenEvidenciaA", citaMemory.getImagen1());
            jsonobject.put("ImagenEvidenciaB", citaMemory.getImagen2());
            jsonobject.put("ImagenEvidenciaC", citaMemory.getImagen3());
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constants.WSUrlInsertarCita, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.i("======>", response.toString());

                            if (response.length() == 0){
                                Toast.makeText(getContext(), R.string.msgInsertUpdateWSError2, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }else {
                                navController.navigate(R.id.bottom_registry);
                                Toast.makeText(getContext(),"Cita Reservada correctamente",Toast.LENGTH_LONG).show();
                                //Toast.makeText(getContext(), R.string.msgInsertUpdateWSError2, Toast.LENGTH_LONG).show();
                            }

                        progress.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("WS======>", error.toString());
                        progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



}