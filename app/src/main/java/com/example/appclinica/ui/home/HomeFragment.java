package com.example.appclinica.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.dao.Paciente;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.user.ChangeUserFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private NavController navController;
    private Paciente paciente;
    private String idPaciente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        navController = NavHostFragment.findNavController(this);

        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        idPaciente = prefs.getAll().get("IdPaciente").toString();

        final Button button = root.findViewById(R.id.btnMyInfo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_myinfo);
            }
        });

        Bundle bundle = getArguments();

        if (bundle != null){
            paciente = new Paciente(bundle.getString("IdPaciente"), bundle.getString("codTipo"), bundle.getString("NumeroDocumento"),
                                    bundle.getString("pacienteNombres"), bundle.getString("pacienteApellidos"),
                                    bundle.getString("pacienteGenero"), bundle.getString("pacienteFechaNacimiento"),
                                    bundle.getString("pacienteTipoSangre"), String.valueOf(bundle.getDouble("pacientePeso")),
                                    String.valueOf(bundle.getDouble("pacienteAltura")));
            setDatosHome(root, paciente);

        }else {
            getPaciente(root);
        }

        return root;
    }


    public void getPaciente(final View root){

        String url = Constants.WSUrlBuscarPacientePorId+"/" +idPaciente;

        /*final ProgressDialog progress = new ProgressDialog(getContext());

        progress.setTitle("Anulando...");
        progress.setMessage("Espere mientras validamos...");
        progress.setCancelable(false);
        progress.show();
         */


        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                    }else {
                        JSONObject object = jsonArray.getJSONObject(0);
                        paciente = new Paciente();
                        paciente.setIdPaciente(object.getString("IdPaciente"));
                        paciente.setCodTipo(object.getString("codTipo"));
                        paciente.setNumeroDocumento(object.getString("NumeroDocumento"));
                        paciente.setNombres(object.getString("Nombres"));
                        paciente.setApellidos(object.getString("Apellidos"));
                        paciente.setSexo(object.getString("Sexo"));
                        paciente.setFechaNacimiento(object.getString("FechaNacimiento"));
                        paciente.setTelefono(object.getString("Telefono"));
                        paciente.setCorreo(object.getString("Correo"));
                        paciente.setTipoSangre(object.getString("TipoSangre"));
                        paciente.setPeso(object.getString("Peso"));
                        paciente.setAltura(object.getString("Altura"));
                        paciente.setFotoUrl(object.getString("FotoUrl"));
                        paciente.setClave(object.getString("Clave"));
                        paciente.setActivo(object.getString("Activo"));

                        setDatosHome(root, paciente);

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

    public void setDatosHome(View root, Paciente paciente){

        String nombrePaciente = paciente.getNombres() + " " + paciente.getApellidos();
        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("NombrePaciente", nombrePaciente);
        editor.commit();

        SimpleDateFormat fechaNacimientoParse = new SimpleDateFormat("yyyy-MM-dd");
        int edad = 0;
        try {
            if (paciente.getFechaNacimiento().length() != 0){
                Date fechaNacimiento = fechaNacimientoParse.parse(paciente.getFechaNacimiento());
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaNacimiento);
                edad = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);

                fechaNacimiento.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView etGenero = (TextView) root.findViewById(R.id.home_etGenero);
        TextView tvPaciente = (TextView) root.findViewById(R.id.home_tvPaciente);
        EditText etEdad = (EditText) root.findViewById(R.id.home_etEdad);
        EditText etPeso = (EditText) root.findViewById(R.id.home_etPeso);
        EditText etAltura = (EditText) root.findViewById(R.id.home_etAltura);
        EditText etTipoSangre = (EditText) root.findViewById(R.id.home_etTipoSangre);

        if(paciente.getSexo().equals("M")) {
            etGenero.setText("Masculino");
        }
        else {
            etGenero.setText("Femenino");
        }

        tvPaciente.setText("Bienvenido " + paciente.getNombres() + " " + paciente.getApellidos());
        etEdad.setText(String.valueOf(edad));
        etPeso.setText(paciente.getPeso());
        etAltura.setText(paciente.getAltura());
        etTipoSangre.setText(paciente.getTipoSangre());
    }

}