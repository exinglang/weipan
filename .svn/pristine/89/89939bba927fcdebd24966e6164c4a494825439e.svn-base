package com.puxtech.weipan.util;

import com.puxtech.weipan.Constant;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mac on 15/11/6.
 */
public class TradeUtils {

    // 获取token字段。 二进制
    public static String getToken(long time, String username, String pwd, byte[] logonKey) {

        // 登录加解密密钥，并不上传密码
        String result = "";
        try {
            byte[] data = username.getBytes(Constant.ENCODE);
            ByteBuffer buffer = ByteBuffer.allocate(data.length + 10);
            // 小段序，服务器在写入写出字节流时，全部为小段序。客户端需要与服务器统一
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            // 明文
//            if (Double
//                    .valueOf(myapp.getNyTrade().getSessionData().getVersion()) > 1.5) {
//                buffer.putShort((short) 0);
//                buffer.putLong(time);
//
//                buffer.putShort((short) data.length);
//                buffer.put(data);
//
//            }
            buffer.putShort((short) data.length);
            buffer.put(data);
            buffer.putLong(time);
            // 密文，并用base64编码
            result = Base64.encode(AES.encrypt(buffer.array(), logonKey));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
