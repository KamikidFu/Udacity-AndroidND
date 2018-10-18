package me.kamikid.news.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.kamikid.news.entity.News;

/**
 * Created by KAMIKID-Shinelon on 2018/2/11.
 */

public final class QueryUtils {
    //For log tag messages use
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * It is not allowed to create one instance of it.
     * So make it private and do nothing inside it.
     */
    private QueryUtils() {
    }

    /**
     * Main function in this class
     * Query the target url and return a list of News objects
     */
    public static List<News> fetchNewsData(String requestURL) {
        URL url = createURL(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error occurred when requesting HTTP", e);
        }
        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {
        if (jsonResponse.isEmpty()) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = (new JSONObject(jsonResponse)).getJSONObject("response");
            JSONArray newsArray = baseJsonResponse.getJSONArray("results");


            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);


                String type = currentNews.getString("type");
                String sectionName = currentNews.getString("sectionName");
                String url = currentNews.getString("webUrl");
                String title = currentNews.getString("webTitle");
                String date = ((currentNews.getString("webPublicationDate")).split("T"))[0];
                String time = ((currentNews.getString("webPublicationDate")).split("T"))[1].replace("Z", "");

                news.add(new News(type, sectionName, url, title, date, time));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error occurred when parsing JSON data");
        }
        return news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error occurred when retrieving JSON results");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error occurred when building URL", e);
        }
        return url;
    }
}
