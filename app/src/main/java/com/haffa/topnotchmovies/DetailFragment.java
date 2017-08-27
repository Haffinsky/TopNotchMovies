package com.haffa.topnotchmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.haffa.topnotchmovies.Data.MovieContentProvider;
import com.haffa.topnotchmovies.Data.MovieDatabaseHelper;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_ID;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.OVERVIEW;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.POSTER_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.VOTE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_MOVIES_LOADER = 2;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/moviedetails");
    String[] projection = {TITLE, BACKDROP_PATH, OVERVIEW, MOVIE_ID, VOTE, POSTER_PATH};
    ImageView backdropImageView;
    ImageView posterImageView;
    TextView ratingTextView;
    TextView titleTextView;
    ScrollView scrollView;
    ExpandableTextView overviewTextView;
    ContentResolver resolver = getAppContext().getContentResolver();
    ContentValues values = new ContentValues();
    Uri BASE_CONTENT_URI_FAVORITES = Uri.parse("content://com.haffa.topnotchmovies/favorite");
    Cursor cursor;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getLoaderManager().initLoader(DETAIL_MOVIES_LOADER, null, this);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        scrollView = rootView.findViewById(R.id.scroll_view);

        backdropImageView = (ImageView) rootView.findViewById(R.id.detail_backdrop_image_view);
        posterImageView = (ImageView) rootView.findViewById(R.id.detail_poster_image_view);
        ratingTextView = (TextView) rootView.findViewById(R.id.rating_text_view);
        titleTextView = (TextView) rootView.findViewById(R.id.detail_title_text_view);
        overviewTextView = (ExpandableTextView) rootView.findViewById(R.id.expand_text_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        Intent intent = getActivity().getIntent();

        final String title = intent.getStringExtra("title");
        final String backdropPath = intent.getStringExtra("backdropPath");
        final String movieId = intent.getStringExtra("movieID");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getAppContext(), "saving to favorites ", Toast.LENGTH_SHORT).show();

                values.put(MovieDatabaseHelper.MOVIE_ID, movieId);
                values.put(MovieDatabaseHelper.TITLE, title);
                values.put(MovieDatabaseHelper.BACKDROP_PATH, backdropPath);

                resolver.insert(BASE_CONTENT_URI_FAVORITES, values);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DETAIL_MOVIES_LOADER:
                return new CursorLoader(
                        getActivity(),
                        BASE_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scrollView.scrollTo(0, backdropImageView.getTop());
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        data.moveToPosition(0);

        Picasso.with(getAppContext())
                .load("http://image.tmdb.org/t/p/w500//" + data.getString(1))
                .into(backdropImageView);

        Picasso.with(getAppContext())
                .load("http://image.tmdb.org/t/p/w185//" + data.getString(5))
                .into(posterImageView);


        if (!data.getString(4).equals("0.0")) {
            ratingTextView.setText(data.getString(4));
        } else {
            ratingTextView.setText("---");
        }

        titleTextView.setText(data.getString(0));

        if (!data.getString(2).equals("null")) {
            overviewTextView.setText(data.getString(2));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
