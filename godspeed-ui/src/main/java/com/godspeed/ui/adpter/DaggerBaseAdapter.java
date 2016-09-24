package com.godspeed.ui.adpter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.ui.dagger.GodspeedUIComponent;

import javax.inject.Inject;

public abstract class DaggerBaseAdapter extends BaseAdapter {

    @Inject
    public DaggerHttpService httpService;

    public Context context;

    public DaggerBaseAdapter(Context context) {
        this.context = context;
        ((GodspeedUIComponent) GodspeedCommonApplication.get(context).getComponent()).inject(this);
    }



}
