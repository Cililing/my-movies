package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;

import android.os.AsyncTask;

import com.example.przemek.mymoviesv3.Activities.MoviesAdapter;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabase;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseNetwork;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseApiException;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.ServerAnswerPraser;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class DownloadMoviesListByTitle extends AsyncTask<String, Void, ArrayList<Movie>> {

    ArrayList<Movie> movieList;
    MoviesAdapter moviesAdapter;

    public DownloadMoviesListByTitle(ArrayList<Movie> movieList, MoviesAdapter moviesAdapter) {
        this.movieList = movieList;
        this.moviesAdapter = moviesAdapter;
    }

    /**
     *
     * @param params string
     * @return
     */
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        try {
            MovieDatabase.getMoviesListByTitle(params[0], movieList);
        } catch (IOException | MovieDatabaseApiException | JSONException e) {
            //TODO
            e.printStackTrace();
        }

        return movieList;

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> ignored) {
        moviesAdapter.notifyDataSetChanged();
    }


}
