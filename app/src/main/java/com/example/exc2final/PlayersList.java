package com.example.exc2final;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlayersList {
    private ArrayList<Player> playersList;

    public PlayersList(){}

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public PlayersList setPlayersList(ArrayList<Player> playersList) {
        this.playersList = playersList;
        return this;
    }
    public void addPlayer(Player player){
        playersList.add(player);
    }
    public void removePlayer(Player player){
        playersList.remove(player);
    }
    public ArrayList<LatLng> getLocationList(){
        ArrayList<LatLng> location= new ArrayList<>();
        for (int i = 0; i <playersList.size() ; i++) {
            location.add(playersList.get(i).getLocation());
        }
        return location;
    }

}
