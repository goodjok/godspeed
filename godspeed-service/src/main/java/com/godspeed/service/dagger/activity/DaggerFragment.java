package com.godspeed.service.dagger.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godspeed.service.dagger.DaggerHttpService;
import com.godspeed.service.dagger.HttpComponent;
import com.godspeed.source.application.GodspeedApplication;

import javax.inject.Inject;

public abstract class DaggerFragment extends Fragment {

    @Inject
    public DaggerHttpService httpService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HttpComponent) GodspeedApplication.get(getActivity()).getComponent()).inject(this);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
