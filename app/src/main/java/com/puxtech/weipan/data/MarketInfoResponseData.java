package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.KLineCycleEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class MarketInfoResponseData extends BaseResponseDataPrice {
    List<MarketInfoEntity> marketList;
    public void convertMarketInfo(byte[] responseData) {
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        short marketCount = buffer.getShort();// 市场数量
        marketList = new ArrayList<MarketInfoEntity>();

        for (int i = 0; i < marketCount; i++) {
            MarketInfoEntity market = new MarketInfoEntity();
            market.setId(buffer.getShort());// 市场编号

            byte[] dst = new byte[buffer.get()];// 交易所名称长度
            buffer.get(dst);
            market.setName(new String(dst));// 交易所名称

            short envCount = buffer.getShort();//
            for (int j = 0; j < envCount; j++) {
                EnvironmentEntity env = new EnvironmentEntity();
                short envid = buffer.getShort();
                env.setId(envid);// 环境编号

                short mainid = buffer.getShort();

                env.setMainEnvironmentId(mainid);// 主环境编号

                byte[] name = new byte[buffer.get()];// 交易所名称长度
                buffer.get(name);
                env.setName(new String(name));// 交易所名称
                env.setMarketId(market.getId());
                env.setType(buffer.get());// 环境类型
                env.setEnvJiaoYiType(buffer.get());// 盘交易类型
                env.setEnvJiaoYiJieType(buffer.get());// 盘交易节类型
                env.setSupportTrade(buffer.get()==1?true:false);
                market.addEnvironment(env);
            }
            marketList.add(market);
        }



    }

    public List<MarketInfoEntity> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<MarketInfoEntity> marketList) {
        this.marketList = marketList;
    }
}
