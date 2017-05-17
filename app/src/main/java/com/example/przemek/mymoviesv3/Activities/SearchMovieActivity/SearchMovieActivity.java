package com.example.przemek.mymoviesv3.Activities.SearchMovieActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.przemek.mymoviesv3.Activities.ErrorActivity.ErrorActivity;
import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsActivity;
import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsMainFragment;
import com.example.przemek.mymoviesv3.Activities.Tools.ActivitiesTag;
import com.example.przemek.mymoviesv3.Activities.Tools.MovieFragmentEmpty;
import com.example.przemek.mymoviesv3.Activities.MovieRecyclerView.MoviesAdapter;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadMoviesListTask;
import com.example.przemek.mymoviesv3.R;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MoviesAdapter mMoviesAdapter;
    private FragmentManager fragmentManager;

    private ArrayList<Movie> searchResult;
    private ArrayList<Movie> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        fragmentManager = getFragmentManager();

        UserData.loadData(this.getApplicationContext());

        userData = UserData.getUserMovies();
        searchResult = new ArrayList<>();

        //load start fragmemt
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loadStartFragment();
        }

        //load recycler view
        recyclerView = (RecyclerView) findViewById(R.id.search_movie_recycler_view);
        recyclerView.setItemViewCacheSize(cacheSize);


        //load search view
        searchView = (SearchView) findViewById(R.id.search_movie_search_view);
        searchView.setOnQueryTextListener(new CustomSearchQueryViewListener());

        //set recycler view
        mMoviesAdapter = new MoviesAdapter(
                searchResult,
                new MovieItemClickListener(),
                this.getApplicationContext()
        );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);
        mMoviesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.saveData(this.getApplicationContext());
    }

    private void loadStartFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MovieFragmentEmpty searchResultFragment = new MovieFragmentEmpty();
        fragmentTransaction.replace(R.id.search_fragment_container, searchResultFragment);
        fragmentTransaction.commit();
    }

    /**
     * Serch handler
     */
    private class CustomSearchQueryViewListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {

            String userRequest = searchView.getQuery().toString();

            if (userRequest.equals("")) {
                Log.i("SearchView", "Empty request");
                return false;
            }

            new DownloadMoviesListTask(
                    searchResult,
                    mMoviesAdapter,
                    getApplicationContext(),
                    ErrorActivity.class).execute(userRequest);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    private class MovieItemClickListener implements com.example.przemek.mymoviesv3.Interfaces.MovieItemClickListener {

        @Override
        public void onClick(View view, int position, Object... params) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                onClickPortrait(view, position, params);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                onClickLandspace(view, position, params);
        }

        @Override
        public void onLongClick(View view, int position, Object... params) {
            //fragment.toString();
        }

        @Override
        public void onMovieMenuItemClick(View view, int position, Object... params) {
            if (params[0].equals(movieRowMenuAddTag)) {
                Movie movie = searchResult.get(position);

                if (UserData.getUserMovies().contains(movie)) {
                    userData.remove(movie);
                } else {
                    userData.add(movie);
                }

                mMoviesAdapter.notifyDataSetChanged();
            }
            else if (params[0].equals(movieRowMenuDetailsTag)) {
                Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
                i.putExtra(ActivitiesTag.movieBundleTag, searchResult.get(position));
                view.getContext().startActivity(i);
            }

        }

        private void onClickLandspace(View view, int position, Object... params) {

            MovieDetailsMainFragment movieDetailsMainFragment = new MovieDetailsMainFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putSerializable(ActivitiesTag.movieBundleTag, searchResult.get(position));

            movieDetailsMainFragment.setArguments(fragmentBundle);
            fragmentTransaction.replace(R.id.search_fragment_container, movieDetailsMainFragment);
            fragmentTransaction.commit();

        }

        private void onClickPortrait(View view, int position, Object... params) {
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.putExtra(ActivitiesTag.movieBundleTag, searchResult.get(position));
            view.getContext().startActivity(i);
        }
    }

}
