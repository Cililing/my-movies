package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabase;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseApiException;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Person;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DownloadCastListTask extends AsyncTask<Integer, Void, ArrayList<Person>> {

    private ArrayList<Person> personList;
    private RecyclerView.Adapter castAdapter;
    private Context mContext;
    private Class networkErrorActivity;

    public DownloadCastListTask(ArrayList<Person> personList, RecyclerView.Adapter castAdapter, Context mContext, Class networkErrorActivity) {
        this.personList = personList;
        this.castAdapter = castAdapter;
        this.mContext = mContext;
        this.networkErrorActivity = networkErrorActivity;
        AsyncTaskManager.asyncTasks.add(this);
    }

    protected ArrayList<Person> doInBackground(Integer... params) {

        try {
            MovieDatabase.getMovieCastById(params[0], personList);
        } catch (IOException | MovieDatabaseApiException | JSONException e) {
            Intent i = new Intent(mContext, networkErrorActivity);
            i.putExtra("last_exception", e);
            mContext.startActivity(i);
        }

        return personList;

    }

    @Override
    protected void onPostExecute(ArrayList<Person> ignored) {
        castAdapter.notifyDataSetChanged();
        AsyncTaskManager.asyncTasks.remove(this);
    }
}
