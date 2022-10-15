package com.example.musicplayer;

import android.content.Context;
import android.graphics.Color;
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
    static class ViewHolder {
        protected TextView text1;
        protected TextView text2;
    }

    public MusicAdapter(@NonNull Context context, List<Song> songList) {
        super(context, 0, songList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.musicadapter, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.name);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.source);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Song currentSong = (Song) getItem(position);
        viewHolder.text1.setText(currentSong.getMnameofSong());
        viewHolder.text2.setText(currentSong.getMsrcofSong());

        if (MediaPlayerChecker.songList.get(position).mstateofSong) {
            viewHolder.text1.setTextColor(Color.BLUE);

        } else {
            viewHolder.text1.setTextColor(Color.BLACK);
        }

        viewHolder.text2.setTextColor(Color.BLACK);
        return convertView;
    }
}
