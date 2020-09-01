package com.example.appclinica.ui.centers;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appclinica.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CenterMapFragment extends Fragment implements OnMapReadyCallback {

    private CenterMapViewModel mViewModel;
    private MapView mapView;
    private GoogleMap googleMap;

    public static CenterMapFragment newInstance() {
        return new CenterMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync();

        return inflater.inflate(R.layout.fragment_center_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CenterMapViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onMapReady(GoogleMap map) {

        Bundle bundle = ((Bundle)this.getArguments());


        LatLng sydney = new LatLng(bundle.getDouble("centerLatitude"), bundle.getDouble("centerLongitude"));
        map.addMarker(new MarkerOptions().position(sydney)
                .title(bundle.getString("centerAddress")));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));

        googleMap = map;

    }
}
