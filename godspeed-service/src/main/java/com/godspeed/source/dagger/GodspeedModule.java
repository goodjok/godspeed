package com.godspeed.source.dagger;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class GodspeedModule {
    private Application mApplication;

    public GodspeedModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }


}