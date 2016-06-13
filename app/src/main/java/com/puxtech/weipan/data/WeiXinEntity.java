package com.puxtech.weipan.data;

import android.content.Context;

import com.puxtech.weipan.ResponseData.AccessTokenResponseData;
import com.puxtech.weipan.data.entitydata.WeiXinAccessTokenEntity;
import com.puxtech.weipan.data.entitydata.WeiXinGetUnionID;
import com.puxtech.weipan.myapplication.PriceData;
import com.puxtech.weipan.util.CustomException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mac on 15/11/5.
 */
public class WeiXinEntity {
    WeiXinAccessTokenEntity weiXinAccessTokenEntity;
    WeiXinGetUnionID weiXinGetUnionID;

    public WeiXinAccessTokenEntity getWeiXinAccessTokenEntity() {
        return weiXinAccessTokenEntity;
    }

    public void setWeiXinAccessTokenEntity(WeiXinAccessTokenEntity weiXinAccessTokenEntity) {
        this.weiXinAccessTokenEntity = weiXinAccessTokenEntity;
    }

    public WeiXinGetUnionID getWeiXinGetUnionID() {
        return weiXinGetUnionID;
    }

    public void setWeiXinGetUnionID(WeiXinGetUnionID weiXinGetUnionID) {
        this.weiXinGetUnionID = weiXinGetUnionID;
    }
}
