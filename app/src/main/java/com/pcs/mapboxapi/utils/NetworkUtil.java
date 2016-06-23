package com.pcs.mapboxapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dharmasai
 */
public class NetworkUtil {

    private NetworkUtil() {
        //default constructor needed
    }

    /**
     * This method checks whether the user is connected to a network or not
     *
     * @param context
     * @return :true, if the user is connected to a network ,else returns false
     */
    public static boolean isOnline(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

}