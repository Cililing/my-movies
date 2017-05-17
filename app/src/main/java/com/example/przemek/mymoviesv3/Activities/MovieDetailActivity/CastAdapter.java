package com.example.przemek.mymoviesv3.Activities.MovieDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.Interfaces.MovieItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Person;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.R;

import java.util.ArrayList;
import java.util.Objects;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{

    private ArrayList<Person> castList;
    private MovieItemClickListener listener; //for checking actors
    private Context mContext;

    CastAdapter(ArrayList<Person> castList, MovieItemClickListener listener, Context mContext) {
        this.castList = castList;
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cast_actor_row, parent, false);

        return new CastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Person person = castList.get(position);

        if (Objects.equals(person.getProfilePath(), "null")) {
            holder.characterImage.setImageResource(R.drawable.emptyposter);
        } else {
            new DownloadImageTask(holder.characterImage, mContext, null)
                    .execute(person.getProfilePath());
        }
        holder.character.setText(person.getCharacter());
        holder.name.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView characterImage;
        private TextView name;
        private TextView character;


        CastViewHolder(View itemView) {
            super(itemView);
            characterImage = (ImageView) itemView.findViewById(R.id.details_person_image);
            name = (TextView) itemView.findViewById(R.id.details_person_name);
            character = (TextView) itemView.findViewById(R.id.details_person_character);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            listener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
//            listener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}
