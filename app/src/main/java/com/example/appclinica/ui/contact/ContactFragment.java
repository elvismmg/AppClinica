package com.example.appclinica.ui.contact;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactFragment extends Fragment {

    private ContactViewModel contactViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                ViewModelProviders.of(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        final TextView textView = root.findViewById(R.id.text_contact);
        contactViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        datosMaestros(root);

        return root;
    }


    public void datosMaestros(final View v){

        /*final ProgressDialog progress = new ProgressDialog(getContext());

        progress.setTitle("Anulando...");
        progress.setMessage("Espere mientras validamos...");
        progress.setCancelable(false);
        progress.show();
        */

        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.WSUrlDatosMaestros, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    if (jsonArray.length() == 0){
                        Toast.makeText(getContext(), "No se encocntraron Datos.", Toast.LENGTH_LONG).show();
                    }else {
                        JSONObject object = jsonArray.getJSONObject(0);

                        final TextView textView1 = v.findViewById(R.id.nombreEmpresa);
                        final TextView textView2 = v.findViewById(R.id.rucEmpresa);
                        final TextView textView3 = v.findViewById(R.id.direccEmpresa);
                        final TextView textView4 = v.findViewById(R.id.urlEmpresa);
                        final TextView textView5 = v.findViewById(R.id.faceEmpresa);
                        final TextView textView6 = v.findViewById(R.id.correoEmpresa);
                        final TextView textView7 = v.findViewById(R.id.telefonoEmpresa);
                        final TextView textView8 = v.findViewById(R.id.celularEmpresa);

                        textView1.setText(object.getString("nombre"));
                        //textView1.setText(object.getString("razSocial"));
                        textView2.setText("RUC: " + object.getString("ruc"));
                        textView3.setText(object.getString("direccion"));
                        textView4.setText(object.getString("pagina"));
                        textView5.setText(object.getString("pagFacebook"));
                        textView6.setText(object.getString("correo"));
                        textView7.setText(object.getString("telefono1"));
                        textView8.setText(object.getString("telefono2") + "/" + object.getString("telefono3"));
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

}