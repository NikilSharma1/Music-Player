package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 1500;
    Toolbar toolbar;
    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        MediaPlayerChecker.mediaPlayer = new MediaPlayer();
        MediaPlayerChecker.songList = new ArrayList<>();
        MediaPlayerChecker.Pos=0;
        /*toolbar=findViewById(R.id.toobar);
        setSupportActionBar(toolbar);*/
        final View myLayout = findViewById(R.id.startscreen);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, TIME_OUT);*/
        /*Intent i = new Intent(MainActivity.this, Activity2.class);
        startActivity(i);*/
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }

        }, 2000L);
       /* Intent intent = new Intent(MainActivity.this, Activity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/
        }

}