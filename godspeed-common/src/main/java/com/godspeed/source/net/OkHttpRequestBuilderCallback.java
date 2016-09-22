package com.godspeed.source.net;

import okhttp3.Request;

public interface OkHttpRequestBuilderCallback {
    void builder(Request.Builder builder);
}
