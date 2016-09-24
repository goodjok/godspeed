package com.godspeed.source.application;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.dagger.GodspeedComponent;

public abstract class GodspeedCommonApplication extends Application {

    protected GodspeedComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmManager timeZone = (AlarmManager) getSystemService(ALARM_SERVICE);
        timeZone.setTimeZone("Asia/Shanghai");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        GodspeedContext.injectApplication(this);


    }


    public static GodspeedCommonApplication get(Context context) {
        return (GodspeedCommonApplication) context.getApplicationContext();
    }

    public GodspeedComponent getComponent() {
        return this.component;
    }


}
