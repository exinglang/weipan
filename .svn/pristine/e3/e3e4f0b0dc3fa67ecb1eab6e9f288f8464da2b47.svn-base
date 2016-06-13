package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;

import java.nio.ByteBuffer;

/**
 * Created by mac on 15/11/4.
 */
public class PriceLogonResponseData extends BaseResponseData {
    byte[] sessionIdByte;
  //  ProtocolInfoData protocolData;
//    public void parseXML( TradeEntity currentTradeEntity,String jsString,byte[] logonKey){
//
//      try {
//        JSONObject root=  checkFail(jsString);
//          if(retCode!=0){
//                  return;
//          }
//
//
//      } catch (Exception e) {
//          e.printStackTrace();
//          parseError();
//
//      }
//
//    }

    /**
     * 转换服务器协议（将服务器返回的byte转换成实体对象）
     *
     * @param data
     * @return
     * @throws Exception 解析出错时抛出异常，原因可能是代码问题或者服务器返回数据改变。
     */
    public void convertPriceLogon(byte[] data) {


        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.get();
            int versionLength = buffer.get();
            byte[] versionByte = new byte[versionLength];
            buffer.get(versionByte);
            retCode = buffer.getShort();// returnCode

            int messageLength = buffer.getShort();
            byte[] messageByte = new byte[messageLength];
            buffer.get(messageByte);
            retMessage = new String(messageByte);
            if(retCode!=0){
                return;
            }
            sessionIdByte = new byte[16];
            buffer.get(sessionIdByte);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }

    }

    public byte[] getSessionIdByte() {
        return sessionIdByte;
    }

    public void setSessionIdByte(byte[] sessionIdByte) {
        this.sessionIdByte = sessionIdByte;
    }
//    private ByteBuffer parseRepInfo(byte[] data) throws Exception {
//        ByteBuffer buffer = ByteBuffer.wrap(data);
//        buffer.get();
//        int versionLength = buffer.get();
//        byte[] versionByte = new byte[versionLength];
//        buffer.get(versionByte);
//        String version = new String(versionByte);
//        retCode = buffer.getShort();
//        int messageLength = buffer.getShort();
//        byte[] messageByte = new byte[messageLength];
//        buffer.get(messageByte);
//        retMessage = new String(messageByte);
//        protocolData.setVersion(version);
//
//        this.retCode = retCode;
//        this.retMessage = retMessage;
//        this.protocolName = protocolName;
//        return buffer;
//    }
}
