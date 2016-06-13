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
import com.puxtech.weipan.ResponseData.SysInfoResponseData;
import com.puxtech.weipan.data.AllCommodityPriceResponseData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.CommodityInfoResponseData;
import com.puxtech.weipan.data.ContractChooseItemResponseData;
import com.puxtech.weipan.data.DealResponseData;
import com.puxtech.weipan.data.EnvTradeTimeResponseData;
import com.puxtech.weipan.data.KLineCycleResponseData;
import com.puxtech.weipan.data.MarketInfoResponseData;
import com.puxtech.weipan.data.OpenAccountResponseData;
import com.puxtech.weipan.data.PriceLogonResponseData;
import com.puxtech.weipan.data.ProtocolResponseData;
import com.puxtech.weipan.data.ProvinceListResponseData;
import com.puxtech.weipan.data.ReportDealResponseData;
import com.puxtech.weipan.data.ThirdPartyLoginResponseData;
import com.puxtech.weipan.data.TradeAccountListResponseData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.VirtualAccountResponseData;
import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.MarketInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.TradeAccountEntity;
import com.puxtech.weipan.helper.OtherHelper;
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
public class AutoLogin extends BaseAppCompatActivity {
    String codeId_moni, codeId_shipan;
    public final static int MONI = 0;
    //String tradeAccount = "666000000000022";
    public final static int SHIPAN = 1;
    public final static int RE_AUTO_LOGON = 1230001;
//    String randdom;
//    public static final String TRADE_URL_MONI = "http://123.151.205.140:19918/tradeweb/httpXmlServlet";//
//    public static final String TRADE_URL_SHIPAN = "http://123.151.205.140:19918/tradeweb/httpXmlServlet";//
//
//    public static final String TRADE_URL_MONI_MONEY= "http://123.151.205.140:19918/tradeweb/httpMoneyServlet";//
//    public static final String TRADE_URL_SHIPAN_MONEY = "http://123.151.205.140:19918/tradeweb/httpMoneyServlet";//
//
//    public static final String TRADE_URL_MONI_REPORT = "http://123.151.205.140:19918/tradeweb/httpReportServlet";//
//    public static final String TRADE_URL_SHIPAN_REPORT = "http://123.151.205.140:19918/tradeweb/httpReportServlet";//


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static final String PRICE_URL = "http://101.251.203.213:8180/quotation_query/query.do";//
//    public static final String TRADE_URL = "http://101.251.203.214:32000/tradeweb/httpXmlServlet";//
//    public static final String TRADE_REPORT_URL = "http://101.251.203.214:32000/tradeweb/httpReportServlet";//
//    public static final String TRADE_MONEY_URL = "http://101.251.203.214:32000/tradeweb/httpMoneyServlet";//


