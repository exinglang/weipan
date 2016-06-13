package com.puxtech.weipan.ResponseData;
import com.puxtech.weipan.data.BaseResponseDataTrade;
import com.puxtech.weipan.data.HeartBeatDealData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class HeartBeatResponseData extends BaseResponseDataTrade {
    long CTM;//服务器传来的当前时间
    long LID;//// 最后广播ID
    long TLID;
    long CMOUPT;// 买卖列表,任一更新
    long BUPT;// 买列表更新
    long UPT;// 卖列表更新
    int NT;//// 是否有新成交 0:没有；1：有
    int TTC;// 成交记录数 第一次传0 随后每次传的是服务器返回来的值
    String TD;// 交易日,用来判断是否改变,改变,要退出重新登录
    int STA;// 0:初始化完成2:结算中3:资金结算完成 4: 暂停交易 5:交易中 6:节间休息 7:交易结束      当前状态的变量 随着心跳的请求 而获得
    ArrayList<HeartBeatDealData> heartBeatDealDataArrayList;//成交序列
    int dealrecord;
    public void parseXML(String jsString) {
        try {
            JSONObject  root = outPutJson(jsString);
          //  JSONObject root = new JSONObject(jsString);
            retCode = root.getInt(TAG_RET);
            if (retCode != 0) {
                return;
            }
            LID = root.getLong(TAG_LID);
            CTM = root.getLong(TAG_CTM);
            CMOUPT = root.getLong(TAG_CMOUPT);
            BUPT = root.getLong(TAG_BUPT);
            UPT = root.getLong(TAG_UPT);
            NT = root.getInt(TAG_NT);
            TTC = root.getInt(TAG_TTC);
            TD = root.getString(TAG_TD);
            STA= root.getInt(TAG_STA);
            TLID=root.getLong(TAG_TLID);
            JSONArray recList=null;
            try {
                recList = root.getJSONArray(TAG_DATA);
                dealrecord = recList.length();// TradeTotalCount
            } catch (JSONException e) {
                dealrecord = 0;
            }
            if (dealrecord > 0  && NT == 1) {
                heartBeatDealDataArrayList = new ArrayList<>();
                for (int i = 0; i < dealrecord; i++) {
                    HeartBeatDealData heartBeatDealData = new HeartBeatDealData();
                    JSONObject rec = recList.getJSONObject(i);
                    heartBeatDealData.setONO(rec.getLong(TAG_ONO));// 委托单号
                    heartBeatDealData.setCMOID(rec.getString(TAG_CMOID));// 商品代码
                    heartBeatDealData.setQT(rec.getLong(TAG_QT));// 当前成交量
                    heartBeatDealData.setTYP(rec.getInt(TAG_TYP));// 委托类型：1开仓；2平仓
                    heartBeatDealData.setBS(rec.getInt(TAG_BS));// 买卖标志：1买；2卖
                    heartBeatDealDataArrayList.add(heartBeatDealData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    private static final String TAG_RET = "ret";
    private static final String TAG_DATA = "data";
    private static final String TAG_TLID = "TLID";
    private static final String TAG_LID = "LID";
    private static final String TAG_CTM = "CTM";
    private static final String TAG_CMOUPT = "CMOUPT";
    private static final String TAG_BUPT = "BUPT";
    private static final String TAG_UPT = "UPT";
    private static final String TAG_NT = "NT";
    private static final String TAG_TTC = "TTC";
    private static final String TAG_TD = "TD";
    private static final String TAG_ONO = "ONTYPO";
    private static final String TAG_CMOID = "CMOID";
    private static final String TAG_QT = "QT";
    private static final String TAG_TYP = "TYP";
    private static final String TAG_BS = "BS";
    private static final String TAG_STA = "STA";

    public long getCTM() {
        return CTM;
    }

    public long getLID() {
        return LID;
    }

    public long getTLID() {
        return TLID;
    }

    public long getCMOUPT() {
        return CMOUPT;
    }

    public long getBUPT() {
        return BUPT;
    }

    public long getUPT() {
        return UPT;
    }

    public int getNT() {
        return NT;
    }

    public int getTTC() {
        return TTC;
    }

    public String getTD() {
        return TD;
    }

    public int getSTA() {
        return STA;
    }

    public ArrayList<HeartBeatDealData> getHeartBeatDealDataArrayList() {
        return heartBeatDealDataArrayList;
    }

    public int getDealrecord() {
        return dealrecord;
    }

}
