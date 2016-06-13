package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.TimeLineEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class TimeLineOneZipResponseData extends BaseResponseDataTimeLine {

    TimeLineEntity timeLineEntity;

    /**
     * zip的timeline 没有commonReturn
     * @param responseData
     */
    public void convertTimeLineOneZip( byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        timeLineEntity=new TimeLineEntity();
       // getCommonReturnData(buffer);
        convertBefore(buffer, timeLineEntity);

    }

    public TimeLineEntity getTimeLineEntity() {
        return timeLineEntity;
    }

    public void setTimeLineEntity(TimeLineEntity timeLineEntity) {
        this.timeLineEntity = timeLineEntity;
    }
}