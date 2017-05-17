package com.example.przemek.mymoviesv3.Activities.MovieRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.Interfaces.MovieItemClickListener;
import com.example.przemek.mymoviesv3.R;

class MoviesAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieAverageRating;
    TextView movieGenres;
    ImageView moviePoster;

    private ImageButton settigins;
    private LinearLayout menuLayout;
    private MovieItemClickListener listener;

    Button addButton;
    Button detailsButton;



    MoviesAdapterHolder(final View itemView, MovieItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        initialize();

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    private void initialize() {

        movieTitle = (TextView) itemView.findViewById(R.id.movie_row_title);
        movieReleaseDate = (TextView) itemView.findViewById(R.id.movie_row_release_date);
        movieAverageRating = (TextView) itemView.findViewById(R.id.movie_row_vote_average);
        movieGenres = (TextView) itemView.findViewById(R.id.movie_row_genres);
        moviePoster = (ImageView) itemView.findViewById(R.id.movie_row_poster);
        menuLayout = (LinearLayout) itemView.findViewById(R.id.movie_row_linear_menu);
        settigins = (ImageButton) itemView.findViewById(R.id.movie_row_button);

        menuLayout.setVisibility(View.GONE);

        settigins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuLayout.getVisibility() == View.VISIBLE) {
                    menuLayout.setVisibility(View.GONE);
                } else {
                    menuLayout.setVisibility(View.VISIBLE);
                }
            }

        });

        //add listeners to buttons in menu
        detailsButton = (Button) menuLayout.findViewById(R.id.movie_row_menu_details);
        final String tag1 = (String) detailsButton.getTag();
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieMenuItemClick(v, getAdapterPosition(), tag1);

                //hide layout after click
                menuLayout.setVisibility(View.GONE);
            }
        });

        addButton = (Button) menuLayout.findViewById(R.id.movie_row_menu_add);
        final String tag2 = (String) addButton.getTag();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieMenuItemClick(v, getAdapterPosition(), tag2);

                //hide layout after click
                menuLayout.setVisibility(View.GONE);}
        });
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v, this.getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if (listener != null) {
            listener.onLongClick(v, this.getAdapterPosition());
            return true;
        }
        return false;
    }

}