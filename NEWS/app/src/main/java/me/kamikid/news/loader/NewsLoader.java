package me.kamikid.news.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import me.kamikid.news.entity.News;
import me.kamikid.news.network.QueryUtils;

/**
 * Created by KAMIKID-Shinelon on 2018/2/11.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();

    private String mUrl;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
