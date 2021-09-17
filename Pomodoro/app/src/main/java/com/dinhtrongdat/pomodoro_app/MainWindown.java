package com.dinhtrongdat.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainWindown extends AppCompatActivity {

    Button btnStart, btnPause, btnBreak;
    ImageView imgView;
    Animation anim;
    TextView txtCountdown;
    ImageButton btnUp, btnDown;
    private long BREAK_TIME = 600000;
    public static ArrayList<String> arrName;
    private CountDownTimer mCountDownTimer;
    private boolean mTimeIsRunning;
    private boolean mBreakTimeIsRunning;
    private long mTimeLeftInMillis = DataManager.getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windown);
        initUI();
    }

    private void initUI() {
        btnStart = (Button) findViewById(R.id.btn_start);
        btnPause = (Button) findViewById(R.id.btn_pause);
        imgView = (ImageView) findViewById(R.id.imgView);
        txtCountdown = (TextView) findViewById(R.id.txt_countdown);
        btnUp = (ImageButton) findViewById(R.id.btn_up);
        btnDown = (ImageButton) findViewById(R.id.btn_down);
        btnBreak = (Button) findViewById(R.id.btn_break);
        updateCountDownText();

        btnPause.setAlpha(0);
        btnBreak.setAlpha(0);
        anim = AnimationUtils.loadAnimation(this, R.anim.translation_icon);
        imgView.setAnimation(anim);

        String[] mangTen = getResources().getStringArray(R.array.arr_plant);
        arrName = new ArrayList<>(Arrays.asList(mangTen));

        Collections.shuffle(arrName);
        int idHinh = getResources().getIdentifier(arrName.get(2), "drawable", getPackageName());
        imgView.setImageResource(idHinh);
        imgView.setAnimation(anim);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setEnabled(true);
                startTime();
                Collections.shuffle(arrName);
                int idimg = getResources().getIdentifier(arrName.get(2), "drawable", getPackageName());
                imgView.setImageResource(idimg);
                btnPause.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnStart.animate().alpha(0).setDuration(300).start();
                btnUp.animate().alpha(0).setDuration(300).start();
                btnDown.animate().alpha(0).setDuration(300).start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimeIsRunning) {
                    pauseTime();
                    btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                    btnStart.animate().alpha(1).setDuration(300).start();
                    btnUp.animate().alpha(1).setDuration(300).start();
                    btnDown.animate().alpha(1).setDuration(300).start();
                    btnPause.setEnabled(false);
                    btnBreak.setEnabled(false);
                    resetTime();
                }
                else if(mBreakTimeIsRunning){
                    pauseTime();
                    btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                    btnStart.animate().alpha(1).setDuration(300).start();
                    btnUp.animate().alpha(1).setDuration(300).start();
                    btnDown.animate().alpha(1).setDuration(300).start();
                    btnPause.setEnabled(false);
                    btnStart.setEnabled(true);
                    resetTime();
                }
            }
        });

        btnBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBreakTime();
                btnPause.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnStart.animate().alpha(0).setDuration(300).start();
                btnBreak.animate().alpha(0).setDuration(300).start();
                btnUp.animate().alpha(0).setDuration(300).start();
                btnDown.animate().alpha(0).setDuration(300).start();
                btnStart.setEnabled(false);
                btnPause.setEnabled(true);
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseTime();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseTime();
            }
        });
    }

    private void startTime() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeIsRunning = true;
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeIsRunning = false;
                mBreakTimeIsRunning = true;
                breakTime();
                btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                btnBreak.animate().alpha(1).setDuration(300).start();
                btnUp.animate().alpha(1).setDuration(300).start();
                btnDown.animate().alpha(1).setDuration(300).start();
                btnBreak.setEnabled(true);
                playSound();
            }
        }.start();
        mTimeIsRunning = true;
    }
    private void startBreakTime(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mBreakTimeIsRunning = false;
                mTimeIsRunning = true;
                resetTime();
                btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                btnStart.animate().alpha(1).setDuration(300).start();
                btnUp.animate().alpha(1).setDuration(300).start();
                btnDown.animate().alpha(1).setDuration(300).start();
                btnStart.setEnabled(true);
            }
        }.start();
    }
    private void updateCountDownText() {
        long hours = TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis));
        long seccons = TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis));

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seccons);
        txtCountdown.setText(timeLeftFormatted);
    }
    private void pauseTime(){
        mCountDownTimer.cancel();
    }
    private void resetTime(){
        mTimeLeftInMillis = DataManager.getTime();
        updateCountDownText();
    }
    private void breakTime(){
        mTimeLeftInMillis = BREAK_TIME;
        updateCountDownText();
    }
    private void increaseTime(){
        mTimeLeftInMillis += 300000;
        DataManager.setTime(mTimeLeftInMillis);
        updateCountDownText();
    }
    private void decreaseTime(){
        mTimeLeftInMillis -= 300000;
        DataManager.setTime(mTimeLeftInMillis);
        updateCountDownText();
    }
    private void playSound(){
        final MediaPlayer alarmSound = MediaPlayer.create(this, R.raw.melody1);
        alarmSound.start();
    }
}