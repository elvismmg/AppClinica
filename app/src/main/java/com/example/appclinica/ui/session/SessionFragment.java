package com.example.appclinica.ui.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appclinica.R;

public class SessionFragment extends Fragment {

    private SessionViewModel sessionViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sessionViewModel =
                ViewModelProviders.of(this).get(SessionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_session, container, false);
        navController = NavHostFragment.findNavController(this);

        /*View root = inflater.inflate(R.layout.fragment_session, container, false);
        final TextView textView = root.findViewById(R.id.text_session);
        sessionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */

        navController.navigate(R.id.nav_login);

        return root;
    }


}