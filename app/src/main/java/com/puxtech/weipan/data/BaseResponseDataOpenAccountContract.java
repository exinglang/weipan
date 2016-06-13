package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.network.Logger;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/4.
 */

public class BaseResponseDataOpenAccountContract extends BaseResponseData {
    protected static final String TAG_REP_CODE = "rep_code";
    protected static final String TAG_REP_MSG = "rep_msg";

    protected static final String TAG_PUXT = "puxt";
    protected static final String TAG_REP_HEADER = "rep_header";
    protected static final String TAG_REP_BODY = "rep_body";

    protected static final String TAG_PROTOCOL_NAME = "protocol_name";


    public JSONObject checkFail(String jsString) throws Exception {

        JSONObject root;
        JSONObject rep;

        root = outPutJson(jsString);//输出日志过长时,日志无法完全显示,在此处进行分割.
        rep = root.getJSONObject(TAG_PUXT).getJSONObject(TAG_REP_HEADER);
        if (rep.has(TAG_REP_CODE)) {
            retCode = rep.getInt(TAG_REP_CODE);
            // if (retCode != Constant.CODE_SUCCESS) {
            retMessage = rep.getString(TAG_REP_MSG);
            if(retCode==0&&retMessage==null){
                retMessage="成功";
            }
//                Logger.v(root.getJSONObject(TAG_PUXT)
//                        .getJSONObject(TAG_REP_HEADER).getString(TAG_PROTOCOL_NAME)
//                        + ",协议解析出错:" + retMessage);
            //}
            //  retMessage = rep.getString(TAG_REP_MSG);
        }
        return root;

    }


}
