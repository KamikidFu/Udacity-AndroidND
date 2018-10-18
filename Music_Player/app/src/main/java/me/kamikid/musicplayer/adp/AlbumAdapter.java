package me.kamikid.musicplayer.adp;

import android.app.Activity;
import android.content.Context;
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
import me.kamikid.musicplayer.obj.AlbumObj;
import me.kamikid.musicplayer.obj.SongObj;

/**
 * Created by KAMIKID-Shinelon on 2018/1/28.
 */

public class AlbumAdapter  extends ArrayAdapter{

    public AlbumAdapter(Activity context, ArrayList albumArrayList) {
        super(context, 0,albumArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.album_item_list, parent,false);
        }

        AlbumObj curAlbum= (AlbumObj) getItem(position);
        TextView albumNameTextView = (TextView) listItemView.findViewById(R.id.albumNameItemList);
        ImageView albumImage = (ImageView) listItemView.findViewById(R.id.imageAlbumItemList);

        albumNameTextView.setText(curAlbum.getAlbumName());
        if(curAlbum.getImgId()!=0){
            albumImage.setImageResource(curAlbum.getImgId());
        }

        return listItemView;
    }
}
