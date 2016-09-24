package com.godspeed.service.dagger;


import com.godspeed.source.dagger.GodspeedComponent;

public interface GodspeedServiceComponent  extends GodspeedComponent {

    void inject(DaggerObject daggerObject);

}