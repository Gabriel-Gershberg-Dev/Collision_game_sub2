package com.example.exc2final;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ScoreActivity extends AppCompatActivity implements LocationListener {

    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String SP_KEY_NAME = "SP_KEY_LIST";
    private int NUM_OF_RECORDS = 5;
    private SPV3 storage = SPV3.getInstance();
    private int score;
    private PlayersList storedPlayersList;
    private Fragment_Map fragment_map;
    private Fragment_List fragment_list;
    private LatLng locationDetails;

    CallBack_UserProtocol callBack_userProtocol = new CallBack_UserProtocol() {
        @Override
        public void zoomLocation(LatLng location) {
            fragment_map.zoom(location);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        checkLocation();


    }

    private void checkLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else
            init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        storage.clear();
                        init();

                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

    private void init() {
        setLocation();
        getPreviousData();
        Log.d("Score", "" + score);
        loadPlayerList();
        storePlayerDetails();
        setFragments();
    }

    private void getPreviousData() {
        Intent previousIntent = getIntent();
        score = previousIntent.getExtras().getInt(KEY_SCORE);
    }


    private void setFragments() {
        fragment_map = new Fragment_Map();
        fragment_list = new Fragment_List();
        fragment_list.setCallBack_userProtocol(callBack_userProtocol);
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map, fragment_map).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();


    }

    private void loadPlayerList() {
        storedPlayersList = storage.loadList();
    }

    private void storePlayerDetails() {
        addPlayerToList();
        storage.storePlayersList(storedPlayersList);
    }

    private void setLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager;
            locationManager = (LocationManager) getSystemService(ScoreActivity.this.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            onLocationChanged(location);
        }

    }


    private void addPlayerToList() {
        if (storedPlayersList.getPlayersList().size() >= NUM_OF_RECORDS) {
            Player lowestPlayer = findLowestScore();
            if (score > lowestPlayer.getScore() && !scoreExists()) {
                storedPlayersList.getPlayersList().remove(lowestPlayer);
                storedPlayersList.addPlayer(new Player(score, locationDetails));
            }
        } else if (!scoreExists())
            storedPlayersList.addPlayer(new Player(score, locationDetails));

    }


    private Player findLowestScore() {
        Player minScorePlayer = storedPlayersList.getPlayersList().get(0);
        for (int i = 0; i < storedPlayersList.getPlayersList().size(); i++) {
            if (storedPlayersList.getPlayersList().get(i).getScore() < minScorePlayer.getScore())
                minScorePlayer = storedPlayersList.getPlayersList().get(i);

        }
        return minScorePlayer;
    }

    private boolean scoreExists() {
        for (int i = 0; i < storedPlayersList.getPlayersList().size(); i++) {
            if (storedPlayersList.getPlayersList().get(i).getScore() == score)
                return true;
        }
        return false;

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationDetails = new LatLng(location.getLatitude(), location.getLongitude());
    }

}