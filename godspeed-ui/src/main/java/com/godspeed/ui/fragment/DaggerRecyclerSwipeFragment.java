package com.godspeed.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.godspeed.source.callback.RecyclerListCallback;
import com.godspeed.source.util.collection.CheckUtil;
import com.godspeed.ui.R;
import com.godspeed.ui.adpter.AdapterDataSource;
import com.godspeed.ui.adpter.DaggerRecyclerSwipeAdapter;
import com.godspeed.ui.fragment.layoutmanager.RecycleViewDivider;
import com.godspeed.ui.widget.title.GodspeedTitleBarView;

import java.util.ArrayList;
import java.util.List;

public abstract class DaggerRecyclerSwipeFragment<T extends AdapterDataSource> extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener {


    protected  RecyclerView baseRecyclerView;

    protected SwipeRefreshLayout refreshWidget;

    protected TextView emptyViewText;


    protected DaggerRecyclerSwipeAdapter mAdapter;

    protected int page = 1;

    protected boolean isLoading;
    protected boolean isLast;

    public int getContentViewId() {
        return R.layout.godspeed_recyclerview;
    }

    @Override
    public   void initWidget(View view) {

        baseRecyclerView = (RecyclerView) view.findViewById(R.id.godspeed_recyclerview);
        refreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.godspeed_swipe_refresh_widget);
        emptyViewText = (TextView) view.findViewById(R.id.godspeed_empty_view_text);

        if (emptyViewText != null && !CheckUtil.isEmpty(emptyMsg())) {
            if (emptyMsg() instanceof String) {
                emptyViewText.setText((String) emptyMsg());
            } else if (emptyMsg() instanceof Integer) {
                emptyViewText.setText((Integer) emptyMsg());
            }
        }

        // 创建一个线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(getOrientation());
        // 设置布局管理器
        baseRecyclerView.setLayoutManager(layoutManager);
        baseRecyclerView.setHasFixedSize(true);


        mAdapter = getAdapter();

        if (isHaveLastPage()) {
            refreshWidget.setEnabled(true);
            refreshWidget.setColorSchemeResources(R.color.white_color);
            refreshWidget.setOnRefreshListener(this);
        } else {
            refreshWidget.setEnabled(false);
        }

        if (isHaveNextPage() && !CheckUtil.isEmpty(mAdapter)) {
            mAdapter.setFooterVisibility(false);
            mAdapter.addFooter(View.inflate(getActivity().getBaseContext(), R.layout.godspeed_pull_footer, null));

            baseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (!isLoading && !isLast && mAdapter.getItemCount() <= (layoutManager.findLastVisibleItemPosition() + 4) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        mAdapter.setFooterVisibility(true);
                        getData(page + 1, GETDATA_STATE_MORE);
                    }
                }
            });
        }
        if (!CheckUtil.isEmpty(mAdapter)) {
            baseRecyclerView.setAdapter(mAdapter);
        }

        if (showDivider()) {
            baseRecyclerView.addItemDecoration(getDivider() == null ? new RecycleViewDivider(getActivity(), getOrientation()) : getDivider());
        }

    }

    @Override
    public void onRefresh() {
        getData(1, GETDATA_STATE_PULL);
    }

    @Override
    public   void initData() {
        getData(page, GETDATA_STATE_INIT);
    }

    public RecyclerListCallback callBack = new RecyclerListCallback() {
        @Override
        public void requestCallBack(List list, int page) {
            setData(page, list);
        }
    };

    private void getData(int page, int state) {
        isLoading = true;
        getData(page, state, callBack);
    }

    protected void setData(int page, List list) {
        mAdapter.setFooterVisibility(false);
        isLoading = false;
        refreshWidget.setRefreshing(false);
        showEmptyView(false);
        this.page = page;
        if (page == 1) {
            isLast = false;
            if (!CheckUtil.isEmpty(emptyMsg()) && CheckUtil.isEmpty(list)) {
                isLast = true;
                showEmptyView(true);
            }
            if (CheckUtil.isEmpty(list) || list.size() < getPageCount()) {
                isLast = true;
            }
            mAdapter.resetData(list);
        } else {
            if (CheckUtil.isEmpty(list) || list.size() < getPageCount()) {
                isLast = true;
            }
            mAdapter.addData(list);
        }
    }

    protected abstract DaggerRecyclerSwipeAdapter getAdapter();

    protected abstract void getData(int page, int state, RecyclerListCallback callBack);

    public RecyclerView getBaseRecyclerView() {
        return baseRecyclerView;
    }


    protected final void setRefreshColorSchemeResources(int... colors) {
        refreshWidget.setColorSchemeResources(colors);
    }

    public void showEmptyView(boolean show) {
        if (emptyViewText != null) {
            emptyViewText.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    protected boolean showTitleLine() {
        return false;
    }

    protected Object emptyMsg() {
        return null;
    }

    public String showLoginErrorText() {
        return null;
    }

    public boolean isHaveLastPage() {
        return true;
    }

    public boolean isHaveNextPage() {
        return true;
    }

    public boolean showDivider() {
        return true;
    }

    public RecyclerView.ItemDecoration getDivider() {
        return null;
    }

    protected int getPageCount() {
        return 10;
    }

    protected boolean haveToolbar() {
        return false;
    }

    protected int getOrientation() {
        return LinearLayoutManager.VERTICAL;
    }

    public final DaggerRecyclerSwipeAdapter getParentAdapter() {
        return mAdapter;
    }

    public final void autoRefresh() {
        if (refreshWidget != null) {
            refreshWidget.setRefreshing(true);
        }
    }

    public final List<T> getDataSource() {
        if (mAdapter == null) {
            return new ArrayList<>();
        }
        return mAdapter.getData();
    }

    public final int getCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getItemCount();
    }

    public final T getItem(int position) {
        if (mAdapter == null) {
            return null;
        }
        return (T) mAdapter.getItem(position);
    }

    public void resetData(List<T> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.resetData(tList);
    }

    public void addData(List<T> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addData(tList);
    }

    public void insertData(List<T> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.insertData(tList);
    }

    public void removeData(List<T> tList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.removeData(tList);
    }

    public void removeData(T t) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.removeData(t);
    }

    public void addHeader(@NonNull View view) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addHeader(view);
    }

    public void addFooter(@NonNull View view) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addFooter(view);
    }

    public void setHeaderVisibility(boolean shouldShow) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setHeaderVisibility(shouldShow);
    }

    public void setFooterVisibility(boolean shouldShow) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setFooterVisibility(shouldShow);
    }

    public int getHeaderCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getHeaderCount();
    }

    public int getFooterCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getFooterCount();
    }

    public View getHeader(int i) {
        if (mAdapter == null) {
            return null;
        }
        return mAdapter.getHeader(i);
    }

    public View getFooter(int i) {
        if (mAdapter == null) {
            return null;
        }
        return mAdapter.getFooter(i);
    }

    public void scrollToPosition(int position) {
        baseRecyclerView.scrollToPosition(position);
    }
}
