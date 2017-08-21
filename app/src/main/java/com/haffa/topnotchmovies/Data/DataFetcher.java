package com.haffa.topnotchmovies.Data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/19/2017.
 */

public class DataFetcher {

    private final String LOG_TAG = DataFetcher.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    MovieDatabaseHelper movieDatabaseHelper = new MovieDatabaseHelper(getAppContext());
    ContentResolver resolver = getAppContext().getContentResolver();
    ContentValues values = new ContentValues();
    Uri BASE_CONTENT_URI = Uri.parse("content://com.haffa.topnotchmovies/movies");
    private String jsonResponse;

    public void run(String url) throws Exception {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.v(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                jsonResponse = response.body().string();

                try {
                    JSONObject rootJsonObject = new JSONObject(jsonResponse);
                    JSONArray resultJsonArray = rootJsonObject.getJSONArray("results");

                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        JSONObject movieDetails = resultJsonArray.getJSONObject(i);
                        String title = movieDetails.getString("title");
                        String backdropPath = movieDetails.getString("backdrop_path");
                        String movieId = movieDetails.getString("id");

                        values.put(MovieDatabaseHelper.TITLE, title);
                        values.put(MovieDatabaseHelper.BACKDROP_PATH, backdropPath);
                        values.put(MovieDatabaseHelper.MOVIE_ID, movieId);

                        resolver.insert(BASE_CONTENT_URI, values);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}