package com.example.exc2final;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class MenuActivity extends AppCompatActivity {
    private AppCompatImageView war_IMG_background;
    private RadioGroup radioModeGroup;
    private RadioGroup radioSpeedGroup;
    private MaterialButton start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        findViews();
        initBackground();
       start.setOnClickListener(view -> {openGamePage();});
    }
    private void findViews(){
         war_IMG_background = findViewById(R.id.war_IMG_background);
         start = findViewById(R.id.menu_btn_start);
         radioSpeedGroup = findViewById(R.id.menu_radioGroupLevel);
         radioModeGroup = findViewById(R.id.menu_radioGroupSensor);

    }
    private void initBackground() {

        Glide.with(this).load("https://eu-wotp.wgcdn.co/dcont/fb/image/lansen_c_wallpaper_640x1136.jpg").into((war_IMG_background));
    }

    private void openGamePage( ) {
        Intent intent = new Intent(this, MainActivity.class);
        MaterialRadioButton modeBtn = findViewById(radioModeGroup.getCheckedRadioButtonId());
        MaterialRadioButton speedBtn = findViewById(radioSpeedGroup.getCheckedRadioButtonId());
        intent.putExtra(MainActivity.KEY_MODE,modeBtn.getText().toString());
        intent.putExtra(MainActivity.KEY_SPEED,speedBtn.getText().toString());
        startActivity(intent);
        finish();
    }
}