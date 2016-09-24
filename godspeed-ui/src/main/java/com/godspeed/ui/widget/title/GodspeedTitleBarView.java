package com.godspeed.ui.widget.title;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.ui.widget.DefaultValue;

import static butterknife.ButterKnife.findById;

public abstract class GodspeedTitleBarView extends RelativeLayout {


//    protected View rootView;

    public GodspeedTitleBarView(Context context) {
        super(context);
    }

    public GodspeedTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GodspeedTitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(23)
    public GodspeedTitleBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public enum POSITION {
        LEFT,
        LEFT_TEXT,
        LEFT_IMAGE,
        MIDDLE,
        MIDDLE_TEXT,
        MIDDLE_IMAGE,
        RIGHT,
        RIGHT_TEXT,
        RIGHT_IMAGE,
        ALL
    }

//    public GodspeedTitleBar(View rootView) {
//        this.rootView = rootView;
//    }

    public void show(POSITION position, @StringRes int res, String str, @DrawableRes int drawableRes, @ColorRes int color) {
        View view = getShowView(position);
        if(view!=null) {
            view.setVisibility(View.VISIBLE);
            if (view instanceof TextView) {
                setTitleText((TextView) view, res, str, drawableRes, color);
            } else {
                setTitleView(view, drawableRes, color);
            }
        }

    }

