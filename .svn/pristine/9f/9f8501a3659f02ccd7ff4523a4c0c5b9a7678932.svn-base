package com.puxtech.weipan;


import android.app.Application;
import android.util.SparseArray;

import com.puxtech.weipan.data.OpenAccountContractEntity;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.WeiXinEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.helper.ThirdPartyAppKeyHelper;
import com.puxtech.weipan.myapplication.PriceData;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.List;

/**
 * Created by mac on 15/11/2.
 */
public class MyApplication extends Application {

    public TradeEntity shipanTradeEntity, moniTradeEntity;
    private WeiXinEntity weiXinEntity;// 储存微信的相关信息
    public static String WX_APP_ID = "";//微信appkey
    public static String WX_APP_SECRET = "";//微信AppSecret
   public  boolean currentTradeEntityIsShiPan;
    public static int recLen = 60;//开户时发送的验证码倒计时.放到此处,防止退出开户页面,验证码重刷
    public static int recLen_contract = 60;//开户时发送的验证码倒计时.放到此处,防止退出开户页面,验证码重刷
    public static int recLen_in = 60;//入金

    private OpenAccountContractEntity openAccountContractEntity;
    private ContentsServerEntity contentsServerEntity;// 目录服务器

    // @Override
    public void onCreate() {
        super.onCreate();
        weiXinEntity = new WeiXinEntity();
        shipanTradeEntity = new TradeEntity(this);
        moniTradeEntity = new TradeEntity(this);
        openAccountContractEntity=new OpenAccountContractEntity();
        //注册微信
        WX_APP_ID = ThirdPartyAppKeyHelper
                .getWeiXinAppId(this);
        WX_APP_SECRET = ThirdPartyAppKeyHelper
                .getWeiXinAppSecret(this);
//        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
//        iwxapi.registerApp(WX_APP_ID);
    }

    public OpenAccountContractEntity getOpenAccountContractEntity() {
        return openAccountContractEntity;
    }

    public void setOpenAccountContractEntity(OpenAccountContractEntity openAccountContractEntity) {
        this.openAccountContractEntity = openAccountContractEntity;
    }

    public TradeEntity getCurrentTradeEntity() {
        if (currentTradeEntityIsShiPan) {

            return shipanTradeEntity;
        } else {
            return moniTradeEntity;
        }

    }

    public TradeEntity getShipanTradeEntity() {
        return shipanTradeEntity;
    }

    public void setShipanTradeEntity() {
        currentTradeEntityIsShiPan = true;
    }
    public TradeEntity getMoniTradeEntity() {
        return moniTradeEntity;
    }

    public void setMoniTradeEntity() {
        currentTradeEntityIsShiPan = false;
    }

    public WeiXinEntity getWeiXinEntity() {
        return weiXinEntity;
    }

    public ContentsServerEntity getContentsServerEntity() {
        return contentsServerEntity;
    }

    public void setContentsServerEntity(ContentsServerEntity contentsServerEntity) {
        this.contentsServerEntity = contentsServerEntity;
    }
}
