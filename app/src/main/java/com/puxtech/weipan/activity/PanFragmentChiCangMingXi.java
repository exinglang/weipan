package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.InterfaceRecycleViewAdapter;
import com.puxtech.weipan.anim.SlideInOutRightItemAnimator;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.HoldDetailData;
import com.puxtech.weipan.data.TradeTrustData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.lvchild.ChiCangCurrentHoldDetailRVChild;
import com.puxtech.weipan.data.lvchild.OnlyTitleRVChild;
import com.puxtech.weipan.data.lvchild.ChiCangSelectCommodityRVChild;
import com.puxtech.weipan.data.lvchild.ChiCangTrustDetailRVChild;
import com.puxtech.weipan.data.lvchild.ChiCangJiaZaiGengDuoRVChild;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class PanFragmentChiCangMingXi extends BaseFragment {
    public static final int CHI_CANG_MING_XI = 1;
    public static final int DAI_CHENG_JIAO = 2;
    public static final int REQUEST_REFRESH_HOLDDETAIL_AND_TRUST = 1;//刷新持仓和指价
    public static final int REQUEST_REFRESH_TRUST = 2;//刷新指价
    public static final int REQUEST_CANCEL_TRUST = 3;//撤销委托
    public static final String SERIALIZABLE_KEY = "serializable_key";
    public static final String DCJJC = "待成交建仓";


    PriceBroadCastReceiver priceReceiver;
    private View fView;
    RecyclerView lv_main;
    SwipeRefreshLayout srl_main;
    public int insertDetailCursor, insertTrustCursor;
    String currentCode = "";
    ArrayList<InterfaceRVEntity> interfaceEntityArrayList;
    InterfaceRecycleViewAdapter adapter;
    buttonCallBack buttonCallBack;
    ArrayList<PriceEntity> allPriceList;
    protected static PanFragmentChiCangMingXi shiPanFragmentChiCangMingXi;

    public static PanFragmentChiCangMingXi getInstance(Context context) {
        shiPanFragmentChiCangMingXi = new PanFragmentChiCangMingXi(context);
        return shiPanFragmentChiCangMingXi;
    }

    public PanFragmentChiCangMingXi(Context context) {
        super();
        this.context = context;

    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.fragment_shipan_public_root_view, null);
        reSetCursor();
        initWdiget();
        initButtonCallBack();
        allPriceList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllPriceList();
        interfaceEntityArrayList = new ArrayList<>();
        adapter = new InterfaceRecycleViewAdapter(context, interfaceEntityArrayList);
        refreshInterfaceEntityList("");
//        if (myapp.currentTradeEntityIsShiPan == true) {
//
//            if (new TradeHelper(context).hasOpen()) {
//                refreshInterfaceEntityList("");
//            } else {
//                //srl_main.removeAllViews();
//                TextView tv = new TextView(context);
//                tv.setText("123123");
//                srl_main.addView(tv,0);
//            }
//        } else {
//            refreshInterfaceEntityList("");
//
//
//        }
        //设置刷新时动画的颜色，可以设置4个

        return fView;
    }

    /**
     * 重置整个RECYCLEVIEW
     */
    private void refreshInterfaceEntityList(String code) {
        TradeHelper tradeHelper = new TradeHelper(context);
        interfaceEntityArrayList.clear();
        ;
        //第一行,选择商品的一行
        ChiCangSelectCommodityRVChild selectCommodityChild = new ChiCangSelectCommodityRVChild(context, PanFragmentChiCangMingXi.this, code);
        interfaceEntityArrayList.add(selectCommodityChild);
        //第二部分,持仓列表
        try {
            List<HoldDetailData> fiveHoldDetailDataList = tradeHelper.getHoldDetailDataListFilter(code, insertDetailCursor);//先进行过滤
            for (int i = 0; i < fiveHoldDetailDataList.size(); i++) {
                HoldDetailData holdDetailData = fiveHoldDetailDataList.get(i);
                new TradeHelper(context).setHoldDetailFlp(holdDetailData);
                ChiCangCurrentHoldDetailRVChild currentHoldDetailChild = new ChiCangCurrentHoldDetailRVChild(context, holdDetailData, buttonCallBack);
                interfaceEntityArrayList.add(currentHoldDetailChild);
            }
            //加载更多
            ifAddLoadMoreRow(fiveHoldDetailDataList.size(), CHI_CANG_MING_XI);
            //第三部分纯文字,待成交建仓
            OnlyTitleRVChild daiChengJiaoJianCangRVChild = new OnlyTitleRVChild(context, DCJJC, R.drawable.yuquan);
            interfaceEntityArrayList.add(daiChengJiaoJianCangRVChild);
            //第四部分,委托建仓
            List<TradeTrustData> fiveTradeTrustDatas = tradeHelper.getTrustDataListFilter(code, insertTrustCursor);
            for (TradeTrustData tradeTrustData : fiveTradeTrustDatas) {
                ChiCangTrustDetailRVChild trustDetailRVChild = new ChiCangTrustDetailRVChild(context, tradeTrustData, buttonCallBack);
                interfaceEntityArrayList.add(trustDetailRVChild);
            }
            //加载更多
            ifAddLoadMoreRow(fiveTradeTrustDatas.size(), DAI_CHENG_JIAO);
        }catch (Exception e){
            e.printStackTrace();;
        }
        adapter = new InterfaceRecycleViewAdapter(context, interfaceEntityArrayList);
        lv_main.setAdapter(adapter);
    }

    /**
     * 判断是否要添加 加载更多的行
     */
    private void ifAddLoadMoreRow(int size, int type) {
        if (((size % 5) == 0) && (size != 0)) {
            //==5说明可能还有更多
            ChiCangJiaZaiGengDuoRVChild trustDaiChengJiaoJianCangRVChild = new ChiCangJiaZaiGengDuoRVChild(context, buttonCallBack, type);
            interfaceEntityArrayList.add(trustDaiChengJiaoJianCangRVChild);
        }
    }

    private void initButtonCallBack() {
        buttonCallBack = new buttonCallBack() {
            /**
             * 平仓操作
             */
            @Override
            public void dropButon(HoldDetailData holdDetailData) {
                Intent intent = new Intent();
                intent.setClass(context, TradeOrderDropActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(SERIALIZABLE_KEY, holdDetailData);
                intent.putExtras(bundle);
                getParentFragment().startActivityForResult(intent, CHI_CANG_MING_XI);
            }

            /**
             * 当前持仓的加载更多
             */
            @Override
            public void holdDetailLoadMore(int type) {
                if (type == CHI_CANG_MING_XI) {
                    insertDetailCursor = insertDetailCursor + 5;
                    refreshInterfaceEntityList(currentCode);
                    ArrayList<TradeTrustData> tradeTrustDatas = myapp.getCurrentTradeEntity().getTradeData().getTrustDataList();
                    lv_main.scrollToPosition(interfaceEntityArrayList.size() - tradeTrustDatas.size() - 1);
                } else {
                    if (type == DAI_CHENG_JIAO) {
                        insertTrustCursor = insertTrustCursor + 5;
                        refreshInterfaceEntityList(currentCode);
                        lv_main.scrollToPosition(interfaceEntityArrayList.size() - 1);
                    }
                }
            }

            public void cancelTrust(final TradeTrustData holdDetailData) {
                ActivityUtils.showAlertWithConfirmText(context, "是否要取消当前委托", "确定", new Runnable() {
                    public void run() {
                        requestData(REQUEST_CANCEL_TRUST, holdDetailData.getOr_n());
                    }
                });
            }
        };
    }

    /**
     * 请求
     *
     * @param requestType 请求的类型
     * @param or_n        委托单号,如果不是撤销委托单的请求,传Null
     */

    private void requestData(final int requestType, final String or_n) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;

            protected void onPreExecute() {
                if (requestType == REQUEST_CANCEL_TRUST) {
                    dialog = ActivityUtils.showLoadingProgressDialog(context);
                }
                super.onPreExecute();
            }

            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, myapp.getCurrentTradeEntity());

                    if (requestType == REQUEST_REFRESH_HOLDDETAIL_AND_TRUST) {
                        //请求持仓和委托
                        responseData = httpManagerTrade.holdDetailRequest(1, "1000", "");
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.trustRequest();
                        }
                    } else if (requestType == REQUEST_REFRESH_TRUST) {
                        //只请求委托
                        responseData = httpManagerTrade.trustRequest();
                    } else if (requestType == REQUEST_CANCEL_TRUST) {
                        //撤销委托单
                        responseData = httpManagerTrade.cancelTrustRequest(or_n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }

            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (requestType == REQUEST_CANCEL_TRUST) {
                    dialog.dismiss();
                }

                if (0 == responseData.getRetCode()) {
                    reSetCursor();
                    currentCode = "";
                    if (requestType == REQUEST_REFRESH_HOLDDETAIL_AND_TRUST) {
                        refreshInterfaceEntityList("");
                        // Snackbar.make(fView, "刷新成功", Snackbar.LENGTH_LONG).show();
                    } else if (requestType == REQUEST_REFRESH_TRUST) {
                        //只请求委托
                        refreshInterfaceEntityList("");
                    } else if (requestType == REQUEST_CANCEL_TRUST) {
                        Snackbar.make(fView, "撤销成功", Snackbar.LENGTH_LONG).show();
                        //撤销委托单后,重新请求
                        requestData(REQUEST_REFRESH_TRUST, null);
                    }
                } else {
                    Snackbar.make(fView, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Snackbar.LENGTH_LONG)
                            .setAction("重试", new View.OnClickListener() {
                                public void onClick(View v) {
                                    requestData(requestType, null);

                                }
                            }).show();
                }
                srl_main.setRefreshing(false);
            }
        });
    }


    /**
     * 根据选择商品,重置RECYCLEVIEW,并可能过滤商品
     */
    public void reSelectCommodity(String code) {
        currentCode = code;
        reSetCursor();
        refreshInterfaceEntityList(currentCode);
    }

    /**
     * 重置点击加载更多时,要显示的长度
     */
    private void reSetCursor() {

        insertDetailCursor = 5;
        insertTrustCursor = 5;
    }

    @Override
    public void onDetach() {
        context.unregisterReceiver(priceReceiver);
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        priceReceiver = new PriceBroadCastReceiver();
        context.registerReceiver(priceReceiver, new IntentFilter(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE));
        super.onAttach(context);
    }

    /**
     * 接受从priceService中传来的所有一口价
     */
    public class PriceBroadCastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {


                if (myapp.currentTradeEntityIsShiPan == true && !new TradeHelper(context).hasOpen()) {
                    return;
                }
                allPriceList = intent
                        .getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
                for (InterfaceRVEntity rvEntity : interfaceEntityArrayList) {

//                    for (HoldDetailData holdDetailData : showHoldDetailDataList) {
                    if (rvEntity instanceof ChiCangCurrentHoldDetailRVChild) {
                        new TradeHelper(context).setHoldDetailFlp(((ChiCangCurrentHoldDetailRVChild) rvEntity).getHoldDetailData());
//                    }
                    }
                }
                adapter.notifyDataSetChanged();

            }

        }
    }


    private void initWdiget() {
        srl_main = (SwipeRefreshLayout) fView.findViewById(R.id.srl_main);
        lv_main = (RecyclerView) fView.findViewById(R.id.lv_main);
        srl_main.setColorSchemeResources(R.color.text_red, R.color.kline_j, R.color.kline_d);
        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                requestData(REQUEST_REFRESH_HOLDDETAIL_AND_TRUST, null);
            }
        });

        lv_main.setLayoutManager(new LinearLayoutManager(context));
        lv_main.setItemAnimator(new SlideInOutRightItemAnimator(lv_main));
        lv_main.setItemViewCacheSize(0);
    }

    /**
     * 建仓或平仓操作后执行刷新数据
     */
    public void onMActivityResult() {
        srl_main.setRefreshing(true);
        requestData(REQUEST_REFRESH_HOLDDETAIL_AND_TRUST, null);
    }

    /**
     * 按钮点击的回调接口
     */
    public interface buttonCallBack {
        void dropButon(HoldDetailData holdDetailData);//平仓

        void holdDetailLoadMore(int type);//平仓

        void cancelTrust(TradeTrustData holdDetailData);//撤销委托单
    }


}
