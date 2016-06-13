package com.puxtech.weipan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.ResponseData.HeartBeatResponseData;
import com.puxtech.weipan.data.AllCommodityPriceResponseData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.CommodityInfoResponseData;
import com.puxtech.weipan.data.DealResponseData;
import com.puxtech.weipan.data.EnvTradeTimeResponseData;
import com.puxtech.weipan.data.KLineCycleResponseData;
import com.puxtech.weipan.data.MarketInfoResponseData;
import com.puxtech.weipan.data.OpenAccountResponseData;
import com.puxtech.weipan.data.PriceLogonResponseData;
import com.puxtech.weipan.data.ProtocolResponseData;
import com.puxtech.weipan.data.ReportDealResponseData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.network.HttpManagerPrice;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.network.HttpSender;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.service.HeartBeatService;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.ScreenSizeUtil;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class Login extends BaseAppCompatActivity {
    String codeId_moni, codeId_shipan;
    ImageView img_checkcode_moni, img_checkcode_shipan;
    EditText et_checkcode_moni, et_checkcode_shipan;
    public final static int MONI = 0;
    public final static int SHIPAN = 1;

    public static final String OPEN_ACCOUNT_URL = "http://172.31.100.142:8780/open_account_front/front.do";//


    public static final String USER_ID_MONI = "102000000000001";
    public static final String USER_ID_SHIPAN = "102000000000001";
    int loginNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        //  Button bt_shipan = (Button) findViewById(R.id.bt_login_shipan);
//        Button bt_moni = (Button) findViewById(R.id.bt_login_moni);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "登陆", toolbar);
////        myapp.getOpenAccountContractEntity().setOpenUrl(OPEN_ACCOUNT_URL);
//        Button bt_login = (Button) findViewById(R.id.bt_login);
//        img_checkcode_moni = (ImageView) findViewById(R.id.img_checkcode_moni);
//        et_checkcode_moni = (EditText) findViewById(R.id.et_checkcode_moni);
//        img_checkcode_shipan = (ImageView) findViewById(R.id.img_checkcode_shipan);
//        et_checkcode_shipan = (EditText) findViewById(R.id.et_checkcode_shipan);
//        img_checkcode_moni.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                et_checkcode_moni.setText("");
//                requestCheckCode(MONI);
//            }
//        });
//        img_checkcode_shipan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                et_checkcode_shipan.setText("");
//                requestCheckCode(SHIPAN);
//            }
//        });
//        requestCheckCodeTwo();
//        bt_moni.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                loginNumber = 1;
//                requestLogin();
//            }
//        });
//
//        bt_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginNumber = 2;
//                requestLogin();
//            }
//        });
//        et_checkcode_moni.addTextChangedListener(
//                new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if (s.length() == 4) {
//                            loginNumber = 2;
//                            requestLogin();
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                }
//        );
    }

    private void requestCheckCodeTwo() {
        requestCheckCode(MONI);
        requestCheckCode(SHIPAN);
    }

    private void requestCheckCode(final int type) {
        String url = null;
        if (type == MONI) {

            url = Constant.TRADE_URL_MONI;
        } else {
            url = Constant.TRADE_URL_SHIPAN;

        }
        try {
            final byte[] data = "1.0".getBytes("utf-8");
            final ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putShort((short) 0x04);
            buffer.putShort((short) data.length);
            buffer.put(data);

            final String finalUrl = url;
            new Thread() {
                public void run() {
                    HttpSender httpSender = new HttpSender();
                    byte[] result = null;
                    try {
                        result = httpSender.requestImgBinaryApi9(
                                buffer.array(), finalUrl);
                    } catch (Exception e) {

                        Toast.makeText(Login.this, "获取验证码错误", Toast.LENGTH_SHORT).show();

                    }
                    if (result != null) {
                        handleLoginCode2(true, result, type);
                    } else {
                        // Toast.makeText(Login.this, "获取验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void requestLogin() {

        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;
            String code_MONI, code_SHIPAN;
            HttpManagerTrade httpManagerTrade;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ActivityUtils.showLoadingProgressDialog(context, "正在加载数据...");
                code_MONI = et_checkcode_moni.getText().toString();
                code_SHIPAN= et_checkcode_shipan.getText().toString();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {


//                    HttpManagerOpenAccountContract openAccountContract = new HttpManagerOpenAccountContract(context);
//                    //获取开户信息
//                    responseData = openAccountContract.requestOpenAccountInfo("112312312");
//                   if (responseData.getRetCode()==0)
//                    return false;
                    HttpManagerPrice httpManagerPrice = new HttpManagerPrice(context);
                    for (int i = 0; i < loginNumber; i++) {

                        if (i == MONI) {

                            myapp.setMoniTradeEntity();
                            httpManagerTrade = new HttpManagerTrade(context, myapp.getMoniTradeEntity());
                            myapp.getCurrentTradeEntity().setTradeUrl(Constant.TRADE_URL_MONI);
                            myapp.getCurrentTradeEntity().setReportUrl(Constant.TRADE_URL_MONI_REPORT);
                            myapp.getCurrentTradeEntity().setMoneyUrl(Constant.TRADE_URL_MONI_MONEY);
                            responseData = httpManagerTrade.requestLogon(USER_ID_MONI, "111111", code_MONI, codeId_moni);
                            myapp.getCurrentTradeEntity().setUserId(USER_ID_MONI);
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_MONI);
                            myapp.getCurrentTradeEntity().setHasLogin(true);
                        } else {

                            myapp.setShipanTradeEntity();
                            httpManagerTrade = new HttpManagerTrade(context, myapp.getShipanTradeEntity());
                            myapp.getCurrentTradeEntity().setTradeUrl(Constant.TRADE_URL_SHIPAN);
                            myapp.getCurrentTradeEntity().setReportUrl(Constant.TRADE_URL_SHIPAN_REPORT);
                            myapp.getCurrentTradeEntity().setMoneyUrl(Constant.TRADE_URL_SHIPAN_MONEY);
                            responseData = httpManagerTrade.requestLogon(USER_ID_SHIPAN, "111111", code_SHIPAN, codeId_shipan);
                            myapp.getCurrentTradeEntity().setUserId(USER_ID_SHIPAN);
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_SHIPAN);
                            myapp.getCurrentTradeEntity().setHasLogin(false);
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.commodityRequest("");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.holdDetailRequest(1, "1000", "");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.holdTotalRequest(1, "");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.accountInfoRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.otherFirmRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.trustRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.dealRequest(1,5);
                            myapp.getCurrentTradeEntity().getTradeData().setDealDataList(((DealResponseData)responseData).dealDataArrayList);
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.reportDealRequest("0",ActivityUtils.getYesterday235959(),1,5);
                            myapp.getCurrentTradeEntity().getTradeData().setReportDealDataList(((ReportDealResponseData)responseData).dealDataArrayList);
                        }
                        if (responseData.getRetCode() == 0) {
                            //心跳信号
                            SharedPreferenceManager spf = new SharedPreferenceManager(context, HeartBeatService.HEART_BEAT + myapp.getCurrentTradeEntity().getUserId());
                            long broadId = spf.getLong(context, SharedPreferenceManager.BROAD_ID, -1);
                            long dealCount = spf.getLong(context, SharedPreferenceManager.DEAL_COUNT, 0);//
                            long lastId = spf.getLong(context, SharedPreferenceManager.LAST_ID, -1);
                            // String td = spf.getString(context, SharedPreferenceManager.TD, "");
                            responseData = httpManagerTrade.heartBeatRequest(myapp.getCurrentTradeEntity(), 1, broadId, dealCount, lastId);
                            HeartBeatResponseData heartBeatResponseData = (HeartBeatResponseData) responseData;
                            new TradeHelper(context).saveSpf( spf, heartBeatResponseData.getLID(), heartBeatResponseData.getTTC(), heartBeatResponseData.getTLID(), heartBeatResponseData.getTD());


                        }
                        if (responseData.getRetCode() != 0) {
                            return false;
                        }

                        myapp.getCurrentTradeEntity().getPriceData().getPriceViewData().setLineViewHeightPortrait(400);
                        myapp.getCurrentTradeEntity().getPriceData().getPriceViewData().setLineViewWidthPortrait(
                                ScreenSizeUtil.getScreenWidth(context));
                        // ArrayList<PriceRuntimeData> priceRuntimeDatas = new ArrayList<PriceRuntimeData>();
                        PriceRuntimeData priceRuntimeData = new PriceRuntimeData();

                        //  priceRuntimeDatas.add(mpriceRuntimeData);

                        myapp.getCurrentTradeEntity().getPriceData().setPriceRuntimeData(priceRuntimeData);
                        //  for (PriceRuntimeData priceRuntimeData : myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()) {

                        responseData = httpManagerPrice.protocolRequest();//获取服务器协议
                        priceRuntimeData.setProtocolInfo(((ProtocolResponseData) responseData).getProtocolData());


                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerPrice.priceLogonRequest();//登录协议
                            priceRuntimeData.setSessionId(((PriceLogonResponseData) responseData).getSessionIdByte());

                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerPrice.KLineCycleRequest();// 请求k线周期
                            priceRuntimeData.setkLineCycleList(((KLineCycleResponseData) responseData).getRetList());
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerPrice.marketInfoRequest();// 请求市场信息
                            priceRuntimeData.setMarketList(((MarketInfoResponseData) responseData).getMarketList());

                        }
                        if (responseData.getRetCode() == 0) {
                            ArrayList<PriceCommodityEntity> allCommodityList = new ArrayList<PriceCommodityEntity>();
                            List<EnvTradeTime> envTradeTimeList = new ArrayList<EnvTradeTime>();
                            for (MarketInfoEntity marketInfoEntity : priceRuntimeData.getMarketList()) {

                                for (EnvironmentEntity envEntity : marketInfoEntity.getEnvList()) {

                                    responseData = httpManagerPrice.commodityInfoRequest(envEntity);// 请求商品信息,根据盘ID
                                    // ((CommodityInfoResponseData) responseData).getPriceCommodityEntityList();
                                    //  myapp.getPriceData().getPriceRuntimeDataByZuId(zuId).getMarketList().get);
                                    List<PriceCommodityEntity> commodityList = ((CommodityInfoResponseData) responseData).getPriceCommodityEntityList();
                                    envEntity.setCommodityList(commodityList);
                                    allCommodityList.addAll(commodityList == null ? new ArrayList<PriceCommodityEntity>()
                                            : commodityList);
                                    //请求交易时间
                                    responseData = httpManagerPrice.getEnvTradeTime(envEntity.getMarketId(),
                                            envEntity.getId());
                                    EnvTradeTime envTradeTime = ((EnvTradeTimeResponseData) responseData).getEntity();
                                    if (envTradeTime != null) {
                                        envTradeTimeList.add(envTradeTime);
                                    }
                                }


                            }

                            priceRuntimeData.setAllCommodityList(
                                    allCommodityList);
                            priceRuntimeData.setEnvTradeTimeList(envTradeTimeList);
                        }

                        HttpManagerPrice priceManager = new HttpManagerPrice(context);
                        EnvironmentEntity env = new EnvironmentEntity();
                        env.setMarketId(102);
                        env.setId(102);
                        AllCommodityPriceResponseData responseData = priceManager
                                .getAllCommodityPriceUseSyncMode(env);
                        TradeEntity tradeEntity = myapp.getCurrentTradeEntity();//防止切换盘后,存错商品
                        if (responseData.getRetCode() == 0) {
                            ArrayList<PriceEntity> allPriceList = responseData.getPriceEntityList();
                            tradeEntity.getPriceData().getPriceRuntimeData().setAllPriceList(allPriceList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                dialog.dismiss();
                if (0 == responseData.getRetCode()) {
                    ActivityUtils.showCenterToast(context, "协议返回成功并解析成功", 2000);
                    startActivity(new Intent(context, TabActivity.class));
                    startService(new Intent(context, PriceService.class));
                    startService(new Intent(context, HeartBeatService.class));

                    finish();
                } else {
                    ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", 2000);


                }

            }

        });
    }

    // 处理登录图片验证码
    public void handleLoginCode2(boolean hasResult, byte[] data, int type) {
        if (hasResult) {
            try {
                ByteBuffer buffer = ByteBuffer.wrap(data);

                buffer.order(ByteOrder.LITTLE_ENDIAN);
                buffer.getShort();
                byte[] result = new byte[buffer.getShort()];
                buffer.get(result);// 协议版本
                long rcode = buffer.getLong();// 返回码
                result = new byte[buffer.getShort()];
                buffer.get(result);// 返回消息
                if (rcode != 0) {
                    // hd.sendMessage(hd.obtainMessage(1, null));
                } else {

                    // 图片数据
                    result = new byte[buffer.getInt()];

                    // 添加
                    buffer.get(result);
                    // 生成验证码图片
                    @SuppressWarnings("deprecation")
                    Drawable d = new BitmapDrawable(new ByteArrayInputStream(
                            result));
                    // 验证码id
                    result = new byte[buffer.getShort()];

                    buffer.get(result);
                    try {
                        if (type == MONI) {

                            codeId_moni = new String(result, "utf-8");
                        } else {
                            codeId_shipan = new String(result, "utf-8");


                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // img_checkcode.setImageDrawable((Drawable) obj.);
                    hd.sendMessage(hd.obtainMessage(type, d));
                }
            } catch (Exception e) {
                //   hd.sendMessage(hd.obtainMessage(1, null));

            }
        } else {// 通讯未成功
            //  hd.sendMessage(hd.obtainMessage(1, null));
        }
    }

    public Handler hd = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:// 获取成功
                    img_checkcode_moni.setImageDrawable((Drawable) msg.obj);

                    break;
                case 1:// 获取成功
                    img_checkcode_shipan.setImageDrawable((Drawable) msg.obj);

                    break;
            }

        }
    };
}
