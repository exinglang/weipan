package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseDataTimeLine extends BaseResponseDataPrice {





    /**
     * 行情v2 5.38 解析前半部分
     *
     * @param buffer
     * @param entity
     * @return
     */
    protected void convertBefore(ByteBuffer buffer,
                               TimeLineEntity entity) {
        entity.setMarketNumber(buffer.getShort());
        entity.setEnvNumber(buffer.getShort());
        entity.setNumber(buffer.getShort());
        int basePrice = buffer.getInt();
        byte lowestUnit = buffer.get();
        entity.setCycle(buffer.getShort());


        int container = 0;

        container = getChaZhi(buffer);

        entity.setTodayOpenPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
        container = getChaZhi(buffer);
        entity.setYesterdayClosePrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));

        container = getChaZhi(buffer);
        entity.setHighestPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
        entity.setHighestTime(buffer.getLong());

        container = getChaZhi(buffer);
        entity.setLowestPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
        entity.setLowestTime(buffer.getLong());

        container = getChaZhi(buffer);// 昨结算价
        entity.setRecordNum(buffer.getInt());
        int days = buffer.getShort();
        entity.setDays(days);

        List<TimePointEntity> pointList = new ArrayList<TimePointEntity>();
        for (int i = 0; i < days; i++) {
            int tradeDate = buffer.getInt();
            int currentDayRecordNum = buffer.getShort();
            for (int j = 0; j < currentDayRecordNum; j++) {
                TimePointEntity point = new TimePointEntity();
                point.setTradeDate(tradeDate);// 自然日
                point.setTime(buffer.getShort());// 分时时间HHmm

                container = getChaZhi(buffer);
                point.setLastPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));// 周期最后一笔报价

                container = getChaZhi(buffer);
                point.setAveragePrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));// 周期内算术平均价

                // int val = (int)(Math.random()*10000+1);
                point.setVolume(buffer.getInt());// 现手,默认为0
                // buffer.getInt();
                // point.setVolume(val);
                point.setPriceId(buffer.getInt());
                pointList.add(point);
            }
        }

        entity.setRecordList(pointList);


    }


}