package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.data.entitydata.InOutHistoryEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class InOutHistoryResponseData extends BaseResponseDataTrade {
    protected static final String TAG_MID = "MID";
    protected static final String TAG_TD = "TD";
    protected static final String TAG_BANK_NAME = "BANK_NAME";
    protected static final String TAG_TT = "TT";
    protected static final String TAG_AM = "AM";
    protected static final String TAG_TS = "TS";
    protected static final String TAG_REM = "REM";
//
//  MID;//流水号
//  TD;//操作时间，微秒
//  BANK_NAME;//银行名称
//  TT;//类型:I入金；O出金
//  AM;//金额
//  TS;//状态
//  REM;//备注


    public int recordNumber;//取得的记录数
    ArrayList<InOutHistoryEntity> inOutHistoryEntityArrayList;


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
                recordNumber = resultList
                        .getJSONArray(Constant.TAG_REC).length();
            } catch (JSONException e) {
                e.printStackTrace();
                recordNumber = 0;
            }
            inOutHistoryEntityArrayList = new ArrayList<>();
            for (int j = 0; j < recordNumber; j++) {
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(j);
                InOutHistoryEntity entity = new InOutHistoryEntity();
                entity.setMid(rec.getString(TAG_MID));
                entity.setTd(rec.getString(TAG_TD));
                entity.setBank_name(rec.getString(TAG_BANK_NAME));
                entity.setTT(rec.getString(TAG_TT).equals("I")?"入金":"出金");
                entity.setAM(rec.getString(TAG_AM));
                entity.setTS(rec.getString(TAG_TS));
                entity.setREM(rec.getString(TAG_REM));
                inOutHistoryEntityArrayList.add(entity);
            }


        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }


    public ArrayList<InOutHistoryEntity> getInOutHistoryEntityArrayList() {
        return inOutHistoryEntityArrayList;
    }
}
