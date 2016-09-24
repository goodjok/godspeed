package com.godspeed.ui.dagger;

import android.app.Application;

import com.godspeed.source.dagger.GodspeedModule;
import com.godspeed.source.net.DaggerHttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UIModule extends GodspeedModule {

    private  Class<? extends DaggerHttpService> clazz;


    public UIModule(Application application, Class<? extends DaggerHttpService> clazz) {
        super(application);
        this.clazz = clazz;
    }



    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public DaggerHttpService provideHttpService() {

        DaggerHttpService httpService = null;
        try {
            httpService =  clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return httpService;
    }
}
