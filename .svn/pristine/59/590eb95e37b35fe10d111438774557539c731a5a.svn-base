package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.data.entitydata.KPointEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class BaseResponseDataKLine extends BaseResponseDataPrice {





    /**
     * 解析K线数据，默认它是单个商品V2
     *
     * @param buffer
     * @return
     */
    public KLineEntity convertOneKLine(ByteBuffer buffer) {
        KLineEntity entity = new KLineEntity();
        entity.setMarketNumber(buffer.getShort());
        entity.setEnvNumber(buffer.getShort());

        entity.setNumber(buffer.getShort());
        int basePrice = buffer.getInt();
        byte lowestUnit = buffer.get();

        entity.setCycle(buffer.getShort());
        int recordNum = buffer.getInt();
        entity.setRecordNum(recordNum);
        ArrayList<KPointEntity> recordList = new ArrayList<KPointEntity>();
        int container=0;

        for (int i = 0; i < recordNum; i++) {
            KPointEntity point = new KPointEntity();
            point.setTime(buffer.getLong());

            container = getChaZhi(buffer);
            point.setOpenPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
            container = getChaZhi(buffer);
            point.setClosePrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
            container = getChaZhi(buffer);
            point.setHighestPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));
            container = getChaZhi(buffer);
            point.setLowestPrice(ActivityUtils.changeDoubleForPrice(container, basePrice, lowestUnit));

            point.setVolume(buffer.getInt());
            point.setPriceId(buffer.getInt());
            recordList.add(point);
        }
        entity.setRecordList(recordList);
        return entity;
    }


}