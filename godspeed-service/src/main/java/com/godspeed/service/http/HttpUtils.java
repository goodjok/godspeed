package com.godspeed.service.http;


import com.godspeed.source.util.io.JsonConvertUtils;

import java.io.File;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtils {


    //添加线程管理并订阅
    @SuppressWarnings("unchecked")
    public static void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    //添加线程管理并订阅
    public static RequestBody buildRequestBody(Object data) {
        String jsonstring = JsonConvertUtils.getStringValue(data);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonstring);
    }

    public static RequestBody buildImageRequestBody(File file) {
        return RequestBody.create(okhttp3.MediaType.parse("image/png"), file);
    }
}
