package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class DealResponseData extends BaseResponseDataTrade {
    public int holdTotalRecordNumber;//取得的记录数
    public ArrayList<TradeDealData> dealDataArrayList;

    public void parseXML(String jsString) {
        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS)
                    .getJSONObject(Constant.TAG_REP);
            JSONObject resultList = null;
            try {
                resultList = rep.getJSONObject(Constant.TAG_LIST);
                holdTotalRecordNumber = resultList
                        .getJSONArray(Constant.TAG_REC).length();
            } catch (JSONException e) {
                e.printStackTrace();
                holdTotalRecordNumber = 0;

            }
            dealDataArrayList = new ArrayList<>();
            for (int j = 0; j < holdTotalRecordNumber; j++) {
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                TradeDealData tradeDealData = new TradeDealData();

//                String TR_N;//成交单号
//                String CO_I; // 商品代码
//                String TY;//买卖方向
//                String QTY;//数量
//                String PR;//数量
//                String LIQPL;//盈亏
//                String COMM;//手续费
//                String OR_N;//委托单号
//                String HL_N;//持仓单号
//                String SE_F;//建仓平仓
//                String TI;//成交时间

                tradeDealData.setTR_N(rec.getString(Constant.TR_N));
                tradeDealData.setCO_I(rec.getString(Constant.CO_I));
                tradeDealData.setTY(rec.getString(Constant.TY));
                tradeDealData.setQTY(rec.getString(Constant.QTY));
                tradeDealData.setPR(rec.getString(Constant.PR));
                tradeDealData.setLIQPL(rec.getString(Constant.LIQPL));
                tradeDealData.setCOMM(rec.getString(Constant.COMM));
                tradeDealData.setOR_N(rec.getString(Constant.OR_N));
                tradeDealData.setHL_N(rec.getString(Constant.HL_N));
                tradeDealData.setSE_F(rec.getString(Constant.SE_F));
                tradeDealData.setTI(rec.getString(Constant.TI));

//                tradeTrustData.setT_qty(rec.getString(Constant.T_QTY));
//                tradeTrustData.setTime(ActivityUtils.getRealTimeOfInteger(rec.getString(Constant.TIME)));
                dealDataArrayList.add(tradeDealData);
            }
            //  currentTradeEntity.getTradeData().setTrustDataList(trustDataArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
