package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.przemek.mymoviesv3.Activities.Tools.ActivitiesTag;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

import butterknife.ButterKnife;

public class MovieDetailsMainFragment extends Fragment {

    private Movie movie;
    private FragmentManager fragmentManager;

    public static MovieDetailsMainFragment getInstance() {
        return new MovieDetailsMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_full, container, false);
        ButterKnife.bind(this, view);
        fragmentManager = getChildFragmentManager();
        //load data
        UserData.loadData(view.getContext());

        movie = (Movie) getArguments().getSerializable(ActivitiesTag.movieBundleTag);
        if (movie == null) movie = new Movie();
        generateThisShit();

        return view;
    }

    private void generateThisShit() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);

        HeaderFragment headerFragment = HeaderFragment.getInstance();
        OverviewFragment overviewFragment = OverviewFragment.getInstance();
        ImagesFragment imagesFragment = ImagesFragment.getInstance();
        RatingBarFragment ratingBarFragment = RatingBarFragment.getInstance();
        CastFragment castFragment = CastFragment.getInstance();

        reloadFragment(headerFragment, bundle, R.id.details_header_holder, ActivitiesTag.detailsHeaderFragment);
        reloadFragment(overviewFragment, bundle, R.id.details_overview_holder, ActivitiesTag.detailsOverviewFragment);
        reloadFragment(imagesFragment, bundle, R.id.details_images_holder, ActivitiesTag.detailsImagesFragment);
        reloadFragment(ratingBarFragment, bundle, R.id.details_rating_holder, ActivitiesTag.detailsRatingFragment);
        reloadFragment(castFragment, bundle, R.id.details_cast_holder, ActivitiesTag.detailsCastFragment);
    }

    @Override
    public void onStop() {
        super.onStop();
        UserData.saveData(getView().getContext());
    }

    private void reloadFragment(Fragment fragment, Bundle bundle, @IdRes int holderId, String tag) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        fragmentManager
                .beginTransaction()
                .replace(holderId,
                        fragment,
                        tag)
                .commit();

    }
}
