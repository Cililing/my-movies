package com.example.przemek.mymoviesv3.MovieDatabaseApi;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MovieDatabaseNetwork {

    /**
     * @param url dabatabase url request
     * @return page contnent as string
     * @throws IOException
     */
    static String downloadJSONRawData(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    static String generateSearchMovieURLbyTitle(String title) {

        String part1 = "https://api.themoviedb.org/3/search/movie?api_key=";
        String part2 = "&language=en-US&query=";
        String part3 = "&page=1&include_adult=false";

        return part1 + ApiParameters.authKeyV3 + part2 + title + part3;
    }

    static String genereteMovieDetailsURLbyId(int id) {
        String p1 = "https://api.themoviedb.org/3/movie/";
        String p2 = "?api_key=";
        String p3 = "&language";

        return p1 + id + p2 + ApiParameters.authKeyV3 + p3 + ApiParameters.default_lang;
    }

    static String generateMovieCastListById(int id) {
        String p1 = "https://api.themoviedb.org/3/movie/";
        String p2 = "/credits?api_key=";

        return p1 + id + p2 + ApiParameters.authKeyV3;
    }

    static String genereateMovieImagesById(int id) {
        String p1 = "https://api.themoviedb.org/3/movie/";
        String p2 = "/images?api_key=";
        String p3 = "&language=en-US&include_image_language=en";

        return p1 + id + p2 + ApiParameters.authKeyV3 + p3;
    }
}
