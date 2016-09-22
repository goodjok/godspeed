package com.squareup.picasso;

/**
 * Created by liuyu on 14-8-21.
 */
public class PicassoTools {
    public static void clearCache(Picasso p) {
        p.cache.clear();
    }

    public static int getCacheSize(Picasso p) {
        return p.cache.size();
    }
}
