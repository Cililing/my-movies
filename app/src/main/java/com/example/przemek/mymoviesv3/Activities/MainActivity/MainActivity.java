package com.example.przemek.mymoviesv3.Activities.MainActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import com.example.przemek.mymoviesv3.Activities.SearchMovieActivity.SearchMovieActivity;
import com.example.przemek.mymoviesv3.Activities.Tools.MovieFragmentEmpty;
import com.example.przemek.mymoviesv3.Activities.Tools.MoviesAdapter;
import com.example.przemek.mymoviesv3.Activities.Tools.OnMovieItemClick;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

public class MainActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private MoviesAdapter mMoviesAdapter;
    private FloatingActionButton floatingActionButton;
    private SearchView searchView;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        UserData.loadData(this.getApplicationContext());

        //load start fragmemt
        fragmentManager = getFragmentManager();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loadStartFragment();
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.main_acticity_floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingButtomClick();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        recyclerView.setItemViewCacheSize(cacheSize);

        searchView  = (SearchView) findViewById(R.id.main_activity_search_view);
        searchView.setOnQueryTextListener(new CustomSearchQueryViewListener());

        mMoviesAdapter = new MoviesAdapter(
                UserData.getUserMovies(),
                new OnMovieItemClick(this,
                        fragmentManager,
                        UserData.getUserMovies(),
                        R.id.main_fragment_container
                ),
                getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);

    }

    public void onFloatingButtomClick() {
        Intent intent = new Intent(this, SearchMovieActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        fragmentTransaction.replace(R.id.main_fragment_container, searchResultFragment);
        fragmentTransaction.commit();
    }

    private class CustomSearchQueryViewListener implements SearchView.OnQueryTextListener {
        //TODO
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }
}
