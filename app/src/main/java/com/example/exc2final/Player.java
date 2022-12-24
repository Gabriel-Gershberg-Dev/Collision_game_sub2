package com.example.exc2final;

import com.google.android.gms.maps.model.LatLng;

public class Player implements Comparable<Player> {
    private int score;
    private LatLng location;

    public Player(int score, LatLng location) {
        this.score = score;
        this.location = location;

    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public int compareTo(Player player) {
        return this.score - player.score;
    }
}
