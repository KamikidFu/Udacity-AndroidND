package me.kamikid.ichengdu.entity;

import java.io.Serializable;

/**
 * Created by KAMIKID-Shinelon on 2018/2/4.
 */

public class Viewpoint implements Serializable{
    private String name;
    private String address;
    private String brief;
    private int imgId;

    public Viewpoint(String name, String address, String brief, int imgId) {
        this.name = name;
        this.address = address;
        this.brief = brief;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBrief() {
        return brief;
    }

    public int getImgId() {
        return imgId;
    }
}
