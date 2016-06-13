package com.puxtech.weipan.data;

import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.PriceSecretEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by mac on 15/11/4.
 */
public class EnvTradeTimeResponseData extends BaseResponseDataPrice {
    EnvTradeTime envTradeTime;
    public void convertEnvTradeTime(byte[] responseData) {

        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        getCommonReturnData(buffer);
        if (retCode != 0) {
            return;
        }
        envTradeTime = new EnvTradeTime();
        envTradeTime.setMarket_num(buffer.getShort());
        envTradeTime.setEnv_num(buffer.getShort());
        envTradeTime.setTradeDay(buffer.getLong());
        envTradeTime.setStartTime(buffer.getLong());
        envTradeTime.setEndTime(buffer.getLong());
        envTradeTime.setLastTradeTime(buffer.getLong());

    }

    public EnvTradeTime getEntity() {
        return envTradeTime;
    }

    public void setEntity(EnvTradeTime entity) {
        this.envTradeTime = entity;
    }
}