package com.godspeed.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.godspeed.source.callback.PermissionCallback;
import com.godspeed.source.util.collection.CheckUtil;
import com.godspeed.source.util.ui.ToastUtil;
import com.godspeed.ui.R;
import com.godspeed.ui.widget.GodspeedTabbar;

import java.util.ArrayList;
import java.util.List;


public  abstract class DaggerTabActivity extends DaggerFragmentActivity {
    private static final String TAB_POSITION = "tab_position";

    protected GodspeedTabbar tabbar;

    protected int mTabPosition = 0;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTabPosition = savedInstanceState.getInt(TAB_POSITION, 0);

            FragmentManager fm = getSupportFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            if (!CheckUtil.isEmpty(fragments)) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment fgm : fragments) {
                    if (fgm != null)
                        ft.remove(fgm);
                }
                ft.commit();
            }
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.godspeed_activity_tabs;
    }


    @Override
    public void initHeader(View rootView) {
        changeNavigation(0);

    }


    @Override
    public void initWidget(View rootView) {
        tabbar = (GodspeedTabbar) findViewById(R.id.tabbars);


        List<GodspeedTabbar.TabInfo> tabInfos = new ArrayList<GodspeedTabbar.TabInfo>();
        buildTabInfos(tabInfos);


        tabbar.addTabs(tabInfos, getSupportFragmentManager(), mTabPosition);

        tabbar.setOnTabPageChangeListener(new GodspeedTabbar.OnTabPageChangeListener() {
            @Override
            public boolean onPageSelected(int position, Fragment fragment) {
                mTabPosition = position;
                changeNavigation(position);
                return false;
            }
        });
    }

    @Override
    public void setWidgetState(View rootView) {
        checkP();
    }

    protected void checkP() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String p : permissions) {
            requestPermission(p, new PermissionCallback() {
                @Override
                public void requestP(boolean own) {
                    if (!own) {
                        ToastUtil.showStringToast("无法获取存储权限，请更改手机权限设置");
                    }
                }
            });
        }
    }

    protected View getTabIndicator(@StringRes int txtId, int resId) {
        View view = getLayoutInflater().inflate(R.layout.godspeed_tab_indicator_view, null);
        ((TextView) view.findViewById(R.id.tab_indicator_tv)).setText(txtId);
        ((ImageView) view.findViewById(R.id.tab_indicator_iv)).setImageResource(resId);

        initTabIndicatorView(view, txtId, resId);
        return view;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TAB_POSITION, mTabPosition);
        super.onSaveInstanceState(outState);
    }

    protected abstract void initTabIndicatorView(View view,@StringRes int txtId, int resId);// 初始化头部

    protected abstract  void changeNavigation(int page);


    //View chatTabView = getTabIndicator(R.string.xxxx, R.drawable.xxxx);
//    tabInfos.add(new LibraryTabbar.TabInfo(chatTabView, xxxxxxxxx));
    protected abstract void buildTabInfos(List<GodspeedTabbar.TabInfo> tabInfos);

}
