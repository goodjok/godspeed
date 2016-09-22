package com.godspeed.source.util.system;

import android.os.CountDownTimer;
import android.widget.TextView;


public class CountdownTimerTask extends CountDownTimer {
    public TextView tv;
    private CountdownTimerTaskCallback callback;

    public CountdownTimerTask(long millisInFuture, long countDownInterval, TextView tv, CountdownTimerTaskCallback callback) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.callback = callback;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv.setEnabled(false);

        if (null != callback) {
            tv.setText(callback.tickString(millisUntilFinished));
        }
    }

    @Override
    public void onFinish() {
        if (null != callback) {
            tv.setText(callback.finishString());
        }
        tv.setEnabled(true);
    }

    public interface CountdownTimerTaskCallback {
        String tickString(long millisUntilFinished);

        String finishString();
    }
}
