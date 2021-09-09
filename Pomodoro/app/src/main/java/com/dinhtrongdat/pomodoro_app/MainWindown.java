package com.dinhtrongdat.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainWindown extends AppCompatActivity {

    Button btnStart, btnPause;
    ImageView imgView;
    Animation anim;
    TextView txtCountdown;
    ImageButton btnUp, btnDown;
    private static long START_TIME_IN_MILLISECONN = 600000;
    public static ArrayList<String> arrName;
    private CountDownTimer mCountDownTimer;
    private boolean mTimeIsRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLISECONN;

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

        btnPause.setAlpha(0);
        anim = AnimationUtils.loadAnimation(this, R.anim.img_anim);

        String[] mangTen = getResources().getStringArray(R.array.arr_plant);
        arrName = new ArrayList<>(Arrays.asList(mangTen));

        Collections.shuffle(arrName);
        int idHinh = getResources().getIdentifier(arrName.get(2), "drawable", getPackageName());
        imgView.setImageResource(idHinh);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime();
                Collections.shuffle(arrName);
                int idimg = getResources().getIdentifier(arrName.get(2), "drawable", getPackageName());
                imgView.setImageResource(idimg);
                btnPause.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnStart.animate().alpha(0).setDuration(300).start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTime();
                btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                btnStart.animate().alpha(1).setDuration(300).start();
            }
        });
    }

    private void startTime() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeIsRunning = false;
                btnPause.animate().alpha(0).translationY(80).setDuration(300).start();
                btnStart.animate().alpha(1).setDuration(300).start();
            }
        }.start();
        mTimeIsRunning = true;
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
        mTimeIsRunning = false;
    }
}