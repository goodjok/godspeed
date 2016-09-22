package com.godspeed.pay.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.godspeed.pay.R;
import com.godspeed.pay.ShareAndPayService;
import com.godspeed.pay.wxapi.event.WXReqEvent;
import com.godspeed.pay.wxapi.event.WXRespEvent;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
    private IWXAPI api;



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wexin_pay_result);


		api = WXAPIFactory.createWXAPI(this, ShareAndPayService.WX_APP_ID);

		api.handleIntent(getIntent(), this);

		finish();
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		finish();
	}

	@Override
	public void onReq(BaseReq baseReq) {
		EventBus.getDefault().post(new WXReqEvent(baseReq));
	}

	@Override
	public void onResp(BaseResp baseResp) {
		EventBus.getDefault().post(new WXRespEvent(baseResp));
	}
}