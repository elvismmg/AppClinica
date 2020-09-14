package com.example.appclinica.ui.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Citas;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private String idCita;
    private NavController navController;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        navController = NavHostFragment.findNavController(this);

        //final EditText textView0 = root.findViewById(R.id.txtIdCita2);
        final EditText textView1 = root.findViewById(R.id.txtPaciente2);
        final EditText textView2 = root.findViewById(R.id.txtEspecialidad2);
        final EditText textView3 = root.findViewById(R.id.txtMedico2);
        final EditText textView4 = root.findViewById(R.id.txtSede2);
        final EditText textView5 = root.findViewById(R.id.txtConsultorio2);
        final EditText textView6 = root.findViewById(R.id.txtFecha2);
        final EditText textView7 = root.findViewById(R.id.txtHora2);
        final EditText textView8 = root.findViewById(R.id.txtTipo2);
        final Button button1 = root.findViewById(R.id.btnAnular);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
            return root;
        }

        final String fechaFormat = datosRecuperados.getString("txtFecha2").substring(8,10) + "/" +
                                    datosRecuperados.getString("txtFecha2").substring(5,7) + "/" +
                                    datosRecuperados.getString("txtFecha2").substring(0,4);

        idCita = datosRecuperados.getString("txtIdCita2");

        //textView0.setText(datosRecuperados.getString("txtIdCita2"));
        textView1.setText(datosRecuperados.getString("txtPaciente2"));
        textView2.setText(datosRecuperados.getString("txtEspecialidad2"));
        textView3.setText(datosRecuperados.getString("txtMedico2"));
        textView4.setText(datosRecuperados.getString("txtSede2"));
        textView5.setText(datosRecuperados.getString("txtConsultorio2"));
        textView6.setText(fechaFormat);
        textView7.setText(datosRecuperados.getString("txtHora2"));
        textView8.setText(datosRecuperados.getString("txtTipo2"));

        if ( datosRecuperados.getString("txtEstado2").trim().equals("R")){
            button1.setVisibility(View.VISIBLE);
        }else {
            button1.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anularCita(view);
                navController.navigate(R.id.bottom_report);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel

    }

    public void anularCita (View v){

        String url = Constants.WSUrlAnularCita+"/" +idCita;
        final ProgressDialog progress = new ProgressDialog(getContext());

        progress.setTitle("Anulando...");
        progress.setMessage("Espere mientras validamos...");
        progress.setCancelable(false);
        progress.show();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //JSONObject jsonObjectResponse = response.getJSONObject(0);

                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("======>", jsonObject.toString());

                    if (jsonObject.length() == 0){
                        Toast.makeText(getContext(), "Ocurrió un error al anular la cita.", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(), "Cita anulada correctamente.", Toast.LENGTH_LONG).show();
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