    public void hidden(POSITION position) {
        View view = getClickView(position);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void show(POSITION position) {
        View view = getClickView(position);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void enable(POSITION position) {
        View view = getClickView(position);
        if (view != null) {
            view.setEnabled(false);
        }
    }


    public void clickable(POSITION position,boolean clickable) {
        View view = getClickView(position);
        if (view != null) {
            view.setClickable(clickable);
        }
    }

    public void disable(POSITION position) {
        View view = getClickView(position);
        if (view != null) {
            view.setEnabled(false);
        }

    }

    protected View getClickView(POSITION position) {
        View view = null;

        switch (position) {
            case LEFT: {
                view = findById(this, getLeftClickRes());
                break;
            }
            case LEFT_TEXT: {
                view = findById(this, getLeftShowRes());
                break;
            }
            case LEFT_IMAGE: {
                view = findById(this, getLeftShowRes());
                break;
            }
            case MIDDLE: {
                view = findById(this, getMiddleClickRes());
                break;
            }
            case MIDDLE_TEXT: {
                view = findById(this, getMiddleShowRes());
                break;
            }
            case MIDDLE_IMAGE: {
                view = findById(this, getMiddleShowRes());
                break;
            }
            case RIGHT: {
                view = findById(this, getRightClickRes());

                break;
            }
            case RIGHT_TEXT: {
                view = findById(this, getRightShowRes());

                break;
            }
            case RIGHT_IMAGE: {
                view = findById(this, getRightShowRes());

                break;
            }
            case ALL: {
                view = this;

                break;
            }
        }
        return view;
    }

    public void onClick(POSITION position, OnClickListener clickListener) {
        View view = getClickView(position);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(clickListener);
        }
    }


    protected View getShowView(POSITION position) {
        View view = null;
        switch (position) {
            case LEFT: {
                view = findById(this, getLeftShowRes());
                break;
            }
            case LEFT_TEXT: {
                view = findById(this, getLeftTextShowRes());
                break;
            }
            case LEFT_IMAGE: {
                view = findById(this, getLeftImageShowRes());
                break;
            }
            case MIDDLE: {
                view = findById(this, getMiddleShowRes());
                break;
            }
            case MIDDLE_TEXT: {
                view = findById(this, getMiddleTextShowRes());
                break;
            }
            case MIDDLE_IMAGE: {
                view = findById(this, getMiddleImageShowRes());
                break;
            }
            case RIGHT: {
                view = findById(this, getRightShowRes());

                break;
            }
            case RIGHT_TEXT: {
                view = findById(this, getRightTextShowRes());

                break;
            }
            case RIGHT_IMAGE: {
                view = findById(this, getRightImageShowRes());

                break;
            }
            case ALL: {
                view = this;

                break;
            }
        }
        return view;
    }

    public abstract
    @IdRes
    int getLeftShowRes();
    public abstract
    @IdRes
    int getLeftTextShowRes();
    public abstract
    @IdRes
    int getLeftImageShowRes();

    public abstract
    @IdRes
    int getMiddleShowRes();

    public abstract
    @IdRes
    int getMiddleTextShowRes();

    public abstract
    @IdRes
    int getMiddleImageShowRes();

    public abstract
    @IdRes
    int getRightShowRes();

    public abstract
    @IdRes
    int getRightTextShowRes();

    public abstract
    @IdRes
    int getRightImageShowRes();

    public abstract
    @IdRes
    int getLeftClickRes();

    public abstract
    @IdRes
    int getMiddleClickRes();

    public abstract
    @IdRes
    int getRightClickRes();


    public void showText(POSITION position, String res) {
        show(position, DefaultValue.NO_STRING_ID, res, DefaultValue.NO_DRAWABLE_ID, DefaultValue.NO_COLOR_ID);
    }

    public void showText(POSITION position, @StringRes int res) {
        show(position, res, DefaultValue.NULL_STRING_VALUE, DefaultValue.NO_DRAWABLE_ID, DefaultValue.NO_COLOR_ID);
    }

    public void showText(POSITION position, @StringRes int res, @ColorRes int color) {
        show(position, res, DefaultValue.NULL_STRING_VALUE, DefaultValue.NO_DRAWABLE_ID, color);
    }

    public void showImage(POSITION position, @DrawableRes int drawableRes) {
        show(position, DefaultValue.NO_STRING_ID, null, drawableRes, DefaultValue.NO_COLOR_ID);
    }


    protected void setTitleText(TextView view, @StringRes int res) {
        setTitleText(view, res, DefaultValue.NULL_STRING_VALUE, DefaultValue.NO_DRAWABLE_ID, DefaultValue.NO_COLOR_ID);
    }

    protected void setTitleText(TextView view, @StringRes int res, @DrawableRes int drawableRes) {
        setTitleText(view, res, DefaultValue.NULL_STRING_VALUE, drawableRes, DefaultValue.NO_COLOR_ID);
    }

    protected void setTitleText(TextView view, @StringRes int res, @DrawableRes int drawableRes, @ColorRes int color) {
        setTitleText(view, res, DefaultValue.NULL_STRING_VALUE, drawableRes, color);
    }

    protected void setTitleText(TextView view, String str) {
        setTitleText(view, DefaultValue.NO_STRING_ID, str, DefaultValue.NO_DRAWABLE_ID, DefaultValue.NO_COLOR_ID);
    }

    protected void setTitleText(TextView view, String str, @DrawableRes int drawableRes) {
        setTitleText(view, DefaultValue.NO_STRING_ID, str, drawableRes, DefaultValue.NO_COLOR_ID);
    }

    protected void setTitleText(TextView view, String str, @DrawableRes int drawableRes, @ColorRes int color) {
        setTitleText(view, DefaultValue.NO_STRING_ID, str, drawableRes, color);
    }

    protected void setTitleText(TextView view, @StringRes int res, String str, @DrawableRes int drawableRes, @ColorRes int color) {
        view.setVisibility(View.VISIBLE);
        if (res > DefaultValue.NO_STRING_ID) {
            view.setText(res);
        }
        if (str != null) {
            view.setText(str);
        }
        if (color > DefaultValue.NO_COLOR_ID) {
            if (Build.VERSION.SDK_INT >= 23) {
                view.setTextColor(GodspeedContext.resources.getColor(color, (Resources.Theme) null));
            } else {
                view.setTextColor(GodspeedContext.resources.getColor(color));
            }
        }

        if (drawableRes > DefaultValue.NO_DRAWABLE_ID) {
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= 23) {
                drawable = GodspeedContext.resources.getDrawable(drawableRes, (Resources.Theme) null);
            } else {
                drawable = GodspeedContext.resources.getDrawable(drawableRes);
            }

            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                view.setCompoundDrawables(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
    }

    protected void setTitleView(View view, @DrawableRes int drawableRes, @ColorRes int color) {
        view.setVisibility(View.VISIBLE);

        if (color > DefaultValue.NO_COLOR_ID) {
            if (Build.VERSION.SDK_INT >= 23) {
                view.setBackgroundColor(GodspeedContext.resources.getColor(color, (Resources.Theme) null));
            } else {
                view.setBackgroundColor(GodspeedContext.resources.getColor(color));
            }
        }

        if (drawableRes > DefaultValue.NO_DRAWABLE_ID) {
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= 23) {
                drawable = GodspeedContext.resources.getDrawable(drawableRes, (Resources.Theme) null);
            } else {
                drawable = GodspeedContext.resources.getDrawable(drawableRes);
            }

            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                if(view instanceof ImageView){
                    ((ImageView)view).setImageDrawable(drawable);
                }else{
                    view.setBackground(drawable);
                }
            }
        }
    }


}
