package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class ReportDealResponseData extends BaseResponseDataTrade {
    public int holdTotalRecordNumber;//取得的记录数
    public ArrayList<TradeReportDealData> dealDataArrayList;

    public void parseXML(String jsString) {
        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS)
                    .getJSONObject(Constant.TAG_REP);
            JSONObject resultList = null;
            holdTotalRecordNumber = 0;
            try {
                if(!rep.has(Constant.TAG_LIST)){

                    return;
                }
                resultList = rep.getJSONObject(Constant.TAG_LIST);
                holdTotalRecordNumber = resultList
                        .getJSONArray(Constant.TAG_REC).length();
            } catch (Exception e) {
                e.printStackTrace();
                holdTotalRecordNumber = 0;

            }
            dealDataArrayList = new ArrayList<>();
            for (int j = 0; j < holdTotalRecordNumber; j++) {
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                TradeReportDealData tradeDealData = new TradeReportDealData();
//
//                String QUANTITY_SUM;// 平仓量合计
//                String TRADE_FUNDS_SUM;// 成交金额合计
//                String TRADE_FEE_SUM;// 手续费合计
//                String TRADE_NO;// 平仓单号
//                String COMMODITY_NAME;// 商品名称
//                String BS_FLAG;// 买卖标志
//                String QUANTITY;// 平仓量
//                String PRICE;// 平仓价
//                String TRADE_FUNDS;
//                String TRADE_FEE;// 手续费
//                String HOLDE_TIME ;// 建仓时间
//                String CLEAR_DATE;// 结算日期，日期型，毫秒

//                tradeDealData.setQUANTITY_SUM(rec.getString(Constant.QUANTITY_SUM));
//                tradeDealData.setTRADE_FUNDS_SUM(rec.getString(Constant.TRADE_FUNDS_SUM));
//                tradeDealData.setTRADE_FEE_SUM(rec.getString(Constant.TRADE_FEE_SUM));
                tradeDealData.setTRADE_NO(rec.getString(Constant.TRADE_NO));
                tradeDealData.setCOMMODITY_NAME(rec.getString(Constant.COMMODITY_NAME));
                tradeDealData.setBS_FLAG(rec.getString(Constant.BS_FLAG));
                tradeDealData.setQUANTITY(rec.getString(Constant.QUANTITY));
                tradeDealData.setPRICE(rec.getString(Constant.PRICE));
                tradeDealData.setTRADE_FUNDS(rec.getString(Constant.TRADE_FUNDS));
                tradeDealData.setTRADE_FEE(rec.getString(Constant.TRADE_FEE));
                tradeDealData.setHOLDE_TIME(rec.getString(Constant.HOLDE_TIME));
                tradeDealData.setCLEAR_DATE(rec.getString(Constant.CLEAR_DATE));

                dealDataArrayList.add(tradeDealData);
            }
            //  currentTradeEntity.getTradeData().setTrustDataList(trustDataArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
