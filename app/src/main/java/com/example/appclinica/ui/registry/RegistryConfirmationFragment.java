package com.example.appclinica.ui.registry;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appclinica.R;

public class RegistryConfirmationFragment extends Fragment {

    private RegistryConfirmationViewModel mViewModel;
    private NavController navController;

    public static RegistryConfirmationFragment newInstance() {
        return new RegistryConfirmationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registry_confirmation, container, false);

        navController = NavHostFragment.findNavController(this);

        root.findViewById(R.id.RegistryConfirmation_btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry);            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryConfirmationViewModel.class);
        // TODO: Use the ViewModel
    }

}