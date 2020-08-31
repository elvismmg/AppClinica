package com.example.appclinica.ui.registry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appclinica.MainActivity;
import com.example.appclinica.R;

public class RegistryPaso1Fragment extends Fragment {

    private RegistryPaso1ViewModel registryPaso1ViewModel;

    public static RegistryPaso1Fragment newInstance() {
        return new RegistryPaso1Fragment();
    }

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

        final Button button1 = root.findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_registry_paso1, new RegistryPaso2Fragment() );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }


}