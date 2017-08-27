package com.haffa.topnotchmovies;


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

import com.haffa.topnotchmovies.Adapters.MovieListAdapter;
import com.haffa.topnotchmovies.Adapters.SearchResultsListAdapter;
import com.haffa.topnotchmovies.Data.MovieContentProvider;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.MOVIE_ID;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.POSTER_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.TITLE;
import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SEARCH_RESULTS_LOADER = 7;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/searchresults");
    String[] projection = {TITLE, BACKDROP_PATH, MOVIE_ID};
    RecyclerView recyclerView;
    SearchResultsListAdapter adapter;

    public SearchResultsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_results_list, container, false);

        getLoaderManager().initLoader(SEARCH_RESULTS_LOADER, null, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getAppContext(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAppContext());

        adapter = new SearchResultsListAdapter(getActivity());
        recyclerView = rootView.findViewById(R.id.search_results_movie_recycler_view);

        if (getResources().getConfiguration().orientation == 1){
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SEARCH_RESULTS_LOADER:
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
