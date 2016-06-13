package com.puxtech.weipan.ResponseData;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.data.BaseResponseDataTrade;
import com.puxtech.weipan.data.OtherFirmData;
import com.puxtech.weipan.data.TradeEntity;

import org.json.JSONObject;

/**
 * Created by mac on 15/11/5.
 */
public class TradeResponseData extends BaseResponseDataTrade {
    public void parseXML(TradeEntity currentTradeEntity, String jsString) {

        try {
           checkFail(jsString);
//            if (retCode != 0) {
//                return;
//            }
//            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS)
//                    .getJSONObject(Constant.TAG_REP);
//            JSONObject rec = rep.getJSONObject(Constant.TAG_LIST)
//                    .getJSONArray(Constant.TAG_REC).getJSONObject(0);
//           OtherFirmData of = new OtherFirmData();
//            of.setE_M_ID(rec.getString(TAG_EMID));
//            currentTradeEntity.getTradeData().setOtherFirmData(of);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
