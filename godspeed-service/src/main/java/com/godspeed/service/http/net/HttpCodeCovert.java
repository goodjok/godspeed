package com.godspeed.service.http.net;


public class HttpCodeCovert {

    public static String covertCodeToMsg(int code){
        switch (code){
            case 500:{
                return "服务器异常";
            }
            default:{
                return "网络异常";
            }
        }
    }
}
