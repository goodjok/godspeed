package com.godspeed.source.util.ui;

import android.widget.Toast;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.util.collection.CheckUtil;
import com.godspeed.source.util.string.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-4
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class ToastUtil {

    private static boolean ISSHOW = false;

    public static void setDebugMode(boolean isShow) {
        ISSHOW = isShow;
    }

    public static void debug(final String msg) {
        if (ISSHOW)
            show("调试：" + msg, Toast.LENGTH_SHORT);
    }

    /*
     * showtime：吐司时长 Stringid：定义 在String里的id object:Stringid里的参数
	 */
    public static void showToast(int showtime, int Stringid,
                                 Object... object) {
        show(StringUtils.getStringByKey(GodspeedContext.context, Stringid, object),
                showtime);
    }

    public static void showToast(int Stringid, Object... object) {
        show(StringUtils.getStringByKey(
                GodspeedContext.context, Stringid, object), Toast.LENGTH_SHORT);
    }

    public static void showStringToast(int showtime,
                                       String showstring) {
        show(showstring, showtime);
    }

    public static void showStringToast(String showstring) {
        show(showstring, Toast.LENGTH_SHORT);
    }

    private static void show(String str, int showTime) {
        if (CheckUtil.isEmpty(str)) {
            return;
        }
        Toast.makeText(GodspeedContext.context, str, showTime).show();
    }
}
