package com.puxtech.weipan.data;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.myapplication.PriceData;
import com.puxtech.weipan.util.CustomException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mac on 15/11/5.
 */
public class TradeEntity {
    public PriceData priceData;
    private TradeData tradeData;
    private OtherData otherData;
    private boolean isTradeState;//根据心跳信号返回的值,检测是否账号处于可交易状态
    private String tradeUrl, reportUrl, moneyUrl;//交易,报表,出入金.
    private String userId="";
    private int envirNumber, platformNumber;
    private boolean hasLogin;



    public ArrayList<SysInfoData> infoList;//首页需要显示的公告列表
    private ArrayList<HashMap<String, String>> openaccountlist;// 可开户盘的列表
    //	private String communityUrl;// 社区的URL
//	private String imageUrl;// 访问图片的URL

    public TradeEntity(Context context) {
        priceData = new PriceData(context);
        tradeData = new TradeData();
        otherData = new OtherData();

    }


    public ArrayList<SysInfoData> getInfoList() {
        return infoList;
    }

    public void setInfoList(ArrayList<SysInfoData> infoList) {
        this.infoList = infoList;
    }

    public PriceData getPriceData() {
        return priceData;
    }

    public void setPriceData(PriceData priceData) {
        this.priceData = priceData;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getEnvirNumber() {
        return envirNumber;
    }

    public void setEnvirNumber(int envirNumber) {
        this.envirNumber = envirNumber;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    public ArrayList<HashMap<String, String>> getOpenaccountlist() {
        return openaccountlist;
    }

    public void setOpenaccountlist(ArrayList<HashMap<String, String>> openaccountlist) {
        this.openaccountlist = openaccountlist;
    }

    public String getTradeUrl() {
        return tradeUrl;
    }

    public void setTradeUrl(String tradeUrl) {
        this.tradeUrl = tradeUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getMoneyUrl() {
        return moneyUrl;
    }

    public void setMoneyUrl(String moneyUrl) {
        this.moneyUrl = moneyUrl;
    }

    public TradeData getTradeData() {
        return tradeData;
    }

    public void setTradeData(TradeData tradeData) {
        this.tradeData = tradeData;
    }

    public OtherData getOtherData() {
        return otherData;
    }

    public void setOtherData(OtherData otherData) {
        this.otherData = otherData;
    }

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public boolean isTradeState() {
        return isTradeState;
    }

    public void setIsTradeState(boolean isTradeState) {
        this.isTradeState = isTradeState;
    }
}
