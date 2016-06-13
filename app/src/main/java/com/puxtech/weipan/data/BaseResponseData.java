package com.puxtech.weipan.data;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.network.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseData {
    public static String ENCODE = "UTF-8";

    protected int retCode;//返回码
    protected String retMessage;//返回信息
    protected String protocolName="";//协议名称

    /**
     * 设置解析错误
     */
    public void parseError() {
        setRetCode(Constant.CODE_ERROR_PRASE);
        setRetMessage(Constant.MESSAGE_ERROR_PRASE);
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }
    //输入日志过长时,日志无法完全显示,在此处进行分割.
    public JSONObject outPutJson(String jsString) throws Exception {

        StringBuffer sb = new StringBuffer(jsString);


        for (int i = 0; i <= sb.length(); i = i + 2000)

        {
            int end;
            if (i + 2000 > sb.length()) {
                end = sb.length();
            } else {
                end = i + 2000;
            }
            String s = sb.substring(i, end);
            Logger.v("responseStr:" + s);

        }
        //  String log = new String(sb.toString());
//        TradeLogRecorder tlr = new TradeLogRecorder();
//        tlr.saveLog(context,"返回:"+ log);
        return new JSONObject(sb.toString());
    }

}
