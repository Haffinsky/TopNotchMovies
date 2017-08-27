package com.haffa.topnotchmovies;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.haffa.topnotchmovies.Data.DataFetcher;
import com.haffa.topnotchmovies.Data.MovieDatabaseHelper;
import com.haffa.topnotchmovies.Utilities.NavigationDrawer;

import static com.haffa.topnotchmovies.Data.Urls.TOP_RATED;
import static com.haffa.topnotchmovies.Data.Urls.TOP_RATED2;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataFetcher dataFetcher = new DataFetcher();
        NavigationDrawer navigationDrawer = new NavigationDrawer();
        navigationDrawer.addNavDrawer(this);
        //downloading top rated movies info
        try {
            dataFetcher.run(TOP_RATED);
            dataFetcher.run(TOP_RATED2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //adding snackbar
        View parent_layout = findViewById(R.id.grid_fragment_container);
        Snackbar snackbar = Snackbar
                .make(parent_layout, "                        " +
                        "Swipe ----> for more options", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
