package com.godspeed.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godspeed.source.application.GodspeedCommonApplication;
import com.godspeed.source.callback.PermissionCallback;
import com.godspeed.source.event.EmptyEvent;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.ui.activity.ActivityFragmentAction;
import com.godspeed.ui.activity.DaggerFragmentActivity;
import com.godspeed.ui.dagger.GodspeedUIComponent;
import com.godspeed.ui.widget.loading.GodspeedLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class DaggerFragment extends Fragment implements ActivityFragmentAction {
    public final static int GETDATA_STATE_PULL = 1;
    public final static int GETDATA_STATE_MORE = 2;
    public final static int GETDATA_STATE_INIT = 3;

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

    public DaggerFragmentActivity getFragmentActivity() {
        return (DaggerFragmentActivity) super.getActivity();
    }

    public boolean checkPermission(String permission) {
        return getFragmentActivity().checkPermission(permission);
    }

    public void requestPermission(String permission, PermissionCallback callback) {
        getFragmentActivity().requestPermission(permission, callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public   View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((GodspeedUIComponent) GodspeedCommonApplication.get(getActivity()).getComponent()).inject(this);
        super.onCreateView(inflater,container,savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Class<Dialog> dialogClass =getDialogClass();
        if(dialogClass!=null)
            godspeedLoadingView = new GodspeedLoadingView(getActivity(), dialogClass);

        int contentViewId=getContentViewId();

        if(contentViewId>0) {
            View view = inflater.inflate(contentViewId, container, false);

            ButterKnife.bind(this, view);
            initHeader(view);
            initWidget(view);
            setWidgetState(view);
            initData();
            return view;
        }
        return null;
    }

    public void tabInitData(int index, Fragment fragment) {

    }

    public abstract int getContentViewId();


    public abstract void initHeader(View rootView);


    public abstract void initWidget(View rootView);

    public abstract void setWidgetState(View rootView);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EmptyEvent event) {

    }

}
