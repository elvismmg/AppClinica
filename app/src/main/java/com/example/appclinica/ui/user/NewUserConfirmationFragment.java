package com.example.appclinica.ui.user;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appclinica.R;

public class NewUserConfirmationFragment extends Fragment {

    private View currentView;
    private NavController navController;
    private NewUserConfirmationViewModel mViewModel;

    public static NewUserConfirmationFragment newInstance() {
        return new NewUserConfirmationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_new_user_confirmation, container, false);
        navController = NavHostFragment.findNavController(this);
        loadButtonEvents();
        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewUserConfirmationViewModel.class);
        // TODO: Use the ViewModel
    }


    private void loadButtonEvents() {
        Button btnBack = (Button) currentView.findViewById(R.id.new_user_confirmation_btnLogin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_login);
                //getParentFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
            }
        });
    }
}