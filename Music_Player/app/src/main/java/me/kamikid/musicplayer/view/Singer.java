package me.kamikid.musicplayer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import me.kamikid.musicplayer.R;
import me.kamikid.musicplayer.adp.SingerAdapter;
import me.kamikid.musicplayer.obj.AlbumObj;
import me.kamikid.musicplayer.obj.SingerObj;

public class Singer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);

        ArrayList<AlbumObj> albumObjArrayList = (ArrayList<AlbumObj>) MainActivity.albumArrayList;

        ArrayList<SingerObj> singerObjs = new ArrayList<>();

        for(int i=0;i<albumObjArrayList.size();i++){
            for(int j=0;j<albumObjArrayList.get(i).getSongArrayList().size();j++){
                if(!singerObjs.contains(albumObjArrayList.get(i).getSongArrayList().get(j).getAuthor())){
                    singerObjs.add(albumObjArrayList.get(i).getSongArrayList().get(j).getAuthor());
                }
            }
        }

        SingerAdapter singerAdapter = new SingerAdapter(this, singerObjs);

        ListView listView = (ListView) findViewById(R.id.singer_list);
        listView.setAdapter(singerAdapter);
    }
}
