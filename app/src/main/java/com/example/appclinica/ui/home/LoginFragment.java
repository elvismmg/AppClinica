package com.example.appclinica.ui.home;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.centers.CentersAdapter;
import com.example.appclinica.ui.dao.CentersDAO;
import com.example.appclinica.ui.dao.DAOException;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;
import com.example.appclinica.ui.models.CenterModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private NavController navController;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        BottomNavigationView bottomNavView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav_view);
        bottomNavView.setVisibility(View.GONE);


        View view = inflater.inflate(R.layout.fragment_login, container, false);

        navController = NavHostFragment.findNavController(this);

        loadButtonEvents(view);

        return view;
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }*/

    private void loadButtonEvents(View view) {
        Button btnLogin = (Button) view.findViewById(R.id.login_btnLogin);
        final EditText etUsername = (EditText) view.findViewById(R.id.login_etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.login_etPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                final ProgressDialog progress = new ProgressDialog(getContext());

                UIUtil.hideKeyboard(getActivity());


                if (TextUtils.isEmpty(etUsername.getText())) {
                    error = true;
                    etUsername.setError(getText(R.string.login_emptyUsername));
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    error = true;
                    etPassword.setError(getText(R.string.login_emptyPassword));
                }

                if (error == false) {
                    //Intent intent = new Intent(getContext(), MainActivity.class);
                    //getContext().startActivity(intent);


                    progress.setTitle("Autenticando...");
                    progress.setMessage("Espere mientras validamos...");
                    progress.setCancelable(false);
                    progress.show();

                    JSONObject jsonobject = new JSONObject();
                    try {
                        jsonobject.put("codTipo", "1");
                        jsonobject.put("NumeroDocumento", etUsername.getText().toString());
                        jsonobject.put("Clave", etPassword.getText().toString());
                        Log.i("======>", jsonobject.toString());
                    } catch (JSONException e) {
                        Log.i("======>", e.getMessage());
                    }

                    JsonArrayCustomRequest jsonObjReq = new JsonArrayCustomRequest(Request.Method.POST,
                            Constants.WSUrlLoginPaciente,jsonobject,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {

                                    if (response.length() == 0) {
                                        progress.dismiss();
                                        etUsername.setError(getText(R.string.login_wrongLoginShort));
                                        etPassword.setError(getText(R.string.login_wrongLoginShort));
                                        Toast.makeText(getContext(), R.string.login_wrongLogin, Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        try {
                                            JSONObject jsonObjectResponse = response.getJSONObject(0);

                                            Bundle bundle = new Bundle();
                                            bundle.putString("IdPaciente", jsonObjectResponse.getString("IdPaciente"));
                                            bundle.putString("codTipo", jsonObjectResponse.getString("codTipo"));
                                            bundle.putString("NumeroDocumento", jsonObjectResponse.getString("NumeroDocumento"));
                                            bundle.putString("pacienteNombres", jsonObjectResponse.getString("Nombres"));
                                            bundle.putString("pacienteApellidos", jsonObjectResponse.getString("Apellidos"));
                                            bundle.putString("pacienteGenero", jsonObjectResponse.getString("Sexo"));
                                            bundle.putString("pacienteFechaNacimiento", jsonObjectResponse.getString("FechaNacimiento"));
                                            bundle.putDouble("pacientePeso", jsonObjectResponse.getDouble("Peso"));
                                            bundle.putDouble("pacienteAltura", jsonObjectResponse.getDouble("Altura"));
                                            bundle.putString("pacienteTipoSangre", jsonObjectResponse.getString("TipoSangre"));

                                            loadCentersToDb(bundle, progress);

//                                            navController.navigate(R.id.nav_home, bundle);
//                                            openSession(jsonObjectResponse.getString("IdPaciente"));
//
//                                            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//                                            BottomNavigationView bottomNavView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav_view);
//                                            bottomNavView.setVisibility(View.VISIBLE);
//
//                                            progress.dismiss();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progress.dismiss();
                                        }

                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                            }

                    );

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(jsonObjReq);

                }
            }
        });

        TextView lnkForgotPassword = (TextView) view.findViewById(R.id.login_tvForgotPassword);
        lnkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.passwordFragment);

                //getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new PasswordFragment()).commit();
            }
        });

        TextView lnkRegister = (TextView) view.findViewById(R.id.login_tvRegistrar);
        lnkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.newUserFragment);
                //getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new NewUserFragment()).commit();
            }
        });
    }

    public void openSession(String idPaciente){
        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("SESSION", true);
        editor.putString("IdPaciente", idPaciente);
        editor.commit();
    }



    private void loadCentersToDb(final Bundle bundle, final ProgressDialog progressDialog){

        JsonArrayCustomRequest jsonObjReq = new JsonArrayCustomRequest(Request.Method.GET,
                Constants.WSUrlGetSedes,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {
                            try {
//                                List<CenterModel> centerList = new ArrayList<>();
                                CentersDAO centersDAO = new CentersDAO(getActivity().getBaseContext());
                                centersDAO.DeleteAll();

                                for(int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObjectResponse = response.getJSONObject(i);
//                                    centerList.add(new CenterModel(
//                                            jsonObjectResponse.getInt("IdSede"),
//                                            jsonObjectResponse.getString("Nombre"),
//                                            jsonObjectResponse.getString("Direccion"),
//                                            jsonObjectResponse.getString("Distrito"),
//                                            jsonObjectResponse.getDouble("UbicacionLatitud"),
//                                            jsonObjectResponse.getDouble("UbicacionLongitud")));

                                    CenterModel centerModel = new CenterModel(
                                            jsonObjectResponse.getInt("IdSede"),
                                            jsonObjectResponse.getString("Nombre"),
                                            jsonObjectResponse.getString("Direccion"),
                                            jsonObjectResponse.getString("Distrito"),
                                            jsonObjectResponse.getDouble("UbicacionLatitud"),
                                            jsonObjectResponse.getDouble("UbicacionLongitud"));

                                    centersDAO.Insert(centerModel);
                                }

                                navController.navigate(R.id.nav_home, bundle);
                                openSession(bundle.getString("IdPaciente"));
                                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                                BottomNavigationView bottomNavView = getActivity().findViewById(R.id.bottom_nav_view);
                                bottomNavView.setVisibility(View.VISIBLE);

                                progressDialog.dismiss();

                            } catch (JSONException | DAOException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);
    }
}