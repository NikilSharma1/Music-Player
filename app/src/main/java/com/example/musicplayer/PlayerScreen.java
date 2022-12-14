package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerScreen extends AppCompatActivity {

    Button pauseButton;
    TextView songName;
    SeekBar durationBar;
    TextView endingTime;
    TextView currentTime;
    int positionofSong;
    String pathofSong;
    String nameofSong;
    ImageView imageView;
    int flag=1; //IT MEANS SONG IS RUNNING
    public void pauseAndStart(View view){
        //IF FLAG ==1 IT MEANS WE WANT TO PAUSE THE SONG NOW
        if(flag==1){
            flag=0;
            pauseButton.setBackgroundResource(android.R.drawable.ic_media_play);
            MediaPlayerChecker.mediaPlayer.pause();
        }else{
            flag=1;
            pauseButton.setBackgroundResource(android.R.drawable.ic_media_pause);

            MediaPlayerChecker.mediaPlayer.start();
        }
    }
    public void songDetailsfornextSong(){
        pathofSong = MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).getMpath();
        nameofSong = MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).getMnameofSong();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(new File(pathofSong).getPath());
        byte [] data = mmr.getEmbeddedPicture();
        if(data!=null){
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //if(bitmap!=null)
        imageView.setImageBitmap(bitmap);}
        songName.setText(nameofSong);
    }
    public void nextSong(View view){
        MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).mstateofSong = false;
        //Toast.makeText(this, String.valueOf(MediaPlayerChecker.songList.size()), Toast.LENGTH_SHORT).show();
        if(MediaPlayerChecker.Pos!=MediaPlayerChecker.songList.size())
        MediaPlayerChecker.Pos+=1;
        else MediaPlayerChecker.Pos=0;
        MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).mstateofSong =true;
        MediaPlayerChecker.adapter.notifyDataSetChanged();
        songDetailsfornextSong();

        newSongisPlayed();


        //Toast.makeText(this, "tik", Toast.LENGTH_SHORT).show();
    }
    public void prevSong(View view){
        MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).mstateofSong = false;
        if(MediaPlayerChecker.Pos!=0)
            MediaPlayerChecker.Pos-=1;
        else MediaPlayerChecker.Pos=MediaPlayerChecker.songList.size()-1;
        MediaPlayerChecker.songList.get(MediaPlayerChecker.Pos).mstateofSong =true;
        MediaPlayerChecker.adapter.notifyDataSetChanged();
        songDetailsfornextSong();
        newSongisPlayed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_screen);

        songName = (TextView) findViewById(R.id.songName);
        durationBar = (SeekBar) findViewById(R.id.songBar);
        currentTime=findViewById(R.id.currentTime);
        endingTime=findViewById(R.id.endingTime);
        pauseButton=findViewById(R.id.pausebutton);
        imageView=findViewById(R.id.imageView2);
        songName.setSelected(true);
        currentTime.setText("0:00");
        //IT WILL LOAD UP INFO REGARDING THE SONG IN RESP. VARIABLES
        boolean statusofSong=getIntent().getBooleanExtra("Status",false);
        if(statusofSong){
            //Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
           SongIsAlreadyPlaying();
        }
        else{
            //Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show();
            songDetails();
            newSongisPlayed();
        }
    }
    public void SongIsAlreadyPlaying(){
        songDetails();
        setDurationBar();
        MediaPlayerChecker.mediaPlayer.start();
        MediaPlayerChecker.mediaPlayer.setLooping(true);
    }
    public void newSongisPlayed(){
        //IT WILL SET THE MEDIA PLAYER WITH PARTICULAR SONG ATTRIBUTES
        setUpMediaPlayer();
        setDurationBar();
        MediaPlayerChecker.mediaPlayer.start();
        MediaPlayerChecker.mediaPlayer.setLooping(true);
    }
    public void songDetails(){
        pathofSong = getIntent().getStringExtra("Path");
        nameofSong = getIntent().getStringExtra("NameofSong");
        positionofSong=getIntent().getIntExtra("position",0);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(new File(pathofSong).getPath());
        byte [] data = mmr.getEmbeddedPicture();
        Bitmap bitmap;
        if(data!=null)
        {bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        //if(bitmap!=null)
        imageView.setImageBitmap(bitmap);}
        songName.setText(nameofSong);
    }
    public void setUpMediaPlayer(){
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
        MediaPlayerChecker.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

            }

        });
    }
    public void setDurationBar(){
        String endT=String.valueOf(((MediaPlayerChecker.mediaPlayer.getDuration()/1000)/60));
        if((MediaPlayerChecker.mediaPlayer.getDuration()/1000)%60<10){
            endT+=":0"+(MediaPlayerChecker.mediaPlayer.getDuration()/1000)%60;
        }
        else endT+=":"+(MediaPlayerChecker.mediaPlayer.getDuration()/1000)%60;
        endingTime.setText(endT);
        durationBar.setMax(MediaPlayerChecker.mediaPlayer.getDuration());
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {

                durationBar.setProgress(MediaPlayerChecker.mediaPlayer.getCurrentPosition());

            }

        }, 0, 1000);

        // LOOPS CURRENT SONG ONCE IT FINISHES
        durationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTime(progress,currentTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(PlayerScreen.this, "Touch began ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MediaPlayerChecker.mediaPlayer != null ) {
                    MediaPlayerChecker.mediaPlayer.seekTo(seekBar.getProgress());
                    //Toast.makeText(PlayerScreen.this, "Touch finish ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                getApplication().setTheme(R.style.AppTheme);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                getApplication().setTheme(android.R.style.Theme_Black);
                break;
        }
    }
    // THIS METHOD WILL SET CURRENT TIME POSITION OF MEDIAPLAYER IN LEFT TEXTVIEW OF TIME DURATIONS
    public void setTime(int time, TextView textView){
        String curT=String.valueOf(((time/1000)/60));
        if((time/1000)%60<10){
            curT+=":0"+(time/1000)%60;
        }
        else curT+=":"+(time/1000)%60;
        textView.setText(curT);
    }
}