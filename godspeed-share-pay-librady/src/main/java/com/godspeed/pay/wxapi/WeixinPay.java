package com.godspeed.pay.wxapi;

import android.app.Activity;

import com.godspeed.pay.ShareAndPayService;
import com.godspeed.pay.ThirdPayEntity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeixinPay {

    ThirdPayEntity payEntity;
      Activity activty;


    IWXAPI msgApi =null;
    PayReq req;
    public WeixinPay(Activity activty, ThirdPayEntity payEntity) {
        this.activty = activty;
        this.payEntity=payEntity;

        msgApi= WXAPIFactory.createWXAPI(activty, null);
    }

    public void pay() {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    genPayReq();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private void genPayReq() {
        msgApi.registerApp(ShareAndPayService.WX_APP_ID);

         req=new PayReq();
        req.appId =ShareAndPayService.WX_APP_ID;
        req.partnerId = payEntity.getPartnerid();
        req.prepayId = payEntity.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = payEntity.getNoncestr();
        req.timeStamp = payEntity.getTimestamp();


//
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//
//        LogUtil.d("signParams", genAppSign(signParams) + "----");
        req.sign = payEntity.getSign();

        msgApi.sendReq(req);

    }
////
//    public static String genAppSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(KEY);
//
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", appSign);
//        return appSign;
//    }




}
