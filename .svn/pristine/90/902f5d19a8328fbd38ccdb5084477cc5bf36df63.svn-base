package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.CustomException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class HoldDetailResponseData extends BaseResponseDataTrade {
    public int holdDetailRecordNumber;//取得的记录数
    ArrayList<HoldDetailData> holdDetailDataList;
    public void parseXML(Context context,TradeEntity currentTradeEntity, String jsString) {
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
                holdDetailRecordNumber = resultList
                        .getJSONArray(Constant.TAG_REC).length();
            } catch (JSONException e) {
                e.printStackTrace();
                holdDetailRecordNumber = 0;
            }
            holdDetailDataList = new ArrayList<>();

            // hm.put("recordcount", builddetialtotalRecord);
            for (int j = 0; j < holdDetailRecordNumber; j++) {
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                String buystate = null;
                if (rec.getString(Constant.TY).equals("1")) {
                    buystate = Constant.BUY;
                } else if (rec.getString(Constant.TY).equals("2")) {
                    buystate = Constant.SELL;
                }

                String sname = null;
                String svalue = rec.getString(Constant.CO_I);


                sname = new TradeHelper(context).getCommodityNameFromCode(svalue);


                // 处理第二级页面的信息
                if (rec.getString(Constant.C_QTY).equals("0")) {
                    continue;
                }
                String holdId = rec.getString(Constant.H_ID);
                HoldDetailData holdDetailData = new HoldDetailData();
                holdDetailData.setStuffId(rec.getString(Constant.CO_I));
                holdDetailData.setHoldId(holdId);
                holdDetailData.setStuffName(sname);
                holdDetailData.setBuyOrSell(buystate);
                holdDetailData.setHoldNumber(rec.getString(Constant.C_QTY));
                holdDetailData.setHoldPrice(rec.getString(Constant.H_P));
                holdDetailData.setBuildPrice(rec.getString(Constant.PR));
                holdDetailData.setFlp("--");
                holdDetailData.setDongJieNumber(rec.getString(Constant.F_QTY));
                holdDetailData.setOpenNumber(rec.getString(Constant.O_QTY));
                holdDetailData.setStopLoss((ActivityUtils.changeDouble(rec
                        .getString(Constant.STOP_LOSS))));
                holdDetailData.setStopEarn(ActivityUtils.changeDouble(rec
                        .getString(Constant.STOP_PROFIT)));
//                double version = 1.3;
//                if (version < Double.valueOf(myapp.getNyTrade()
//                        .getSessionData().getVersion())) {
//
//                    holdDetailEntity.setBaoZhengJinBiLi(rec
//                            .getString(Constant.MAR_RATE));
//                    // templist.add(rec.getString(Constant.MAR_RATE)); // 保证金比例
//                } else {
                holdDetailData.setBaoZhengJinBiLi("");
                //    }
                holdDetailData.setBaoZhengJin(ActivityUtils.changeDouble(rec
                        .getString(Constant.MAR)));
                holdDetailData.setBuildTime(ActivityUtils.getRealData(rec
                        .getString(Constant.OR_T)));
                holdDetailData.setCo_id(rec.getString(Constant.CO_ID));
                holdDetailDataList.add(holdDetailData);
            }
            currentTradeEntity.getTradeData().setHoldDetailDataList(holdDetailDataList);


        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
