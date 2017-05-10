package com.example.przemek.mymoviesv3.Activities.MovieRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapterHolder> {

    private ArrayList<Movie> moviesList;
    private Context mContext;
    private CustomItemClickListener listener;

    public MoviesAdapter(ArrayList<Movie> moviesList, CustomItemClickListener listener, Context mContext) {
        this.listener = listener;
        this.moviesList = moviesList;
        this.mContext = mContext;
    }

    @Override
    public MoviesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row_menu, parent, false);
        return new MoviesAdapterHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.moviePoster.setImageDrawable(null);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieAverageRating.setText(String.valueOf(movie.getVoteAverage()));
        holder.movieReleaseDate.setText(movie.getReleaseDate());
        holder.movieGenres.setText(movie.getGenresList());
        //load img
        if (movie.getBitmapPoster() == null) {
            //there is no bitmapposter saved
            new DownloadImageTask(holder.moviePoster, mContext, null)
                    .execute(movie.getPosterPath());
        } else {
            holder.moviePoster.setImageBitmap(movie.getBitmapPoster());
        }

        if (UserData.getUserMovies().contains(movie)) {
            holder.addButton.setText(mContext.getResources().getString(R.string.button_remove_from_favourities));
        } else {
            holder.addButton.setText(mContext.getResources().getString(R.string.button_add_to_favourities));
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
