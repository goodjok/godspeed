package com.godspeed.source.dagger;


import com.godspeed.source.application.GodspeedApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GodspeedModule.class})
public interface GodspeedComponent {

    GodspeedApplication injectApplication(GodspeedApplication application);

}