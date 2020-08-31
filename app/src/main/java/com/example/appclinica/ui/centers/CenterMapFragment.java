package com.example.appclinica.ui.centers;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appclinica.R;

public class CenterMapFragment extends Fragment {

    private CenterMapViewModel mViewModel;

    public static CenterMapFragment newInstance() {
        return new CenterMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            //container.removeAllViews();
            container.clearDisappearingChildren();
        }
        return inflater.inflate(R.layout.fragment_center_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CenterMapViewModel.class);
        // TODO: Use the ViewModel
    }

}