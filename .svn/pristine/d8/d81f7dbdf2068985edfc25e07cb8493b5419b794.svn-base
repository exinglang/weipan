package com.puxtech.weipan.ResponseData;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.data.BaseResponseDataTrade;
import com.puxtech.weipan.data.BaseResponseDataWeiXin;
import com.puxtech.weipan.data.OtherFirmData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEntity;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/5.
 */
public class AccessTokenResponseData extends BaseResponseDataWeiXin {


    private static final String TAG_ACCESS_TOKEN = "access_token";
    private static final String TAG_EXPIRES_IN = "expires_in";
    private static final String TAG_REFRESH_TOKEN = "refresh_token";
    private static final String TAG_OPENID = "openid";
    private static final String TAG_SCOPE = "scope";

    WeiXinAccessTokenEntity getTokenEntity;

    public void parse(String jsString) {
        try {
            JSONObject rep = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            //没有ERROR CODE,就是返回正确
            getTokenEntity = new WeiXinAccessTokenEntity();
            getTokenEntity.setAccess_token(rep.getString(TAG_ACCESS_TOKEN));
            getTokenEntity.setExpires_in(rep.getInt(TAG_EXPIRES_IN));
            getTokenEntity.setRefresh_token(rep.getString(TAG_REFRESH_TOKEN));
            getTokenEntity.setOpenid(rep.getString(TAG_OPENID));
            getTokenEntity.setScope(rep.getString(TAG_SCOPE));
        } catch (Exception e) {
            e.printStackTrace();
            retCode = Constant.CODE_ERROR_PRASE;
            retMessage = Constant.MESSAGE_ERROR_PRASE;
        }
    }

    public WeiXinAccessTokenEntity getGetTokenEntity() {
        return getTokenEntity;
    }
}
