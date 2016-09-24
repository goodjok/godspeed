package com.test;

import javax.inject.Singleton;

import dagger.Component;
import com.godspeed.service.dagger.ServiceModule;
import com.godspeed.service.dagger.GodspeedServiceComponent;
import com.godspeed.ui.dagger.GodspeedUIComponent;

@Singleton
@Component(modules = {ServiceModule.class})
public interface MYComponent extends GodspeedUIComponent,GodspeedServiceComponent{
}
