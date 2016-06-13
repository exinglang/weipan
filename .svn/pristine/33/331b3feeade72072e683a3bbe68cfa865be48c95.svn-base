package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class HoldTotalResponseData extends BaseResponseDataTrade {
    public int holdTotalRecordNumber;//取得的记录数
    public ArrayList<HoldTotalData> holdTotalDataList;

    public void parseXML(TradeEntity currentTradeEntity, String jsString) {
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
            holdTotalDataList = new ArrayList<>();

            for (int j = 0; j < holdTotalRecordNumber; j++) {


                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                HoldTotalData hte = new HoldTotalData();
                hte.setCode(rec.getString(Constant.CO_I));
                hte.setFlp(ActivityUtils.changeDouble(rec
                        .getString(Constant.FL_P)));
                hte.setChiCangBaoZhengJin(ActivityUtils.changeDouble(rec
                        .getString(Constant.MAR)));
                String buystate = null;
                String buyvalue = rec.getString(Constant.TY);
                if (buyvalue.equals("1")) {
                    buystate = Constant.BUY;
                } else if (buyvalue.equals("2")) {
                    buystate = Constant.SELL;
                }

                hte.setBuyOrSell(buystate);
                hte.setNumber(rec.getString(Constant.QTY));
                if (rec.getString(Constant.QTY).equals("0"))
                    continue;
                hte.setJianCangJunJia(ActivityUtils.changeDouble(rec
                        .getString(Constant.O_A)));

                hte.setPinJunChiCangJia((ActivityUtils.changeDouble(rec
                        .getString(Constant.A_H))));
                hte.setDongJieShuLiang(rec.getString(Constant.F_QTY));
                holdTotalDataList.add(hte);
            }
            //   myapp.getNyTrade().getJyData().setHoldTotalEntityList(hteList);
            currentTradeEntity.getTradeData().setHoldTotalDataList(holdTotalDataList);


        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
