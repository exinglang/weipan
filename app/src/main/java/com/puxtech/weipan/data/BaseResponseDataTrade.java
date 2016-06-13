package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.network.Logger;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseDataTrade extends BaseResponseData {
    private static final String ATTR_CODE = "retcode";


    public JSONObject checkFail(String jsString) throws  Exception{

        JSONObject root;
        JSONObject rep;

            root = outPutJson(jsString);//输出日志过长时,日志无法完全显示,在此处进行分割.
            rep = root.getJSONObject(Constant.TAG_MMTS).getJSONObject(Constant.TAG_REP);
            if (rep.has(ATTR_CODE)) {

                retCode = rep.getInt(ATTR_CODE);
                if (retCode != Constant.CODE_SUCCESS) {
                    retMessage = rep.getString(Constant.MESSAGE);
                    Logger.v(root.getJSONObject("MMTS")
                            .getJSONObject("REP").getString("name")
                            + ",协议解析出错:" + retMessage);
                }
//                    if (!(rcode == 0)) {
//                        hm.put(Constant.RESULT, false);
//                        String message = rep.getString(ATTR_MESSAGE);
//                        Log.i(Constant.RESULT, root.getJSONObject("MMTS")
//                                .getJSONObject("REP").getString("name")
//                                + ",协议解析出错");
//                        hm.put(Constant.MESSAGE, message);
//                    }
            }
            // 下面的IF是为了应对各种返回协议(前置机不同,返回的协议格式也不同..)
            if (rep.has(Constant.RESULT)) {
                JSONObject result = rep.getJSONObject(Constant.RESULT);
                retCode = result.getInt("RETCODE");
                if (retCode != Constant.CODE_SUCCESS) {
                    retMessage = rep.getString(Constant.MESSAGE);
//                        hm.put(Constant.RESULT, false);
//                        String message = result.getString(Constant.MESSAGE);
//                        // String message = e.getAttribute(ATTR_MESSAGE);
//                        hm.put(Constant.MESSAGE, message);

                }
            }



//        }
        return root;

    }



}
