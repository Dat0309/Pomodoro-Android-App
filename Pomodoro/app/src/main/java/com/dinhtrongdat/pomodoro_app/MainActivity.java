package com.dinhtrongdat.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtSplash, subSplas;
    ImageView imgSplash;
    Animation imgAnim, textAnim;
    int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();


    }
    private void initUI(){
        txtSplash = (TextView) findViewById(R.id.txt_splash);
        subSplas = (TextView) findViewById(R.id.txt_sub_splash);
        imgSplash = (ImageView) findViewById(R.id.img_splash);

        imgAnim = AnimationUtils.loadAnimation(this,R.anim.img_anim);
        textAnim = AnimationUtils.loadAnimation(this, R.anim.txt_anim);

        imgSplash.setAnimation(imgAnim);
        txtSplash.setAnimation(textAnim);
        subSplas.setAnimation(textAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainWindown.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(imgSplash,"logo_image");
                pairs[1] = new Pair<View, String>(txtSplash,"logo_text");
                pairs[2] = new Pair<View, String>(subSplas, "logo_text");

                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, option.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}