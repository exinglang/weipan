package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.util.AES;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.Base64;

import org.json.JSONObject;
import org.w3c.dom.Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

/**
 * Created by mac on 15/11/4.
 */
public class LogonResponseData extends BaseResponseDataTrade {

    private static final String TAG_SRV_KEY = "SRV_KEY";
    private static final String TAG_CLT_KEY = "CLT_KEY";
    private static final String TAG_IS_ENCRYPT = "IS_ENCRYPT";
    private static final String TAG_CHG_PWD = "CHG_PWD";
    private static final String TAG_IP = "LAST_IP";
    private static final String TAG_TIME = "LAST_TIME";
    private static final String LAST_MODULE = "LAST_MODULE";


    Boolean forceChangePW;


    String ip, module, time;

    public void parseXML(TradeEntity currentTradeEntity, String jsString, byte[] logonKey) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }

            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS).getJSONObject(Constant.TAG_REP);

            byte[] srvKey = rep.getString(TAG_SRV_KEY).getBytes(ENCODE);
            currentTradeEntity.getOtherData()
                    .setToken(srvKey);
            // 设置是否加密
            currentTradeEntity.getOtherData()
                    .setAes(rep.getString(TAG_IS_ENCRYPT).equals("1"));
            ByteBuffer buffer = ByteBuffer.wrap(AES.decrypt(
                    Base64.decode(rep.getString(TAG_CLT_KEY)), logonKey));
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            byte[] key = new byte[buffer.getShort()];
            buffer.get(key);

            currentTradeEntity.getOtherData().setKey(key);// 会话密钥
            key = new byte[buffer.getShort()];
            buffer.get(key);
            currentTradeEntity.getOtherData()
                    .setSid(new String(key, ENCODE));// sessionID

            // 登录信息 由于可能出现pc m 端均第一次登陆的情况 使用getLoginString对是否第一次登陆进行了判断
            String modulef;
            try {
                ip = rep.getString(TAG_IP);
                time = ActivityUtils.millisTime(Long.parseLong(rep.getString(TAG_TIME)));
                modulef = rep.getString(LAST_MODULE);
//				ip = rep.getString("RANDOM_KEY");
//				time = data.millisT ime(Long.parseLong(rep.getString("RANDOM_KEY")));
//				modulef = rep.getString("RANDOM_KEY");
            } catch (Exception e) {
                ip = "首次登录";
                time = "首次登录";
                modulef = "首次登录";
                e.printStackTrace();
            }

            if (modulef.equals("P")) {
                module = "PC端";
            } else if (modulef.equals("M")) {
                module = "手机端";
            } else {
                module = "首次登录";
            }

            // 判断是否需要修改密码
            if (rep.getString(TAG_CHG_PWD).equals("1")) {// 强制修改密码
                forceChangePW = true;
            }


//          hm.put("forcechangepw", forceChangePW);
//          hm.put("ip", ip);
//          hm.put("module", module);
//          hm.put("time", time);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }

    }

}
