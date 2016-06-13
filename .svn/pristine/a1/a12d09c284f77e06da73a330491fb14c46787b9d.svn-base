package com.puxtech.weipan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.activity.PanFragmentWeiTuoXiaDan;
import com.puxtech.weipan.data.AllCommodityPriceResponseData;
import com.puxtech.weipan.data.BaseResponseDataPrice;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.network.HttpManagerPrice;
import com.puxtech.weipan.network.Logger;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b>Description:</b>
 * <p>
 * 自动刷新最新价格的service，每隔几秒获取一次最新价格，然后发送广播，需要刷新价格的Activity自行注册广播接收器
 */
public class PriceService extends Service {

    public static final String KEY_ALL_COMMODITY_PRICE = "PriceActivity.KEY_ALL_COMMODITY_PRICE";// 所有商品最新一口价

    HttpManagerPrice priceManager;
    BaseResponseDataPrice responseDataPrice;
    Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        priceManager = new HttpManagerPrice(this);
        responseDataPrice = new BaseResponseDataPrice();
        // 创建定时线程
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    getPriceFromServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 500, 1000);// 暂时每隔1秒取一次
    }

    /**
     * 按盘查询盘口信息
     */
    private void getPriceFromServer() throws Exception {
        MyApplication myApp = (MyApplication) PriceService.this
                .getApplication();
        PriceRuntimeData priceRuntimeData = myApp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        List<MarketInfoEntity> marketList = priceRuntimeData.getMarketList();
        for (MarketInfoEntity market : marketList) {
            List<EnvironmentEntity> envList = market.getEnvList();
            for (EnvironmentEntity env : envList) {
                AllCommodityPriceResponseData responseData = priceManager
                        .getAllCommodityPriceUseSyncMode(env);
                if (responseData.getRetCode() == 0) {
                    ArrayList<PriceEntity> allPriceList = responseData.getPriceEntityList();
                    if(priceRuntimeData!=myApp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()){
                        //如果得到价格后,当前环境改变了,就不发送广播了.
                        return;
                    }
//                    myApp.getMoniTradeEntity().getPriceData().getPriceRuntimeData().setAllPriceList(allPriceList);
//                    myApp.getShipanTradeEntity().getPriceData().getPriceRuntimeData().setAllPriceList(allPriceList);
                    myApp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setAllPriceList(allPriceList);


                    Intent intent = new Intent(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE);
                    intent.putParcelableArrayListExtra(
                            PriceService.KEY_ALL_COMMODITY_PRICE, allPriceList);
                    intent.setPackage(getPackageName());
                    sendBroadcast(intent);
                } else {
                    Logger.e("PriceService错误：" + responseData.getRetCode());
                }

            }
        }


        // }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("priceService#onDestroy");
        // 取消自动刷新
        timer.cancel();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
