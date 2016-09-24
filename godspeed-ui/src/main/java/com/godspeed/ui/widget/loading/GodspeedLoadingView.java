package com.godspeed.ui.widget.loading;


import android.app.Dialog;
import android.content.Context;
import android.os.Message;

import com.godspeed.source.util.system.ThreadUtil;
import com.godspeed.source.util.system.WeakReferenceHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GodspeedLoadingView {

    protected static final int MESSAGE_BASE_SHOW_LOADING = 900001;
    protected static final int MESSAGE_BASE_HIDE_LOADING = 900002;


    protected Context context;
    protected Class<Dialog> dialogClass;
    protected Dialog dialog;

    public GodspeedLoadingView(Context context, Class<Dialog> dialogClass) {
        this.context = context;
        this.dialogClass = dialogClass;
    }



    protected static class BaseActivityHandler extends WeakReferenceHandler<GodspeedLoadingView> {

        public BaseActivityHandler(GodspeedLoadingView loadingView) {
            super(loadingView);
        }

        @Override
        protected void handleMessage(Message msg, GodspeedLoadingView loadingView) {
            switch (msg.what) {
                case MESSAGE_BASE_SHOW_LOADING: {
                    loadingView.showLoadingView();

                    break;
                }
                case MESSAGE_BASE_HIDE_LOADING: {
                    loadingView.hiddenLoadingView();
                    break;
                }
            }
        }
    }

    private BaseActivityHandler activityHandler = new BaseActivityHandler(this);

    public void showLoading() {
        if (ThreadUtil.isMainThread()) {
            showLoadingView();
        } else {
            activityHandler.sendEmptyMessage(MESSAGE_BASE_SHOW_LOADING);
        }

    }

    protected void showLoadingView() {
        if (dialog == null) {

            try {
                Constructor c1 = dialogClass.getDeclaredConstructor(new Class[]{Context.class});
                c1.setAccessible(true);
                dialog = (Dialog) c1.newInstance(new Object[]{context});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public void hiddenLoading() {
        if (ThreadUtil.isMainThread()) {
            hiddenLoadingView();
        } else {
            activityHandler.sendEmptyMessage(MESSAGE_BASE_HIDE_LOADING);
        }

    }

    protected void hiddenLoadingView() {
        if (dialog != null && dialog.isShowing())
            dialog.hide();
    }
}
