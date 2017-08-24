package com.haffa.topnotchmovies;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haffa.topnotchmovies.Adapters.FavoritesListAdapter;
import com.haffa.topnotchmovies.Adapters.MovieListAdapter;
import com.haffa.topnotchmovies.Data.MovieContentProvider;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_ID;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FAVORITES_LOADER = 5;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/favorite");
    String[] projection = {MOVIE_ID, BACKDROP_PATH, TITLE};
    RecyclerView recyclerView;
    FavoritesListAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        getLoaderManager().initLoader(FAVORITES_LOADER, null, this);

        adapter = new FavoritesListAdapter(getAppContext());
        recyclerView = rootView.findViewById(R.id.favorite_movie_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FAVORITES_LOADER:
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
