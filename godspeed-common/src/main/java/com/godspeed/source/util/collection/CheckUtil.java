package com.godspeed.source.util.collection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CheckUtil {
    public CheckUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Object object) {
        return object == null;
    }

    public static boolean isEmpty(Object[] object) {
        return object == null || object.length == 0;
    }

    public static boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean isValid(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValid(Map map) {
        return map != null && map.size() > 0;
    }

    public static boolean isValid(Object object) {
        return object != null;
    }

    public static boolean isValid(Object[] object) {
        return object != null && object.length > 0;
    }


    public static boolean isURL(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            new URL(str);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return true;
        }
        return false;
    }
}
