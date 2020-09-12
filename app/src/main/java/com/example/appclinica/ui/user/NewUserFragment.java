package com.example.appclinica.ui.user;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
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

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class NewUserFragment extends Fragment {

    private View currentView;
    private NewUserViewModel mViewModel;
    private NavController navController;

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_new_user, container, false);
        navController = NavHostFragment.findNavController(this);

//        loadSexComboBox(view);
        loadBirthdate();
        loadButtonEvents();

        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewUserViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadSexComboBox(View view) {
//        Spinner spinner = (Spinner)view.findViewById(R.id.spSex);
//        ArrayList<String> sexList = new ArrayList<>();
//        sexList.add("Masculino");
//        sexList.add("Femenino");
//
//        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sexList);
//        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(sexAdapter);
    }

    private void loadBirthdate() {
//        final EditText dpBirthdate = (EditText) currentView.findViewById(R.id.new_user_etBirthdate);
//        Calendar calendar = Calendar.getInstance();
//        final int year = calendar.get(calendar.YEAR);
//        final int month = calendar.get(calendar.MONTH);
//        final int day = calendar.get(calendar.DAY_OF_MONTH);
//
//
//        dpBirthdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        dpBirthdate.setText(year + "/" + month + "/" + year);
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
//            }
//        });
    }

    private void loadButtonEvents() {
        Button btnBack = (Button) currentView.findViewById(R.id.new_user_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_login);
                //getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
            }
        });

        Button btnSave = (Button) currentView.findViewById(R.id.new_user_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
                if (validate(view)) {
                    //Toast.makeText(getContext(), R.string.newUser_msgSave, Toast.LENGTH_LONG).show();

                    UIUtil.hideKeyboard(getActivity());
                    final ProgressDialog progress = new ProgressDialog(getContext());
                    progress.setTitle("Grabando...");
                    progress.setMessage("Espere por favor...");
                    progress.setCancelable(false);
                    progress.show();

                    final JSONObject jsonobject = new JSONObject();
                    try {
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
                            Constants.WSUrlInsertarPaciente,jsonobject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if (response.length() == 0) {
                                        progress.dismiss();
                                        Toast.makeText(getContext(), R.string.msgInsertUpdateWSError, Toast.LENGTH_LONG).show();
                                    }
                                    else {
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("pacienteNombres", jsonobject.getString("Nombres"));
//                                            bundle.putString("pacienteApellidos", jsonobject.getString("Apellidos"));
//                                            bundle.putString("pacienteGenero", jsonobject.getString("Sexo"));
//                                            bundle.putString("pacienteFechaNacimiento", jsonobject.getString("FechaNacimiento"));
//                                            bundle.putInt("pacientePeso", jsonobject.getInt("Peso"));
//                                            bundle.putDouble("pacienteAltura", jsonobject.getDouble("Altura"));
//                                            bundle.putString("pacienteTipoSangre", jsonobject.getString("TipoSangre"));

                                        navController.navigate(R.id.newUserConfirmationFragment);
                                        //openSession(jsonobject.getString("IdPaciente"));

//                                            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//                                            BottomNavigationView bottomNavView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav_view);
//                                            bottomNavView.setVisibility(View.VISIBLE);

                                        progress.dismiss();

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

    private boolean validate(View view){
        boolean isValid = true;


        //Número de documento
        etDocumentNumber = (EditText) currentView.findViewById(R.id.new_user_etDocumentNumber);
        if (!validateEmptyField(etDocumentNumber)) {
            isValid = false;
        }
//        if(!documentNumber.matches(Constants.regExEmail)) {
//            isValid = false;
//            etDocumentNumber.setError(getText(R.string.validation_msgInvalidEmail));
//        }

        //Nombres
        etFirstName = (EditText) currentView.findViewById(R.id.new_user_etFirstName);
        if (!validateEmptyField(etFirstName)) {
            isValid = false;
        }

        //Apellidos
        etLastName = (EditText) currentView.findViewById(R.id.new_user_etLastName);
        if (!validateEmptyField(etLastName)) {
            isValid = false;
        }

        //Genero
        rgGender = (RadioGroup) currentView.findViewById(R.id.new_user_rgGender);
        if (rgGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(getContext(), R.string.validation_msgInvalidGender, Toast.LENGTH_LONG).show();
        }

//        //Fecha nacimiento
        dpBirthdate = (DatePicker) currentView.findViewById(R.id.new_user_dpBirthdate);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if((currentYear - dpBirthdate.getYear()) < 0) {
            isValid = false;
            Toast.makeText(getContext(), R.string.validation_msgInvalidBirthdate, Toast.LENGTH_LONG).show();
        }

        //Telefono
        etPhoneNumber = (EditText) currentView.findViewById(R.id.new_user_etPhoneNumber);
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (!validateEmptyField(etPhoneNumber)) {
            isValid = false;
        } else if(!phoneNumber.matches(Constants.regExPhoneNumber)) {
            isValid = false;
            etPhoneNumber.setError(getText(R.string.validation_msgInvalidPhone));
        }

        //Correo
        eteMail = (EditText) currentView.findViewById(R.id.new_user_eteMail);
        String eMail = eteMail.getText().toString().trim();
        if (!validateEmptyField(eteMail)) {
            isValid = false;
        } else if(!eMail.matches(Constants.regExEmail)) {
            isValid = false;
            eteMail.setError(getText(R.string.validation_msgInvalidEmail));
        }

        //Tipo de sangre
        etBloodType = (EditText) currentView.findViewById(R.id.new_user_etBloodType);
        if (!validateEmptyField(etBloodType)) {
            isValid = false;
        }

        //Peso
        etWeight = (EditText) currentView.findViewById(R.id.new_user_etWeight);
        if (etWeight.getText().length() > 0 && Integer.valueOf(etWeight.getText().toString()) < 1){
            isValid = false;
            etWeight.setError(getText(R.string.validation_msgLessoeEqualThanZero));
        }

        //Altura
        etHeight = (EditText) currentView.findViewById(R.id.new_user_etHeight);
        if (etHeight.getText().length() > 0 && Double.valueOf(etHeight.getText().toString()) < 1){
            isValid = false;
            etWeight.setError(getText(R.string.validation_msgLessoeEqualThanZero));
        }


        //Contraseña
        etPassword = (EditText) currentView.findViewById(R.id.new_user_etPassword);
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

    public void openSession(String idPaciente){
        SharedPreferences prefs = getActivity().getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("SESSION", true);
        editor.putString("IdPaciente", idPaciente);
        editor.commit();
    }
}