package com.godspeed.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.net.DaggerHttpService;
import com.godspeed.source.util.collection.CheckUtil;
import com.godspeed.ui.R;
import com.godspeed.ui.fragment.DaggerRecyclerSwipeFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class DaggerRecyclerSwipeAdapter<T extends AdapterDataSource, VH extends DaggerRecylerViewAdapter.BaseRecyclerViewHolder> extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    public static final int HEADER_VIEW_TYPE = -1000;
    public static final int FOOTER_VIEW_TYPE = -2000;
    protected static Context mContext;
    protected static LayoutInflater mLayoutInflater;
    protected List<T> mData = new ArrayList<T>();
    protected final List<View> mHeaders = new ArrayList<>();
    protected final List<View> mFooters = new ArrayList<>();
    protected AdapterView.OnItemClickListener mListener;


    protected DaggerHttpService httpService;

    public DaggerRecyclerSwipeAdapter(DaggerRecyclerSwipeFragment context) {
        mContext = context.getActivity();
        mLayoutInflater = LayoutInflater.from(mContext);
        httpService = context.httpService;

    }

    public DaggerRecyclerSwipeAdapter(DaggerRecyclerSwipeFragment context, List<T> list) {
        this(context);
        resetData(list);
    }

    public void resetData(List<T> list) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> tList) {
        if (!CheckUtil.isEmpty(tList)) {
            mData.addAll(tList);
        }
        notifyDataSetChanged();
    }

    public void insertData(List<T> tList) {
        if (!CheckUtil.isEmpty(tList)) {
            mData.addAll(0, tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(List<T> tList) {
        if (!CheckUtil.isEmpty(tList)) {
            mData.removeAll(tList);
        }
        notifyDataSetChanged();
    }

    public void removeData(T t) {
        if (!CheckUtil.isEmpty(t)) {
            mData.remove(t);
        }
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        if (CheckUtil.isEmpty(mData)) {
            return;
        }
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mData.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position-getHeaderCount());
//        notifyItemRangeChanged(0, mData.size());
    }

    public void changeData(T data, int index) {
        mData.remove(index);
        mData.add(index, data);
//        notifyDataSetChanged();
    }

    /**
     * Adds a header view.
     */
    public void addHeader(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null header!");
        }
        mHeaders.add(view);
        notifyDataSetChanged();
    }

    public void removeHeader() {
        mHeaders.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a footer view.
     */
    public void addFooter(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't have a null footer!");
        }
        mFooters.add(view);
        notifyDataSetChanged();
    }

    /**
     * Toggles the visibility of the header views.
     */
    public void setHeaderVisibility(boolean shouldShow) {
        for (View header : mHeaders) {
            header.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
        notifyDataSetChanged();
    }

    /**
     * Toggles the visibility of the footer views.
     */
    public void setFooterVisibility(boolean shouldShow) {
        for (View footer : mFooters) {
            footer.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
            try {
                RelativeLayout footerView = (RelativeLayout) footer.findViewById(R.id.pull_footer);
                footerView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * @return the number of headers.
     */
    public final int getHeaderCount() {
        return mHeaders.size();
    }

    /**
     * @return the number of footers.
     */
    public final int getFooterCount() {
        return mFooters.size();
    }

    /**
     * Gets the indicated header, or null if it doesn't exist.
     */
    public View getHeader(int i) {
        return i < getHeaderCount() ? mHeaders.get(i) : null;
    }

    /**
     * Gets the indicated footer, or null if it doesn't exist.
     */
    public View getFooter(int i) {
        return i < getFooterCount() ? mFooters.get(i) : null;
    }

    private boolean isHeader(int viewType) {
        return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + getHeaderCount());
    }

    protected boolean isFooter(int viewType) {
        return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + getFooterCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return HEADER_VIEW_TYPE + position;
        } else if (position < (getItemCount() - getFooterCount())) {
            return super.getItemViewType(position - getHeaderCount());
        } else {
            return FOOTER_VIEW_TYPE + position - (getItemCount() - getFooterCount());
        }
    }

    public void setItemListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);
            View headerView = getHeader(whichHeader);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return new RecyclerView.ViewHolder(headerView) {
            };
        } else if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
            View footerView = getFooter(whichFooter);
            footerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return new RecyclerView.ViewHolder(footerView) {
            };

        } else {
            return onCreateViewHolder(mLayoutInflater.inflate(getLayout(), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position < getHeaderCount()) {
            // Headers don't need anything special

        } else if (position < getItemCount() - getFooterCount()) {
            // This is a real position, not a header or footer. Bind it.
            bindView((VH) holder, getItem(position - getHeaderCount()), position - getHeaderCount());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) {
                        return;
                    }
                    mListener.onItemClick(null, holder.itemView, holder.getLayoutPosition() - getHeaderCount(), holder.getLayoutPosition() - getHeaderCount());
                }

            });
            mItemManger.bindView(holder.itemView, position);
        } else {
            // Footers don't need anything special
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount();
    }

    public T getItem(int position) {
        if (CheckUtil.isEmpty(mData)) {
            return null;
        }
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    public int getDataCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    public abstract void bindView(VH holder, T data, int position);

    @LayoutRes
    public abstract int getLayout();

    public abstract VH onCreateViewHolder(View view);

    protected String getString(@StringRes int res) {
        return GodspeedContext.resources.getString(res);
    }

    protected void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
