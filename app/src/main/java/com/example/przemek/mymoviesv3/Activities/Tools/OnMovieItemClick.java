package com.example.przemek.mymoviesv3.Activities.Tools;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsActivity;
import com.example.przemek.mymoviesv3.Activities.MovieDetailActivity.MovieDetailsMainFragment;
import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;

import java.io.Serializable;
import java.util.List;


public class OnMovieItemClick implements CustomItemClickListener {

    private Context mContext;
    private FragmentManager fragmentManager;
    private List items;
    private int container;

    public OnMovieItemClick(Context context, FragmentManager fragmentManager, List items, int container) {
        mContext = context;
        this.fragmentManager = fragmentManager;
        this.items = items;
        this.container = container;
    }

    @Override
    public void onClick(View view, int position, String... params) {
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            onClickPortrait(view, position, params);
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            onClickLandspace(view, position, params);
    }

    @Override
    public void onLongClick(View view, int position, String... params) {
        //nothing to do
    }

    private void onClickLandspace(View view, int position, String... params) {

        MovieDetailsMainFragment movieDetailsMainFragment = new MovieDetailsMainFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putSerializable("movie", (Serializable) items.get(position));

        movieDetailsMainFragment.setArguments(fragmentBundle);
        fragmentTransaction.replace(container, movieDetailsMainFragment);
        fragmentTransaction.commit();

    }

    private void onClickPortrait(View view, int position, String... params) {
        Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
        i.putExtra("movie", (Serializable) items.get(position));
        view.getContext().startActivity(i);
    }
}
