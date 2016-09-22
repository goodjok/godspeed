package com.godspeed.source.net;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpServiceSetting {

    public HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BASIC;
    public String baseURL;

    public int timeout = 30;

    public OkHttpRequestBuilderCallback builderCallback;

    public HttpServiceSetting(String baseURL) {
        this.baseURL = baseURL;
    }
}
