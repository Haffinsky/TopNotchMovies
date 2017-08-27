package com.haffa.topnotchmovies.Data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.ACTOR_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.CAST_TABLE_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.CHARACTER_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.GENDER;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_DETAIL_TABLE_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.POSTER_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.PROFILE_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.SIMILAR_TABLE_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/20/2017.
 */

public class DetailDataFetcher {

    private final String LOG_TAG = DataFetcher.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    MovieDatabaseHelper movieDatabaseHelper = new MovieDatabaseHelper(getAppContext());

    ContentResolver resolver = getAppContext().getContentResolver();
    ContentResolver castResolver = getAppContext().getContentResolver();
    ContentResolver similarResolver = getAppContext().getContentResolver();

    ContentValues values = new ContentValues();
    ContentValues castValues = new ContentValues();
    ContentValues similarValues = new ContentValues();

    Uri DETAIL_BASE_CONTENT_URI = Uri.parse("content://com.haffa.topnotchmovies/moviedetails");
    Uri CAST_BASE_CONTENT_URI = Uri.parse("content://com.haffa.topnotchmovies/cast");
    Uri SIMILAR_BASE_CONTENT_URI = Uri.parse("content://com.haffa.topnotchmovies/similar");

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
                SQLiteDatabase db = movieDatabaseHelper.getWritableDatabase();
                db.delete(MOVIE_DETAIL_TABLE_NAME, null, null);
                db.delete(CAST_TABLE_NAME, null, null);
                db.delete(SIMILAR_TABLE_NAME, null, null);

                try {
                    JSONObject rootJsonObject = new JSONObject(jsonResponse);

                    String title = rootJsonObject.getString("title");
                    String backdropPath = rootJsonObject.getString("backdrop_path");
                    String overview = rootJsonObject.getString("overview");
                    String movieId = rootJsonObject.getString("id");
                    String posterPath = rootJsonObject.getString("poster_path");
                    String vote = rootJsonObject.getString("vote_average");

                    values.put(MovieDatabaseHelper.TITLE, title);
                    values.put(MovieDatabaseHelper.BACKDROP_PATH, backdropPath);
                    values.put(MovieDatabaseHelper.OVERVIEW, overview);
                    values.put(MovieDatabaseHelper.MOVIE_ID, movieId);
                    values.put(MovieDatabaseHelper.VOTE, vote);
                    values.put(MovieDatabaseHelper.POSTER_PATH, posterPath);

                    resolver.insert(DETAIL_BASE_CONTENT_URI, values);

                    JSONObject creditsJsonObject = rootJsonObject.getJSONObject("credits");
                    JSONArray castJsonArray = creditsJsonObject.getJSONArray("cast");

                    for (int i = 0; i < castJsonArray.length() && i < 10; i++) {
                        JSONObject castJsonObject = castJsonArray.getJSONObject(i);

                        String actorName = castJsonObject.getString("name");
                        String characterName = castJsonObject.getString("character");
                        String profilePath = castJsonObject.getString("profile_path");
                        String gender = castJsonObject.getString("gender");

                        castValues.put(ACTOR_NAME, actorName);
                        castValues.put(CHARACTER_NAME, characterName);
                        castValues.put(PROFILE_PATH, profilePath);
                        castValues.put(GENDER, gender);

                        castResolver.insert(CAST_BASE_CONTENT_URI, castValues);
                    }

                    JSONObject similarJsonObject = rootJsonObject.getJSONObject("similar");
                    JSONArray resultsJsonArray = similarJsonObject.getJSONArray("results");
                    for (int i = 0; i < resultsJsonArray.length() && i < 12; i++) {
                        JSONObject resultsJsonObject = resultsJsonArray.getJSONObject(i);

                        String similarTitle = resultsJsonObject.getString("title");
                        String similarPosterPath = resultsJsonObject.getString("poster_path");

                        similarValues.put(TITLE, similarTitle);
                        similarValues.put(POSTER_PATH, similarPosterPath);

                        similarResolver.insert(SIMILAR_BASE_CONTENT_URI, similarValues);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

