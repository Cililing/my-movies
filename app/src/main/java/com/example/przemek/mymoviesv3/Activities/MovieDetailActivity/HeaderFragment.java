package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.Activities.ErrorActivity.ErrorActivity;
import com.example.przemek.mymoviesv3.Activities.Tools.ActivitiesTag;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.Other.AutoResizeTextView;
import com.example.przemek.mymoviesv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderFragment extends Fragment {

    @BindView(R.id.details_poster)
    ImageView poster;
    @BindView(R.id.details_title)
    AutoResizeTextView title;
    @BindView(R.id.details_original_title)
    AutoResizeTextView originalTitle;
    @BindView(R.id.details_duration)
    TextView duration;

    public static HeaderFragment getInstance() {
        return new HeaderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_header, container, false);
        ButterKnife.bind(this, view);

        initlize();

        return view;
    }

    private void initlize() {
        Movie movie = (Movie) getArguments().getSerializable(ActivitiesTag.movieBundleTag);

        if (movie == null) return;
        String posterPath = movie.getPosterPath();
        new DownloadImageTask(poster, getActivity().getApplicationContext(), ErrorActivity.class)
                .execute(posterPath);

        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOriginalTitle());

        int rt = movie.getRuntime();
        int h = rt / 60;
        int m = rt % 60;
        String runtime = String.format("%1$dh %2$dm", h, m);
        duration.setText(runtime);

    }
}
