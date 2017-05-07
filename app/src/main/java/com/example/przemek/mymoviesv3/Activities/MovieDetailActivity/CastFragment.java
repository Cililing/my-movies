package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.przemek.mymoviesv3.Activities.ErrorActivity.ErrorActivity;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Person;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadCastListTask;
import com.example.przemek.mymoviesv3.R;

import java.util.ArrayList;

public class CastFragment extends Fragment {

    private RecyclerView recyclerView;
    private CastAdapter mCastAdapter;


    public static CastFragment getInstance() {
        return new CastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.details_cast_recycler_view);
        recyclerView.setItemViewCacheSize(20);

        ArrayList<Person> castList = new ArrayList<>();

        mCastAdapter = new CastAdapter(castList, null, getActivity());
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                );
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCastAdapter);
        mCastAdapter.notifyDataSetChanged();

        Movie movie = (Movie) getArguments().getSerializable("movie");

        if (movie == null) return null;
        new DownloadCastListTask(
                castList,
                mCastAdapter,
                getActivity().getApplicationContext(),
                ErrorActivity.class)
        .execute(movie.getId());

        return view;
    }
}
