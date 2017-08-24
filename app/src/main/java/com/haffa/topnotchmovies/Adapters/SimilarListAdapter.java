package com.haffa.topnotchmovies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haffa.topnotchmovies.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/21/2017.
 */

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListAdapter.ViewHolder> {

    Context mContext;
    Cursor cursor;

    public SimilarListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.similar_row_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        Picasso.with(getAppContext()).load("http://image.tmdb.org/t/p/w185//" +
                cursor.getString(1))
                .into(holder.similarPosterView);

        holder.similarTitleView.setText(cursor.getString(0));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView similarPosterView;
        private TextView similarTitleView;

        public ViewHolder(View itemView) {
            super(itemView);

            similarPosterView = itemView.findViewById(R.id.similar_poster_view);
            similarTitleView = itemView.findViewById(R.id.similar_movie_title_view);
        }
    }
}

