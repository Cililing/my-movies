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

import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsActivity;
import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsMainFragment;
import com.example.przemek.mymoviesv3.Activities.MovieRecyclerView.MoviesAdapter;
import com.example.przemek.mymoviesv3.Activities.SearchMovieActivity.SearchMovieActivity;
import com.example.przemek.mymoviesv3.Activities.Tools.MovieFragmentEmpty;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.Other.UserData;
import com.example.przemek.mymoviesv3.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private MoviesAdapter mMoviesAdapter;
    private FloatingActionButton floatingActionButton;
    private SearchView searchView;

    private ArrayList<Movie> userData;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        UserData.loadData(this.getApplicationContext());

        userData = UserData.getUserMovies();

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
                userData,
                new CustomItemClickListener(),
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

    private class CustomItemClickListener implements com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener {

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
                Movie m = userData.get(position);
                userData.remove(m);
                mMoviesAdapter.notifyDataSetChanged();
            }
            else if (params[0].equals(movieRowMenuDetailsTag)) {
                Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
                i.putExtra("movie", (Serializable) userData.get(position));
                view.getContext().startActivity(i);
            }

        }

        private void onClickLandspace(View view, int position, Object... params) {

            MovieDetailsMainFragment movieDetailsMainFragment = new MovieDetailsMainFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putSerializable("movie", (Serializable) userData.get(position));

            movieDetailsMainFragment.setArguments(fragmentBundle);
            fragmentTransaction.replace(R.id.main_fragment_container, movieDetailsMainFragment);
            fragmentTransaction.commit();

        }

        private void onClickPortrait(View view, int position, Object... params) {
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.putExtra("movie", (Serializable) userData.get(position));
            view.getContext().startActivity(i);
        }
    }

    private class CustomSearchQueryViewListener implements SearchView.OnQueryTextListener {
        //TODO
        @Override
        public boolean onQueryTextSubmit(String query) {
            //TODO
//            if (query.equals("")) {
//                userData = (ArrayList<Movie>) UserData.getUserMovies().clone();
//                mMoviesAdapter.notifyDataSetChanged();
//                return true;
//            }
//
//            userData.clear();
//            for (Movie m : UserData.getUserMovies()) {
//                if (m.isMatchedPattern(query)) {
//                    userData.add(m);
//                }
//            }
//            mMoviesAdapter.notifyDataSetChanged();
//            return true;flase;
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            //todo
//
//            if (Objects.equals(newText, "")) {
//                this.onQueryTextSubmit("");
//            }
//            return true;
            return false;
        }

    }
}
