package com.haffa.topnotchmovies.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.haffa.topnotchmovies.Data.DataFetcher;
import com.haffa.topnotchmovies.FavoritesActivity;
import com.haffa.topnotchmovies.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static com.haffa.topnotchmovies.Data.Urls.NOW_PLAYING;
import static com.haffa.topnotchmovies.Data.Urls.NOW_PLAYING2;
import static com.haffa.topnotchmovies.Data.Urls.TOP_RATED;
import static com.haffa.topnotchmovies.Data.Urls.TOP_RATED2;
import static com.haffa.topnotchmovies.Data.Urls.UPCOMING_URL;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/22/2017.
 */

public class NavigationDrawer {

    public void addNavDrawer(final Activity activity) {

        final DataFetcher dataFetcher = new DataFetcher();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withSelectable(false).withIcon(R.drawable.movie_icon);
        PrimaryDrawerItem topNotchMovies = new PrimaryDrawerItem()
                .withSelectable(false).withName("TOP NOTCH MOVIES");
        PrimaryDrawerItem selectMovie = new PrimaryDrawerItem()
                .withSelectable(false).withName("WHAT MOVIES WOULD YOU LIKE TO SEE?");
        final SecondaryDrawerItem NOW_PLAYING222 = new SecondaryDrawerItem().withIdentifier(2)
                .withName("NOW PLAYING");
        final SecondaryDrawerItem TOP_RATED_MOVIES = new SecondaryDrawerItem().withIdentifier(3)
                .withName("TOP RATED MOVIES");
        final SecondaryDrawerItem UPCOMING = new SecondaryDrawerItem().withIdentifier(6)
                .withName("UPCOMING MOVIES");
        final SecondaryDrawerItem SEARCH = new SecondaryDrawerItem().withIdentifier(4)
                .withName("SEARCH");
        final SecondaryDrawerItem FAVORITES = new SecondaryDrawerItem().withIdentifier(5)
                .withName("FAVORITES");


        new DrawerBuilder()
                .withActivity(activity)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        topNotchMovies,
                        new DividerDrawerItem(),
                        FAVORITES,
                        SEARCH,
                        new DividerDrawerItem(),
                        selectMovie,
                        new DividerDrawerItem(),
                        NOW_PLAYING222,
                        TOP_RATED_MOVIES,
                        UPCOMING
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch ((int) drawerItem.getIdentifier()) {
                    case 2:
                        try {
                            dataFetcher.run(NOW_PLAYING);
                            dataFetcher.run(NOW_PLAYING2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            dataFetcher.run(TOP_RATED);
                            dataFetcher.run(TOP_RATED2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        Intent intent = new Intent(getAppContext(), FavoritesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getAppContext().startActivity(intent);
                        break;
                    case 4:
                        SearchDialog searchDialog = new SearchDialog();
                        searchDialog.showSearchDialog(activity);
                        break;
                    case 6:
                        try {
                            dataFetcher.run(UPCOMING_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                return false;
            }
        })
                .build();
    }
}

