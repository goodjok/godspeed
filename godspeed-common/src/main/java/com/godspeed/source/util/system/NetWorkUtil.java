package com.godspeed.source.util.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.godspeed.source.context.GodspeedContext;

public class NetWorkUtil {

    private static NetworkInfo networkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) GodspeedContext.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info;
    }


    public static boolean connectionAvailable() {
        NetworkInfo info = NetWorkUtil.networkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean connectionWifi() {
        NetworkInfo info = NetWorkUtil.networkInfo();

        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        else
            return false;
    }

    public static boolean connectionWWWLAN() {
        NetworkInfo info = NetWorkUtil.networkInfo();

        if (connectionAvailable() && info.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        else
            return false;
    }

}


