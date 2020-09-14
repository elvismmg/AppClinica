package com.example.appclinica.ui.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        //mAdapter = new CitasAdapter(getContext(), citasList, this);
        mAdapter = new CitasAdapter(citasList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareCitasData();

        return root;
    }

    private void prepareCitasData() {

        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        String idPaciente = prefs.getAll().get("IdPaciente").toString();

        String url = Constants.WSUrlGetCitas+"/" +idPaciente;

        citasList.clear();

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                    }else {
                        Citas cita;
                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            cita = new Citas(object.getString("idCita"),
                                    object.getString("Fecha"),
                                    object.getString("Hora"),
                                    object.getString("nombreMedico"),
                                    object.getString("especialidad"),
                                    object.getString("nombrePaciente"),
                                    object.getString("sede"),
                                    object.getString("consultorio"),
                                    object.getString("seguro"),
                                    object.getString("Copago"),
                                    object.getString("tipoCitaDesc"),
                                    object.getString("estado"),
                                    object.getString("estadoDesc"),
                                    object.getString("Numero"),
                                    object.getString("ImagenEvidenciaUrlA"),
                                    object.getString("ImagenEvidenciaUrlB"),
                                    object.getString("ImagenEvidenciaUrlC"));
                            citasList.add(cita);
                        }

                        mAdapter.notifyDataSetChanged();
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