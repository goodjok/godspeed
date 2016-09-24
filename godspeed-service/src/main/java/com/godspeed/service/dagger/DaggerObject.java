package com.godspeed.service.dagger;


import com.godspeed.service.application.GodspeedServiceApplication;
import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.net.DaggerHttpService;

import java.io.Serializable;

import javax.inject.Inject;

public class DaggerObject implements Serializable{

    @Inject
    public DaggerHttpService httpService;

    public DaggerObject() {
        ((GodspeedServiceComponent) GodspeedServiceApplication.get(GodspeedContext.context).getComponent()).inject(this);
    }
}
