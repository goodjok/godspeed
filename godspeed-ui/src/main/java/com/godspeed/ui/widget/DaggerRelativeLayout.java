package com.godspeed.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.ui.dagger.GodspeedUIComponent;

import javax.inject.Inject;

public abstract class DaggerRelativeLayout extends RelativeLayout {

    @Inject
    public DaggerHttpService httpService;

    public DaggerRelativeLayout(Context context) {
        super(context);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }
}
