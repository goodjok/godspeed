package com.godspeed.service.application;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public abstract class GodspeedServiceApplication extends GodspeedCommonApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        initDB();

    }


    public void initDB() {
        FlowConfig.Builder builder = new FlowConfig.Builder(getApplicationContext());
        FlowManager.init(builder.build());
    }

}
