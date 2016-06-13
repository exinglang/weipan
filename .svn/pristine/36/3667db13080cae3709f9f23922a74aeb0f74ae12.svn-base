package com.puxtech.weipan.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.ResponseData.HeartBeatResponseData;
import com.puxtech.weipan.data.HeartBeatDealData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p/>
 * 自动刷新最新价格的service，根据用户定义时间请求数据，然后发送广播，需要刷新价格的Activity自行注册广播接收器
 */
public class HeartBeatService extends Service {
    public static final String HEART_BEAT = "HEART_BEAT";

    MyApplication myapp;
    Timer timer;
    Context context;

    //firstLogin?1:0
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myapp = (MyApplication) getApplicationContext();
        context = HeartBeatService.this;
        //heartbeatmanager = HeartBeatManager.getInstance(getBaseContext());
        //initLogoutReceiver();
        // 创建定时线程
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                if (myapp.getMoniTradeEntity().isHasLogin()) {
                    requestHeartBeat(myapp.getMoniTradeEntity());
                }
                if (myapp.getShipanTradeEntity().isHasLogin()) {
                    requestHeartBeat(myapp.getShipanTradeEntity());
                }
            }
        };
        timer.schedule(task, 1, 20000);// 暂时每隔15秒取一次
    }


    private void requestHeartBeat(final TradeEntity tradeEntity) {
        AsyncTask<Void, Void, Integer> task = new AsyncTask<Void, Void, Integer>() {
            HeartBeatResponseData heartBeatResponseData;
            SharedPreferenceManager spf = new SharedPreferenceManager(context, HEART_BEAT + tradeEntity.getUserId());
            long broadId = spf.getLong(context, SharedPreferenceManager.BROAD_ID, -1);
            long dealCount = spf.getLong(context, SharedPreferenceManager.DEAL_COUNT, 0);//
            long lastId = spf.getLong(context, SharedPreferenceManager.LAST_ID, -1);
            String td = spf.getString(context, SharedPreferenceManager.TD, "");

            //上面的值已在登录时赋值过.
            @Override
            protected Integer doInBackground(Void... arg0) {
                try {
                    HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, tradeEntity);
                    //登录时已访问过一遍,所以传0即可
                    heartBeatResponseData = httpManagerTrade.heartBeatRequest(tradeEntity, 0, broadId, dealCount, lastId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if (0 == heartBeatResponseData.getRetCode()) {
                    // 判断是否有新的广播
                    if (heartBeatResponseData.getLID() > broadId) {
                        if (tradeEntity == myapp.getShipanTradeEntity()) {
                            Toast.makeText(context, "您有新的公告  请注意查收", Toast.LENGTH_LONG)
                                    .show();
                        }
                        spf.putBoolean(context, SharedPreferenceManager.TRADER_PUBLIC_NEW_INFO, true);

                    }
                    //检测是否有新成交
                    checkNewDeal(heartBeatResponseData);
                    //登录时已请求过TD,此处只需判断交易日是否切换.
                    // 交易日变化的话要退出重新登陆
                    if (!heartBeatResponseData.getTD().equals(td)) {
                        relogon();
                    }
                    //0:等待开市中,1:开市准备中,2:结算中,3:休市中,4:交易暂停中,5:交易中,6:节间休息中,7:准备结算中
                    if (heartBeatResponseData.getSTA() == 0 || heartBeatResponseData.getSTA() == 1 || heartBeatResponseData.getSTA() == 3) {
                        tradeEntity.setIsTradeState(false);
                    } else {
                        tradeEntity.setIsTradeState(true);
                    }
                    //保存下次发送需要的数据
                    new TradeHelper(context).saveSpf(spf, heartBeatResponseData.getLID(), heartBeatResponseData.getTTC(), heartBeatResponseData.getTLID(), heartBeatResponseData.getTD());
                } else {
                    //重新登录
                    relogon();
                }

            }
        };
        task.execute();
    }

    /**
     * 检查是否有新成交
     *
     * @param heartBeatResponseData
     */
    private void checkNewDeal(HeartBeatResponseData heartBeatResponseData) {
        for (int i = 0; i < heartBeatResponseData.getDealrecord(); i++) {
            HeartBeatDealData heartBeatDealData = heartBeatResponseData.getHeartBeatDealDataArrayList().get(i);
            String typ = null;
            if (heartBeatDealData.getTYP() == 1) {
                typ = Constant.JIAN_CANG;
            } else if (heartBeatDealData.getTYP() == 2) {
                typ = Constant.PING_CANG;
            }
            String bs = null;
            if (heartBeatDealData.getBS() == 1) {
                bs = Constant.BUY;
            } else if (heartBeatDealData.getBS() == 2) {
                bs = Constant.SELL;
            }
            String sname = new TradeHelper(context).getCommodityNameByCode(heartBeatDealData.getCMOID());
            Toast.makeText(context, heartBeatDealData.getONO() + "号委托成交" + "\n" + sname + " " + typ + bs + heartBeatDealData.getQT() + "手", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 重新登录
     */
    private void relogon() {
        new TradeHelper(context).reLogon();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        //unregisterReceiver(receiver);
        //tl.onDestroy();

    }

//	@Override
//	public boolean onUnbind(Intent intent) {
//		return super.onUnbind(intent);
//	}
//	class LogoutReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if (intent.getAction().equals(Constant.LOGOFF)) {
//				stopSelf();
//			}
//
//
//		}
//	}

}
