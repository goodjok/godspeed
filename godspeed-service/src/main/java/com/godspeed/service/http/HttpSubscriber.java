package com.godspeed.service.http;


import com.godspeed.source.net.HttpCallback;
import com.godspeed.source.net.HttpException;

import retrofit2.Response;
import rx.Subscriber;

public abstract class HttpSubscriber<T> extends Subscriber<Response<HttpResult<T>>> {
    HttpCallback callback;

    public HttpSubscriber(HttpCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (callback != null) {
            callback.onError(new HttpException(-100,e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onNext(Response<HttpResult<T>> httpResultResponse) {
        if(httpResultResponse.code()==  200){

            HttpResult<T> result = httpResultResponse.body();

            if(result.getCode()==getSuccessCode()){
                if (callback != null) {

                    callback.onSuccess(result,httpResultResponse.headers().toMultimap());
                }
            } else {

                validateCode(result.getCode());

                if (callback != null) {
                    callback.onError(new HttpException(result.getCode(),result.getMsg()));
                }
            }
        } else {

            if (callback != null) {
                callback.onError(new HttpException(httpResultResponse.code(),HttpCodeCovert.covertCodeToMsg(httpResultResponse.code())));
            }
        }
    }

    protected abstract int getSuccessCode();

    protected abstract void validateCode(int code);

}
