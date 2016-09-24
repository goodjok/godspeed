package com.godspeed.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.ui.dagger.GodspeedUIComponent;

import javax.inject.Inject;

public abstract class DaggerView extends View{

    @Inject
    public DaggerHttpService httpService;

    public DaggerView(Context context) {
        super(context);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }

    public DaggerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this.getContext()).getComponent()).inject(this);
    }

}
