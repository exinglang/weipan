package com.puxtech.weipan.data;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.data.entitydata.PriceSecretEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class PriceSercetListResponseData extends BaseResponseDataPrice {
    ArrayList<PriceSecretEntity> priceSec;

    public void convertPriceSercetList(byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        byte secretCount = buffer.get();
        priceSec = new ArrayList<>(
                secretCount);
        PriceSecretEntity pse;
        for (int i = 0; i < secretCount; i++) {
            byte[] biaoshi = new byte[buffer.get()];
            buffer.get(biaoshi);
            byte[] value = new byte[buffer.get()];
            buffer.get(value);
            pse = new PriceSecretEntity();
            pse.setKey(new String(biaoshi));
            pse.setSecret(value);
            priceSec.add(pse);
        }

    }

    public ArrayList<PriceSecretEntity> getPriceSec() {
        return priceSec;
    }

    public void setPriceSec(ArrayList<PriceSecretEntity> priceSec) {
        this.priceSec = priceSec;
    }
}