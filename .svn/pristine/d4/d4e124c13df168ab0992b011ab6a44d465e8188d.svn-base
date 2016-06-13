package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.ContentsServerEnvEntity;
import com.puxtech.weipan.data.entitydata.ThirdPartyLoginEntity;
import com.puxtech.weipan.data.entitydata.TradeAccountEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/5.
 */
public class TradeAccountListResponseData extends BaseResponseDataOpenAccountContract {

//    "trade_account_list": [
//    {
//        "jys_code":"101",
//            "env_code":"101",
//            "trade_account":"102000000000001"
//    },
//    {
//        "jys_code":"101",
//            "env_code":"102",
//            "trade_account":"102000000000002"
//    }
//    ]

    ArrayList<TradeAccountEntity> entityList;
    protected static final String TAG_TRADE_ACCOUNT_LIST = "trade_account_list";
    protected static final String TAG_JYS_CODE = "jys_code";
    protected static final String TAG_ENV_CODE = "env_code";
    protected static final String TAG_TRADE_ACCOUNT = "trade_account";


    public void parseXML(MyApplication myapp, String jsString) {

        try {
            JSONObject root = checkFail(jsString);
            if (retCode != 0) {
                return;
            }
            JSONObject rep = root.getJSONObject(TAG_PUXT)
                    .getJSONObject(TAG_REP_BODY);
            JSONArray array = rep
                    .getJSONArray(TAG_TRADE_ACCOUNT_LIST);
            entityList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                TradeAccountEntity itemData = new TradeAccountEntity();
                itemData.setEnv_code(rec.getString(TAG_ENV_CODE));
                itemData.setJys_code(rec.getString(TAG_JYS_CODE));
                itemData.setTrade_account(rec.getString(TAG_TRADE_ACCOUNT));
                entityList.add(itemData);


                List<ContentsServerEnvEntity> envList = myapp
                        .getContentsServerEntity().getYwSystemEntity().getJysList().get(0).getEnvList();
                for (ContentsServerEnvEntity envEntity : envList) {
                    //1实盘, 2模拟盘,3.会员验证实盘
                    if (envEntity.getCategory() == 1&&(envEntity.getCode()+"").equals(itemData.getEnv_code())) {
                        myapp.getShipanTradeEntity().setUserId(itemData.getTrade_account());
                        //1是实盘
//                        Constant.TRADE_URL_SHIPAN = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
//                        Constant.TRADE_URL_SHIPAN_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
//                        Constant.TRADE_URL_SHIPAN_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
//                        ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setTradeUrl(envEntity.getYwList().get(0).getLlList().get(0).getUrl());
//                        ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setMoneyUrl(envEntity.getYwList().get(2).getLlList().get(0).getUrl());
//                        ((MyApplication) context.getApplicationContext()).getShipanTradeEntity().setReportUrl(envEntity.getYwList().get(1).getLlList().get(0).getUrl());

                    }
                    if (envEntity.getCategory() == 2&&(envEntity.getCode()+"").equals(itemData.getEnv_code())) {
                        myapp.getMoniTradeEntity().setUserId(itemData.getTrade_account());

//                        Constant.TRADE_URL_MONI = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
//                        Constant.TRADE_URL_MONI_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
//                        Constant.TRADE_URL_MONI_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
//                        ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setTradeUrl(envEntity.getYwList().get(0).getLlList().get(0).getUrl());
//                        ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setMoneyUrl(envEntity.getYwList().get(2).getLlList().get(0).getUrl());
//                        ((MyApplication) context.getApplicationContext()).getMoniTradeEntity().setReportUrl(envEntity.getYwList().get(1).getLlList().get(0).getUrl());

                    }

                }

            }
            myapp.getOpenAccountContractEntity().setTradeAccountEntityArrayList(entityList);

        } catch (Exception e) {
            e.printStackTrace();
            parseError();

        }


    }

    public ArrayList<TradeAccountEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<TradeAccountEntity> entityList) {
        this.entityList = entityList;
    }

    public static String getTagTradeAccountList() {
        return TAG_TRADE_ACCOUNT_LIST;
    }

    public static String getTagJysCode() {
        return TAG_JYS_CODE;
    }

    public static String getTagEnvCode() {
        return TAG_ENV_CODE;
    }

    public static String getTagTradeAccount() {
        return TAG_TRADE_ACCOUNT;
    }
}
