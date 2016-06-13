package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by mac on 15/11/5.
 */
public class CommodityResponseData extends BaseResponseDataTrade {

    public void parseXML(TradeEntity currentTradeEntity, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }

            ArrayList<CommodityData> commodityDatas=new ArrayList<>();
            JSONObject rep = root.getJSONObject(Constant.TAG_MMTS).getJSONObject(Constant.TAG_REP);


            JSONObject resultList = rep.getJSONObject(Constant.TAG_LIST);
            for (int i = 0; i < resultList.getJSONArray(Constant.TAG_REC)
                    .length(); i++) {
                CommodityData cd = new CommodityData();
                JSONObject rec = resultList.getJSONArray(Constant.TAG_REC)
                        .getJSONObject(i);
                cd.setCode(rec.getString(Constant.CO_I));
                cd.setName(rec.getString(Constant.CO_N));
                cd.setW_D_T_P(rec.getString(Constant.W_D_T_P));
                cd.setW_D_S_L_P(rec.getString(Constant.W_D_S_L_P));
                cd.setW_D_S_P_P(rec.getString(Constant.W_D_S_P_P));
                cd.setB_X_O_P(rec.getString(Constant.B_X_O_P));
                cd.setS_X_O_P(rec.getString(Constant.S_X_O_P));
                cd.setB_O_P(rec.getString(Constant.B_O_P));
                cd.setB_L_P(rec.getString(Constant.B_L_P));
                cd.setS_O_P(rec.getString(Constant.S_O_P));
                cd.setS_L_P(rec.getString(Constant.S_L_P));
                cd.setB_S_L(rec.getString(Constant.B_S_L));
                cd.setB_S_P(rec.getString(Constant.B_S_P));
                cd.setS_S_L(rec.getString(Constant.S_S_L));
                cd.setS_S_P(rec.getString(Constant.S_S_P));
                cd.setC_L_B_O(rec.getString(Constant.C_L_B_O));
                cd.setC_L_B_C(rec.getString(Constant.C_L_B_C));
                cd.setC_L_C_O(rec.getString(Constant.C_L_C_O));
                cd.setC_L_C_C(rec.getString(Constant.C_L_C_C));
                cd.setCT_S(rec.getString(Constant.CT_S));// 交易单位
                cd.setM_H(rec.getString(Constant.M_H));// 最大持仓量2
                cd.setX_O_B_D_D(rec.getString(Constant.X_O_B_D_D));// 限价建仓买点差
                cd.setX_O_S_D_D(rec.getString(Constant.X_O_S_D_D));// 限价建仓卖点差4
                cd.setSTOP_L_P(rec.getString(Constant.STOP_L_P));// 止损下单点差
                cd.setSTOP_P_P(rec.getString(Constant.STOP_P_P));// 止盈下单点差
                cd.setU_O_D_D_MIN(rec.getString(Constant.U_O_D_D_MIN));// 用户报价点差最小值
                cd.setU_O_D_D_MAX(rec.getString(Constant.U_O_D_D_MAX));// 用户报价点差最大值
                cd.setU_O_D_D_DF(rec.getString(Constant.U_O_D_D_DF));// 用户报价点差默认值
                cd.setP_MIN_H(rec.getString(Constant.P_MIN_H));// 单笔最小可委托数量
                cd.setP_M_H(rec.getString(Constant.P_M_H));// 单笔最大可委托数量
                cd.setSPREAD(rec.getString(Constant.SPREAD));// 商品最小变动单位
                cd.setX_O_B_D_D(rec.getString(Constant.X_O_B_D_D));// 限价建仓买 点差
                cd.setX_O_S_D_D(rec.getString(Constant.X_O_S_D_D));// 限价建仓卖点差
                cd.setB_P_D_D(rec.getString(Constant.B_P_D_D));// 买价点差
                cd.setS_P_D_D(rec.getString(Constant.S_P_D_D));// 卖价点差
                cd.setU_O_D_D_MIN(rec.getString(Constant.U_O_D_D_MIN));// 点差最小值
                cd.setU_O_D_D_MAX(rec.getString(Constant.U_O_D_D_MAX));// 点差最大值18
                cd.setMA_V(rec.getString(Constant.MA_V));// 建仓保证金系数 19
                cd.setCON_U(rec.getString(Constant.CON_U));// 交易单位 20
                cd.setTRADEMODE(rec.getString(Constant.TRADEMODE));// 交易模式 21
                cd.setMIN_HQ_MOVE(rec.getString(Constant.MIN_HQ_MOVE));// 最小报价单位 22
                commodityDatas.add(cd);

            }

            currentTradeEntity.getTradeData().setCommodityDataList(commodityDatas);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }
}
