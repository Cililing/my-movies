package com.example.przemek.mymoviesv3.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseLongArray;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserData {

    private static final String USER_DATA = "com.example.przemek.mymoviesv3";

    private static final String USER_DATA_NUMBER_OF_MOVIES = "number_of_movies";
    private static final String USER_DATA_MOVIE_PRE_FILENAME = "movie_";
    private static final String USER_DATA_MOVIE_NUMBER_OF_RATINGS = "number_of_ratings";
    private static final String USER_DATA_MOVIE_RATINGS_FILENAME = "ratings";


    private static ArrayList<Movie> userMovies = new ArrayList<>();
    private static ArrayList<Movie> searchResult = new ArrayList<>();
    private static SparseLongArray userRatings = new SparseLongArray();

    public static ArrayList<Movie> getUserMovies() {
        return userMovies;
    }

    public static ArrayList<Movie> getSearchResult() {
        return searchResult;
    }

    public static SparseLongArray getUserRatings() { return userRatings; }

    public static synchronized void saveData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        saveSavedMovied(editor);
        saveMovieRatings(editor);
    }

    public static void loadData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        loadSavedMovies(settings);
        loadSavedRatings(settings);
    }

    private static void saveSavedMovied(SharedPreferences.Editor editor) {
        editor.clear(); //remove all data in shared preferences

        editor.putInt(USER_DATA_NUMBER_OF_MOVIES, userMovies.size());

        Gson gson = new Gson();
        for (int i = 0; i < userMovies.size(); i++) {
            //preferencename:
            String preference = USER_DATA_MOVIE_PRE_FILENAME + String.valueOf(i);
            String movie = gson.toJson(userMovies.get(i));
            editor.putString(preference, movie);
            //put every single movie
        }

        editor.commit();
    }

    private static void saveMovieRatings(SharedPreferences.Editor editor) {
        editor.putInt(USER_DATA_MOVIE_NUMBER_OF_RATINGS, userRatings.size());

        Gson gson = new Gson();
        String ratings = gson.toJson(userRatings);
        editor.putString(USER_DATA_MOVIE_RATINGS_FILENAME, ratings);
        editor.commit();
    }

    private static void loadSavedMovies(SharedPreferences settigns) {

        int numberOfMovies = settigns.getInt(USER_DATA_NUMBER_OF_MOVIES, 0);

        userMovies.clear();
        Gson gson = new Gson();
        for (int i = 0; i < numberOfMovies; i++) {
            String preference = USER_DATA_MOVIE_PRE_FILENAME + String.valueOf(i);
            String movieAsString = settigns.getString(preference, "");

            if (movieAsString.equals("")) {
                Log.i("UserData", "error while loading data");
                return;
            }

            Movie movie = gson.fromJson(movieAsString, Movie.class);
            userMovies.add(movie);
        }
    }

    private static void loadSavedRatings(SharedPreferences preferences) {

        int numberOfRatings = preferences.getInt(USER_DATA_MOVIE_NUMBER_OF_RATINGS, 0);
        String ratingsAsString = preferences.getString(USER_DATA_MOVIE_RATINGS_FILENAME, "");

        Gson gson = new Gson();
        userRatings = gson.fromJson(ratingsAsString, SparseLongArray.class);

    }

    public static long getMovieRating(int id) {
        return userRatings.get(id);
    }

    public static void saveMovieRating(int id, int rating) {
        userRatings.put(id, rating);
    }
}
