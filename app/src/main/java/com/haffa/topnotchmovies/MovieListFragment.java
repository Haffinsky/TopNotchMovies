package com.haffa.topnotchmovies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haffa.topnotchmovies.Adapters.MovieListAdapter;
import com.haffa.topnotchmovies.Data.MovieContentProvider;
import com.haffa.topnotchmovies.Data.MovieDatabaseHelper;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_ID;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LOADER = 1;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/movies");
    String[] projection = {TITLE, BACKDROP_PATH, MOVIE_ID};
    RecyclerView recyclerView;
    MovieListAdapter adapter;

    public MovieListFragment() {

    }

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("AYY", String.valueOf(BASE_CONTENT_URI));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        getLoaderManager().initLoader(MOVIES_LOADER, null, this);

        MovieDatabaseHelper movieDatabaseHelper = new MovieDatabaseHelper(getAppContext());
        SQLiteDatabase sqLiteDatabase = movieDatabaseHelper.getReadableDatabase();


        adapter = new MovieListAdapter(getActivity());
        recyclerView = rootView.findViewById(R.id.movie_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        return rootView;
    }
    @Override
    public void onResume(){
        super.onResume();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MOVIES_LOADER:
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
        adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}