    //    "http://123.151.205.141:8180/quotation_query/query.do"
//
//    public static final String USER_ID_MONI = "102000000000001";
//    public static final String USER_ID_SHIPAN = "102000000000001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        randdom = ActivityUtils.getRandomId();
        //  Button bt_shipan = (Button) findViewById(R.id.bt_login_shipan);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "登陆", toolbar);
//        myapp.getOpenAccountContractEntity().setOpenUrl(Constant.OPEN_ACCOUNT_URL);
//        myapp.getOpenAccountContractEntity().setWeiPanFrontUrl(Constant.WEIPAN_URL);
        requestLogin();

    }


    private void requestLogin() {

        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            //            Dialog dialog;
            HttpManagerTrade httpManagerTrade;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                dialog = ActivityUtils.showLoadingProgressDialog(context, "正在加载数据...");
            }

            /**
             * @param params
             * @return
             */
            @Override
            protected Boolean doInBackground(Void... params) {
                try {


                    HttpManagerPrice httpManagerPrice = new HttpManagerPrice(context);
                    HttpManagerOpenAccountContract openAccountContract = new HttpManagerOpenAccountContract(context);
                    SharedPreferenceManager spf_WX = new SharedPreferenceManager(context, SharedPreferenceManager.WEI_XIN);



                    responseData = openAccountContract.thirdPartyLoginRequest(spf_WX.getString(context, SharedPreferenceManager.WEI_XIN_UNION_ID, ""));
                    //testUnionid  //未签约
                    //Unionid  //已签约

//                    HttpManagerOpenAccountContract httpManager1 = new HttpManagerOpenAccountContract(context);
//                    responseData = httpManager1.requestSignVirtualBank("666000000000028");
                    if (responseData.getRetCode() != 0) {
                        return false;
                    }

                    //获取绑定的交易账号
                    responseData = openAccountContract.tradeAccountListRequest();
                    if (responseData.getRetCode() != 0) {

                        return false;
                    }

//
                    TradeAccountListResponseData tradeAccountListResponseData = (TradeAccountListResponseData) responseData;
                    if (tradeAccountListResponseData.getEntityList().size() == 0||myapp.getMoniTradeEntity().getUserId().equals("")) {
                        //如果没有绑定账户,需要请求开通模拟盘开户,或者异常情况(有一个账户,但账户配置的jysCode或者envCode与目录服务器配置的不一致)
                        //请求开户
//                        HttpManagerOpenAccountContract httpManager = new HttpManagerOpenAccountContract(context);

//                        responseData = httpManager.requestOpenAccount("0", ActivityUtils.getRandomId(), "0");
//                        String customer_no = null;
//                        if (responseData.getRetCode() == 0) {
//                            customer_no = ((OpenAccountResponseData) responseData).getCustomerNo();
//                            //绑定交易账户
//                            responseData = httpManager.requestBindTrade(false, customer_no);
//                        }
//                        //签约模拟银行
//                        if (responseData.getRetCode() == 0) {
//                            //String customer_no = ((OpenAccountResponseData) responseData).getCustomerNo();
//                            responseData = httpManager.requestSignVirtualBank(customer_no);
//
//                            int c = 1;
//                            while (c <= 5) {
//                                Thread.sleep(2000);
//                                responseData = httpManager.requestSignVirtualBank(customer_no);
//                                if (responseData.getRetCode() == 0) {
//
//                                    break;
//                                }
//                                c = c + 1;
//                            }
//
//
//                        }
                        HttpManagerOpenAccountContract httpManager = new HttpManagerOpenAccountContract(context);
                        String customer_no;
                        responseData = httpManager.requestVirtualAccount();


                        if (responseData.getRetCode() == 0) {
//                            customer_no = ((VirtualAccountResponseData) responseData).getCustomerNo();
//                            //绑定交易账户
//                            responseData = httpManager.requestBindTrade(false, customer_no);
                            responseData.setRetCode(RE_AUTO_LOGON);
                            return false;
                            //   requestLogin();
                        }
                    }
                    //获取轮播图片
                    openAccountContract.getAdPicture();
                    //获取开户信息
                    if (!myapp.getShipanTradeEntity().getUserId().equals("")) {
                        responseData = openAccountContract.requestOpenAccountInfo(myapp.getShipanTradeEntity().getUserId());
                        if (responseData.getRetCode() != 0) {
                            return false;
                        }
                    }
                    //for (int i = 0; i < tradeAccountListResponseData.getEntityList().size(); i++) {
                    for (int i = 0; i < tradeAccountListResponseData.getEntityList().size(); i++) {


                        if (i == MONI) {

                            myapp.setMoniTradeEntity();
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_MONI);
                            httpManagerTrade = new HttpManagerTrade(context, myapp.getMoniTradeEntity());
//                            myapp.getCurrentTradeEntity().setTradeUrl(Constant.TRADE_URL_MONI);
//                            myapp.getCurrentTradeEntity().setReportUrl(Constant.TRADE_URL_MONI_REPORT);
//                            myapp.getCurrentTradeEntity().setMoneyUrl(Constant.TRADE_URL_MONI_MONEY);
//                            myapp.getCurrentTradeEntity().setUserId(tradeAccountListResponseData.getEntityList().get(i).getTrade_account());
                            myapp.getCurrentTradeEntity().setUserId(myapp.getMoniTradeEntity().getUserId());


                            myapp.getCurrentTradeEntity().setHasLogin(true);
                        } else {

                            myapp.setShipanTradeEntity();
                            httpManagerTrade = new HttpManagerTrade(context, myapp.getShipanTradeEntity());
//                            myapp.getCurrentTradeEntity().setTradeUrl(Constant.TRADE_URL_SHIPAN);
//                            myapp.getCurrentTradeEntity().setReportUrl(Constant.TRADE_URL_SHIPAN_REPORT);
//                            myapp.getCurrentTradeEntity().setMoneyUrl(Constant.TRADE_URL_SHIPAN_MONEY);
                            //    myapp.getCurrentTradeEntity().setUserId(tradeAccountListResponseData.getEntityList().get(i).getTrade_account());
                            myapp.getCurrentTradeEntity().setUserId(myapp.getShipanTradeEntity().getUserId());
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_SHIPAN);
                            myapp.getCurrentTradeEntity().setHasLogin(true);
                            //只有实盘去请求公告
                            responseData = httpManagerTrade.requestSysInfo("1", "5");
                            myapp.getCurrentTradeEntity().setInfoList(((SysInfoResponseData) responseData).dataList);

                        }
//                        myapp.getCurrentTradeEntity().getPriceData().getPriceViewData().setLineViewHeightPortrait(300);
//                        myapp.getCurrentTradeEntity().getPriceData().getPriceViewData().setLineViewWidthPortrait(
//                                ScreenSizeUtil.getScreenWidth(context));
                        responseData = openAccountContract.tradeLogonRequest();
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
                            responseData = httpManagerTrade.dealRequest(1, 5);
                            myapp.getCurrentTradeEntity().getTradeData().setDealDataList(((DealResponseData) responseData).dealDataArrayList);
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.reportDealRequest("0", ActivityUtils.getYesterday235959(), 1, 5);
                            myapp.getCurrentTradeEntity().getTradeData().setReportDealDataList(((ReportDealResponseData) responseData).dealDataArrayList);
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
                            new TradeHelper(context).saveSpf(spf, heartBeatResponseData.getLID(), heartBeatResponseData.getTTC(), heartBeatResponseData.getTLID(), heartBeatResponseData.getTD());
                        }
                        if (responseData.getRetCode() != 0) {
                            return false;
                        }
                    }
                    HttpManagerOpenAccountContract httpManger = new HttpManagerOpenAccountContract(context);

                    if (new TradeHelper(context).hasContract()) {
                        ContractChooseItemResponseData responseData = httpManger.requestProvinceList(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).getProvince_id());
                        myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).setProvince_name(responseData.getChooseItemDataArrayList().get(0).getName());
                        responseData = httpManger.requestBranchInfo(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).getBranch_id());
                        try {
                            //未签约账户,服务器返回其他签约信息不为空的问题.待验证
                            myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).setBranch_name(responseData.getChooseItemDataArrayList().get(0).getName());
                        } catch (Exception e) {

                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            myapp.setMoniTradeEntity();
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_MONI);
                        } else {
                            myapp.setShipanTradeEntity();
                            myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_SHIPAN);
                        }
                        myapp.getCurrentTradeEntity().getPriceData().getPriceViewData().setLineViewHeightPortrait(300);
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
                            ArrayList<PriceCommodityEntity> allCommodityList = new ArrayList<>();
                            List<EnvTradeTime> envTradeTimeList = new ArrayList<>();
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
//                        EnvironmentEntity env = new EnvironmentEntity();
//                        env.setMarketId(2);
//                        env.setId(2);
                        EnvironmentEntity env = priceRuntimeData.getMarketList().get(0).getEnvList().get(0);
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
//                dialog.dismiss();
                if (0 == responseData.getRetCode()) {
                    ActivityUtils.showCenterToast(context, "登陆成功", 2000);
                    startActivity(new Intent(context, TabActivity.class));
                    startService(new Intent(context, PriceService.class));
                    startService(new Intent(context, HeartBeatService.class));
                    finish();
                } else if (responseData.getRetCode() == RE_AUTO_LOGON) {
                    //模拟盘自动签约成功过,重新登陆一遍
                    requestLogin();
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

                    break;
                case 1:// 获取成功

                    break;
            }

        }
    };
}
