package com.example.przemek.mymoviesv3.Interfaces;

import android.view.View;

public interface CustomItemClickListener {

    String movieRowMenuDetailsTag = "movie_row_menu_details";
    String movieRowMenuAddTag = "movie_row_menu_add";

    void onClick(View view, int position, Object... params);
    void onLongClick(View view, int position, Object... params);
    void onMovieMenuItemClick(View view, int position, Object... params);

}
