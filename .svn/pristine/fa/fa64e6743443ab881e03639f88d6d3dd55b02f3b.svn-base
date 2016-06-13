package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.network.Logger;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseDataWeiXin extends BaseResponseData {
    private static final String TAG_ERRCODE = "errcode";
    private static final String TAG_ERRMSG = "errmsg";

    protected JSONObject checkFail(String jsString) throws Exception {
        JSONObject rep;
        rep = outPutJson(jsString);//输出日志过长时,日志无法完全显示,在此处进行分割.
        if (!rep.has(TAG_ERRCODE)) {
            retCode = 0;
        } else {
            retCode = rep.getInt(TAG_ERRCODE);
            retMessage = rep.getString(TAG_ERRMSG);
        }
        return rep;

    }


}
