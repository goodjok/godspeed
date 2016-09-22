package com.godspeed.pay;

import android.content.Context;
import android.content.pm.PackageManager;

import com.umeng.socialize.PlatformConfig;

public class ShareAndPayService extends PlatformConfig{

    public static String WX_APP_ID ="";
    public static String UMENG_APPKEY ="";
    public static String UMENG_CHANNEL ="";

    public static void initService(Context context){

          WX_APP_ID = getMetaValue(context,"WX_APP_ID");
          UMENG_APPKEY = getMetaValue(context,"UMENG_APPKEY");
          UMENG_CHANNEL = getMetaValue(context,"UMENG_CHANNEL");

        if(WX_APP_ID==null)
            throw  new Error("meta-data  name ->APP_ID  value not exist");
        if(UMENG_APPKEY==null)
            throw  new Error("meta-data  name ->UMENG_APPKEY  value not exist");
        if(UMENG_CHANNEL==null)
            throw  new Error("meta-data  name ->UMENG_CHANNEL  value not exist");
    }

    private static String getMetaValue(Context context,String name) {
        String channel = "";
        try {
            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(name);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
