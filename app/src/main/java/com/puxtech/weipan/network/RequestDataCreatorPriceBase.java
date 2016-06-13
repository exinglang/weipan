package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.helper.PriceLinkHelper;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15/11/4.
 */
public class RequestDataCreatorPriceBase extends RequestDataCreatorBase{


    public RequestDataCreatorPriceBase(Context context) {
        super(context);

    }
    public RequestDataCreatorPriceBase() {
        super();

    }
    /**
     * 取得公用头部，不包括协议编号
     * @param
     * @param protocolNumber 协议编号
     * @return
     */
    public byte[] getHead( int protocolNumber){
        byte[] sessionId = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getSessionId();
        byte[] version = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getProtocolInfo().getVersion().getBytes();
        byte sessionidCount = (byte)1;//sessionid个数
        byte sessionFlagLength = (byte)3;//session标识长度
        byte[] sessionFlag = new byte[]{(byte)1, (byte)5, (byte)9};//session标识内容

        byte tokenCount = (byte)3;//token个数
        byte tokenLength = (byte)1;//token长度
        byte[] token = new byte[]{(byte)9};//token内容

        ByteBuffer buffer = ByteBuffer.allocate(1 + 1 + version.length + 1 + sessionidCount*(1 + sessionFlagLength + 1 + sessionId.length) + 1 + tokenCount*(1+tokenLength));
        buffer.put((byte) protocolNumber);
        buffer.put((byte) version.length);
        buffer.put(version);
        buffer.put(sessionidCount);
        for (int i = 0; i < sessionidCount; i++) {
            buffer.put(sessionFlagLength);
            buffer.put(sessionFlag);
            buffer.put((byte) sessionId.length);
            buffer.put(sessionId);
        }
        buffer.put(tokenCount);
        for (int i = 0; i < tokenCount; i++) {
            buffer.put(tokenLength);
            buffer.put(token);
        }
        return buffer.array();
    }


    public byte[] requestBinaryAndChangeAvailableLink(ByteBuffer requestData) throws Exception {
        return requestBinaryAvailableLink(requestData,new PriceLinkHelper(context));

    }

    public byte[] requestBinaryAvailableLink(ByteBuffer requestData,PriceLinkHelper linkHelper) throws Exception {
        byte[] retData;
        HttpSender httpSender = new HttpSender();
        try {
            retData = httpSender.requestBinary(requestData.array(), linkHelper.getCurrentUrl());
        } catch (Exception e) {
            // 失败后切换链路递归请求
            Logger.e("priceLogonRequest()...请求失败，开始切换链路");
            String availableUrl = linkHelper.switchLink();
            if (availableUrl != null) {
                return requestBinaryAvailableLink(requestData,linkHelper);
            } else {
                throw new Exception();
            }
        }
        // linkHelper.clearFailedLink();//可能没用,先去掉
        return retData;
    }

}
