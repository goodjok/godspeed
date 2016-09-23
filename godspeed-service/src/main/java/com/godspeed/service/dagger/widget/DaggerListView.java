package com.godspeed.service.dagger.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.godspeed.service.dagger.DaggerHttpService;
import com.godspeed.service.dagger.HttpComponent;
import com.godspeed.source.application.GodspeedApplication;

import javax.inject.Inject;

public abstract class DaggerListView extends ListView {

    @Inject
    public DaggerHttpService httpService;

    public DaggerListView(Context context) {
        super(context);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }
}
