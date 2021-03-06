package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.ResponseData.HeartBeatResponseData;
import com.puxtech.weipan.ResponseData.OtherFirmResponseData;
import com.puxtech.weipan.ResponseData.TradeResponseData;
import com.puxtech.weipan.data.AccountInfoResponseData;
import com.puxtech.weipan.data.BaseResponseDataTrade;
import com.puxtech.weipan.data.CommodityResponseData;
import com.puxtech.weipan.data.DealResponseData;
import com.puxtech.weipan.data.HoldDetailResponseData;
import com.puxtech.weipan.data.HoldTotalResponseData;
import com.puxtech.weipan.data.InOutHistoryResponseData;
import com.puxtech.weipan.data.LogonResponseData;
import com.puxtech.weipan.data.ReportDealResponseData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.TrustResponseData;
import com.puxtech.weipan.util.MD5;

import java.util.HashMap;

/**
 * Created by zuochenyong on 15/11/4.
 */
public class HttpManagerMoney extends HttpManagerTrade {

    public HttpManagerMoney(Context context) {
        super(context);


    }


    /**
     * 修改资金密码
     */
    public BaseResponseDataTrade requestChangeMoneyPassword(String oldPwd, String newPwd) {
        BaseResponseDataTrade responseData = new BaseResponseDataTrade();
        String requestData;
        String rName = "money_modpwd";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("OPWD", oldPwd);
            map.put("NPWD", newPwd);
            // return
            requestData = getRequestData(map, rName);
            outPutRequestLog(requestData);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        return publicCheckFail(rName, requestData, responseData);
    }


    /**
     * 出入金操作
     */
    public BaseResponseDataTrade requestInOut(String type, String amount, String password) {
        BaseResponseDataTrade responseData = new BaseResponseDataTrade();
        String requestData;
        String rName = "money_transfer";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("TYPE", type);// 出入金

            map.put("BANK_ID", "900");
            map.put("AMOUNT", amount);
            map.put("PWD", password);
            requestData = getRequestData(map, rName);
            outPutRequestLog(requestData);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        return publicCheckFail(rName, requestData, responseData);

    }

    /**
     * 入金第三方验证码
     */
    public BaseResponseDataTrade requestInCheckCode() {
        BaseResponseDataTrade responseData = new BaseResponseDataTrade();
        String requestData;
        String rName = "money_valCode";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("FIRMID ", myapp.getShipanTradeEntity().getUserId());// 出入金
// myapp.getShipanTradeEntity().getTradeData().getOtherFirmData().getE_M_ID()
            map.put("MOBILE",myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getPhone());
            map.put("SMS_TYPE", "01");
            requestData = getRequestData(map, rName);
            outPutRequestLog(requestData);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        return publicCheckFail(rName, requestData, responseData);

    }
    /**
     * 第三方入金
     */
    public BaseResponseDataTrade requestIn(String code,String amount) {
        BaseResponseDataTrade responseData = new BaseResponseDataTrade();
        String requestData;
        String rName = "money_quickWithhold";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("FIRMID ", myapp.getShipanTradeEntity().getUserId());// 出入金
// myapp.getShipanTradeEntity().getTradeData().getOtherFirmData().getE_M_ID()
            map.put("AMOUNT",amount);
            map.put("CURRENCY", "CNY");
            map.put("MOBILE", myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getPhone());
            map.put("SMS_CODE", code);
            requestData = getRequestData(map, rName);
            outPutRequestLog(requestData);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        return publicCheckFail(rName, requestData, responseData);

    }
    /**
     * 出入金查询
     */
    public InOutHistoryResponseData requestInOutHistory(String startTime, String endTime, int startNum) {
        InOutHistoryResponseData responseData = new InOutHistoryResponseData();
        String requestData;
        String rName = "money_iom_query";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("ST", startTime);
            map.put("ET", endTime);
            map.put("STARTNUM", startNum+"");
            map.put("RECCNT", "5");
            map.put("SORTFLD", "MID");
            map.put("ISDESC", "1");
            requestData = getRequestData(map, rName);
            outPutRequestLog(requestData);
        } catch (Exception e) {
            createError(responseData, e);
            return responseData;
        }
        HttpSender httpSender = new HttpSender();
        String responseStr;
        try {
            responseStr = httpSender.requestJson(requestData.getBytes(), myapp.getCurrentTradeEntity().getMoneyUrl(), rName);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        responseData.parseXML(responseStr);
        return responseData;

    }

    /**
     * 有的协议只需要判断成功或失败.没有其他信息
     */
    private BaseResponseDataTrade publicCheckFail(String rName, String requestData, BaseResponseDataTrade responseData) {
        HttpSender httpSender = new HttpSender();
        String responseStr;
        try {
            responseStr = httpSender.requestJson(requestData.getBytes(), myapp.getCurrentTradeEntity().getMoneyUrl(), rName);
        } catch (Exception e) {
            networkError(responseData, e);
            return responseData;
        }
        try {
            responseData.checkFail(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
            responseData.parseError();

        }
        return responseData;
    }

}
