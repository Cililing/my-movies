package com.example.przemek.mymoviesv3.MovieDatabaseApi;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseNetwork.generateMovieCastListById;
import static com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseNetwork.generateSearchMovieURLbyTitle;
import static com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseNetwork.genereteMovieDetailsURLbyId;

public class MovieDatabase {

    public static void getMoviesListByTitle(String title, ArrayList<Movie> outList) throws IOException, JSONException, MovieDatabaseApiException {

        //generate url
        String url = generateSearchMovieURLbyTitle(title);

        //generate json request
        String JSONRequest = MovieDatabaseNetwork.downloadJSONRawData(url);

        //get movie list and save it in outList
        ServerAnswerPraser.generateMovieListFromJSONRequest(JSONRequest, outList);

        //add movie extra data
        for (Movie movie : outList) {
            addExtraMovieData(movie);
        }
    }

    public static void getMovieCastById(int id, ArrayList<Person> outList) throws IOException, JSONException, MovieDatabaseApiException {
        String url = generateMovieCastListById(id);
        String JSONRequest = MovieDatabaseNetwork.downloadJSONRawData(url);
        ServerAnswerPraser.generateMovieCastListFromJSONRequest(JSONRequest, outList);

    }

    /**
     * @param url filename of poster in database
     * @return full poster url
     */
    public static String getPosterURL(String url) {
        return ApiParameters.defaultImageRequest + url;
    }

    private static void addExtraMovieData(Movie movie) throws IOException, JSONException, MovieDatabaseApiException {
        String url = genereteMovieDetailsURLbyId(movie.getId());
        String JSONRequest = MovieDatabaseNetwork.downloadJSONRawData(url);
        ServerAnswerPraser.addExtraDataToMovie(JSONRequest, movie);
    }

}
