package com.godspeed.service.dagger.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.godspeed.service.dagger.DaggerHttpService;
import com.godspeed.service.dagger.HttpComponent;
import com.godspeed.source.application.GodspeedApplication;

import javax.inject.Inject;

public abstract class DaggerRelativeLayout extends RelativeLayout {

    @Inject
    public DaggerHttpService httpService;

    public DaggerRelativeLayout(Context context) {
        super(context);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((HttpComponent) GodspeedApplication.get(this.getContext()).getComponent()).inject(this);
    }
}
