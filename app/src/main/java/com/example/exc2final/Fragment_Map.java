package com.example.exc2final;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Fragment_Map extends Fragment {


    private PlayersList playersList = SPV3.getInstance().loadList();
    private ArrayList<LatLng> locationList = playersList.getLocationList();
    private LatLng israelLatLng = new LatLng(31, 35);
    SupportMapFragment supportMapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setAllTopLocations();
        return view;
    }


    private void setAllTopLocations() {
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                for (int i = 0; i < playersList.getPlayersList().size(); i++) {
                    googleMap.addMarker(new MarkerOptions().position(playersList.getPlayersList().get(i).getLocation()));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(playersList.getPlayersList().get(i).getLocation()));
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(israelLatLng, 6));


            }
        });

    }

    public void zoom(LatLng location) {
        ;
        supportMapFragment.getMapAsync(googleMap -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));


        });

    }


}
