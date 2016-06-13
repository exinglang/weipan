package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.KLineCycleEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class KLineCycleResponseData extends BaseResponseDataKLine {
    List<KLineCycleEntity> retList;
    public void convertKLineCycle(byte[] responseData) {
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
         retList = new ArrayList<KLineCycleEntity>();
        int count = buffer.get();// 周期数量
        for (int i = 0; i < count; i++) {
            KLineCycleEntity item = new KLineCycleEntity();
            item.setId(buffer.get());// 周期id
            byte[] nameBytes = new byte[buffer.get()];// 周期名称长度
            buffer.get(nameBytes);
            item.setName(new String(nameBytes));
            byte[] markBytes = new byte[buffer.get()];// 周期标识长度
            buffer.get(markBytes);// 周期标识
            item.setMark(new String(markBytes));
            retList.add(item);
        }


    }

    public List<KLineCycleEntity> getRetList() {
        return retList;
    }

    public void setRetList(List<KLineCycleEntity> retList) {
        this.retList = retList;
    }
}
