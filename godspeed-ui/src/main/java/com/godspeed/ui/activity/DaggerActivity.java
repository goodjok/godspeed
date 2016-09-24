package com.godspeed.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.callback.PermissionCallback;
import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.event.EmptyEvent;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.ui.dagger.GodspeedUIComponent;
import com.godspeed.ui.widget.loading.GodspeedLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract    class DaggerActivity extends Activity implements ActivityFragmentAction {
    protected PermissionCallback permissionCallback;
    public static final int CHECK_PERMISSION_CODE = 9527748;

    @Inject
    public DaggerHttpService httpService;

    protected GodspeedLoadingView godspeedLoadingView;


    @Override
    public void showLoading() {
        if(godspeedLoadingView!=null)
            godspeedLoadingView.showLoading();
    }

    @Override
    public void hiddenLoading() {
        if(godspeedLoadingView!=null)
            godspeedLoadingView.hiddenLoading();
    }


    protected <T> T getExtra(String key, T value) {
        Object o = null;
        if (value instanceof String) {
            o = this.getIntent().getStringExtra(key);
        } else if (value instanceof Boolean) {
            o = this.getIntent().getBooleanExtra(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            o = this.getIntent().getIntExtra(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            o = this.getIntent().getFloatExtra(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            o = this.getIntent().getLongExtra(key, ((Long) value).longValue());
        } else if (value instanceof Serializable) {
            o = this.getIntent().getSerializableExtra(key);
        }
        T t = (T) o;
        return t;
    }

    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public void requestPermission(String permission, PermissionCallback callback) {
        permissionCallback = callback;
        if (checkPermission(permission)) {
            permissionCallback.requestP(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, CHECK_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CHECK_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (permissionCallback != null)
                        permissionCallback.requestP(true);
                } else {
                    if (permissionCallback != null)
                        permissionCallback.requestP(false);
                }
                return;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((GodspeedUIComponent) GodspeedCommonApplication.get(this).getComponent()).inject(this);
        GodspeedContext.setTopAcivity(new WeakReference<Activity>(this));

        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Class<? extends Dialog> dialogClass =getDialogClass();
        if(dialogClass!=null)
            godspeedLoadingView = new GodspeedLoadingView(this, dialogClass);

        int contentViewId=getContentViewId();

        if(contentViewId>0) {
            View view = View.inflate(this, contentViewId, null);
            this.setContentView(view);
            ButterKnife.bind(this, view);

            initHeader(view);
            initWidget(view);
            setWidgetState(view);
        }
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public abstract int getContentViewId();


    public abstract void initHeader(View rootView);


    public abstract void initWidget(View rootView);

    public abstract void setWidgetState(View rootView);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EmptyEvent event) {

    }


}
