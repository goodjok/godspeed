package com.godspeed.service.dagger.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.godspeed.service.dagger.DaggerHttpService;
import com.godspeed.service.dagger.HttpComponent;
import com.godspeed.source.application.GodspeedApplication;

import javax.inject.Inject;

public abstract class DaggerAdapter extends BaseAdapter {

    @Inject
    public DaggerHttpService httpService;

    public Context context;

    public DaggerAdapter(Context context){
        super();

        this.context = context;
        ((HttpComponent) GodspeedApplication.get(context).getComponent()).inject(this);
    }
}
