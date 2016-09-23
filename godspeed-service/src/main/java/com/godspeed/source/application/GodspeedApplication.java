package com.godspeed.source.application;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.dagger.GodspeedComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public abstract class GodspeedApplication extends Application {

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

        initDB();

    }


    public static GodspeedApplication get(Context context) {
        return (GodspeedApplication) context.getApplicationContext();
    }

    public GodspeedComponent getComponent() {
        return this.component;
    }

    public void initDB() {
        FlowConfig.Builder builder = new FlowConfig.Builder(getApplicationContext());
        FlowManager.init(builder.build());
    }

}
