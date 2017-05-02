package com.example.przemek.mymoviesv3.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadMoviesListByTitle;
import com.example.przemek.mymoviesv3.R;

public class SearchMovieActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private SearchView searchView;

    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        recyclerView = (RecyclerView) findViewById(R.id.search_movie_recycler_view);
        recyclerView.setItemViewCacheSize(cacheSize);

        searchView = (SearchView) findViewById(R.id.search_movie_search_view);

        searchView.setOnQueryTextListener(new CustomSearchQueryViewListener());

        mMoviesAdapter = new MoviesAdapter(UserData.getSearchResult(), new OnMovieItemClickOpenDetails(UserData.getSearchResult()), getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);


        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class CustomSearchQueryViewListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {

            String userRequest = searchView.getQuery().toString();

            if(userRequest.equals("")) {
                Log.i("SearchView", "Empty request");
                return false;
            }

            new DownloadMoviesListByTitle(UserData.getSearchResult(), mMoviesAdapter).execute(userRequest);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }


}
