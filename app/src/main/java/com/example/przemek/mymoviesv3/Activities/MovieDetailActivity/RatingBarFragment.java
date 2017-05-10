package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.Other.Tools;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RatingBarFragment extends Fragment {

    @BindView(R.id.details_user_vote_ratebar)
    RatingBar userVote;
    @BindView(R.id.details_vote_averrage_ratebar)
    RatingBar voteAverrage;
    @BindView(R.id.details_fav_button)
    ImageButton favButton;
    @BindView(R.id.details_popularity)
    TextView popularity;


    private Movie movie;
    boolean movieInCollection = false;

    public static RatingBarFragment getInstance() {
        return new RatingBarFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_rate, container, false);

        ButterKnife.bind(this, view);

        generate();
        return view;
    }

    private void generate() {

        movie = (Movie) getArguments().getSerializable("movie");

        if (movie == null) return;


        movieInCollection = Tools.chceckIfMovieIsInYourCollection(movie);
        if (!movieInCollection) {
            Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_off);
            favButton.setImageDrawable(drawable);
        }
        //user have it in his collection
        else {
            Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_on);
            favButton.setImageDrawable(drawable);
        }


        voteAverrage.setRating(movie.getVoteAverage());
        userVote.setRating(UserData.getMovieRating(movie.getId()));

        userVote.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                UserData.getUserRatings().put(movie.getId(), (long) rating);
            }
        });

        if (movie.getPopularity() == 0) {
            popularity.setText("");
        } else {
            popularity.setText(getResources().getString(
                    R.string.rating_bar_popularity,
                    movie.getPopularity()));
        }

    }


    @OnClick(R.id.details_fav_button)
    public void OnFavButtonClick() {

        //user dont have this film in his collecion
        if (!movieInCollection) {
            Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_on);
            favButton.setImageDrawable(drawable);
            UserData.getUserMovies().add(movie);
            Tools.makeLongToast(getActivity().getApplicationContext(), getResources().getString(R.string.details_film_ok));
            movieInCollection = true;
        }
        //user have it in his collection
        else {
            Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.drawable.btn_star_big_off);
            favButton.setImageDrawable(drawable);
            UserData.getUserMovies().remove(movie);
            Tools.makeLongToast(getActivity().getApplicationContext(), getResources().getString(R.string.details_film_deleted));
            movieInCollection = false;
        }

    }


}
