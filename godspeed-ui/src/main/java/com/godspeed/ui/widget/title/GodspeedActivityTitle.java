package com.godspeed.ui.widget.title;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.godspeed.source.context.GodspeedContext;

import static butterknife.ButterKnife.findById;

public class GodspeedActivityTitle {


    public static void hideTitleBar(Activity activity,int title_id) {
        RelativeLayout common_title_layout = findById(activity,title_id);
        common_title_layout.setVisibility(View.GONE);
    }

    public static void showTitleBar(Activity activity,int title_id) {
        RelativeLayout common_title_layout =findById(activity,title_id);
        common_title_layout.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("deprecation")
    public static void setTitleText(Activity activity,int text_id,@StringRes int res, @DrawableRes int drawableRes, @ColorRes int color) {
        TextView title = findById(activity, text_id);
        title.setVisibility(View.VISIBLE);
        title.setText(res);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(GodspeedContext.resources.getColor(color, null));
        }else{
            title.setTextColor(GodspeedContext.resources.getColor(color));
        }
        if (drawableRes <= 0) {
            return;
        }
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable =GodspeedContext.resources.getDrawable(drawableRes,null);
        }else{
            drawable =GodspeedContext.resources.getDrawable(drawableRes);
        }
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        title.setCompoundDrawables(drawable, null, null, null);
    }

    @SuppressWarnings("deprecation")
    public static void setTitleText(Activity activity,int text_id,String res, @DrawableRes int drawableRes, @ColorRes int color) {
        TextView title = findById(activity, text_id);
        title.setVisibility(View.VISIBLE);
        title.setText(res);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(GodspeedContext.resources.getColor(color, null));
        }else{
            title.setTextColor(GodspeedContext.resources.getColor(color));
        }
        if (drawableRes <= 0) {
            return;
        }
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable =GodspeedContext.resources.getDrawable(drawableRes,null);
        }else{
            drawable =GodspeedContext.resources.getDrawable(drawableRes);
        }
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        title.setCompoundDrawables(drawable, null, null, null);
    }



    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的 location 位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
