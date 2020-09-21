package com.example.appclinica.ui.registry;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
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
import com.example.appclinica.ui.dao.HorarioMedico;
import com.example.appclinica.ui.dao.Medico;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;
import com.example.appclinica.ui.report.CitasAdapter;
import com.example.appclinica.ui.report.ReportViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistryPaso2Fragment extends Fragment {

    private RegistryPaso2ViewModel mViewModel;
    private List<Medico> medicoList = new ArrayList<>();
    private List<HorarioMedico> medicoHorarioList = new ArrayList<>();
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private MedicoAdapter mAdapter1;
    private MedicoAdapter mAdapter2;
    private NavController navController;
    private static CitaMemory citaMemory;

    private CalendarView calendario1;
    private CalendarView calendario2;

    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;

    public static String fechaSeleccionada;

    private TabHost tabHost;

    public static RegistryPaso2Fragment newInstance() {
        return new RegistryPaso2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_registry_paso2, container, false);

        navController = NavHostFragment.findNavController(this);

        final Bundle datosRecuperados = getArguments();
        citaMemory = RegistryPaso1Fragment.recuperarDatos(datosRecuperados);

        //--Asignamos las propiedades del control
        final TabHost pestaña = (TabHost)root.findViewById(R.id.tabHost);
        pestaña.setup();

        tabHost = pestaña;

        //Pestaña 1
        TabHost.TabSpec spec = pestaña.newTabSpec("Pestaña Uno");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Médico");
        pestaña.addTab(spec);

        recyclerView1 = (RecyclerView) root.findViewById(R.id.recycler_view_medico1);
        mAdapter1 = new MedicoAdapter(this.getContext(), medicoList, this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(gridLayoutManager1);
        recyclerView1.setAdapter(mAdapter1);

        //Pestaña 2
        spec = pestaña.newTabSpec("Pestaña Dos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Fecha");
        pestaña.addTab(spec);

        recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_view_medico2);
        mAdapter2 = new MedicoAdapter(this.getContext(), medicoList, this, root);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(gridLayoutManager2);
        recyclerView2.setAdapter(mAdapter2);

        calendario1 = (CalendarView) root.findViewById(R.id.calendario1);
        calendario2 = (CalendarView) root.findViewById(R.id.calendario2);
        radioGroup1 = (RadioGroup) root.findViewById(R.id.radioGroupHorario1);
        radioGroup1 = (RadioGroup) root.findViewById(R.id.radioGroupHorario2);

        calendario1.setVisibility(View.INVISIBLE);

        if (radioGroup1 != null) radioGroup1.removeAllViews();
        if (radioGroup2 != null) radioGroup2.removeAllViews();

        prepareMedicosData1(root);
        //prepareMedicosData2(root, "");

        /*RadioButton nuevoRadio = crearRadioButton("HOLA", 10);
        if (radioGroup1 != null) radioGroup1.addView(nuevoRadio);
         */

        pestaña.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int tab = pestaña.getCurrentTab();
                if (radioGroup1 != null) radioGroup1.removeAllViews();
                if (radioGroup2 != null) radioGroup2.removeAllViews();
                if (tab == 0){
                    prepareMedicosData1(root);
                }else{
                    medicoList.clear();
                }
            }
        });

        calendario1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month,
                                            int dayOfMonth) {
                int tab = pestaña.getCurrentTab();
                String selectedDate1 = getFechaFormat(year, month, dayOfMonth);
                int selectedItem;
                if (tab == 0){
                    selectedItem = mAdapter1.getSelectedItem();
                }else{
                    selectedItem = mAdapter2.getSelectedItem();
                }

                if (radioGroup1 != null) radioGroup1.removeAllViews();

                prepareMedicosHorario1(tab, root, selectedItem, selectedDate1 );
            }
        });

        calendario2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month,
                                            int dayOfMonth) {
                int tab = pestaña.getCurrentTab();
                String selectedDate1 = getFechaFormat(year, month, dayOfMonth);
                int selectedItem;
                if (tab == 0){
                    selectedItem = mAdapter1.getSelectedItem();
                }else{
                    selectedItem = mAdapter2.getSelectedItem();
                }

                if (radioGroup2 != null) radioGroup2.removeAllViews();

                prepareMedicosData2(root, selectedDate1);
                fechaSeleccionada = selectedDate1;

                //prepareMedicosHorario2(tab, root, selectedItem, selectedDate1 );
            }
        });

        final Button button1 = root.findViewById(R.id.btnRegresar2);
        final Button button2 = root.findViewById(R.id.btnContinuar2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry, datosRecuperados);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab = pestaña.getCurrentTab();

                if (tab == 1){
                    medicoHorarioList = mAdapter2.medicoHorarioList;
                }

                saveDatosMemory(view, tab);
                if ((citaMemory.getHorario().equals(""))){
                    Toast.makeText(getContext(), "Es necesario selecionar el Horario.", Toast.LENGTH_LONG).show();
                }else{
                    Bundle datosAEnviar = new Bundle();
                    RegistryPaso1Fragment.enviarDatos(datosAEnviar,citaMemory);
                    navController.navigate(R.id.bottom_registry3, datosAEnviar);
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryPaso2ViewModel.class);
        // TODO: Use the ViewModel
    }

    private String getFechaFormat(int year, int month, int dayOfMonth){

        //Define formato inicial.
        String fecha = (year + "/"+ (month + 1) + "/"+ dayOfMonth);
        DateFormat formatoInicial = new SimpleDateFormat("yyyy/MM/dd");

        String actualdate = "";
        try {
            Date dateInicial = formatoInicial.parse(fecha);
            //* Aqui define el formato deseado.
            String nuevoFormato = "yyyy-MM-dd";

            SimpleDateFormat formatoSalida = new SimpleDateFormat(nuevoFormato);
            actualdate = formatoSalida.format(dateInicial);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return actualdate;
    }

    private void saveDatosMemory(View root, int tab){

        int selectedItem;
        if (tab == 0){
            selectedItem = radioGroup1.getCheckedRadioButtonId();
        }else{
            selectedItem = radioGroup1.getCheckedRadioButtonId();
        }

        //RadioButton primerRadio2 = (RadioButton) radioGroup2.getChildAt(0);
       //if (primerRadio2 != null) primerRadio2.setChecked(true);

        //int selectedItem = mAdapter2.getSelectedItem();

        citaMemory.setMedico(medicoHorarioList.get(selectedItem).getMedico());
        citaMemory.setNombreMedico(medicoHorarioList.get(selectedItem).getNombreMedico());
        citaMemory.setConsultorio(medicoHorarioList.get(selectedItem).getConsultorio());
        citaMemory.setConsultorioT(medicoHorarioList.get(selectedItem).getConsultorio_t());
        citaMemory.setHorario(medicoHorarioList.get(selectedItem).getIdProgramacionAtencion());
        citaMemory.setFecha(medicoHorarioList.get(selectedItem).getFecha());
        citaMemory.setHora(medicoHorarioList.get(selectedItem).getHora());

    }

    private RadioButton crearRadioButton(String marca, int i) {
        int id = i + 1;
        //--Asignamos las propiedades del control
        //tabHost = (TabHost) findViewById(R.id.tabHost);

        //Pestaña 1
        //TabHost.TabSpec spec = tabHost.newTabSpec("Pestaña Uno");

        //RadioButton nuevoRadio = new RadioButton(tabHost.get);
        RadioButton nuevoRadio = new RadioButton(getContext());
        LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        nuevoRadio.setLayoutParams(params);
        nuevoRadio.setText(marca);
        nuevoRadio.setTag(marca);
        nuevoRadio.setId(id);
        return nuevoRadio;
    }

    private void prepareMedicosData1(View root) {
        medicoList.clear();

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        //calendario1 = (CalendarView) root.findViewById(R.id.calendario1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate1;
        if (calendario1 != null) selectedDate1 = sdf.format(new Date(calendario1.getDate()));
        JSONObject jsonobject = new JSONObject();

        try {
            //jsonobject.put("fecha", selectedDate1);
            jsonobject.put("especialidad", citaMemory.getEspecialidad());
            jsonobject.put("sede", citaMemory.getSede());
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonArrayCustomRequest stringRequest= new JsonArrayCustomRequest(Request.Method.POST, Constants.WSUrlGetMedicos1, jsonobject,
            new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    //JSONArray jsonArray = new JSONArray(response);

                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        if (calendario1 != null) calendario1.setVisibility(View.INVISIBLE);
                        if (radioGroup1 != null) radioGroup1.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }else {
                        Medico medico;
                        Integer imagen;
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            imagen = R.drawable.doctor_male;
                            if (object.getString("FotoUrl").equals("doctor_female")){
                                imagen = R.drawable.doctor_female;
                            }

                            medico = new Medico(object.getString("IdMedico"),
                                                object.getString("nombreMedico"),
                                                object.getString("especialidad"),
                                                object.getString("IdConsultorio"),
                                                object.getString("consultorio"),
                                                object.getString("FotoUrl"),
                                                imagen);
                            medicoList.add(medico);
                        }
                        mAdapter1.notifyDataSetChanged();
                        progress.dismiss();

                        if (calendario1 != null) calendario1.setVisibility(View.VISIBLE);
                        if (radioGroup1 != null) radioGroup1.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                    progress.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        progress.dismiss();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void prepareMedicosData2(View root, String selectedDate1) {

        medicoList.clear();

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        //calendario1 = (CalendarView) root.findViewById(R.id.calendario1);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String selectedDate1 = "";
        //if (calendario1 != null) selectedDate1 = sdf.format(new Date(calendario2.getDate()));

        JSONObject jsonobject = new JSONObject();

        try {
            jsonobject.put("fecha", selectedDate1);
            jsonobject.put("especialidad", citaMemory.getEspecialidad());
            jsonobject.put("sede", citaMemory.getSede());
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonArrayCustomRequest stringRequest= new JsonArrayCustomRequest(Request.Method.POST, Constants.WSUrlGetMedicos2, jsonobject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.i("======>", jsonArray.toString());

                            if (jsonArray.length() == 0){
                                if (recyclerView2 != null) recyclerView2.setVisibility(View.INVISIBLE);
                                if (radioGroup2 != null) radioGroup2.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }else {
                                Medico medico;
                                Integer imagen;
                                List<String> items = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    imagen = R.drawable.doctor_male;
                                    if (object.getString("FotoUrl").equals("doctor_female")){
                                        imagen = R.drawable.doctor_female;
                                    }

                                    medico = new Medico(object.getString("IdMedico"),
                                            object.getString("nombreMedico"),
                                            object.getString("especialidad"),
                                            object.getString("IdConsultorio"),
                                            object.getString("consultorio"),
                                            object.getString("FotoUrl"),
                                            imagen);
                                    medicoList.add(medico);
                                }
                                mAdapter2.notifyDataSetChanged();
                                progress.dismiss();

                                if (recyclerView2 != null) recyclerView2.setVisibility(View.VISIBLE);
                                if (radioGroup2 != null) radioGroup2.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            Log.i("======>", e.getMessage());
                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void prepareMedicosHorario1(final int tab, View root, int selectedItem, String selectedDate1) {
        medicoHorarioList.clear();

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        JSONObject jsonobject = new JSONObject();

        try {
            String medico = "";
            if (medicoList.size() > 0 ) medico = medicoList.get(selectedItem).getCodigo();
            Log.i("======>", selectedItem + "" );
            Log.i("======>", selectedDate1 );
            jsonobject.put("fecha", selectedDate1);
            jsonobject.put("medico", medico );
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonArrayCustomRequest stringRequest= new JsonArrayCustomRequest(Request.Method.POST, Constants.WSUrlGetProgramacion, jsonobject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.i("======>", jsonArray.toString());

                            if (jsonArray.length() == 0){
                                Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }else {
                                HorarioMedico horarioMedico;
                                //List<String> items = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    horarioMedico = new HorarioMedico(object.getString("IdProgramacionAtencion"),
                                                                    object.getString("Fecha"),
                                                                    object.getString("Hora"),
                                                                    object.getString("IdMedico"),
                                                                    object.getString("nombreMedico"),
                                                                    object.getString("IdEspecialidad"),
                                                                    object.getString("especialidad"),
                                                                    object.getString("IdConsultorio"),
                                                                    object.getString("consultorio"),
                                                                    object.getString("IdSede"));
                                    medicoHorarioList.add(horarioMedico);
                                    RadioButton nuevoRadio = crearRadioButton(horarioMedico.getHora(), i);
                                    radioGroup1.addView(nuevoRadio);

                                }

                                //RadioButton primerRadio = primerRadio = (RadioButton) radioGroup1.getChildAt(1);
                                RadioButton primerRadio = primerRadio = (RadioButton) radioGroup1.getChildAt(0);
                                primerRadio.setChecked(true);

                                progress.dismiss();

                            }
                        } catch (JSONException e) {
                            Log.i("======>", e.getMessage());
                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void prepareMedicosHorario2(final int tab, View root, int selectedItem, String selectedDate1) {
        medicoHorarioList.clear();

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        JSONObject jsonobject = new JSONObject();

        try {
            String medico = "";
            if (medicoList.size() > 0 ) medico = medicoList.get(selectedItem).getCodigo();
            Log.i("======>", selectedItem + "" );
            Log.i("======>", selectedDate1 );
            jsonobject.put("fecha", selectedDate1);
            jsonobject.put("medico", medico );
            Log.i("======>", jsonobject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("======>", e.getMessage());
        }

        JsonArrayCustomRequest stringRequest= new JsonArrayCustomRequest(Request.Method.POST, Constants.WSUrlGetProgramacion, jsonobject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.i("======>", jsonArray.toString());

                            if (jsonArray.length() == 0){
                                Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }else {
                                HorarioMedico horarioMedico;
                                //List<String> items = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    horarioMedico = new HorarioMedico(object.getString("IdProgramacionAtencion"),
                                            object.getString("Fecha"),
                                            object.getString("Hora"),
                                            object.getString("IdMedico"),
                                            object.getString("nombreMedico"),
                                            object.getString("IdEspecialidad"),
                                            object.getString("especialidad"),
                                            object.getString("IdConsultorio"),
                                            object.getString("consultorio"),
                                            object.getString("IdSede"));

                                    medicoHorarioList.add(horarioMedico);
                                    RadioButton nuevoRadio = crearRadioButton(horarioMedico.getHora(), i);
                                    radioGroup2.addView(nuevoRadio);
                                }

                                RadioButton primerRadio = (RadioButton) radioGroup2.getChildAt(0);
                                primerRadio.setChecked(true);

                                progress.dismiss();

                            }
                        } catch (JSONException e) {
                            Log.i("======>", e.getMessage());
                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                        progress.dismiss();
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}

