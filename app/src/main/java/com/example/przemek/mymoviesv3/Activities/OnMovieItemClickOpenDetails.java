package com.example.przemek.mymoviesv3.Activities;

import android.content.Intent;
import android.view.View;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;

import java.util.ArrayList;

public class OnMovieItemClickOpenDetails implements CustomItemClickListener {

    ArrayList<Movie> moviesList;
    public OnMovieItemClickOpenDetails(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public void onClick(View view, int position, String... params) {
        Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
        i.putExtra("movie", moviesList.get(position));
        view.getContext().startActivity(i);
    }

    @Override
    public void onLongClick(View view, int position, String... params) {
        //TODO
    }
}
