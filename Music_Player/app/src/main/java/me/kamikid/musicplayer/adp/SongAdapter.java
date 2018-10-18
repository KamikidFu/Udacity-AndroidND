package me.kamikid.musicplayer.adp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.kamikid.musicplayer.R;
import me.kamikid.musicplayer.obj.SingerObj;
import me.kamikid.musicplayer.obj.SongObj;

/**
 * Created by Kamikid-Yoga on 2018/1/29.
 */

public class SongAdapter extends ArrayAdapter<SongObj> {

    public SongAdapter(Activity context, ArrayList songArrayList) {
        super(context, 0,songArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_item_list, parent,false);
        }

        SongObj curSong= (SongObj) getItem(position);
        TextView songNameTextView = (TextView) listItemView.findViewById(R.id.songNameItemList);
        TextView songAuthorNameTextView = (TextView) listItemView.findViewById(R.id.songAuthorNameItemList);
        ImageView songImage = (ImageView) listItemView.findViewById(R.id.imageSongItemList);

        songNameTextView.setText(curSong.getSongName());
        songAuthorNameTextView.setText(curSong.getAuthor().getSingerName());
        if(curSong.getImgId()!=0){
            songImage.setImageResource(curSong.getImgId());
        }

        return listItemView;
    }

}
