package com.haffa.topnotchmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haffa.topnotchmovies.Adapters.CastListAdapter;
import com.haffa.topnotchmovies.Adapters.MovieListAdapter;
import com.haffa.topnotchmovies.Adapters.SimilarListAdapter;
import com.haffa.topnotchmovies.Data.MovieContentProvider;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_ID;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.POSTER_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


public class SimilarMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SIMILAR_MOVIES_LOADER = 4;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/similar");
    String[] projection = {TITLE, POSTER_PATH};
    RecyclerView recyclerView;
    SimilarListAdapter adapter;

    public SimilarMoviesFragment() {
        // Required empty public constructor
    }


    public static SimilarMoviesFragment newInstance() {
        SimilarMoviesFragment fragment = new SimilarMoviesFragment();
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
        // Inflate the layout for this fragment
        getLoaderManager().initLoader(SIMILAR_MOVIES_LOADER, null, this);

        View rootView = inflater.inflate(R.layout.fragment_similar_movies, container, false);

        recyclerView = rootView.findViewById(R.id.similar_movies_list);
        adapter = new SimilarListAdapter(getAppContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getAppContext(), 1, GridLayoutManager.HORIZONTAL, false));
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SIMILAR_MOVIES_LOADER:
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
