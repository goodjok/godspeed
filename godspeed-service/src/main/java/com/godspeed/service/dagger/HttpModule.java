package com.godspeed.service.dagger;

import android.app.Application;

import com.godspeed.source.dagger.GodspeedModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class  HttpModule extends GodspeedModule {

    private  Class<? extends DaggerHttpService> clazz;


    public HttpModule(Application application,Class<? extends DaggerHttpService> clazz) {
        super(application);
        this.clazz = clazz;
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
