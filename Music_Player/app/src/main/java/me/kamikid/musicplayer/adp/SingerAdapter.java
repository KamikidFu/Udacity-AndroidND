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
import me.kamikid.musicplayer.obj.AlbumObj;
import me.kamikid.musicplayer.obj.SingerObj;
import me.kamikid.musicplayer.view.Singer;

/**
 * Created by Kamikid-Yoga on 2018/1/29.
 */

public class SingerAdapter extends ArrayAdapter<SingerObj> {

    public SingerAdapter(Activity context, ArrayList singerArrayList) {
        super(context, 0,singerArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.singer_item_list, parent,false);
        }

        SingerObj curSinger= (SingerObj) getItem(position);
        TextView singerNameTextView = (TextView) listItemView.findViewById(R.id.singerNameItemList);
        ImageView Image = (ImageView) listItemView.findViewById(R.id.imageSingerItemList);

        singerNameTextView.setText(curSinger.getSingerName());
        if(curSinger.getImgId()!=0){
            Image.setImageResource(curSinger.getImgId());
        }

        return listItemView;
    }
}
