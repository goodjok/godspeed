package com.godspeed.pay;



public class ThirdPayEntity {
    private String appid = "";
    private String partnerid = "";
    private String prepayid = "";
    private String noncestr = "";
    private String timestamp = "";

    String sign = "";


    private String resultUrl = "";

    private String orderId = "";

    private int type;

    public static boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    public boolean validate(){
        if (type== PayContants.ORDER_CREAT_AliPay){
            return isValid(orderId);
        }else{
            return isValid(appid)&&isValid(partnerid)&&isValid(prepayid)&&isValid(noncestr)&&isValid(timestamp)&&isValid(sign);
        }
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
