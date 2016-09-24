package com.godspeed.source.dagger;

import android.app.Application;


public class GodspeedModule {
    protected Application mApplication;

    public GodspeedModule(Application application) {
        mApplication = application;
    }


}