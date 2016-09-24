package com.godspeed.ui.activity;

import android.app.Dialog;
import android.support.annotation.IdRes;
import android.view.View;

public interface ActivityFragmentAction {

    int getContentViewId();

    void initHeader(View rootView);

    void initWidget(View rootView);

    void setWidgetState(View rootView);

    void initData();

    void showLoading() ;

    void hiddenLoading() ;

    Class<? extends Dialog> getDialogClass();

    @IdRes
    int getTitleBarRes();
}
