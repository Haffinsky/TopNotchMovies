package com.haffa.topnotchmovies.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Rafal on 8/20/2017.
 */

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.ViewHolder> {

    Context mContext;
    Cursor cursor;

    public CastListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.cast_row_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Resources resources = mContext.getResources();
        Log.v("add", cursor.getString(1));
        
        if (!cursor.getString(1).isEmpty()) {
            holder.actorNameView.setText(cursor.getString(0) + " as " + cursor.getString(1));
        } else {
            holder.actorNameView.setText(cursor.getString(0));
        }

        if (!cursor.getString(3).equals("null")) {
            Picasso.with(getAppContext()).load("http://image.tmdb.org/t/p/w92//" +
                cursor.getString(3))
                .into(holder.profileImageView);
        } else {
            Picasso.with(getAppContext()).load(R.drawable.chair)
                    .into(holder.profileImageView);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImageView;
        private TextView actorNameView;

        public ViewHolder(View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.actor_profile_view);
            actorNameView = itemView.findViewById(R.id.actor_text_view);
        }
    }
}





