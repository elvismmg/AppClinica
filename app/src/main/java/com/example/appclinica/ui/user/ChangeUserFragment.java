package com.example.appclinica.ui.user;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclinica.R;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.helpers.JsonArrayCustomRequest;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeUserFragment extends Fragment {

    private View currentView;
    private ChangeUserViewModel mViewModel;
    public static ChangeUserFragment newInstance() {
        return new ChangeUserFragment();
    }
    private EditText etDocumentNumber;
    private EditText etFirstName;
    private EditText etLastName;
    private RadioGroup rgGender;
    private DatePicker dpBirthdate;
    private EditText etPhoneNumber;
    private EditText eteMail;
    private EditText etBloodType;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etPassword;
    private String idPaciente;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_change_user, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        idPaciente = prefs.getAll().get("IdPaciente").toString();



        loadButtonEvents();
        loadInfo();

        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangeUserViewModel.class);
        // TODO: Use the ViewModel
    }

    private  void loadButtonEvents() {
//        Button btnSave = (Button) currentView.findViewById(R.id.change_user_btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
//                if (validate(view)) {
//                    Toast.makeText(getContext(), R.string.changeUser_msgSave, Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        Button btnSave = (Button) currentView.findViewById(R.id.change_user_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(view)) {
                    UIUtil.hideKeyboard(getActivity());
                    final ProgressDialog progress = new ProgressDialog(getContext());
                    progress.setTitle("Actualizando...");
                    progress.setMessage("Espere por favor...");
                    progress.setCancelable(false);
                    progress.show();

                    final JSONObject jsonobject = new JSONObject();
                    try {
                        jsonobject.put("IdPaciente", idPaciente);
                        jsonobject.put("codTipo", "1");
                        jsonobject.put("NumeroDocumento", etDocumentNumber.getText().toString());
                        jsonobject.put("Nombres", etFirstName.getText().toString());
                        jsonobject.put("Apellidos", etLastName.getText().toString());
                        jsonobject.put("Sexo", rgGender.findViewById(rgGender.getCheckedRadioButtonId()).getTag());
                        jsonobject.put("FechaNacimiento", dpBirthdate.getYear() + "-" + dpBirthdate.getMonth() + "-" + dpBirthdate.getDayOfMonth());
                        jsonobject.put("Telefono", etPhoneNumber.getText().toString());
                        jsonobject.put("Correo", eteMail.getText().toString());
                        jsonobject.put("TipoSangre", etBloodType.getText().toString());
                        jsonobject.put("Peso", etWeight.getText().toString());
                        jsonobject.put("Altura", etHeight.getText().toString());
                        jsonobject.put("Clave", etPassword.getText().toString());
                        Log.i("======>", jsonobject.toString());
                    } catch (JSONException e) {
                        Log.i("======>", e.getMessage());
                    }

                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            Constants.WSUrlActualizarPaciente,jsonobject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if (response.length() == 0) {
                                        progress.dismiss();
                                        Toast.makeText(getContext(), R.string.msgInsertUpdateWSError, Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        progress.dismiss();
                                        Toast.makeText(getContext(), R.string.changeUser_msgSave, Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    progress.dismiss();
                                }
                            }

                    );

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(jsonObjReq);

                }
            }
        });
    }

    private  void loadInfo() {
        final EditText etDocumentNumber = (EditText) currentView.findViewById(R.id.change_user_etDocumentNumber);
        final EditText etFirstName = (EditText) currentView.findViewById(R.id.change_user_etFirstName);
        final EditText etLastName = (EditText) currentView.findViewById(R.id.change_user_etLastName);
        final RadioButton rbMale = (RadioButton) currentView.findViewById(R.id.change_user_rbMale);
        final RadioButton rbFemale = (RadioButton) currentView.findViewById(R.id.change_user_rbFemale);
        final DatePicker dpBirthdate = (DatePicker) currentView.findViewById(R.id.change_user_dpBirthdate);
        final EditText etPhoneNumber = (EditText) currentView.findViewById(R.id.change_user_etPhoneNumber);
        final EditText eteMail = (EditText) currentView.findViewById(R.id.change_user_eteMail);
        final EditText etWeight = (EditText) currentView.findViewById(R.id.change_user_etWeight);
        final EditText etHeight = (EditText) currentView.findViewById(R.id.change_user_etHeight);
        final EditText etBloodType = (EditText) currentView.findViewById(R.id.change_user_etBloodType);
        final EditText etPassword = (EditText) currentView.findViewById(R.id.change_user_etPassword);

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando...");
        progress.setMessage("Espere por favor...");
        progress.setCancelable(false);
        progress.show();

        JsonArrayCustomRequest jsonObjReq = new JsonArrayCustomRequest(Request.Method.GET,
                Constants.WSUrlBuscarPacientePorId + "/" + idPaciente,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() == 0) {
                            Toast.makeText(getContext(), R.string.msgNoInformation, Toast.LENGTH_LONG).show();
                        }
                        else {
                            try {
                                JSONObject jsonObjectResponse = response.getJSONObject(0);

                                etDocumentNumber.setText(jsonObjectResponse.getString("NumeroDocumento"));
                                etFirstName.setText(jsonObjectResponse.getString("Nombres"));
                                etLastName.setText(jsonObjectResponse.getString("Apellidos"));

                                if(jsonObjectResponse.getString("Sexo").equals("M")){
                                    rbMale.setChecked(true);
                                    rbFemale.setChecked(false);
                                }
                                else {
                                    rbMale.setChecked(false);
                                    rbFemale.setChecked(true);
                                }

                                Calendar calendarFechaNacimiento = getCalendarFechaNacimiento(jsonObjectResponse.getString("FechaNacimiento"));
                                dpBirthdate.init(calendarFechaNacimiento.get(Calendar.YEAR), calendarFechaNacimiento.get(Calendar.MONTH),calendarFechaNacimiento.get(Calendar.DAY_OF_MONTH), null);

                                etPhoneNumber.setText(jsonObjectResponse.getString("Telefono"));
                                eteMail.setText(jsonObjectResponse.getString("Correo"));
                                etBloodType.setText(jsonObjectResponse.getString("TipoSangre"));
                                etWeight.setText(jsonObjectResponse.getString("Peso"));
                                etHeight.setText(jsonObjectResponse.getString("Altura"));
                                etPassword.setText(jsonObjectResponse.getString("Clave"));

                                progress.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progress.dismiss();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progress.dismiss();
                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);

    }

    private boolean validate(View view){
        boolean isValid = true;


        //Número de documento
        etDocumentNumber = (EditText) currentView.findViewById(R.id.change_user_etDocumentNumber);
        if (!validateEmptyField(etDocumentNumber)) {
            isValid = false;
        }
//        if(!documentNumber.matches(Constants.regExEmail)) {
//            isValid = false;
//            etDocumentNumber.setError(getText(R.string.validation_msgInvalidEmail));
//        }

        //Nombres
        etFirstName = (EditText) currentView.findViewById(R.id.change_user_etFirstName);
        if (!validateEmptyField(etFirstName)) {
            isValid = false;
        }

        //Apellidos
        etLastName = (EditText) currentView.findViewById(R.id.change_user_etLastName);
        if (!validateEmptyField(etLastName)) {
            isValid = false;
        }

        //Genero
        rgGender = (RadioGroup) currentView.findViewById(R.id.change_user_rgGender);
        if (rgGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(getContext(), R.string.validation_msgInvalidGender, Toast.LENGTH_LONG).show();
        }

//        //Fecha nacimiento
        dpBirthdate = (DatePicker) currentView.findViewById(R.id.change_user_dpBirthdate);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if((currentYear - dpBirthdate.getYear()) < 0) {
            isValid = false;
            Toast.makeText(getContext(), R.string.validation_msgInvalidBirthdate, Toast.LENGTH_LONG).show();
        }

        //Telefono
        etPhoneNumber = (EditText) currentView.findViewById(R.id.change_user_etPhoneNumber);
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (!validateEmptyField(etPhoneNumber)) {
            isValid = false;
        } else if(!phoneNumber.matches(Constants.regExPhoneNumber)) {
            isValid = false;
            etPhoneNumber.setError(getText(R.string.validation_msgInvalidPhone));
        }

        //Correo
        eteMail = (EditText) currentView.findViewById(R.id.change_user_eteMail);
        String eMail = eteMail.getText().toString().trim();
        if (!validateEmptyField(eteMail)) {
            isValid = false;
        } else if(!eMail.matches(Constants.regExEmail)) {
            isValid = false;
            eteMail.setError(getText(R.string.validation_msgInvalidEmail));
        }

        //Tipo de sangre
        etBloodType = (EditText) currentView.findViewById(R.id.change_user_etBloodType);
        if (!validateEmptyField(etBloodType)) {
            isValid = false;
        }

        //Peso
        etWeight = (EditText) currentView.findViewById(R.id.change_user_etWeight);
        if (etWeight.getText().length() > 0 && Double.valueOf(etWeight.getText().toString()) < 1){
            isValid = false;
            etWeight.setError(getText(R.string.validation_msgLessoeEqualThanZero));
        }

        //Altura
        etHeight = (EditText) currentView.findViewById(R.id.change_user_etHeight);
        if (etHeight.getText().length() > 0 && Double.valueOf(etHeight.getText().toString()) < 1){
            isValid = false;
            etWeight.setError(getText(R.string.validation_msgLessoeEqualThanZero));
        }


        //Contraseña
        etPassword = (EditText) currentView.findViewById(R.id.change_user_etPassword);
        if (!validateEmptyField(etPassword)) {
            isValid = false;
        }


        return isValid;
    }

    private boolean validateEmptyField(EditText editText) {
        boolean isValid = true;

        String valueText = editText.getText().toString().trim();
        if (valueText.isEmpty()) {
            isValid = false;
            editText.setError(getText(R.string.validation_msgRequiredField));
        }

        return isValid;
    }

    private boolean validateEmptyField(EditText editText, int length) {
        boolean isValid = true;

        String valueText = editText.getText().toString().trim();
        if (valueText.isEmpty()) {
            isValid = false;
            editText.setError(getText(R.string.validation_msgRequiredField));
        } else if (valueText.length() > length)
        {
            isValid = false;
            editText.setError(getText(R.string.validation_msgLenghtField));
        }

        return isValid;
    }

    private Calendar getCalendarFechaNacimiento(String fechanacimiento){
        SimpleDateFormat fechaNacimientoParse = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int edad = 0;
        try {
            Date fechaNacimiento = fechaNacimientoParse.parse(fechanacimiento);
            cal.setTime(fechaNacimiento);
            //edad = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cal;
    }
}