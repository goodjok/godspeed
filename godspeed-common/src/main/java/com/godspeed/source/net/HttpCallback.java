package com.godspeed.source.net;

import java.util.List;
import java.util.Map;

public interface HttpCallback<T> {

    void onError(HttpException e);

    void onSuccess(T t,Map<String, List<String>> headers);
}
