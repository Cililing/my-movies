package com.example.przemek.mymoviesv3.MovieDatabaseApi;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class Movie implements Cloneable, Serializable {

    public static final int maxRate = 10;

    private int id = 0;
    private String posterPath = "posterPath";
    private String overview = "overview";
    private String originalTitle = "originalTitle";
    private String title = "title";
    private String releaseDate = "releaseDate";
    private String originalLanguage = "originalLanguage";
    private String[] genres = new String[1];
    private float voteAverage = 0;

    private int budget = 0;
    private int popularity = 0;
    private int runtime = 0;

    private Bitmap bitmapPoster = null;

    private float userRating = 0;

    private ArrayList<String> posters = new ArrayList<>();
    private ArrayList<String> backdrops = new ArrayList<>();

    public Movie() {
        //nothing to do
    }

    public Movie(String title, String genre, String year) {

        this.title = title;
        genres[0] = genre;
        this.releaseDate = year;
        this.overview = "No description";
    }

    public Movie(int id, String poster_path, String overview, String original_title, String title, String release_date, String original_language, String[] genres, float vote_averange) {
        this.id = id;
        this.posterPath = poster_path;
        this.overview = overview;
        this.originalTitle = original_title;
        this.title = title;
        this.releaseDate = release_date;
        this.originalLanguage = original_language;
        this.genres = genres;
        this.voteAverage = vote_averange;
    }

    public Object clone() throws CloneNotSupportedException {
        super.clone();
        Movie m = new Movie(id, posterPath, overview, originalTitle, title, releaseDate, originalLanguage, genres, voteAverage);
        m.budget = budget;
        m.popularity = popularity;
        m.runtime = runtime;
        m.bitmapPoster = bitmapPoster;
        m.userRating = userRating;
        return m;
    }

    public boolean isMatchedPattern(String pattern) {
        if (title.toLowerCase().contains(pattern.toLowerCase())) return true;
        if (originalTitle.toLowerCase().contains(pattern.toLowerCase())) return true;
        return false;
    }

    public JSONObject generateJSONObject() throws JSONException {
        JSONObject thisMovie = new JSONObject();
        thisMovie.put(ApiParameters.id, this.id);
        thisMovie.put(ApiParameters.poster_path, this.posterPath);
        thisMovie.put(ApiParameters.overview, this.overview);
        thisMovie.put(ApiParameters.original_title, this.originalTitle);
        thisMovie.put(ApiParameters.title, this.title);
        thisMovie.put(ApiParameters.release_date, this.releaseDate);
        thisMovie.put(ApiParameters.original_language, this.originalLanguage);

        //put genres
        JSONArray movieGenres = new JSONArray();
        for (String genre : this.genres) {
            movieGenres.put(genre);
        }
        //todo genres
        thisMovie.put(ApiParameters.genre_ids, this.genres);

        return thisMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return ApiParameters.defaultImageRequest + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getGenresList() {
        String genresText = "";
        for (String s : genres) {
            if (s != null) {
                genresText += s + ", ";
            }
        }
        if (!genresText.equals("")) genresText = genresText.substring(0, genresText.length() - 2); //removie ", " from last genre
        return genresText;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public Bitmap getBitmapPoster() {
        return bitmapPoster;
    }

    public void setBitmapPoster(Bitmap bitmapPoster) {
        this.bitmapPoster = bitmapPoster;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            if (((Movie) obj).id == this.id) {
                return true;
            }
        }

        return false;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public ArrayList<String> getPosters() {
        return posters;
    }

    public void setPosters(ArrayList<String> posters) {
        this.posters = posters;
    }

    public ArrayList<String> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ArrayList<String> backdrops) {
        this.backdrops = backdrops;
    }

    public ArrayList<String> getPostersAndBackDrops() {
        ArrayList<String> srcs = new ArrayList<>();
        srcs.addAll(getPosters());
        srcs.addAll(getBackdrops());
        return srcs;
    }
}
