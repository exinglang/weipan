package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.TradeAccountEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class TradeLogonResponseData extends BaseResponseDataOpenAccountContract {

//    "is_encrypt": 1,
//            "session": "72434242594755789",
//            "last_time": "1452233607000",
//            "user_id": "666000000000012",
//            "svr_key": "QTWnOVnWEspQdNTDVOql9zbrmRNIE5QiRYaMJ7kQFnirwUya+ETUcpcNqz9HzJqU"


    protected static final String TAG_IS_ENCRYPT = "is_encrypt";
    protected static final String TAG_SESSION = "session";

    protected static final String TAG_LAST_TIME = "last_time";
    protected static final String TAG_ENV_CODE = "env_code";
    protected static final String TAG_SVR_KEY = "svr_key";


    public void parseXML(MyApplication myapp, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            myapp.getCurrentTradeEntity().getOtherData().setIsEncrypt(rep.getString(TAG_IS_ENCRYPT).equals("0") ? false : true);
            myapp.getCurrentTradeEntity().getOtherData().setSid(rep.getString(TAG_SESSION));

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }


}
