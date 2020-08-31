package com.example.appclinica.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appclinica.MainActivity;
import com.example.appclinica.R;
import com.example.appclinica.ui.user.NewUserFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loadButtonEvents(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadButtonEvents(View view) {
        Button btnLogin = (Button)view.findViewById(R.id.login_btnLogin);
        final EditText etUsername = (EditText) view.findViewById(R.id.login_etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.login_etPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;

                if (TextUtils.isEmpty(etUsername.getText())) {
                    error = true;
                    etUsername.setError(getText(R.string.login_forgot_emptyUsername));
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    error = true;
                    etPassword.setError(getText(R.string.login_forgot_emptyPassword));
                }
                if (error == false){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });

        TextView lnkForgotPassword = (TextView)view.findViewById(R.id.login_tvForgotPassword);
        lnkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new PasswordFragment()).commit();
            }
        });

        TextView lnkRegister = (TextView)view.findViewById(R.id.login_tvRegistrar);
        lnkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new NewUserFragment()).commit();
            }
        });
    }

}