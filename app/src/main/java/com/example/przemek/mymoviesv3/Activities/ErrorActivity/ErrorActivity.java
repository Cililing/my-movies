package com.example.przemek.mymoviesv3.Activities.ErrorActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.przemek.mymoviesv3.Other.Tools;
import com.example.przemek.mymoviesv3.R;

/**
 * Error activity - shows information about problems with network connection.
 */
public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        findViewById(R.id.activity_error_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAnywhere();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //check if you have connection with internet
        if(checkInternetConnection()) {
            super.onBackPressed();
        }
        else {
            Tools.makeLongToast(this.getApplicationContext(), getResources().getString(R.string.error_toast));
        }

    }

    public void onClickAnywhere() {
        onBackPressed();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
