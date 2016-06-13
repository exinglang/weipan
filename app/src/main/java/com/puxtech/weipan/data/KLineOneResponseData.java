package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.data.entitydata.KPointEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by mac on 15/11/4.
 */
public class KLineOneResponseData extends BaseResponseDataKLine {

    KLineEntity kLineEntity;

    /**
     * zip的timeline 没有commonReturn
     * @param responseData
     */
    public void convertKLineOne( byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        kLineEntity=new KLineEntity();
        getCommonReturnData(buffer);
        if(getRetCode()!=0){
            return;
        }

        kLineEntity.setMarketNumber(buffer.getShort());
        kLineEntity.setEnvNumber(buffer.getShort());

        kLineEntity.setNumber(buffer.getShort());
        int basePrice = buffer.getInt();
        byte lowestUnit = buffer.get();

        kLineEntity.setCycle(buffer.getShort());
        int recordNum = buffer.getInt();
        kLineEntity.setRecordNum(recordNum);
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
        kLineEntity.setRecordList(recordList);

    }

    public KLineEntity getkLineEntity() {
        return kLineEntity;
    }
}