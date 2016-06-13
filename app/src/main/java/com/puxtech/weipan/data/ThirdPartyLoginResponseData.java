package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.ThirdPartyLoginEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class ThirdPartyLoginResponseData extends BaseResponseDataOpenAccountContract {

    //"token_b": " <TOKENB>...With User ID...</TOKENB>",
//        "user_id": "11",
//        "phone": "",
//        "nickname": "阿三",
//        "avatar": "
    ThirdPartyLoginEntity entity;
    protected static final String TAG_TOKENB = "token_b";
    protected static final String TAG_USER_ID = "user_id";
    protected static final String TAG_PHONE = "phone";
    protected static final String TAG_NICKNAME = "nickname";
    protected static final String TAG_AVATAR = "avatar";


    public void parseXML(MyApplication myapp, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            entity = new ThirdPartyLoginEntity();
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            entity.setToken_b(rep.getString(TAG_TOKENB));
            entity.setWeiPanId(rep.getString(TAG_USER_ID));
            entity.setPhone(rep.getString(TAG_PHONE));
            entity.setNickname(rep.getString(TAG_NICKNAME));
            entity.setAvatar(rep.getString(TAG_AVATAR));
            myapp.getOpenAccountContractEntity().setThirdPartyLoginEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }


}
