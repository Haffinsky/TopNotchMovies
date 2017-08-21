package com.haffa.topnotchmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haffa.topnotchmovies.Adapters.CastListAdapter;
import com.haffa.topnotchmovies.Data.MovieContentProvider;

import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.ACTOR_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.BACKDROP_PATH;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.CHARACTER_NAME;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.GENDER;
import static com.haffa.topnotchmovies.Data.MovieDatabaseHelper.PROFILE_PATH;

import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;


public class CastFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CAST_LOADER = 3;
    Uri BASE_CONTENT_URI = Uri.parse("content://" + MovieContentProvider.CONTENT_AUTHORITY + "/cast");
    String[] projection = {ACTOR_NAME, CHARACTER_NAME, GENDER, PROFILE_PATH};
    CastListAdapter adapter;
    RecyclerView recyclerView;

    public CastFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static CastFragment newInstance(String param1, String param2) {
        CastFragment fragment = new CastFragment();
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

        getLoaderManager().initLoader(CAST_LOADER, null, this);

        View rootView =  inflater.inflate(R.layout.fragment_cast, container, false);

        recyclerView = rootView.findViewById(R.id.cast_recycler_view);
        adapter = new CastListAdapter(getAppContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case CAST_LOADER:
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
