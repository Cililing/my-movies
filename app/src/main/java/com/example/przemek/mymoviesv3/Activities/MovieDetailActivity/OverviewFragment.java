package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {

    public static OverviewFragment getInstance() {
        return new OverviewFragment();
    }

    @BindView(R.id.details_overview)
    TextView overview;
    @BindView(R.id.details_genres)
    TextView genres;
    @BindView(R.id.details_release_date)
    TextView releaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_overview, container, false);
        ButterKnife.bind(this, view);

        initialize();
        return view;
    }

    private void initialize() {
        Movie movie = (Movie) getArguments().getSerializable("movie");

        if (movie == null) return;

        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        genres.setText(movie.getGenresList());
    }
}
