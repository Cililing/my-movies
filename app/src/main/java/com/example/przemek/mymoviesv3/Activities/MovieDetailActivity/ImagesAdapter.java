package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.przemek.mymoviesv3.Activities.ErrorActivity.ErrorActivity;
import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.ApiParameters;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabase;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    CustomItemClickListener listener;
    Movie movie;
    Context mContext;

    public ImagesAdapter(Movie movie, CustomItemClickListener listener, Context mContext) {
        this.movie = movie;
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_images_item, parent, false);

        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        String src = MovieDatabase.getPosterURL(movie.getPostersAndBackDrops().get(position));
        //holder.image.setImageDrawable(null);
        new DownloadImageTask(
                holder.image,
                mContext,
                null).execute(src);
    }

    @Override
    public int getItemCount() {
        return movie.getPostersAndBackDrops().size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView image;

        public ImagesViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.details_movie_images_holder_iv);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), image);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}
