package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.przemek.mymoviesv3.Activities.Tools.ActivitiesTag;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        UserData.loadData(this.getApplicationContext());
        Movie movie = (Movie) getIntent().getExtras().getSerializable(ActivitiesTag.movieBundleTag);
        if (movie == null) movie = new Movie();

        FragmentManager fragmentManager = getFragmentManager();
        MovieDetailsMainFragment movieDetailsMainFragment = MovieDetailsMainFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ActivitiesTag.movieBundleTag, movie);
        movieDetailsMainFragment.setArguments(bundle);

        fragmentManager
                .beginTransaction()
                .add(R.id.movie_details_activity_content,
                        movieDetailsMainFragment)
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.saveData(this.getApplicationContext());
    }
}