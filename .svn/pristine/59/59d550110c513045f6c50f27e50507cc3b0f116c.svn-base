package com.puxtech.weipan.ResponseData;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.BaseResponseDataWeiXin;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEntity;
import com.puxtech.weipan.data.entitydata.WeiXinGetUnionID;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/5.
 */
public class UnionIDResponseData extends BaseResponseDataWeiXin {
    WeiXinGetUnionID getUnionID;

    private static final String TAG_OPENID = "openid";
    private static final String TAG_NICKNAME= "nickname";
    private static final String TAG_SEX = "sex";
    private static final String TAG_PROVINCE = "province";
    private static final String TAG_CITY = "city";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_HEADIMGURL = "headimgurl";
    private static final String TAG_UNIONID = "unionid";

    public void parse(String jsString) {
        try {
            JSONObject json = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            getUnionID = new WeiXinGetUnionID();
            //没有ERROR CODE,就是返回正确
            getUnionID.setOpenid(json.getString(TAG_OPENID));
            getUnionID.setNickname(json.getString(TAG_NICKNAME));
            getUnionID.setSex(json.getInt(TAG_SEX));
            getUnionID.setProvince(json.getString(TAG_PROVINCE));
            getUnionID.setCity(json.getString(TAG_CITY));
            getUnionID.setCountry(json.getString(TAG_COUNTRY));
            getUnionID.setHeadimgurl(json.getString(TAG_HEADIMGURL));
            getUnionID.setUnionid(json.getString(TAG_UNIONID));



        } catch (Exception e) {
            e.printStackTrace();
            retCode = Constant.CODE_ERROR_PRASE;
            retMessage = Constant.MESSAGE_ERROR_PRASE;
        }
    }

    public WeiXinGetUnionID getGetUnionIDEntity() {
        return getUnionID;
    }
}
