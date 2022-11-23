package com.example.exc1final;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class DataManager {
    private static Context context;

    public DataManager setContext(Context context) {
        this.context = context;
        return this;
    }
    public static ArrayList<ArrayList<PicObject>> BuildGrid(int rows, int col){
        ArrayList<ArrayList<PicObject>> grid = new ArrayList<>();
        for (int i = 0; i <rows ; i++) {
            grid.add(new ArrayList());
            for (int j = 0; j <col ; j++) {
                if( i != rows-1)
                    grid.get(i).add(new PicObject().setType(PicObject.Type.ROCK));
                else
                    grid.get(i).add(new PicObject().setType(PicObject.Type.CAR));


            }
        }
        return grid;
    }



}
