package com.godspeed.pay.wxapi.event;

import com.tencent.mm.sdk.modelbase.BaseResp;

public class WXRespEvent {

    private BaseResp resp;

    public BaseResp getResp() {
        return resp;
    }

    public WXRespEvent(BaseResp resp) {
        this.resp = resp;
    }
}
