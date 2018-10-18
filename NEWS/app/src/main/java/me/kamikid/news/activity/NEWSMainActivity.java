package me.kamikid.news.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.kamikid.news.R;
import me.kamikid.news.adapter.NewsAdapter;
import me.kamikid.news.entity.News;
import me.kamikid.news.loader.NewsLoader;

public class NEWSMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = NEWSMainActivity.class.getSimpleName();
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter newsAdapter;
    private TextView emptyTextView;
    private ListView newsListView;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsmain);


        loadingIndicator = (View) findViewById(R.id.loading_indicator);
        newsListView = (ListView) findViewById(R.id.news_list);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyTextView);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News temp = newsAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(temp.getUrl()));
                startActivity(intent);
            }
        });

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderMgr = getLoaderManager();
            loaderMgr.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.empty_text);
        }

    }//End of onCreate()


    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        loadingIndicator.setVisibility(View.GONE);
        newsAdapter.clear();

        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        } else {
            emptyTextView.setText(R.string.empty_text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equalsIgnoreCase(getString(R.string.settings_topics_key)) ||
                key.equalsIgnoreCase(getString(R.string.settings_from_date_key)) ||
                key.equalsIgnoreCase(getString(R.string.settings_to_date_key))) {

            newsAdapter.clear();

            emptyTextView.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.VISIBLE);

            getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String topics = sharedPrefs.getString(
                getString(R.string.settings_topics_key),
                getString(R.string.settings_topics_default)
        );

        String fromDate = sharedPrefs.getString(
                getString(R.string.settings_from_date_key),
                getString(R.string.settings_default_date)
        );

        String toDate = sharedPrefs.getString(
                getString(R.string.settings_to_date_key),
                getString(R.string.settings_default_date)
        );

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test
        uriBuilder.appendQueryParameter("q",topics);
        uriBuilder.appendQueryParameter("from-date",fromDate);
        uriBuilder.appendQueryParameter("to-date",toDate);
        uriBuilder.appendQueryParameter("api-key","test");

        Log.d(LOG_TAG,"URL SEE: "+uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

}
