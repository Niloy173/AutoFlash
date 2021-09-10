package com.example.finalexam;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WIFiState {

    public boolean isConnected(Context context) {

        //checking wifi is connected or not connected
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return (activeNetworkInfo != null && activeNetworkInfo.isConnected());


        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }
}
