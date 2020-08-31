package com.example.appclinica.ui.user;

import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appclinica.R;
import com.example.appclinica.ui.helpers.Constants;
import com.example.appclinica.ui.home.LoginFragment;

import java.util.Calendar;

public class NewUserFragment extends Fragment {

    private View currentView;
    private NewUserViewModel mViewModel;

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_new_user, container, false);

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
                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
            }
        });

        Button btnSave = (Button) currentView.findViewById(R.id.new_user_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
                if (validate(view)) {
                    Toast.makeText(getContext(), R.string.newUser_msgSave, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate(View view){
        boolean isValid = true;


        EditText etDocumentNumber = (EditText) currentView.findViewById(R.id.new_user_etDocumentNumber);
        String documentNumber = etDocumentNumber.getText().toString().trim();
//        if(documentNumber.isEmpty()) {
//            isValid = false;
//            etDocumentNumber.setError(getText(R.string.validation_msgRequiredField));
//        } else if(!documentNumber.matches(Constants.regExEmail)) {
//            isValid = false;
//            etDocumentNumber.setError(getText(R.string.validation_msgInvalidEmail));
//        }

        //Número de documento
        if (!validateEmptyField(etDocumentNumber)) {
            isValid = false;
        }
//        if(!documentNumber.matches(Constants.regExEmail)) {
//            isValid = false;
//            etDocumentNumber.setError(getText(R.string.validation_msgInvalidEmail));
//        }

        //Nombres
        EditText etFirstName = (EditText) currentView.findViewById(R.id.new_user_etFirstName);
        if (!validateEmptyField(etFirstName)) {
            isValid = false;
        }

        //Apellidos
        EditText etLastName = (EditText) currentView.findViewById(R.id.new_user_etLastName);
        if (!validateEmptyField(etLastName)) {
            isValid = false;
        }

        //Genero
        RadioGroup rgGender = (RadioGroup) currentView.findViewById(R.id.new_user_rgGender);
        if (rgGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(getContext(), R.string.validation_msgInvalidGender, Toast.LENGTH_LONG).show();
        }

//        //Fecha nacimiento
        DatePicker dpBirthdate = (DatePicker) currentView.findViewById(R.id.new_user_dpBirthdate);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if((currentYear - dpBirthdate.getYear()) < 0) {
            isValid = false;
            Toast.makeText(getContext(), R.string.validation_msgInvalidBirthdate, Toast.LENGTH_LONG).show();
        }

        //Telefono
        EditText etPhoneNumber = (EditText) currentView.findViewById(R.id.new_user_etPhoneNumber);
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (!validateEmptyField(etPhoneNumber)) {
            isValid = false;
        } else if(!phoneNumber.matches(Constants.regExPhoneNumber)) {
            isValid = false;
            etPhoneNumber.setError(getText(R.string.validation_msgInvalidPhone));
        }

        //Correo
        EditText eteMail = (EditText) currentView.findViewById(R.id.new_user_eteMail);
        String eMail = eteMail.getText().toString().trim();
        if (!validateEmptyField(eteMail)) {
            isValid = false;
        } else if(!eMail.matches(Constants.regExEmail)) {
            isValid = false;
            eteMail.setError(getText(R.string.validation_msgInvalidEmail));
        }

        //Tipo de sangre
        EditText etBloodType = (EditText) currentView.findViewById(R.id.new_user_etBloodType);
        if (!validateEmptyField(etBloodType)) {
            isValid = false;
        }

        //Contraseña
        EditText etPassword = (EditText) currentView.findViewById(R.id.new_user_etPassword);
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
}