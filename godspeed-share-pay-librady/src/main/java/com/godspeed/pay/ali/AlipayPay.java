package com.godspeed.pay.ali;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.godspeed.pay.ThirdPayEntity;

public class AlipayPay {

    public static final int SDK_PAY_FLAG = 1000000;

    private Activity activty;

    private Handler mHandler;

    public AlipayPay(Activity activty, Handler mHandler) {
        this.activty = activty;
        this.mHandler = mHandler;
    }

    public void pay(final ThirdPayEntity payInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activty);
                String result = alipay.pay(payInfo.getResultUrl(),true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;//TODO: “9000”则代表支付成功,“8000”支付结果确认中,其它为失败
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
