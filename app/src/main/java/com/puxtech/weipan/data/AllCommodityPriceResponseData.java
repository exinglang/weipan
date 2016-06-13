package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.WuDangEntity;
import com.puxtech.weipan.network.Logger;
import com.puxtech.weipan.util.ActivityUtils;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class AllCommodityPriceResponseData extends BaseResponseDataPrice {


    ArrayList<PriceEntity> priceEntityList;

    public void convertAllCommodityPrice(MyApplication myapp,byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);

        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }

        priceEntityList = new ArrayList<>();
        short marketId = buffer.getShort();
        short envId = buffer.getShort();
		/*
		 * commodityType 1 - 现货商品 2 - 现货连续商品 3 - 期货商品 4 - 股指类型 5 - 股票类型
		 */
        buffer.get();// 盘下商品类型
        int size = buffer.getShort();// 商品数量
        for (int i = 0; i < size; i++) {
            PriceEntity priceEntity = new PriceEntity();
           // priceEntity.setZuId(zuId);
            priceEntity.setMarketNumber(marketId);
            priceEntity.setEnvNumber(envId);
            priceEntity.setNumber(buffer.getShort());// 商品编号
            // Log.i("doit商品编号：", entity.getNumber() + "");
            entitySetCode(myapp, priceEntity, marketId, envId);
            byte commodityType = buffer.get();
            priceEntity.setCommodityType(commodityType);
            // Log.i("doit商品类型：", commodityType + "");

            int basePrice = buffer.getInt();// 基准价
            // Log.i("doit基准价：", basePrice + "");
            byte lowestUnit = buffer.get();// 最小变价单位
            // Log.i("doit最小变动单位：：", lowestUnit + "");
            short pankouLength = buffer.getShort();// 盘口长度
            // Log.i("doit盘口长度：", pankouLength + "");
            if (pankouLength > 0) {
                priceEntity.setPankou_index(buffer.getInt());

                priceEntity.setPrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));//最新价
                priceEntity.setBuy(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));
                priceEntity.setSale(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));
                priceEntity.setHigh(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));
                priceEntity.setLow(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));
                priceEntity.setYesterdayClosePrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));//昨收
                priceEntity.setYesterdayJieSuanPrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));//昨结算价
                priceEntity.setTodayOpenPrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer), basePrice, lowestUnit));//开盘价

                priceEntity.setPriceTime(buffer.getLong());
                priceEntity.setBuyguaPaiNumber(buffer.getInt()+"");
                priceEntity.setSellguaPaiNumber(buffer.getInt()+"");//挂牌数量

                int dataLength = buffer.getInt();
                // Log.i("doit扩充数据长度：",dataLength + ""); ;
                if (dataLength == 0) {
                    priceEntityList.add(priceEntity);
                    continue;
                }
                parseCommodityType(buffer, commodityType, priceEntity, basePrice,lowestUnit);

                priceEntityList.add(priceEntity);
            } else {
                priceEntity.setBuyguaPaiNumber("0");
                priceEntity.setSellguaPaiNumber("0");
                priceEntityList.add(priceEntity);

            }
        }


    }
    // 为此实体类设置商品代码
    private void entitySetCode(MyApplication myapp, PriceEntity entity,
                               int marketId, int envId) {

        List<MarketInfoEntity> marketList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getMarketList();

        for (int j = 0; j < marketList.size(); j++) {
            MarketInfoEntity market = marketList.get(j);

            if (market.getId() == marketId) {
                List<EnvironmentEntity> envList = market.getEnvList();
                for (int k = 0; k < envList.size(); k++) {
                    EnvironmentEntity env = envList.get(k);
                    if (env.getId() == envId) {

                        List<PriceCommodityEntity> comList = myapp
                                .getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getMarketList().get(j)
                                .getEnvList().get(k).getCommodityList();
                        for (PriceCommodityEntity com : comList) {
                            if (com.getNumber() == entity.getNumber()) {

                                //                        for (int i = 0; i < allPriceList.size(); i++) {
//                            PriceEntity item = allPriceList.get(i);
//                            String commodityName = myApp.getPriceData(zuEntity.getZuId())
//                                    .getNameFromCommodityInfo(
//                                            allPriceList.get(i).getNumber());
//                            item.setName(commodityName);
//                        }
                                entity.setName(com.getName());
                                entity.setCode(com.getCode());
                            }

                        }

                    }

                }

            }

        }

    }

    private void parseCommodityType(ByteBuffer buffer, byte commodityType,
                                    PriceEntity entity, int basePrice, byte lowestUnit) {
        if (commodityType == 1) {


        } else if (commodityType == 2) {

//			  现货连续商品
            entity.setDealCount(buffer.getInt());
            entity.setAmount(buffer.getDouble());
            entity.setVolume(buffer.getInt());
            entity.setBuyNumber(buffer.getInt());
            entity.setSaleNumber(buffer.getInt());
            entity.setHoldNumber(buffer.getInt());
            entity.setCangCha(buffer.getInt());
            entity.setJieSuanJia(buffer.getShort());
        } else if (commodityType == 3) {
            // 期货商品
            entity.setDealCount(buffer.getInt());
            entity.setAmount(buffer.getDouble());
            entity.setVolume(buffer.getInt());
            entity.setBuyNumber(buffer.getInt());
            entity.setSaleNumber(buffer.getInt());
            entity.setHoldNumber(buffer.getInt());
            entity.setCangCha(buffer.getInt());
            entity.setJieSuanJia(buffer.getShort());
        } else if (commodityType == 6) {
            // // 股脂类型
            // entity.setDealCount(buffer.getInt());
            // entity.setAmount(buffer.getDouble());

        } else if (commodityType == 5) {
            // 股票类型
            // 时间问题,未写完
            entity.setDealCount(buffer.getInt());
            entity.setAmount(buffer.getDouble());
            entity.setVolume(buffer.getInt());
            entity.setBuyNumber(buffer.getInt());
            entity.setSaleNumber(buffer.getInt());
            entity.setNeiPan(buffer.getLong());
            entity.setWaiPan(buffer.getLong());

        } else if (commodityType == 4) {

            // 当日结算价 short
            // 涨跌幅 float
            // 建仓量 int
            // 平仓量 int
            // 总持仓量 int
            // 仓差 int
            // 现量 int
            // 外盘 int
            // 内盘 int
            // 五档数据长度 short
            // 五档序号 int
            entity.setTodayJieSuanJia(getChaZhi(buffer));
            // Log.i("doit当然结算价：", entity.getTodayJieSuanJia() + "");
            entity.setZhangDieFu(buffer.getFloat());
            // Log.i("doit涨跌：", entity.getZhangDieFu() + "");
            entity.setBuildNumber(buffer.getInt());
            // Log.i("doit建仓量：", entity.getBuildNumber() + "");
            entity.setDropNumber(buffer.getInt());
            // Log.i("doit平仓量：", entity.getDropNumber() + "");
            entity.setHoldNumber(buffer.getInt());
            // Log.i("doit总持仓量：", entity.getHoldNumber() + "");
            entity.setCangCha(buffer.getInt());
            // Log.i("doit仓差：", entity.getCangCha() + "");
            entity.setVolume(buffer.getInt());
            // Log.i("doit现量：", entity.getVolume() + "");
            entity.setWaiPan(buffer.getInt());
            // Log.i("doit外盘：", entity.getWaiPan() + "");
            entity.setNeiPan(buffer.getInt());
            // Log.i("doit内盘：", entity.getNeiPan() + "");
            short wuDangDataLength = buffer.getShort();

            if (wuDangDataLength == 0) {
                ArrayList<WuDangEntity> wuDangList = new ArrayList<WuDangEntity>();
                entity.setWuDangList(wuDangList);
                return;
            }

            // 挂牌
            ArrayList<WuDangEntity> wuDangList = new ArrayList<WuDangEntity>();
            entity.setWuDangId(buffer.getInt());
            // Log.i("doit五档序号：", entity.getWuDangId() + "");
            byte buyNumber = buffer.get();
            for (int j = 0; j < buyNumber; j++) {
                WuDangEntity wudang = new WuDangEntity();
                wudang.setIfBuy(true);

                wudang.setPrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer),
                        basePrice, lowestUnit));
                // Log.i("doit买价：", wudang.getPrice() + "");
                wudang.setGuaPaiNumber(buffer.getInt());
                // Log.i("doit挂牌数：", wudang.getGuaPaiNumber() + "");
                wudang.setZhaiPaiNumber(buffer.getInt());
                // Log.i("doit摘牌数：", wudang.getZhaiPaiNumber() + "");
                wuDangList.add(wudang);
            }

            byte sellNumber = buffer.get();
            for (int j = 0; j < sellNumber; j++) {
                WuDangEntity wudang = new WuDangEntity();
                wudang.setIfBuy(false);

                wudang.setPrice(ActivityUtils.changeDoubleForPrice(getChaZhi(buffer),
                        basePrice, lowestUnit));
                // Log.i("doit卖价：", wudang.getPrice() + "");
                wudang.setGuaPaiNumber(buffer.getInt());
                // Log.i("doit挂牌数：", wudang.getGuaPaiNumber() + "");
                wudang.setZhaiPaiNumber(buffer.getInt());
                // Log.i("doit摘牌数：", wudang.getZhaiPaiNumber() + "");
                wuDangList.add(wudang);
            }
            entity.setWuDangList(wuDangList);
        }
    }


    public ArrayList<PriceEntity> getPriceEntityList() {
        return priceEntityList;
    }

    public void setPriceEntityList(ArrayList<PriceEntity> priceEntityList) {
        this.priceEntityList = priceEntityList;
    }
}