package com.haffa.topnotchmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haffa.topnotchmovies.Data.DetailDataFetcher;
import com.haffa.topnotchmovies.DetailActivity;
import com.haffa.topnotchmovies.R;
import com.squareup.picasso.Picasso;

import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/24/2017.
 */

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {
    Context mContext;
    Cursor cursor;

    public SearchResultsListAdapter(Context context){
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.search_results_row_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.titleTextView.setText(cursor.getString(0));

        if (!cursor.getString(1).equals("null")) {
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w500//" + cursor.getString(1))
                    .into(holder.posterImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        } else return cursor.getCount();
    }

    public void swapCursor(final Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView posterImageView;
        private TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            posterImageView = (ImageView) itemView.findViewById(R.id.search_results_backdrop_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.search_results_movie_title_view);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            cursor.moveToPosition(position);

            String title = cursor.getString(0);
            String backdropPath = cursor.getString(1);
            String movieID = cursor.getString(2);
            DetailDataFetcher detailDataFetcher = new DetailDataFetcher();
            try {
                detailDataFetcher.run("https://api.themoviedb.org/3/movie/" + cursor.getString(2) +
                        "?api_key=8ddcee182fdd87b09acb4757c6890d2a&append_to_response=videos,details,similar,credits,reviews");
            } catch (Exception e) {
                e.printStackTrace();
            }
            SystemClock.sleep(1000);
            Intent intent = new Intent(getAppContext(), DetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("backdropPath", backdropPath);
            intent.putExtra("movieID", movieID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getAppContext().startActivity(intent);
        }
    }
}
