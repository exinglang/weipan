package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.network.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class KLineAndZipUrlResponseData extends BaseResponseDataKLine {

    KLineEntity kLineEntity;

    public void convertKLineAndZipUrl( byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        kLineEntity = convertOneKLine(buffer);
        int zipCount = buffer.get();
        List<String> zipUrls = new ArrayList<String>();
        byte[] urlB = null;
        int zipLen = -1;
        for (int i = 0; i < zipCount; i++) {
            zipLen = buffer.getInt();
            urlB = new byte[zipLen];
            buffer.get(urlB);
            String url = new String(urlB);
            Logger.d("--------------------------------------zip url = " + url);
            zipUrls.add(url);
        }
        kLineEntity.setZipURLs(zipUrls);

    }


    public KLineEntity getkLineEntity() {
        return kLineEntity;
    }
}