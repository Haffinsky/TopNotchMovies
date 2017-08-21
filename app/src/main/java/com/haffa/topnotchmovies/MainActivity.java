package com.haffa.topnotchmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haffa.topnotchmovies.Data.DataFetcher;
import com.haffa.topnotchmovies.Data.MovieDatabaseHelper;

import static com.haffa.topnotchmovies.Data.Urls.TEST_URL;
import static com.haffa.topnotchmovies.Data.Urls.TEST_URL2;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataFetcher dataFetcher = new DataFetcher();
        MovieDatabaseHelper movieDatabaseHelper = new MovieDatabaseHelper(this);
        //movieDatabaseHelper.dropAndRecreateDatabase();
        try {
            dataFetcher.run(TEST_URL);
            dataFetcher.run(TEST_URL2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
