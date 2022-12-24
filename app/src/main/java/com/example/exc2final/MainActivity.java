package com.example.exc2final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_SPEED = "KEY_SPEED";
    private AppCompatImageView sea_IMG_background;
    private FloatingActionButton game_BTN_Right;
    private FloatingActionButton game_BTN_Left;
    private MaterialTextView game_TXT_score;
    ArrayList<ArrayList<View>> viewsArray;
    private ArrayList<View> hearts;
    private GameManager game;
    private int delay = 0;
    private Timer timer;
    private String modeSelected;
    private String speedSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SPV3.init(this);
        getMenuValues();

        gameInit();


    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        timer = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer == null)
            startTimer();

    }

    private void getMenuValues() {
        Intent previousIntent = getIntent();
        modeSelected = previousIntent.getExtras().getString(KEY_MODE);
        speedSelected = previousIntent.getExtras().getString(KEY_SPEED);
    }

    private void openScorePage(int score) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.KEY_SCORE, score);
        startActivity(intent);
        finish();
    }

    private void setButtons() {
        game_BTN_Right.setVisibility(View.VISIBLE);
        game_BTN_Left.setVisibility(View.VISIBLE);
        game_BTN_Right.setOnClickListener(v -> clicked(GameManager.Direction.RIGHT));
        game_BTN_Left.setOnClickListener(v -> clicked(GameManager.Direction.LEFT));
    }

    private void setSensor() {
        SensorMovement.CallBack_steps callBack_steps = new SensorMovement.CallBack_steps() {
            @Override
            public void moveLeft() {
                clicked(GameManager.Direction.LEFT);

            }

            @Override
            public void moveRight() {
                clicked(GameManager.Direction.RIGHT);
            }

        };
        SensorMovement sensorMovement = new SensorMovement(this, callBack_steps);
        sensorMovement.start();
    }

    private void setSpeed() {
        switch (speedSelected) {
            case "Slow":
                delay = 2000;
                break;
            case "Medium":
                delay = 1000;
                break;
            case "High":
                delay = 500;
                break;
            default:
                break;
        }
    }

    private void setMode() {
        switch (modeSelected) {
            case "Sensor":
                setSensor();
                break;
            case "Buttons":
                setButtons();
                break;
            default:
                break;
        }
    }

    private void clicked(GameManager.Direction direction) {
        int row = game.getObjects().size() - 1;
        int col = 0;
        for (int i = 0; i < game.getObjects().get(row).size(); i++) {
            if (game.getObjects().get(row).get(i).getIsOn())
                col = i;
        }
        game.move(direction, row, col);


    }

    private ArrayList<View> getLinearLayoutChild(View view) {
        int layoutChildren = ((LinearLayout) view).getChildCount();
        ArrayList<View> children = new ArrayList<>();

        for (int i = 0; i < layoutChildren; i++)
            children.add((((LinearLayout) view).getChildAt(i)));

        return children;
    }

    private ArrayList<ArrayList<View>> findGridViews() {
        LinearLayout layout = findViewById(R.id.main_linearLayout_1);
        ArrayList<View> mainLinear = getLinearLayoutChild(layout);
        ArrayList<ArrayList<View>> grid = new ArrayList<>();

        for (int i = 0; i < getLinearLayoutChild(layout).size(); i++)
            grid.add(getLinearLayoutChild(mainLinear.get(i)));

        return grid;

    }

    private void initViewsGrid(ArrayList<ArrayList<PicObject>> objectsArray) {
        for (int i = 0; i < objectsArray.size(); i++) {
            for (int j = 0; j < objectsArray.get(i).size(); j++) {
                objectsArray.get(i).get(j).setImageRes((ShapeableImageView) viewsArray.get(i).get(j)).setImage();
                objectsArray.get(i).get(j).setIsOn(false);

            }

        }

    }

    private void findAllViews() {
        viewsArray = findGridViews();
        hearts = getLinearLayoutChild(findViewById(R.id.game_Layout_Hearts));
        game_BTN_Right = findViewById(R.id.main_FAB2_Right);
        game_BTN_Left = findViewById(R.id.main_FAB1_Left);
        sea_IMG_background = findViewById(R.id.sea_IMG_background);
        game_TXT_score = findViewById(R.id.game_TXT_score);

    }

    private void initHeatsView() {
        for (int i = 0; i < hearts.size(); i++)
            Glide.with(this).load("https://www.freepngimg.com/download/russia/86808-head-putin-vladimir-of-jaw-president-russia.png").into((ShapeableImageView) hearts.get(i));
    }

    private void initBackground() {

        Glide.with(this).load("https://images.hdqwalls.com/download/war-thunder-5k-2017-2h-1125x2436.jpg").into((sea_IMG_background));
    }


    private void onTotalLose() {
        if (game.isLose()) {
            toast("Game over!!!");
            timer.cancel();
            openScorePage(game.getScore());
        }
    }

    private void updateLifeUI() {
        for (int h = 0; h < game.getWrong(); h++)
            hearts.get(hearts.size() - 1 - h).setVisibility(View.INVISIBLE);
    }

    private void onCollision(PicObject.Type type) {
        switch (type) {
            case ROCK:
                game.setWrong(game.getWrong() + 1);
                onTotalLose();
                updateLifeUI();
                vibrate();
                if (game.getWrong() < hearts.size() - 1)
                    toast("Oops!");
                makeVoice(R.raw.explosion);
                initRound();
                break;
            case COIN:
                game.addCoinScore();
                updateScoreUi();
                initRound();
                break;
        }


    }

    private void updateScoreUi() {
        game_TXT_score.setText("Score: " + game.getScore());
    }

    private void setInitialCar() {
        game.getObjects().get(game.getObjects().size() - 1).get(0).setIsOn(true);

    }


    private void initRound() {
        game.buildNewRoundGrid();
        initViewsGrid(game.getObjects());
        game.initialState();
        setInitialCar();


    }

    private void gameInit() {
        findAllViews();
        game = new GameManager(hearts.size(), viewsArray.size(), viewsArray.get(0).size());
        initBackground();
        initHeatsView();
        initRound();
        setMode();
        setSpeed();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> gameProcess());
            }
        }, delay, delay);
    }

    private void makeVoice(int voiceFile) {
        final MediaPlayer mp;
        mp = MediaPlayer.create(this, voiceFile);
        mp.start();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        v.vibrate(200);
    }

    private void toast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_LONG).show();
    }


    private void gameProcess() {
        boolean[][] b = game.getCurrentOn();
        boolean collision = false;
        game.addTimeScore();
        updateScoreUi();
        GameManager.Direction d = GameManager.Direction.DOWN;
        for (int i = 0; i < game.getObjects().size() && !collision; i++) {
            for (int j = 0; j < game.getObjects().get(i).size(); j++) {
                if (b[i][j] == true) {
                    game.move(d, i, j);
                    if (game.checkCollision(i, j) != null) {
                        onCollision(game.getObjects().get(i).get(j).getType());
                        break;
                    }


                }


            }

        }
    }


}











