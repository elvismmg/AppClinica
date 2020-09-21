package com.example.appclinica.ui.registry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.MainActivity;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CitaMemory;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.dao.Especialidad;
import com.example.appclinica.ui.dao.Sede;
import com.example.appclinica.ui.dao.Seguro;
import com.example.appclinica.ui.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistryPaso1Fragment extends Fragment {

    private RegistryPaso1ViewModel registryPaso1ViewModel;
    private NavController navController;

    private List<Sede> sedesList = new ArrayList<>();
    private List<Especialidad> especialidadList = new ArrayList<>();
    private List<Seguro> seguroList = new ArrayList<>();

    private CitaMemory citaMemory;

    public static RegistryPaso1Fragment newInstance() {
        return new RegistryPaso1Fragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registryPaso1ViewModel =
                ViewModelProviders.of(this).get(RegistryPaso1ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_registry_paso1, container, false);
        final TextView textView = root.findViewById(R.id.text_registry);

        navController = NavHostFragment.findNavController(this);

        registryPaso1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        cargarSedes(root);
        cargarEspecialidades(root);
        cargarSeguros(root);

        final Switch switch1 = root.findViewById(R.id.switch1);
        final Spinner spinner_1 = root.findViewById(R.id.spinner_seguro);
        final TextView txtCopago = root.findViewById(R.id.txtCopago);
        final TextView lblSeguro = root.findViewById(R.id.lblSeguro);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //do stuff when Switch is ON
                    spinner_1.setVisibility(View.VISIBLE);
                    txtCopago.setVisibility(View.VISIBLE);
                    lblSeguro.setVisibility(View.VISIBLE);
                } else {
                    //do stuff when Switch if OFF
                    spinner_1.setVisibility(View.INVISIBLE);
                    txtCopago.setVisibility(View.INVISIBLE);
                    lblSeguro.setVisibility(View.INVISIBLE);
                }
            }
        });

        final Button button1 = root.findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sedesList.size() > 0 && especialidadList.size() > 0){
                    citaMemory = new CitaMemory();
                    saveDatosMemory(root);

                    Bundle datosAEnviar = new Bundle();
                    enviarDatos(datosAEnviar, citaMemory);
                    if ((citaMemory.getEspecialidad().equals(""))){
                        Toast.makeText(getContext(), "Es necesario selecionar la Especialidad.", Toast.LENGTH_LONG).show();
                    }else{
                        navController.navigate(R.id.bottom_registry2, datosAEnviar);
                    }
                }else{
                    Toast.makeText(getContext(), "Es necesario selecionar la Especialidad.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    private void saveDatosMemory(View root){
        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        String idPaciente = prefs.getAll().get("IdPaciente").toString();
        String nombrePaciente = prefs.getAll().get("NombrePaciente").toString();

        Switch switch1 = (Switch) root.findViewById(R.id.switch1);
        Spinner spinner1 = (Spinner) root.findViewById(R.id.spinner_sede);
        Spinner spinner2 = (Spinner) root.findViewById(R.id.spinner_especialidad);
        Spinner spinner3 = (Spinner) root.findViewById(R.id.spinner_seguro);
        RadioGroup radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) root.findViewById(selectedId);

        citaMemory.setPaciente(idPaciente);
        citaMemory.setPacienteNombre(nombrePaciente);
        if (radioButton.getText().toString().equals("Presencial")){
            citaMemory.setTipoCita("2");
        }else{
            citaMemory.setTipoCita("1");
        }
        citaMemory.setTipoCitaT(radioButton.getText().toString());
        citaMemory.setSede(sedesList.get(spinner1.getSelectedItemPosition()).getIdSede());
        citaMemory.setSedeT(sedesList.get(spinner1.getSelectedItemPosition()).getNombre());
        citaMemory.setEspecialidad(especialidadList.get(spinner2.getSelectedItemPosition()).getIdEspecialidad());
        citaMemory.setEspecialidadT(especialidadList.get(spinner2.getSelectedItemPosition()).getNombre());

        if (switch1.isChecked()){
            citaMemory.setSeguro(seguroList.get(spinner3.getSelectedItemPosition()).getIdEmpresaSeguro());
            citaMemory.setSeguroT(seguroList.get(spinner3.getSelectedItemPosition()).getNombre());
            citaMemory.setCopago(seguroList.get(spinner3.getSelectedItemPosition()).getCopago());
        }else{
            citaMemory.setSeguro("");
            citaMemory.setSeguroT("");
            citaMemory.setCopago("");
        }

        citaMemory.setMedico("");
        citaMemory.setNombreMedico("");
        citaMemory.setConsultorio("");
        citaMemory.setConsultorioT("");
        citaMemory.setHorario("");
        citaMemory.setFecha("");
        citaMemory.setHora("");
    }

    public void cargarSedes(View v){

        /*final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();
         */
        final Spinner spinnerSede;
        final SedeAdapter adapterSede;

        spinnerSede         = (Spinner)v.findViewById(R.id.spinner_sede);
        adapterSede         = new SedeAdapter(getContext(), android.R.layout.simple_spinner_item, sedesList);
        adapterSede.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.WSUrlGetSedes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                    }else {
                        Sede sede;
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            sede = new Sede(object.getString("IdSede"),
                                    object.getString("Nombre"),
                                    object.getString("Direccion"),
                                    object.getString("Distrito"),
                                    object.getDouble("UbicacionLatitud"),
                                    object.getDouble("UbicacionLongitud"),
                                    object.getString("FotoUrl"));
                            sedesList.add(sede);
                        }

                        spinnerSede.setAdapter(adapterSede);
                        //spinnerEspecialidad.setAdapter(adapterEspecialidad);
                        //spinnerSeguro.setAdapter(adapterSeguro);
                        //progress.dismiss();

                    }
                } catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                    //progress.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        //progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void cargarEspecialidades(View v){

        final Spinner spinnerEspecialidad;
        final EspecialidadAdapter adapterEspecialidad;

        spinnerEspecialidad = (Spinner)v.findViewById(R.id.spinner_especialidad);
        adapterEspecialidad = new EspecialidadAdapter(getContext(), android.R.layout.simple_spinner_item, especialidadList);

        adapterEspecialidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.WSUrlGetEspecilidades, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                    }else {
                        Especialidad especialidad;
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            especialidad = new Especialidad(object.getString("IdEspecialidad"),
                                    object.getString("Nombre"),
                                    object.getString("descripcion"));
                            especialidadList.add(especialidad);
                        }

                        spinnerEspecialidad.setAdapter(adapterEspecialidad);
                        //progress.dismiss();

                    }
                } catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                    //progress.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        //progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void cargarSeguros(View v){

        final Spinner spinnerSeguro;
        final TextView txtCopago = v.findViewById(R.id.txtCopago);

        final SeguroAdapter adapterSeguro;

        spinnerSeguro       = (Spinner)v.findViewById(R.id.spinner_seguro);
        adapterSeguro       = new SeguroAdapter(getContext(), android.R.layout.simple_spinner_item, seguroList);
        adapterSeguro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSeguro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                txtCopago.setText("Tu Co-Pago es S/. " + seguroList.get(position).getCopago());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.WSUrlGetSeguros, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                    }else {
                        Seguro seguro;
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            seguro = new Seguro(object.getString("IdEmpresaSeguro"),
                                    object.getString("Nombre"),
                                    object.getString("Copago"));
                            seguroList.add(seguro);
                        }

                        spinnerSeguro.setAdapter(adapterSeguro);
                        //progress.dismiss();

                    }
                } catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                    //progress.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        //progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /*String paciente, String pacienteNombre, String tipoCita, String tipoCitaT,
    String sede, String sedeT, String especialidad, String especialidadT,
    String seguro, String seguroT, String copago, String medico, String nombreMedico,
    String consultorio, String consultorioT, String horario, String fecha, String hora
     */
    static public void enviarDatos(Bundle datosAEnviar, CitaMemory citaMemory){
        //Bundle datosAEnviar = new Bundle();
        datosAEnviar.putString("paciente", citaMemory.getPaciente());
        datosAEnviar.putString("pacienteNombre", citaMemory.getPacienteNombre());
        datosAEnviar.putString("tipoCita", citaMemory.getTipoCita());
        datosAEnviar.putString("tipoCitaT", citaMemory.getTipoCitaT());
        datosAEnviar.putString("sede", citaMemory.getSede());
        datosAEnviar.putString("sedeT", citaMemory.getSedeT());
        datosAEnviar.putString("especialidad", citaMemory.getEspecialidad());
        datosAEnviar.putString("especialidadT", citaMemory.getEspecialidadT());
        datosAEnviar.putString("seguro", citaMemory.getSeguro());
        datosAEnviar.putString("seguroT", citaMemory.getSeguroT());
        datosAEnviar.putString("copago", citaMemory.getCopago());
        datosAEnviar.putString("medico", citaMemory.getMedico());
        datosAEnviar.putString("nombreMedico", citaMemory.getNombreMedico());
        datosAEnviar.putString("consultorio", citaMemory.getConsultorio());
        datosAEnviar.putString("consultorioT", citaMemory.getConsultorioT());
        datosAEnviar.putString("horario", citaMemory.getHorario());
        datosAEnviar.putString("fecha", citaMemory.getFecha());
        datosAEnviar.putString("hora", citaMemory.getHora());
    }

    static public CitaMemory recuperarDatos(Bundle datosRecuperados){
        //Bundle datosRecuperados = getArguments();
        CitaMemory citaMemory = new CitaMemory();
        citaMemory.setPaciente(datosRecuperados.getString("paciente"));
        citaMemory.setPacienteNombre(datosRecuperados.getString("pacienteNombre"));
        citaMemory.setTipoCita(datosRecuperados.getString("tipoCita"));
        citaMemory.setTipoCitaT(datosRecuperados.getString("tipoCitaT"));
        citaMemory.setSede(datosRecuperados.getString("sede"));
        citaMemory.setSedeT(datosRecuperados.getString("sedeT"));
        citaMemory.setEspecialidad(datosRecuperados.getString("especialidad"));
        citaMemory.setEspecialidadT(datosRecuperados.getString("especialidadT"));
        citaMemory.setSeguro(datosRecuperados.getString("seguro"));
        citaMemory.setSeguroT(datosRecuperados.getString("seguroT"));
        citaMemory.setCopago(datosRecuperados.getString("copago"));
        citaMemory.setMedico(datosRecuperados.getString("medico"));
        citaMemory.setNombreMedico(datosRecuperados.getString("nombreMedico"));
        citaMemory.setConsultorio(datosRecuperados.getString("consultorio"));
        citaMemory.setConsultorioT(datosRecuperados.getString("consultorioT"));
        citaMemory.setHorario(datosRecuperados.getString("horario"));
        citaMemory.setFecha(datosRecuperados.getString("fecha"));
        citaMemory.setHora(datosRecuperados.getString("hora"));

        return citaMemory;
    }

}