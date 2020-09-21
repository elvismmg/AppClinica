package com.example.appclinica.ui.centers;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CentersDAO;
import com.example.appclinica.ui.dao.DAOException;
import com.example.appclinica.ui.models.CenterModel;
import com.example.appclinica.ui.registry.RegistryPaso2Fragment;
import com.example.appclinica.ui.user.ChangeUserFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

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

        View view = inflater.inflate(R.layout.fragment_center_map, container, false);

        loadButtonEvents(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.center_map_tvTitle);
        Bundle bundle = ((Bundle)this.getArguments());

        if (bundle != null) {
            tvTitle.setText(bundle.getString("centerName"));
        }

        return view;
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
        if (bundle == null) {
            return;
        }

        if (bundle.getString("loadAllCenters") == "1") {

            CentersDAO centersDAO = new CentersDAO(getActivity().getBaseContext());
            try {
                List<CenterModel> centerList = centersDAO.GetAll();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (CenterModel item : centerList) {

                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).title(item.getName());
                    map.addMarker(markerOptions);
                    builder.include(markerOptions.getPosition());
                }

                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 120);
                map.animateCamera(cu);

            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        else {
            LatLng latLng = new LatLng(bundle.getDouble("centerLatitude"), bundle.getDouble("centerLongitude"));
            map.addMarker(new MarkerOptions().position(latLng)
                    .title(bundle.getString("centerAddress")));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        }

        googleMap = map;

    }

    public void loadButtonEvents(View view){
        Button btnBack = (Button) view.findViewById(R.id.center_map_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().add(R.id.layout_centers_mainFrame, new CentersFragment()).commit();



            }
        });


    }
}
