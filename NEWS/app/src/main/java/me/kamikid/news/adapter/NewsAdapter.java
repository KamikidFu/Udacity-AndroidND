package me.kamikid.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.kamikid.news.R;
import me.kamikid.news.entity.News;

/**
 * Created by KAMIKID-Shinelon on 2018/2/11.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        try {
            News currentNews = getItem(position);

            TextView typeTextView = (TextView) listItemView.findViewById(R.id.news_type);
            TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title);
            TextView dateTextView = (TextView) listItemView.findViewById(R.id.news_date);
            TextView timeTextView = (TextView) listItemView.findViewById(R.id.news_time);
            typeTextView.setText(currentNews.getType());
            titleTextView.setText(currentNews.getTitle());
            dateTextView.setText(currentNews.getDate());
            timeTextView.setText(currentNews.getTime());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Error ocurred when inflating view with data");
        }

        return listItemView;
    }

}