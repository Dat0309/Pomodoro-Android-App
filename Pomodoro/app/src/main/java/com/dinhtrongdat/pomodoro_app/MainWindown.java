package com.dinhtrongdat.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainWindown extends AppCompatActivity {

    Button btnStart, btnPause;
    ImageView imgView;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windown);
        initUI();
    }

    private void initUI(){
        btnStart = (Button) findViewById(R.id.btn_start);
        btnPause = (Button) findViewById(R.id.btn_pause);
        imgView = (ImageView) findViewById(R.id.imgView);

        btnPause.setAlpha(0);
        anim = AnimationUtils.loadAnimation(this,R.anim.img_anim);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnStart.animate().alpha(0).setDuration(300).start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                btnStart.animate().alpha(1).setDuration(300).start();
            }
        });

    }

}