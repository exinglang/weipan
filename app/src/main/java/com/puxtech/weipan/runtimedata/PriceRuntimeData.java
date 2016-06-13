package com.puxtech.weipan.runtimedata;

import android.util.SparseArray;

import com.puxtech.weipan.data.ProtocolInfoData;
import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.KLineCycleEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.PriceSecretEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.util.CustomException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/9.
 */
public class PriceRuntimeData {
    private int zuId;//行情服务器组ID
    private byte[] sessionId;
    private short currentCycleId;// 当前商品id

    private ProtocolInfoData protocolInfo;// 服务器协议信息
    //    private ArrayList<CommodityInfoEntity> allCommodityInfo;// 所有商品信息
    private List<KLineCycleEntity> kLineCycleList;// k线周期集合
       private ArrayList<PriceEntity> allPriceList;// 所有最新一口价的集
    private List<MarketInfoEntity> marketList;// 市场信息列表
    ArrayList<PriceCommodityEntity> allCommodityList;
    int CurrentCommodityNumber;
   String CurrentCommodityCode;
    private SparseArray<TimeLineEntity> allTimeLine = new SparseArray<TimeLineEntity>();// 缓存分时图的分时数据，Map类型<商品编号，TimeLineEntity>
    ArrayList<PriceSecretEntity> priceSecretEntityArrayList;
    private List<EnvTradeTime> envTradeTimeList;//启动时获取的交易时间

    public int getCommodityType() {
//        if (allCommodityList != null) {
//            for (int i = 0; i < allCommodityList.size(); i++) {
//                if (allCommodityList.get(i).getNumber() == currentCommodityNumber) {
//                    return allCommodityList.get(i).getJiaoYiLX();
//                }
//            }
//        }
        return 0;
    }//改用按盘查询所有商品的交易类型，盘口中的交易类型有误2015/2/28 以后会升级



    // 根据标识返回解密zip包所需要的密钥
    public byte[] getSecret(String key) throws CustomException {
        if (priceSecretEntityArrayList == null)
            return null;
        for (int i = 0, len = priceSecretEntityArrayList.size(); i < len; i++) {
            if (key.equals(priceSecretEntityArrayList.get(i).getKey())) {
                return priceSecretEntityArrayList.get(i).getSecret();
            }
        }
        throw new CustomException("PriceRunTimeData.getSecret,循环LIST未匹配到KEY,返回null");

    }
    public ArrayList<PriceCommodityEntity> getAllCommodityList() {
        return allCommodityList;
    }

    public void setAllCommodityList(ArrayList<PriceCommodityEntity> allCommodityList) {
        this.allCommodityList = allCommodityList;
    }
    public PriceCommodityEntity getCommodityByNumber(int number) {
        for (int i = 0; i < allCommodityList.size(); i++) {
            if (allCommodityList.get(i).getNumber() == number) {
                return allCommodityList.get(i);
            }
        }
        return null;
    }

    public List<EnvTradeTime> getEnvTradeTimeList() {
        return envTradeTimeList;
    }

    public void setEnvTradeTimeList(List<EnvTradeTime> envTradeTimeList) {
        this.envTradeTimeList = envTradeTimeList;
    }

    public EnvTradeTime getEnvTradeTime(int market_num, int env_num) {
        for (EnvTradeTime e : envTradeTimeList) {
            if (e.getMarket_num() == market_num && env_num == e.getEnv_num()) {
                return e;
            }
        }
        return null;
    }
    public SparseArray<TimeLineEntity> getAllTimeLine() {
        return allTimeLine;
    }

    public void setAllTimeLine(SparseArray<TimeLineEntity> allTimeLine) {
        this.allTimeLine = allTimeLine;
    }

    public short getCurrentCycleId() {
        return currentCycleId;
    }

    public void setCurrentCycleId(short currentCycleId) {
        this.currentCycleId = currentCycleId;
    }

    public List<KLineCycleEntity> getkLineCycleList() {
        return kLineCycleList;
    }

    public void setkLineCycleList(List<KLineCycleEntity> kLineCycleList) {
        this.kLineCycleList = kLineCycleList;
    }

    public int getZuId() {
        return zuId;
    }

    public void setZuId(int zuId) {
        this.zuId = zuId;
    }

    public ProtocolInfoData getProtocolInfo() {
        return protocolInfo;
    }

    public void setProtocolInfo(ProtocolInfoData protocolInfo) {
        this.protocolInfo = protocolInfo;
    }

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }


    public List<MarketInfoEntity> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<MarketInfoEntity> marketList) {
        this.marketList = marketList;
    }

    public ArrayList<PriceEntity> getAllPriceList() {
        return allPriceList;
    }

    public void setAllPriceList(ArrayList<PriceEntity> allPriceList) {
        this.allPriceList = allPriceList;
    }

    public String getCurrentCommodityCode() {
        return CurrentCommodityCode;
    }

    public void setCurrentCommodityCode(String currentCommodityCode) {
        CurrentCommodityCode = currentCommodityCode;
    }

    public PriceEntity getPriceEntityByCode(String code) {

        for(PriceEntity priceEntity:getAllPriceList()){
            if (priceEntity.getCode().equals(code)) {
                return priceEntity;
            }

        }
        new CustomException("PriceRuntimeData.getPriceEntityByCode(String code):未从ArrayList<PriceEntity>匹配到CODE:+"+code+"+对应的商品");
        return null;
    }

    public int getCurrentCommodityNumber() {
        return CurrentCommodityNumber;
    }

    public void setCurrentCommodityNumber(int currentCommodityNumber) {
        CurrentCommodityNumber = currentCommodityNumber;
    }

    public ArrayList<PriceSecretEntity> getPriceSecretEntityArrayList() {
        return priceSecretEntityArrayList;
    }

    public void setPriceSecretEntityArrayList(ArrayList<PriceSecretEntity> priceSecretEntityArrayList) {
        this.priceSecretEntityArrayList = priceSecretEntityArrayList;
    }
}
