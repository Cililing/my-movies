package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//Set image downloaded from internet
public class DownloadImageTask extends android.os.AsyncTask<String, Void, Bitmap> {

    private final int emptyPosterID;
    private ImageView imageView;
    private Context mContext;

    public DownloadImageTask(ImageView imageView, Context mContext) {
        this.imageView = imageView;
        this.mContext = mContext;
        emptyPosterID = mContext.getResources().getIdentifier("emptyposter.jpg", "drawable", mContext.getPackageName());
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        InputStream in = null;
        String urldisplay = urls[0];
        Bitmap result = null;

        try {
            in = new java.net.URL(urldisplay).openStream();
            result = BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException fnfe) {
            result = BitmapFactory.decodeResource(mContext.getResources(), emptyPosterID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}