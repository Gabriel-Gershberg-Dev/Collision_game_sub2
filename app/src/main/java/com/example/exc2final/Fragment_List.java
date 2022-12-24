package com.example.exc2final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Fragment_List extends Fragment {

    private ListView players_list_view;
    private CallBack_UserProtocol callBack_userProtocol;
    private PlayersList playersList = SPV3.getInstance().loadList();
    private ArrayList<String> playersStrings;
    private AppCompatImageView scoreBackground;
    public void setCallBack_userProtocol(CallBack_UserProtocol callBack_userProtocol) {
        this.callBack_userProtocol = callBack_userProtocol;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initBackground(view);
        Collections.sort(playersList.getPlayersList());
        convertToStrings();
        setList();
        return view;
    }

    private void setList() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.list_row_design, playersStrings);
        players_list_view.setAdapter(arrayAdapter);
        players_list_view.setOnItemClickListener((adapterView, view, i, l) -> {
            if (callBack_userProtocol != null) {
                callBack_userProtocol.zoomLocation(playersList.getPlayersList().get(i).getLocation());
            }

        });
    }

    private void convertToStrings() {
        playersStrings = new ArrayList<>();
        for (int i = 0; i < playersList.getPlayersList().size(); i++) {
            playersStrings.add((i + 1) + ". Score: " + playersList.getPlayersList().get(i).getScore());
        }
    }


    private void findViews(View view) {
        players_list_view = view.findViewById(R.id.list_players);
        scoreBackground=view.findViewById(R.id.score_list_background);

    }
    private void initBackground(View view){
        Glide.with(getActivity()).load("https://wallpaperaccess.com/full/1205014.jpg").into((scoreBackground));    }
}
