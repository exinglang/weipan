package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;

import java.nio.ByteBuffer;

/**
 * Created by mac on 15/11/4.
 */
public class ProtocolResponseData extends BaseResponseData {

    ProtocolInfoData protocolData;

    public ProtocolInfoData getProtocolData() {
        return protocolData;
    }



    /**
     * 转换服务器协议（将服务器返回的byte转换成实体对象）
     *
     * @param data
     * @return
     * @throws Exception 解析出错时抛出异常，原因可能是代码问题或者服务器返回数据改变。
     */
    public void convertProtocol(MyApplication myapp, byte[] data) {
        protocolData = new ProtocolInfoData();
        try {

            ByteBuffer buffer = parseRepInfo(data);
            if (retCode != 0) {
                return;
            }
            int protocolLength = buffer.get();
            byte[] protocolByte = new byte[protocolLength];
            buffer.get(protocolByte);
            String protocol = new String(protocolByte);
            protocolData.setProtocol(protocol);


        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    private ByteBuffer parseRepInfo(byte[] data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.get();
        int versionLength = buffer.get();
        byte[] versionByte = new byte[versionLength];
        buffer.get(versionByte);
        String version = new String(versionByte);
        retCode = buffer.getShort();
        int messageLength = buffer.getShort();
        byte[] messageByte = new byte[messageLength];
        buffer.get(messageByte);
        retMessage = new String(messageByte);
        protocolData.setVersion(version);

        this.retCode = retCode;
        this.retMessage = retMessage;
        this.protocolName = protocolName;
        return buffer;
    }
}
