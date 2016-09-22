package com.godspeed.source.util.system;

import android.os.Looper;

public class ThreadUtil {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
