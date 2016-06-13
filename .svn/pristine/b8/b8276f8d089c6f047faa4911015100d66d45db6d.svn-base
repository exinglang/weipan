package com.puxtech.weipan.data.entitydata;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class EnvironmentEntity {

    private int id;// 环境(盘)编号

    private int marketId;// 交易所(盘)编号
    private int mainEnvironmentId;// 主环境(盘)编号
    private int type;// 环境(盘)类型
    private String name;
    private List<PriceCommodityEntity> commodityList;
    private int envJiaoYiType;
    private int envJiaoYiJieType;
    private boolean supportTrade;
    private String env_tag;

    public String getEnv_tag() {
        return env_tag;
    }

    public void setEnv_tag(String env_tag) {
        this.env_tag = env_tag;
    }

    public boolean isSupportTrade() {
        return supportTrade;
    }

    public void setSupportTrade(boolean supportTrade) {
        this.supportTrade = supportTrade;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getEnvJiaoYiType() {
        return envJiaoYiType;
    }

    public void setEnvJiaoYiType(int envJiaoYiType) {
        this.envJiaoYiType = envJiaoYiType;
    }

    public int getEnvJiaoYiJieType() {
        return envJiaoYiJieType;
    }

    public void setEnvJiaoYiJieType(int envJiaoYiJieType) {
        this.envJiaoYiJieType = envJiaoYiJieType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainEnvironmentId() {
        return mainEnvironmentId;
    }

    public void setMainEnvironmentId(int mainEnvironmentId) {
        this.mainEnvironmentId = mainEnvironmentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PriceCommodityEntity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<PriceCommodityEntity> commodityList) {
        this.commodityList = commodityList;
    }

    public void addCommodity(PriceCommodityEntity commodity) {
        if (commodityList == null) {
            commodityList = new ArrayList<PriceCommodityEntity>();
        }
        commodityList.add(commodity);
    }

    @Override
    public String toString() {
        return "EnvironmentEntity [id=" + id + ", mainEnvironmentId="
                + mainEnvironmentId + ", type=" + type + ", commodityList="
                + commodityList + "]";
    }

    public int getCommodityModeFromCode(int commodityNumber) {
        int mode = 0;
        for (PriceCommodityEntity entity : getCommodityList()) {
            if (Integer.valueOf(entity.getCode()) == commodityNumber) {
                return mode;
            }

        }
        return -1;
    }

}
