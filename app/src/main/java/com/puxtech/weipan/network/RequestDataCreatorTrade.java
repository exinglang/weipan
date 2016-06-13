package com.puxtech.weipan.network;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.util.AES;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.Base64;
import com.puxtech.weipan.util.MD5;
import com.puxtech.weipan.util.TradeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15/11/4.
 */
public class RequestDataCreatorTrade  extends RequestDataCreatorBase{


    public RequestDataCreatorTrade(Context context) {
        super(context);

    }

    /**
     * 登录协议logon
     *
     * @throws Exception 请处理异常情况
     */
    public String createLogonnewRequestData(String rName, String pwd, String username,
                                            String checkcode, String codeid, byte[] logonKey)  {
        HashMap<String, String> map = new HashMap<>();
        long time = System.currentTimeMillis();
        map.put("USER_ID", username);
        map.put("PASSWORD", pwd);
        map.put("VALID_CODE", checkcode);
        map.put("VALID_UUID", codeid);
        map.put("LOGONTIME", time + "");
        map.put("REGISTER_WORD", "");
        map.put("VERSIONINFO", "");
        map.put("CLT_TOKEN", TradeUtils.getToken(time, username, pwd, logonKey));
        return getRequestData(map, rName);
    }

    /**
     * 商品信息协议
     *
     * @throws Exception 请处理异常情况
     */
    public String createCommodityRequestData(String rName, String commodityCode) throws Exception {


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("COMMODITY_ID", commodityCode);
        map.put("A_N", "");
        map.put("P_P", "");

        return getRequestData(map, rName);
    }

    /**
     * 持仓明细协议
     *
     *
     */
    public String createHoldDetailRequestData(String rName, int start, String num, String commodityCode) {


        HashMap<String, String> map = new HashMap<String, String>();

        map.put("COMMODITY_ID", commodityCode);
        map.put("MARKET_ID", "1");
        map.put("STARTNUM", String.valueOf(start));
        map.put("RECCNT", num);

        map.put("SORTFLD", "");
        map.put("ISDESC", "");
        map.put("A_N", "");
        map.put("P_P", "");

        return getRequestData(map, rName);
    }
    /**
     * 建仓
     *
     *
     */
    public String createShiJiaTradeRequestData(String rName,String stuffCode, boolean isBuy, String num,
                                         String cut, boolean isBuild, String closemode, String holding_id,double price) {
        HashMap<String, String> map = new HashMap<>();
        map.put("BUY_SELL", isBuy ? "1" : "2");
        map.put("COMMODITY_ID", stuffCode);
        map.put("PRICE", String.valueOf(price));
        map.put("QTY", num);
        map.put("OTHER_ID", myapp.getCurrentTradeEntity().getTradeData().getOtherFirmData().getE_M_ID());
        map.put("SETTLE_BASIS", isBuild ? "1" : "2");
        map.put("CLOSEMODE", closemode);
        map.put("HOLDING_ID", holding_id);
        if (cut.length() <= 0)
            cut = "0";
        map.put("DOT_DIFF", cut);
        map.put("A_N", "");
        map.put("P_P", "");
        return getRequestData(map, rName);
    }

    /**
     * 建仓
     *
     *
     */
    public String createZhiJiaTradeRequestData(String rName,String signal, boolean isBuy, String price,
                                               String num, String stop_loss, String stop_profit) {
        HashMap<String, String> map = new HashMap<>();
        map.put("BUY_SELL", isBuy ? "1" : "2");
        map.put("COMMODITY_ID", signal);
        map.put("PRICE", price);
        map.put("QTY", num);
        map.put("OTHER_ID", myapp.getCurrentTradeEntity().getTradeData().getOtherFirmData().getE_M_ID());
        map.put("STOP_LOSS", stop_loss);
        map.put("STOP_PROFIT", stop_profit);
        map.put("VALIDITY_TYPE", "0");
        map.put("A_N", "");
        map.put("P_P", "");
        return getRequestData(map, rName);
    }
    /**
     * @param rName 协议名称
     * @param start 开始位置
     * @param commodityCode
     * @return
     * @throws Exception
     */
    public String createHoldTotalRequestData(String rName, int start,  String commodityCode) throws Exception {


        HashMap<String, String> map = new HashMap<String, String>();

        map.put("STARTNUM", String.valueOf(start));
        map.put("RECCNT", "");
        map.put("COMMODITY_ID", commodityCode);
        map.put("MARKET_ID", "1");
        map.put("SORTFLD", "");
        map.put("SORTFLD", "");
        map.put("A_N", "");
        map.put("P_P", "");

        return getRequestData(map, rName);
    }

