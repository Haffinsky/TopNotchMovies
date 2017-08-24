package com.haffa.topnotchmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haffa.topnotchmovies.Data.DataFetcher;
import com.haffa.topnotchmovies.Data.MovieDatabaseHelper;
import com.haffa.topnotchmovies.Utilities.NavigationDrawer;

import static com.haffa.topnotchmovies.Data.Urls.NOW_PLAYING;
import static com.haffa.topnotchmovies.Data.Urls.NOW_PLAYING2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataFetcher dataFetcher = new DataFetcher();
        NavigationDrawer navigationDrawer = new NavigationDrawer();
        navigationDrawer.addNavDrawer(this);
        try {
            dataFetcher.run(NOW_PLAYING);
            dataFetcher.run(NOW_PLAYING2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
