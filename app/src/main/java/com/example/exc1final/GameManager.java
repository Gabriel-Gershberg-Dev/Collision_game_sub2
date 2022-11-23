package com.example.exc1final;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {


    private int wrong = 0;
    private int life;
    public enum Direction{DOWN,RIGHT,LEFT}
    private Direction direction;
    private int numOfRocks=0;
    private ArrayList<ArrayList<PicObject>> objects;



    public GameManager(int life, int gridRows, int gridCols, int numOfRocks) {
        this.life = life;
        this.numOfRocks=numOfRocks;
        objects = DataManager.BuildGrid(gridRows,gridCols);
    }
    public void buildNewRoundGrid(){
        objects = DataManager.BuildGrid(objects.size(),objects.get(0).size());
    }


    public int getWrong() {
        return wrong;
    }


    public void initialState(int numberOfRocks){
        offAllObjects();
        Random rand = new Random(500);
        for (int i = 0; i <numberOfRocks ; i++) {
            insertRockToEmpty();

        }
    }
    void insertRockToEmpty() {
        boolean flag = false;
        while (!flag) {

            int min = 0;
            int maxRow = objects.size() - 3;
            int maxCol = objects.get(0).size() - 1;
            int randomNumRow = ThreadLocalRandom.current().nextInt(min, maxRow + 1);
            int randomNumCol = ThreadLocalRandom.current().nextInt(min, maxCol + 1);
            if (objects.get(randomNumRow).get(randomNumCol).getIsOn() == true)
                continue;
            else {
                objects.get(randomNumRow).get(randomNumCol).setIsOn(true);
                flag = true;
            }
        }
    }
    private void offAllObjects(){
        for (int i = 0; i <objects.size() ; i++) {
            for (int j = 0; j <objects.get(i).size() ; j++) {
                objects.get(i).get(j).setIsOn(false);
            }

        }
    }



    public boolean checkCollision (int viewRow, int viewCol) {
        boolean[][] b= getCurrentOn();
        if (viewRow == objects.size() - 2 &&
                ( objects.get(viewRow).get(viewCol).getIsOn() && objects.get(viewRow + 1).get(viewCol).getIsOn()) &&
                objects.get(viewRow).get(viewCol).getType() != objects.get(viewRow + 1).get(viewCol).getType())
            {

                return true;
            }

            return false;

        }
        public boolean[][] getCurrentOn(){
            boolean[][] isOnMat=new boolean[objects.size()][objects.get(0).size()];
            for (int i = 0; i <objects.size() ; i++) {
                for (int j = 0; j < objects.get(0).size(); j++) {
                    isOnMat[i][j]=objects.get(i).get(j).getIsOn();
                }
            }
            return isOnMat;
        }


    public void move(Direction direction, int viewRow, int viewCol) {

        switch (direction) {
            case DOWN:
                if(objects.get(viewRow).get(viewCol).getType()!= PicObject.Type.CAR) {
                    if (viewRow < objects.size() - 2 && !objects.get(viewRow + 1).get(viewCol).getIsOn()) {
                        objects.get(viewRow).get(viewCol).setIsOn(false);
                        objects.get(viewRow + 1).get(viewCol).setIsOn(true);

                    } else if (viewRow == objects.size() - 2 && !checkCollision(viewRow, viewCol)) {
                        objects.get(viewRow + 1).get(viewCol).setType(PicObject.Type.ROCK);
                        objects.get(viewRow + 1).get(viewCol).setImage();
                        objects.get(viewRow).get(viewCol).setIsOn(false);
                        objects.get(viewRow + 1).get(viewCol).setIsOn(true);
                    } else if (viewRow == objects.size() - 1) {
                        objects.get(viewRow).get(viewCol).setType(PicObject.Type.CAR);
                        objects.get(viewRow).get(viewCol).setIsOn(false);
                        objects.get(viewRow).get(viewCol).setImage();
                        insertRockToEmpty();
                    }
                }
                break;
            case RIGHT:
                if (viewCol + 1 < objects.get(viewCol).size()) {
                    objects.get(viewRow).get(viewCol).setIsOn(false);
                    objects.get(viewRow).get(viewCol + 1).setIsOn(true);
                }

                break;
            case LEFT:
                if (viewCol - 1 >= 0) {
                    objects.get(viewRow).get(viewCol).setIsOn(false);
                    objects.get(viewRow).get(viewCol - 1).setIsOn(true);
                }
                break;


        }
    }

    public ArrayList<ArrayList<PicObject>> getObjects(){return objects;}

    public void setSate( int row, int col, boolean state){
        objects.get(row).get(col).setIsOn(state);
    }

    public boolean isLose() {
        return wrong == life;
    }
    public void setWrong(int wrong){
        this.wrong=wrong;
    }




}
