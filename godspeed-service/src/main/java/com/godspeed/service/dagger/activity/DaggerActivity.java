package com.godspeed.service.dagger.activity;

import android.app.Activity;
import android.os.Bundle;

import com.godspeed.service.dagger.DaggerHttpService;
import com.godspeed.service.dagger.HttpComponent;
import com.godspeed.source.application.GodspeedApplication;
import com.godspeed.source.context.GodspeedContext;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public abstract class DaggerActivity extends Activity{

    @Inject
    public DaggerHttpService httpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((HttpComponent) GodspeedApplication.get(this).getComponent()).inject(this);
        GodspeedContext.setTopAcivity(new WeakReference<Activity>(this));
        super.onCreate(savedInstanceState);
    }
}
