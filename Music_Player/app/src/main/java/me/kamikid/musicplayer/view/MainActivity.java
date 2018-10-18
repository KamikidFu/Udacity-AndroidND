package me.kamikid.musicplayer.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.kamikid.musicplayer.R;
import me.kamikid.musicplayer.obj.AlbumObj;
import me.kamikid.musicplayer.obj.SingerObj;
import me.kamikid.musicplayer.obj.SongObj;

public class MainActivity extends AppCompatActivity {

    Button bPrev;
    Button bPlay;
    Button bNext;
    Button bSinger;
    Button bSong;
    Button bAlbum;
    static List<AlbumObj> albumArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bPrev = (Button) findViewById(R.id.buttonPrevSong);
        bPlay = (Button) findViewById(R.id.buttonPlay);
        bNext = (Button) findViewById(R.id.buttonNextSong);
        bSinger = (Button) findViewById(R.id.buttonSingers);
        bSong = (Button) findViewById(R.id.buttonSongs);
        bAlbum = (Button) findViewById(R.id.buttonAlbum);

        albumArrayList = new ArrayList<>();

        albumArrayList.add(new AlbumObj("丑奴儿", R.drawable.the_servile2017));
        SingerObj temp = new SingerObj("草东没有派对",0);
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("Intro",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("丑",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("烂泥",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("勇敢的人",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("大风吹",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("埃玛",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("等",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("鬼",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("在",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("山海",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("我们",temp,0));
        albumArrayList.get(0).addSong(new me.kamikid.musicplayer.obj.SongObj("情歌",temp,0));

        albumArrayList.add(new AlbumObj("在平坦的路面上曲折前行",R.drawable.xucen));
        temp = new SingerObj("许岑",0);
        albumArrayList.get(1).addSong(new SongObj("赠票",temp,0));
        albumArrayList.get(1).addSong(new SongObj("更好的我",temp,0));
        albumArrayList.get(1).addSong(new SongObj("渐远的捷达王",temp,0));
        albumArrayList.get(1).addSong(new SongObj("情种",temp,0));
        albumArrayList.get(1).addSong(new SongObj("卧底",temp,0));
        albumArrayList.get(1).addSong(new SongObj("杜鹃",temp,0));
        albumArrayList.get(1).addSong(new SongObj("回报",temp,0));
        albumArrayList.get(1).addSong(new SongObj("必将",temp,0));
        albumArrayList.get(1).addSong(new SongObj("就在今夜",temp,0));

        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Play previous music", Toast.LENGTH_SHORT).show();
            }
        });

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bPlay.getText().toString().equalsIgnoreCase("Play")) {
                    Toast.makeText(getApplicationContext(), "Play music", Toast.LENGTH_SHORT).show();
                    bPlay.setText(R.string.stop_playing_song);
                }else{
                    Toast.makeText(getApplicationContext(), "Stop playing", Toast.LENGTH_SHORT).show();
                    bPlay.setText(R.string.play);
                }
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Play next music", Toast.LENGTH_SHORT).show();
            }
        });

        bSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSinger = new Intent(getApplicationContext(),Singer.class);
                startActivity(goSinger);
            }
        });

        bSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSong = new Intent(getApplicationContext(),Song.class);
                startActivity(goSong);
            }
        });

        bAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAlbum = new Intent(getApplicationContext(),Album.class);
                startActivity(goAlbum);
            }
        });

    }
}
