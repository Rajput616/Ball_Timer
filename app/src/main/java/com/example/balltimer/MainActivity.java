package com.example.balltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void buttonClicked(View view){
        if(counterIsActive){
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP");

            countDownTimer =  new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Timer Completed", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.godsplan);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int sec = secondsLeft - (minutes * 60);
        String minString = Integer.toString(minutes);
        String secondString = Integer.toString(sec);
        if(minString.length() == 1){
            minString = "0"+minString;
        }
        if(secondString.length() == 1){
            secondString = "0"+secondString;
        }
        timerTextView.setText(minString+":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.textView);
        goButton = findViewById(R.id.button);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}