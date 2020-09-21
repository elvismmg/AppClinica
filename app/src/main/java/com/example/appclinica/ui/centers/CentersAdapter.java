package com.example.appclinica.ui.centers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appclinica.R;
import com.example.appclinica.ui.models.CenterModel;
import com.example.appclinica.ui.registry.RegistryPaso2Fragment;

import java.security.PrivateKey;
import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.MyViewHolder> {

    private Context context;
    private List<CenterModel> centersList;

    public CentersAdapter(List<CenterModel> list, Context context) {
        this.centersList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.centers_row_recycler, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CenterModel centerModel = centersList.get(position);
        holder.tvCenterName.setText(centerModel.getName());
        holder.tvCenterAddress.setText(centerModel.getAddress());
        holder.ivShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CenterMapFragment centerMapFragment = new CenterMapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("centerName", centerModel.getName());
                bundle.putString("centerAddress", centerModel.getAddress());
                bundle.putDouble("centerLatitude", centerModel.getLatitude());
                bundle.putDouble("centerLongitude", centerModel.getLongitude());
                bundle.putString("loadAllCenters", "0");
                centerMapFragment.setArguments(bundle);

                //centerMapFragment.getParentFragmentManager().beginTransaction().replace(R.id.layout_main, centerMapFragment).commit();
                //FragmentActivity fragmentActivity = (context)FragmentActivity;
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                //centerMapFragment.getParentFragmentManager().beginTransaction().replace(R.id.layout_main, centerMapFragment).commit();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Fragment centers = fragmentManager.findFragmentById(R.id.layout_centers);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.layout_centers_mainFrame, centerMapFragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return centersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCenterName, tvCenterAddress;
        ImageView ivShowMap;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCenterName = (TextView)itemView.findViewById(R.id.centers_row_tvCenterName);
            tvCenterAddress = (TextView)itemView.findViewById(R.id.centers_row_tvCenterAddress);
            ivShowMap = (ImageView)itemView.findViewById(R.id.centers_row_ivShowMap);
        }
    }
}
