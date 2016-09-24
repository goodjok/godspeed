package com.godspeed.ui.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.godspeed.source.util.collection.CheckUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class DaggerAdapter<T> extends DaggerBaseAdapter {


    protected List<T> libraryAdapterList = new ArrayList<T>();
    protected ReleaseListener releaseListener;
//
    public DaggerAdapter(Context context) {
        super(context);
    }

    public DaggerAdapter(Context ctx, List<T> list) {
        this(ctx);
        resetData(list);
    }

    public List<T> getLibraryAdapterList() {
        return libraryAdapterList;
    }

    @Override
    public int getCount() {
        return libraryAdapterList.size();
    }

    @Override
    public T getItem(int position) {
        return libraryAdapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getItemView();
        }
        initItemView(position, convertView, parent);
        return convertView;
    }

    public abstract View getItemView();

    public abstract void initItemView(int position, View convertView, ViewGroup parent);

    public void resetData(List<T> tList) {
        if (CheckUtil.isEmpty(tList)) {
            tList = new ArrayList<T>();
        }
        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() > pageCount());
        }
        this.libraryAdapterList.clear();
        this.libraryAdapterList.addAll(tList);
        notifyDataSetChanged();
    }

    public void addData(List<T> tList) {
        if (CheckUtil.isEmpty(tList)) {
            tList = new ArrayList<T>();
        }
        if (releaseListener != null) {
            releaseListener.listStopRefresh();
            releaseListener.listShowOrHideFooter(tList.size() > pageCount());
        }
        this.libraryAdapterList.addAll(tList);
        notifyDataSetChanged();
    }

    public void startActivity(Intent it) {
        context.startActivity(it);
    }

    public void startActivityForResult(Intent it, int requestCode) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("ctx must activity");
        }
        ((Activity) context).startActivityForResult(it, requestCode);
    }

    public void startActivityForResult(Intent it, int requestCode, Bundle options) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("ctx must activity");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Activity) context).startActivityForResult(it, requestCode, options);
        } else {
            startActivityForResult(it, requestCode);
        }
    }

    public void setReleaseListener(ReleaseListener releaseListener) {
        this.releaseListener = releaseListener;
    }

    public interface ReleaseListener {
        public void listStopRefresh();

        public void listShowOrHideFooter(boolean show);
    }

    protected int pageCount() {
        return 10;
    }

    protected boolean isRelayout() {
        return true;
    }


}
