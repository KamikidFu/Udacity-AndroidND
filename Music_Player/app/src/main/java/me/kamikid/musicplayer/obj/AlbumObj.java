package me.kamikid.musicplayer.obj;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import me.kamikid.musicplayer.view.Album;

/**
 * Created by KAMIKID-Shinelon on 2018/1/28.
 */

public class AlbumObj{
    private String albumName;
    private ArrayList<SongObj> songArrayList;
    private int imgId;

    public AlbumObj(String albumName, int imgId) {
        this.albumName = albumName;
        this.imgId=imgId;
        songArrayList = new ArrayList<>();
    }

    public void addSong(SongObj song) {
        songArrayList.add(song);
    }

    public void removeSong(SongObj song) {
        songArrayList.remove(song);
    }

    public void removeSong(String songName) {
        for (int i = 0; i < songArrayList.size(); i++) {
            if (songArrayList.get(i).getSongName().equalsIgnoreCase(songName)) {
                songArrayList.remove(i);
                return;
            }
        }
    }

    public String getAlbumName() {
        return albumName;
    }

    public ArrayList<SongObj> getSongArrayList() {
        return songArrayList;
    }

    public int getImgId() {
        return imgId;
    }
}
