package me.kamikid.musicplayer.obj;

/**
 * Created by KAMIKID-Shinelon on 2018/1/28.
 */

public class SingerObj {
    private String singerName;
    private int imgId;

    public SingerObj(String singerName, int imgId) {
        this.singerName = singerName;
        this.imgId = imgId;
    }

    public String getSingerName() {
        return singerName;
    }

    public int getImgId() {
        return imgId;
    }
}
