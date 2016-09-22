package com.godspeed.pay.wxapi.event;


import com.tencent.mm.sdk.modelbase.BaseReq;

public class WXReqEvent {
    private BaseReq req;

    public BaseReq getReq() {
        return req;
    }

    public WXReqEvent(BaseReq req){
        this.req = req;
    }
}
