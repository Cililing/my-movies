package com.example.przemek.mymoviesv3.MovieDatabaseApi;

public class ApiParameters {


    static final String authKeyV3 = "1be20bf49cc747dbab1cd4b6c67433ee";
    static final String authKeyV4 =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                    "eyJhdWQiOiIxYmUyMGJmNDljYzc0N2RiYWIxY" +
                    "2Q0YjZjNjc0MzNlZSIsInN1YiI6IjU5MDVkMW" +
                    "I2OTI1MTQxNjliODAxY2JmZCIsInNjb3BlcyI" +
                    "6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w" +
                    "Qvk8bS8RJlc0EEEzVZ3Fbu8hxMpKHUXZyC4JFAId6Q";

    static final String default_lang = "en-US";
    static final String query_lang = "language";


    static final String defaultImageRequest = "https://image.tmdb.org/t/p/w500/";
    static final String genreRequest = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + authKeyV3 +"&language=en-US";



    //results
    static final String status_message = "status_message";
    static final String page_number = "page";
    static final String results = "results";

    static final String genres = "genres";
    static final String genre_key = "id";
    static final String genre_value = "name";

    //movie default keys
    static final String id = "id";
    static final String poster_path = "poster_path";
    static final String overview = "overview";
    static final String original_title = "original_title";
    static final String title = "title";
    static final String release_date = "release_date";
    static final String original_language = "original_language";
    static final String genre_ids = "genre_ids";
    static final String vote_average = "vote_average";
    static final String budget = "budget";
    static final String popularity = "popularity";
    static final String runtime = "runtime";

    //movie cast default keys
    //static final String id = "id";
    static final String cast_array = "cast";
    static final String name = "name";
    static final String character = "character";
    static final String profile_path = "profile_path";


    //movie images
    static final String posters_array  = "posters";
    static final String backdrops_array = "backdrops";
    static final String file_path = "file_path";
}