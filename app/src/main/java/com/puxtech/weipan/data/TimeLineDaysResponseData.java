package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.data.entitydata.WuDangEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class TimeLineDaysResponseData extends BaseResponseDataTimeLine {

    TimeLineEntity timeLineEntity;

    public void convertTimeLineDays( byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        timeLineEntity = new TimeLineEntity();
        convertBefore(buffer, timeLineEntity);
        int url_count = buffer.getInt();
        byte[] dst;
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < url_count; i++) {
            byte sy = buffer.get();
            dst = new byte[sy];
            buffer.get(dst);
            urls.add(new String(dst));
        }
        timeLineEntity.setZipUrls(urls);

    }




    public TimeLineEntity getTimeLineEntity() {
        return timeLineEntity;
    }

    public void setTimeLineEntity(TimeLineEntity timeLineEntity) {
        this.timeLineEntity = timeLineEntity;
    }
}