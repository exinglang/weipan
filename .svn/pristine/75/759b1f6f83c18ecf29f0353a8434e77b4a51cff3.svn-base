package com.puxtech.weipan.data;

import android.text.TextUtils;

import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.network.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class CommodityInfoResponseData extends BaseResponseDataPrice {
    List<PriceCommodityEntity> priceCommodityEntityList;

    public void convertCommodityInfo(byte[] responseData) {
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        priceCommodityEntityList = new ArrayList<PriceCommodityEntity>();
        short marketNumber = buffer.getShort();
        short envNumber = buffer.getShort();

        int commodityCount = buffer.getShort();// 商品数量

        for (int k = 0; k < commodityCount; k++) {

            PriceCommodityEntity entity = new PriceCommodityEntity();
            short number = buffer.getShort();
            entity.setNumber(number);// 商品编号

            byte[] codeBytes = new byte[buffer.get()];// 商品代码长度
            buffer.get(codeBytes);

            entity.setCode(new String(codeBytes));

            byte[] nameBytes = new byte[buffer.get()];// 商品名称长度
            buffer.get(nameBytes);
            entity.setName(new String(nameBytes));

            byte status = buffer.get();// 商品状态
            // 取出byte的第一位
            byte byte1 = (byte) ((status >> 0) & 0x1);
            // 取出byte的第五位
            byte byte5 = (byte) ((status >> 4) & 0x1);
            Logger.e("----------------" + entity.getCode() + ", byte1 = "
                    + byte1 + ", byte5 = " + byte5);
            entity.setStatus(byte1);
            // 关于第五位bit，0：商品代码不区分大小写；1：商品代码区分大小写
            entity.setIgnoreCase(byte5 == 0);
            entity.setSpread(buffer.getInt());

            // 每个商品都加上了商品所在环境和市场
            entity.setMarketNumber(marketNumber);
            entity.setEnvNumber(envNumber);
            entity.setJiaoYiLX(buffer.get());
//            entity.setEnvType(env.getType());
//            entity.setMainEnvNumber(env.getMainEnvironmentId());
            //（是主盘或独立盘 && 盘tag不为空 && 不是实盘 ）的才会在名称后面加盘tag
            //可能不需要使用,左辰勇注释
//            if(env.getMainEnvironmentId()==0 && !TextUtils.isEmpty(env.getEnv_tag()) && env.getType() != 0){
//                entity.setName(entity.getName()+"（"+env.getEnv_tag()+"）");
//            }
//            entity.setZuId(zuId);

            priceCommodityEntityList.add(entity);


        }


    }

    public List<PriceCommodityEntity> getPriceCommodityEntityList() {
        return priceCommodityEntityList;
    }

    public void setPriceCommodityEntityList(List<PriceCommodityEntity> priceCommodityEntityList) {
        this.priceCommodityEntityList = priceCommodityEntityList;
    }
}