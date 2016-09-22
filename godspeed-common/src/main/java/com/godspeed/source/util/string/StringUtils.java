package com.godspeed.source.util.string;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: SlothMonkey
 * Date: 12-8-1
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {


    public static boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    /*
     * 通过 Key从Bundle中获取相对应的整型值
	 */
    public static int getIntResFormBundle(Bundle bundle, String parm) {
        int res = 0;
        try {
            res = new Integer(bundle.get(parm).toString());
        } catch (Exception e) {
            e.printStackTrace();
            res = 0;
        }
        return res;
    }


    /*
	 * 通过 Key从Bundle中获取相对应的String值
	 */
    public static boolean containsKeyFormBundle(Bundle bundle, String parm) {
        return bundle.containsKey(parm);
    }


    /*
	 * 通过 Key从Bundle中获取相对应的String值
	 */
    public static String getStringResFormBundle(Bundle bundle, String parm) {
        String res = "";
        try {
            String str = bundle.get(parm).toString();
            if (isEmpty(str)) {
                res = "";
            } else {
                res = str;
            }
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

    /**
     * 字符串是否为空：包括null和""
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    /*
         * 根据ID获取相应String内容
         */
    public static String getStringByKey(Context context, int keyid,
                                        Object... object) {
        String res = "";
        try {
            res = context.getResources().getString(keyid, object);
        } catch (Exception e) {
            e.printStackTrace();
            res = "";
        }
        return res;
    }

    /*
     * 根据ID获取相应String内容然后按Html显示 ,
     */
    public static String getHtmlStringByKey(Context context, int keyid,
                                            Object... object) {
        String res = "";
        try {
            res = getStringByKey(context, keyid, object);
            res = Html.fromHtml(res).toString();
        } catch (Exception e) {
            e.printStackTrace();
            res = "";
        }
        return res;
    }

    public static String getEditTextText(EditText et) {
        String res = "";
        if (et != null) {
            res = et.getText().toString().trim();
        }
        return res;
    }

    public static String getTextViewText(TextView et) {
        String res = "";
        if (et != null) {
            res = et.getText().toString().trim();
        }
        return res;
    }


}