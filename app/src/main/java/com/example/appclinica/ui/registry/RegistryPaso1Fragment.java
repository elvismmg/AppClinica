package com.example.appclinica.ui.registry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appclinica.R;

public class RegistryPaso1Fragment extends Fragment {

    private RegistryPaso1ViewModel registryPaso1ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registryPaso1ViewModel =
                ViewModelProviders.of(this).get(RegistryPaso1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registry_paso1, container, false);
        final TextView textView = root.findViewById(R.id.text_registry);
        registryPaso1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}