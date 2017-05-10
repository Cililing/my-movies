package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.app.Fragment;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.Other.Tools;
import com.example.przemek.mymoviesv3.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImagesFragment extends Fragment {

    @BindView(R.id.details_movie_images_rv) RecyclerView recyclerView;
    @BindView(R.id.details_movie_images_view) ImageView imageView;
    private ImagesAdapter imagesAdapter;

    private Movie movie;
    private Drawable lastClickedDrawable = null;

    public static ImagesFragment getInstance() {
        return new ImagesFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_images, container, false);
        ButterKnife.bind(this, view);

        //get movie
        movie = (Movie) getArguments().getSerializable("movie");
        if (movie == null) return null;

        //set rv
        recyclerView.setItemViewCacheSize(20);
        imagesAdapter = new ImagesAdapter(movie, new CustomItemClickListener(), getActivity());

        RecyclerView.LayoutManager layoutManager = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
             layoutManager = new LinearLayoutManager(getActivity());
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imagesAdapter);

        imagesAdapter.notifyDataSetChanged();

        return view;
    }

    class CustomItemClickListener implements com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener {

        @Override
        public void onClick(View view, int position, Object... params) {
            lastClickedDrawable = ((ImageView) params[0]).getDrawable();
            if (lastClickedDrawable != null) imageView.setImageDrawable(lastClickedDrawable);
        }

        @Override
        public void onLongClick(View view, int position, Object... params) {
        }

        @Override
        public void onMovieMenuItemClick(View view, int position, Object... params) {
        }
    }

    @OnClick(R.id.details_movie_images_view)
    public void showPopUpImage(View anchorView) {

        final View view = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_movie_images_popup,
                null);

        final PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        final ImageView image = (ImageView) view.findViewById(R.id.details_pop_up_iv);
        image.setImageDrawable(lastClickedDrawable);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(anchorView);

    }
}
