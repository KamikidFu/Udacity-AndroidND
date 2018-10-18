package me.kamikid.musicplayer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import me.kamikid.musicplayer.R;
import me.kamikid.musicplayer.adp.AlbumAdapter;
import me.kamikid.musicplayer.obj.AlbumObj;

public class Album extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        ArrayList<AlbumObj> albumObjArrayList = (ArrayList<AlbumObj>) MainActivity.albumArrayList;

        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumObjArrayList);

        ListView listView = (ListView) findViewById(R.id.album_list);
        listView.setAdapter(albumAdapter);
    }
}
