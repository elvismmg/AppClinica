package com.example.appclinica.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appclinica.R;

public class PasswordFragment extends Fragment {

    private PasswordViewModel mViewModel;
    private NavController navController;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_password, container, false);
        navController = NavHostFragment.findNavController(this);
        loadButtonEvents(view);
        return view;
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        // TODO: Use the ViewModel
    }*/

    private void loadButtonEvents(View view) {
        Button btnBack = (Button) view.findViewById(R.id.password_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_login);
                //getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
            }
        });

        final EditText etUsername = (EditText) view.findViewById(R.id.password_etUsername);
        Button btnSave = (Button) view.findViewById(R.id.password_btnSend);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
                if (TextUtils.isEmpty(etUsername.getText())) {
                    validateControls();
                    etUsername.setError(getText(R.string.passwordRecovery_msgEmptyUsername));
                } else {
                    Toast.makeText(getContext(), R.string.passwordRecovery_msgSend, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateControls(){
        return true;
    }

}