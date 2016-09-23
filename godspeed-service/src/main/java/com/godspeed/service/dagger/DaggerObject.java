package com.godspeed.service.dagger;


import com.godspeed.source.application.GodspeedApplication;
import com.godspeed.source.context.GodspeedContext;

import javax.inject.Inject;

public class DaggerObject {

    @Inject
    public DaggerHttpService httpService;

    public DaggerObject() {
        ((HttpComponent) GodspeedApplication.get(GodspeedContext.context).getComponent()).inject(this);
    }
}
