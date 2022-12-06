package com.example.exc2final;

import java.util.ArrayList;

public class DataManager {


    public static ArrayList<ArrayList<PicObject>> BuildGrid(int rows, int col) {
        ArrayList<ArrayList<PicObject>> grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            grid.add(new ArrayList());
            for (int j = 0; j < col; j++) {
                if (i != rows - 1)
                    grid.get(i).add(new PicObject().setType(PicObject.Type.ROCK));
                else
                    grid.get(i).add(new PicObject().setType(PicObject.Type.CAR));


            }
        }
        return grid;
    }


}
