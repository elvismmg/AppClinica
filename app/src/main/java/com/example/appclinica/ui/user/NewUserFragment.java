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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appclinica.R;
import com.example.appclinica.ui.home.LoginFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class NewUserFragment extends Fragment {

    private NewUserViewModel mViewModel;

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        loadSexComboBox(view);
        loadBirthdate(view);
        loadButtonEvents(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewUserViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadSexComboBox(View view) {
        Spinner spinner = (Spinner)view.findViewById(R.id.spSex);
        ArrayList<String> sexList = new ArrayList<>();
        sexList.add("Masculino");
        sexList.add("Femenino");

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sexList);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sexAdapter);
    }

    private void loadBirthdate(View view) {
        final EditText dpBirthdate = (EditText)view.findViewById(R.id.dpBirthdate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);


        dpBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dpBirthdate.setText(year + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private  void loadButtonEvents(View view) {
        Button btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
            }
        });

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
                Toast.makeText(getContext(), R.string.newUser_msgSave, Toast.LENGTH_LONG).show();
            }
        });
    }
}