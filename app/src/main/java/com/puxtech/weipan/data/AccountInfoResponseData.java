package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.util.ActivityUtils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class AccountInfoResponseData extends BaseResponseDataTrade {

    public void parseXML(TradeEntity currentTradeEntity, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS)
                    .getJSONObject(Constant.TAG_REP);
            JSONObject rec = rep.getJSONObject(Constant.TAG_LIST)
                    .getJSONArray(Constant.TAG_REC).getJSONObject(0);
            AccountInfoData ai = new AccountInfoData();
            ai.setFN(rec.getString(Constant.FN));// 账户名称0
            ai.setFlp(rec.getString(Constant.RF));//
            ai.setIF(ActivityUtils.changeDouble(rec
                    .getString(Constant.IF)));// 期初余额
            ai.setCM(ActivityUtils.changeDouble(rec
                    .getString(Constant.CM)));// 上日保证金
            ai.setUF(ActivityUtils.changeDouble(rec
                    .getString(Constant.UF)));// 当前可用保证金
            ai.setIOF(ActivityUtils.changeDouble(ActivityUtils.handle(rec
                    .getString(Constant.IOF))));// 当日出入金
            ai.setRM(rec.getString(Constant.RM));
            ai.setOR_F(ActivityUtils.changeDouble(rec
                    .getString(Constant.OR_F)));
            ai.setPL(rec.getString(Constant.PL));// 当日平仓盈亏合计(刘振中确认)
            ai.setFEE(ActivityUtils.changeDouble(rec
                    .getString(Constant.FEE)));// 当日手续费合计
            ai.setCD(ActivityUtils.changeDouble(rec
                    .getString(Constant.CD)));// 上日延期费9
            ai.setOR_F_M(ActivityUtils.changeDouble(ActivityUtils.handle(rec
                    .getString(Constant.OR_F_M))));// 冻结保证金10
            ai.setOR_F_F(ActivityUtils.changeDouble(ActivityUtils.handle(rec
                    .getString(Constant.OR_F_F))));// 冻结手续费11
            ai.setC_STA(rec.getString(Constant.C_STA));
            ai.setIB(rec.getString(Constant.IB));
            ai.setDangqianquanyi(rec.getString(Constant.EQT));
            ai.setFenxianlv("0.00%");

            currentTradeEntity.getTradeData().setAccountInfoData(ai);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
