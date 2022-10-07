package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 1;
    ListView listView;

    ArrayList<Song> songList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Toolbar toolbar=findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        if(ContextCompat.checkSelfPermission(Activity2.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(Activity2.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //We can show our custom dialog here

            }
            else {
                //It will show android's default dialog box
                ActivityCompat.requestPermissions(Activity2.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }
        else {
            upload();
        }
    }
    private void upload() {
        listView = findViewById(R.id.musicview);
        songList = new ArrayList<>();
        getAudio();
        MusicAdapter adapter = new MusicAdapter(this, songList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity2.this, PlayerScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("Path",String.valueOf(songList.get(position).getMpath()));
                intent.putExtra("NameofSong",String.valueOf(songList.get(position).getMnameofSong()));
                startActivity(intent);

            }
        });
    }

    private void getAudio() {
        try {
            final Cursor  cur = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.TITLE}, null, null,
                    "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

            int count = cur.getCount();

            int i = 0;
            if (cur.moveToFirst()) {
                do {
                    String song=cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String path=cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    long album_id=cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                    String artist=cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    Uri artUri = Uri
                            .parse("content://media/external/audio/albumart");
                    Uri albumArtUri = ContentUris.withAppendedId(artUri, album_id);
                    songList.add(new Song(song,artist,path));

                   i++;
                } while (cur.moveToNext());
            }

            cur.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Activity2.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                        upload();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.example_menu,menu);
        return true;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                //getApplication().setTheme(R.style.AppTheme);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                //getApplication().setTheme(android.R.style.Theme_Black);
                break;
        }
    }
}