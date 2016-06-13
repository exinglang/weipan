package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.TradeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15/11/4.
 */
public class RequestDataCreatorTradeBase extends RequestDataCreatorBase{
    private Context context;


    public RequestDataCreatorTradeBase(Context context) {
        super(context);

    }
    // 给一些复用的KEY赋值
    protected String jsonRequestPutHead(JSONObject json, String name)
            throws JSONException {
        // TODO Auto-generated method stub
        json.put("id", ActivityUtils.getId(myapp));
        json.put("name", name);
        if (!(name.equals("get_version") || name.equals("logon"))) {
            // get_version和logon没有这两个属性
            json.put("SESSION_ID", myapp.getCurrentTradeEntity().getOtherData().getSid());
            json.put("USER_ID", myapp.getCurrentTradeEntity().getUserId());
        }
        JSONObject rep = new JSONObject();
        rep.put("REQ", json);
        if (!name.equals("get_version")) {
            // get_version没有此键值
            rep.put("version", "1.0");
        }
        JSONObject mmts = new JSONObject();
        mmts.put("MMTS", rep);
//        TradeLogRecorder tlr = new TradeLogRecorder();
//        tlr.saveLog(myapp, "请求:"+json.toString());
        return mmts.toString();
    }
    // 将map中的值,组成json
    protected String getRequestData(HashMap<String, String> map, String rName) {
        JSONObject json = new JSONObject();
        String req = null;
        try {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> m = it.next();
                String key = m.getKey();
                String value = m.getValue();
                json.put(key, value);
            }
            req = jsonRequestPutHead(json, rName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return req;

    }

}
