package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class TrustResponseData extends BaseResponseDataTrade {
    public int holdTotalRecordNumber;//取得的记录数
    public ArrayList<TradeTrustData> trustDataArrayList;

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
                holdTotalRecordNumber = resultList
                        .getJSONArray(Constant.TAG_REC).length();
            } catch (JSONException e) {
                e.printStackTrace();
                holdTotalRecordNumber = 0;

            }
            trustDataArrayList = new ArrayList<>();
            for (int j = 0; j < holdTotalRecordNumber; j++) {
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                TradeTrustData tradeTrustData = new TradeTrustData();



                String sname = new TradeHelper(context).getCommodityNameFromCode(rec.getString(Constant.CO_I));
                tradeTrustData.setName(sname);
                tradeTrustData.setOr_n(rec.getString(Constant.OR_N));
                tradeTrustData.setStuffId(rec.getString(Constant.CO_I));
                tradeTrustData.setType(rec.getString(Constant.TYPE));
                tradeTrustData.setQty(rec.getString(Constant.QTY));
                tradeTrustData.setPri(rec.getString(Constant.PRI));
                tradeTrustData.setStop_loss(rec.getString(Constant.STOP_LOSS));
                tradeTrustData.setStop_profit(rec.getString(Constant.STOP_PROFIT));
                tradeTrustData.setF_mar(rec.getString(Constant.F_MAR));
                tradeTrustData.setF_fee(rec.getString(Constant.F_FEE));
                tradeTrustData.setSta(rec.getString(Constant.STA));
                tradeTrustData.setO_t(rec.getString(Constant.O_T));
                tradeTrustData.setSe_f(rec.getString(Constant.SE_F));
                tradeTrustData.setH_no(rec.getString(Constant.H_NO));
                tradeTrustData.setCo_id(rec.getString(Constant.CO_ID));
                tradeTrustData.setT_qty(rec.getString(Constant.T_QTY));
                tradeTrustData.setTime(ActivityUtils.getRealTimeOfInteger(rec.getString(Constant.TIME)));
                trustDataArrayList.add(tradeTrustData);
            }
            currentTradeEntity.getTradeData().setTrustDataList(trustDataArrayList);
        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
