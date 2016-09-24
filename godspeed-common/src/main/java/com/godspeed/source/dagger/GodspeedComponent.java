package com.godspeed.source.dagger;


import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.net.DaggerHttpService;

public interface GodspeedComponent {

    GodspeedCommonApplication injectApplication(GodspeedCommonApplication application);

    DaggerHttpService provideHttpService();

}