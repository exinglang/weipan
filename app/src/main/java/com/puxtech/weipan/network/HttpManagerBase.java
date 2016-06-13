package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.helper.PriceLinkHelper;

import java.nio.ByteBuffer;

/**
 * Created by mac on 15/11/6.
 */
public class HttpManagerBase {
    protected Context context;
    MyApplication myapp;


    public HttpManagerBase(Context context){
        this.context = context;
        myapp = (MyApplication) context.getApplicationContext();
        //currentTradeEntity=myapp.getCurrentTradeEntity();


    }
    public HttpManagerBase(){

    }
    void outPutRequestLog(String requestData) {
        Logger.d("requestStr = " + requestData);
    }
    protected void createError(BaseResponseData responseData,Exception e) {
        e.printStackTrace();
        responseData.setRetCode(Constant.CODE_ERROR_CERATE);
        responseData.setRetMessage(Constant.MESSAGE_ERROR_CERATE);
    }
    protected void networkError(BaseResponseData responseData,Exception e) {
        e.printStackTrace();
        responseData.setRetCode(Constant.CODE_ERROR_INTERNET);
        responseData.setRetMessage(Constant.MESSAGE_ERROR_INTERNET);
    }
    protected void unknowError(BaseResponseData responseData,Exception e) {
        e.printStackTrace();
        responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
        responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
    }
}