    /**
     * 指价单查询
     * @param rName 协议名称
     * @return
     * @throws Exception
     */
    public String createTrustRequestData(String rName) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("OR_OC", "");// 开平仓（1开仓2平仓，空全部）1
        map.put("BUY_SELL", "");// 买、卖类型 买卖(1/2)（空表示所有）
        map.put("OR_TP", "");// 委托单类型（1市价2指价，空全部）
        map.put("OR_ST", "1");// 1已委托2已成交3已撤单4部分成交5部分成交后撤单，空全部
        map.put("STARTNUM", "1");// 1
        map.put("ORDER_NO", "");
        map.put("MARKET_ID", "");
        map.put("RECCNT", "0");
        map.put("SORTFLD", "");
        map.put("ISDESC", "0");
        map.put("UT", "");
        map.put("A_N", "");
        map.put("P_P", "");
        return getRequestData(map, rName);
    }

    /**
     * 成交查询
     * @param rName 协议名称
     *@param start
     * @param end @return
     * @throws Exception
     */
    public String createDealRequestData(String rName, int start, int end) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("COMMODITY_ID", "");// 商品类别
        map.put("STARTNUM",start+"");// 开始条数
        map.put("BUY_SELL", "");// 买卖方向
        map.put("OR_OC", "");// 建仓平仓
        map.put("MARKET_ID", "");// 建仓平仓
        map.put("LAST_TRADE_ID", "");// 建仓平仓
        map.put("RECCNT", end+"");
        map.put("SORTFLD", "");
        map.put("ISDESC", "");
        map.put("A_N", "");
        map.put("P_P", "");
        return getRequestData(map, rName);
    }
    /**
     * 报表成交查询
     * @param rName 协议名称
     *@param start
     * @throws Exception
     */
    public String createReportDealRequesReporttData(String rName, String startTime, String endTime,
                                                    int start,int num) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("ST", startTime);
        map.put("ET", endTime);
        map.put("STARTNUM", start+"");
        map.put("RECCNT", num+"");
        map.put("SORTFLD", "");
        map.put("ISDESC", "");
        return getRequestData(map, rName);
    }
    /**
     * @param rName 协议名称
     * @return
     * @throws Exception
     */
    public String createCancelTrustRequestData(String rName,String order_no) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ORDER_NO", order_no);
        map.put("A_N", "");
        map.put("P_P", "");
        return getRequestData(map, rName);
    }
    public String createAccountInfoRequestData(String rName) throws Exception {


        HashMap<String, String> map = new HashMap<String, String>();



        return getRequestData(map, rName);
    }


    public String createOtherFirmRequestData(String rName) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("IS_D", "1");
        return getRequestData(map, rName);
    }


    public String createHeartBeatRequestData(TradeEntity tradeEntity,int firstLogin,long broadId,long dealCount,long lastid) throws Exception {
        JSONObject json = new JSONObject();
        json.put("ver", "1.0");
        json.put("uid",tradeEntity.getUserId());
        json.put("broadcast", broadId);
        json.put("tradecnt", dealCount);
        json.put("sessionid", tradeEntity.getOtherData().getSid());
        json.put("curlogon",firstLogin);
        json.put("agencyno", "");
        json.put("phonepwd", "");
        json.put("lastid", lastid);
        json.put("CO_I", "");
        return json.toString();
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
}
