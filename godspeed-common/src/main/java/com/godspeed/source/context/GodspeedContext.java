package com.godspeed.source.context;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class GodspeedContext {
    private static String app_identity;
    private static String user_agent = "";

    public static Resources resources;
    public static Context context;
    public static SharedPreferences preferences;
    public static String packageName;

    private static WeakReference<Activity> topAcivity;

    public static WeakReference<Activity> getTopAcivity() {
        return topAcivity;
    }

    @SuppressWarnings("unchecked")
    public static void setTopAcivity(WeakReference<Activity> topAcivity) {
        GodspeedContext.topAcivity = topAcivity;
    }

    public static void injectApplication(Context application) {
        context = application;
        resources = application.getResources();
        packageName = application.getPackageName();
        app_identity = packageName.replace("\\.", "_");
        preferences = application.getSharedPreferences(app_identity, Activity.MODE_PRIVATE);
        getUA(context);
    }

    private static String getUA(Context context) {
        try {
            WebView webview;
            webview = new WebView(context);
            webview.layout(0, 0, 0, 0);
            WebSettings settings = webview.getSettings();
            String ua = settings.getUserAgentString();
            Log.e("HJJ", "User Agent:" + ua);

            return ua;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return   version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getUserAgent() {
        return user_agent;
    }

    public static String getString(String key, String defValue) {

        String token = preferences.getString(key, defValue);

        return token;
    }

    public static int getInt(String key, int defValue) {
        int token = preferences.getInt(key, defValue);

        return token;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        boolean token = preferences.getBoolean(key, defValue);

        return token;
    }

    public static void setString(String key, String defValue) {

        SharedPreferences.Editor editor = preferences.edit();
        if (defValue == null) {
            editor.remove(key);
        } else {
            editor.putString(key, defValue);
        }
        editor.apply();
    }


    public static void remove(String key) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void setInt(String key, int defValue) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, defValue);
        editor.apply();
    }

    public static void setLong(String key, long defValue) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, defValue);
        editor.apply();
    }

    public static void setFloat(String key, float defValue) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, defValue);
        editor.apply();
    }

    public static void setBoolean(String key, boolean defValue) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, defValue);
        editor.apply();
    }


    public static String getMetaValue(String name) {
        String channel = "";
        try {
            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(name);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }



    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    protected static String uuid;
    protected static final String PREFS_FILE = "gank_device_id.xml";
    protected static final String PREFS_DEVICE_ID = "gank_device_id";

    static public String getUDID()
    {
        if( uuid ==null ) {
            synchronized (context) {
                if( uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences( PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null );

                    if (id != null) {
                        // Use the ids previously computed and stored in the prefs file
                        uuid = id;
                    } else {

                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                        // Use the Android ID unless it's broken, in which case fallback on deviceId,
                        // unless it's not available, then fallback on a random number which we store
                        // to a prefs file
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                            } else {
                                final String deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                                uuid = deviceId!=null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }

                        // Write the value out to the prefs file
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid).apply();
                    }
                }
            }
        }
        return uuid;
    }
}
