package me.kamikid.musicplayer.obj;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KAMIKID-Shinelon on 2018/1/28.
 */

public class SongObj {
    private String songName;
    private SingerObj author;
    private int imgId;

    public SongObj(String songName) {
        this.songName = songName;
    }

    public SongObj(String songName, SingerObj author, int imgId) {
        this.songName = songName;
        this.author = author;
        this.imgId=imgId;
    }


    public String getSongName() {
        return songName;
    }

    public SingerObj getAuthor() {
        return author;
    }

    public int getImgId() {
        return imgId;
    }
}
