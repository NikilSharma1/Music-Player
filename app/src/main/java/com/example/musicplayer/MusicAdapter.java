package com.example.musicplayer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MusicAdapter extends ArrayAdapter {


    public MusicAdapter(@NonNull Context context, List<Song> songList) {
        super(context, 0, songList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.musicadapter, parent, false);
        }
        Song currentSong = (Song) getItem(position);

        TextView nameview = (TextView) listitemview.findViewById(R.id.name);
        String name= currentSong.getMnameofSong();
        nameview.setText(name);

        TextView sourceview = (TextView) listitemview.findViewById(R.id.source);
        String source=currentSong.getMsrcofSong();
        sourceview.setText(source);
        return listitemview;
    }
}
