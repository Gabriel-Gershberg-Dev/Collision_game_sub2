package com.example.exc2final;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {


    private  final int COIN_SCORE = 10 ;
    private int wrong = 0;
    private int life;
    public enum Direction {DOWN, RIGHT, LEFT}
    private ArrayList<ArrayList<PicObject>> objects;
    private int score=0;
    private final int NUMBER_OF_ROCKS=2;
    private final int NUMBER_OF_COINS=2;


    public GameManager(int life, int gridRows, int gridCols) {
        this.life = life;
        objects = DataManager.BuildGrid(gridRows, gridCols);
    }

    public void buildNewRoundGrid() {
        objects = DataManager.BuildGrid(objects.size(), objects.get(0).size());
    }


    public int getWrong() {
        return wrong;
    }


    public void initialState() {
        offAllObjects();
        for (int i = 0; i < NUMBER_OF_COINS; i++) {
            insertObjectToEmpty(PicObject.Type.COIN);

        }
        for (int i = 0; i < NUMBER_OF_ROCKS; i++) {
            insertObjectToEmpty(PicObject.Type.ROCK);

        }

    }

    void insertObjectToEmpty(PicObject.Type type) {
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
                objects.get(randomNumRow).get(randomNumCol).setType(type).setImage();
                objects.get(randomNumRow).get(randomNumCol).setIsOn(true);
                flag = true;
            }
        }
    }

    private void offAllObjects() {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.get(i).size(); j++) {
                objects.get(i).get(j).setIsOn(false);
            }

        }
    }


    public PicObject.Type checkCollision(int viewRow, int viewCol) {
        boolean[][] b = getCurrentOn();
        if (viewRow == objects.size() - 2 &&
                (objects.get(viewRow).get(viewCol).getIsOn() && objects.get(viewRow + 1).get(viewCol).getIsOn()) &&
                objects.get(viewRow).get(viewCol).getType() != objects.get(viewRow + 1).get(viewCol).getType()) {

            return objects.get(viewRow).get(viewCol).getType() ;
        }

        return null;

    }

    public void addCoinScore(){
        this.score+=COIN_SCORE;
    }

    public boolean[][] getCurrentOn() {
        boolean[][] isOnMat = new boolean[objects.size()][objects.get(0).size()];
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.get(0).size(); j++) {
                isOnMat[i][j] = objects.get(i).get(j).getIsOn();
            }
        }
        return isOnMat;
    }


    public void move(Direction direction, int viewRow, int viewCol ) {

        switch (direction) {
            case DOWN:
                if (objects.get(viewRow).get(viewCol).getType() != PicObject.Type.CAR) {
                    if (viewRow <= objects.size() - 2 && !objects.get(viewRow + 1).get(viewCol).getIsOn() && checkCollision(viewRow, viewCol)==null) {
                        objects.get(viewRow).get(viewCol).setIsOn(false);
                        objects.get(viewRow + 1).get(viewCol).setIsOn(true);
                        objects.get(viewRow + 1).get(viewCol).setType(objects.get(viewRow).get(viewCol).getType()).setImage();

                    } else if (viewRow == objects.size() - 1) {
                        objects.get(viewRow).get(viewCol).setIsOn(false);
                        insertObjectToEmpty( objects.get(viewRow).get(viewCol).getType());
                        objects.get(viewRow).get(viewCol).setType(PicObject.Type.CAR).setImage();

                    }
                }
                break;
            case RIGHT:
                if (viewCol + 1 < objects.get(viewCol).size() && objects.get(viewRow).get(viewCol).getType() == objects.get(viewRow).get(viewCol + 1).getType()) {
                    objects.get(viewRow).get(viewCol).setIsOn(false);
                    objects.get(viewRow).get(viewCol + 1).setIsOn(true);
                }

                break;
            case LEFT:
                if (viewCol - 1 >= 0 && objects.get(viewRow).get(viewCol).getType() == objects.get(viewRow).get(viewCol - 1).getType()) {
                    objects.get(viewRow).get(viewCol).setIsOn(false);
                    objects.get(viewRow).get(viewCol - 1).setIsOn(true);
                }
                break;


        }
    }


    public ArrayList<ArrayList<PicObject>> getObjects() {
        return objects;
    }

    public int getScore(){return score;}
    public boolean isLose() {
        return wrong == life;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }


}
