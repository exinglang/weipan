package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.network.Logger;

import org.json.JSONObject;

import java.nio.ByteBuffer;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseDataPrice extends BaseResponseData {
    protected String protocolVersion = "";//协议版本
    protected int protocolNumber;

    /**
     * 从返回数据中获取公共返回数据
     *
     * @param buffer 原始的完整返回的byte[]
     * @return
     */
    public void getCommonReturnData(ByteBuffer buffer) {
        //CommonReturnEntity entity = new CommonReturnEntity();
        int protocolNumber = buffer.get();
        int versionLength = buffer.get();
        byte[] versionByte = new byte[versionLength];
        buffer.get(versionByte);
        String version = new String(versionByte);
        int returnCode = buffer.getShort();
        int messageLength = buffer.getShort();
        byte[] messageByte = new byte[messageLength];
        buffer.get(messageByte);

        String message = new String(messageByte);
        setProtocolNumber(protocolNumber);
        setProtocolVersion(version);
        setRetCode(returnCode);
        setRetMessage(message);
    }

    private final int underflow_v=32767;

    protected int getChaZhi(ByteBuffer buffer) {
        int container;
        container = buffer.getShort();
        if(container==underflow_v){
            container=buffer.getInt();
        }
        return container;
    }
    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }
}
