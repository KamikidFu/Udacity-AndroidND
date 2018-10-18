package me.kamikid.musicplayer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import me.kamikid.musicplayer.R;
import me.kamikid.musicplayer.adp.SongAdapter;
import me.kamikid.musicplayer.obj.AlbumObj;
import me.kamikid.musicplayer.obj.SongObj;

public class Song extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ArrayList<AlbumObj> albumObjArrayList = (ArrayList<AlbumObj>) MainActivity.albumArrayList;

        ArrayList<SongObj> songObjs = new ArrayList<>();

        for(int i=0;i<albumObjArrayList.size();i++){
            for(int j=0;j<albumObjArrayList.get(i).getSongArrayList().size();j++){
                if(!songObjs.contains(albumObjArrayList.get(i).getSongArrayList().get(j))){
                    songObjs.add(albumObjArrayList.get(i).getSongArrayList().get(j));
                }
            }
        }

        SongAdapter songAdapter = new SongAdapter(this, songObjs);

        ListView listView = (ListView) findViewById(R.id.song_list);
        listView.setAdapter(songAdapter);
    }
}
