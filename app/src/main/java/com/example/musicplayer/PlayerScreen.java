package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerScreen extends AppCompatActivity {

    Button pauseButton;
    int flag=1; //IT MEANS SONG IS RUNNING
    public void pauseAndStart(View view){
        //IF FLAG ==1 IT MEANS WE WANT TO PAUSE THE SONG NOW
        if(flag==1){
            flag=0;
            MediaPlayerChecker.mediaPlayer.pause();
        }else{
            flag=1;
            MediaPlayerChecker.mediaPlayer.start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_screen);
        /*Toolbar toolbar=findViewById(R.id.toobar);
        setSupportActionBar(toolbar);*/
        TextView songName = (TextView) findViewById(R.id.songName);
        SeekBar durationBar = (SeekBar) findViewById(R.id.songBar);
        pauseButton=findViewById(R.id.pausebutton);
        songName.setSelected(true);
        String pathofSong = getIntent().getStringExtra("Path");
        String nameofSong = getIntent().getStringExtra("NameofSong");
        songName.setText(nameofSong);

        if (MediaPlayerChecker.mediaPlayer != null) {
            MediaPlayerChecker.mediaPlayer.reset();
        }

        Uri uri = Uri.parse("file:///" + pathofSong);

        MediaPlayerChecker.mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            MediaPlayerChecker.mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MediaPlayerChecker.mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaPlayerChecker.mediaPlayer.start();
        durationBar.setMax(MediaPlayerChecker.mediaPlayer.getDuration());
        durationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //MediaPlayerChecker.mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                durationBar.setProgress(MediaPlayerChecker.mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);
    }
}