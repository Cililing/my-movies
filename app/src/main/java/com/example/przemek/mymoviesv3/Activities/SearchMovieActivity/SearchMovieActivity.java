package com.example.przemek.mymoviesv3.Activities.SearchMovieActivity;

import android.app.Activity;
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
import com.example.przemek.mymoviesv3.Activities.Tools.MovieFragmentEmpty;
import com.example.przemek.mymoviesv3.Activities.Tools.MoviesAdapter;
import com.example.przemek.mymoviesv3.Activities.Tools.OnMovieItemClick;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadMoviesListTask;
import com.example.przemek.mymoviesv3.R;

public class SearchMovieActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MoviesAdapter mMoviesAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        fragmentManager = getFragmentManager();
        UserData.loadData(this.getApplicationContext());

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
                UserData.getSearchResult(),
                new OnMovieItemClick(
                        this,
                        fragmentManager,
                        UserData.getSearchResult(),
                        R.id.search_fragment_container
                ),
                getApplicationContext());

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

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE & searchResultFragment != null) {
//            getFragmentManager().beginTransaction().remove(searchResultFragment).commit();
//        }
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
                    UserData.getSearchResult(),
                    mMoviesAdapter,
                    getApplicationContext(),
                    ErrorActivity.class)
                    .execute(userRequest);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

}
