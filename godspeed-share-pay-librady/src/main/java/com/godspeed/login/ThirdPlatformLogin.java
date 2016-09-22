package com.godspeed.login;

import android.app.Activity;
import android.content.Intent;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ThirdPlatformLogin {
    UMShareAPI mShareAPI;

    public void auth(SHARE_MEDIA shareMedia,Activity activity,UMAuthListener listener){
        mShareAPI = UMShareAPI.get(activity);
        mShareAPI.doOauthVerify(activity, shareMedia, listener);
    }

    public void unauth(SHARE_MEDIA shareMedia,Activity activity,UMAuthListener listener){
        mShareAPI = UMShareAPI.get(activity);
        mShareAPI.deleteOauth(activity, shareMedia, listener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
