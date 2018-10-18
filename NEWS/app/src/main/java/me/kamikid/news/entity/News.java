package me.kamikid.news.entity;

import java.util.Date;

/**
 * Created by KAMIKID-Shinelon on 2018/2/11.
 */

public class News {
    private String type;
    private String sectionName;
    private String url;
    private String title;
    private String date;
    private String time;

    public News(String type, String sectionName, String url, String title, String date, String time) {
        this.type = type;
        this.sectionName = sectionName;
        this.url = url;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
