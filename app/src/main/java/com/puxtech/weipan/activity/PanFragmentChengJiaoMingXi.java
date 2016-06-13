package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.InterfaceRecycleViewAdapter;
import com.puxtech.weipan.anim.SlideInOutRightItemAnimator;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.DealResponseData;
import com.puxtech.weipan.data.ReportDealResponseData;
import com.puxtech.weipan.data.TradeDealData;
import com.puxtech.weipan.data.TradeReportDealData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.lvchild.ChengJiaoHistoryItemRVChild;
import com.puxtech.weipan.data.lvchild.ChengJiaoJiaZaiGengDuoRVChild;
import com.puxtech.weipan.data.lvchild.ChengJiaoTodayItemRVChild;
import com.puxtech.weipan.data.lvchild.OnlyTitleRVChild;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class PanFragmentChengJiaoMingXi extends BaseFragment {
    public static final int REQUEST_REFRESH_ALL_DEAL_5 = 1;//今日成交和历史成交
    public static final int TODAY_LOADMORE = 2;//今日成交.加载更多
    public static final int HISTORY_LOADMORE = 3;//历史成交.加载更多
    public static final String ITEM_TITLE_TODAY_DEAL = "今日成交";
    public static final String ITEM_TITLE_HISTORY_DEAL = "历史成交";


    private View fView;
    RecyclerView lv_main;
    SwipeRefreshLayout srl_main;
    public int insertTodayDealCursor, insertHistoryDealCursor;
    ArrayList<InterfaceRVEntity> interfaceEntityArrayList;
    InterfaceRecycleViewAdapter adapter;
    buttonCallBack buttonCallBack;
    ArrayList<PriceEntity> allPriceList;
    protected static PanFragmentChengJiaoMingXi shiPanFragmentChengJiaoMingXi;

    public static PanFragmentChengJiaoMingXi getInstance(Context context) {
        shiPanFragmentChengJiaoMingXi = new PanFragmentChengJiaoMingXi(context);
        return shiPanFragmentChengJiaoMingXi;
    }

    public PanFragmentChengJiaoMingXi(Context context) {
        super();
        this.context = context;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.fragment_shipan_public_root_view, null);
        initWdiget();
        initButtonCallBack();
        allPriceList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllPriceList();
        showTradeDealDataList = new ArrayList<>();
        showTradeReportDealDataList = new ArrayList<>();

        initListView();

//        if (myapp.currentTradeEntityIsShiPan == true) {
//
//            if(new TradeHelper(context).hasOpen()) {
//                initListView();
//            }}
//        else {
//            initListView();
//
//
//        }

        //设置刷新时动画的颜色，可以设置4个

        return fView;
    }

    /**
     * 初始化界面.最多各显示五个
     */
    private void initListView() {
        reSetCursor();
        TradeHelper tradeHelper = new TradeHelper(context);
        showTradeDealDataList.clear();
        showTradeReportDealDataList.clear();
        refreshInterfaceEntityList(tradeHelper.getTodayDealDataListFilter(5), tradeHelper.getHistoryDealDataListFilter(5));


//        if (myapp.currentTradeEntityIsShiPan == true) {
//
//            if (new TradeHelper(context).hasOpen()) {
//                refreshInterfaceEntityList(tradeHelper.getTodayDealDataListFilter(5), tradeHelper.getHistoryDealDataListFilter(5));
//            }
//        } else {
//
//
//        }

    }

    List<TradeDealData> showTradeDealDataList;
    List<TradeReportDealData> showTradeReportDealDataList;
    boolean showTodayMore, showHistoryMore;//是否显示加载更多

    /**
     * 重置整个RECYCLEVIEW
     */
    private void refreshInterfaceEntityList(List<TradeDealData> newTradeDealDataList, List<TradeReportDealData> newTradeReportDealDataList) {
        // TradeHelper tradeHelper = new TradeHelper();
        interfaceEntityArrayList = new ArrayList<>();
        //第一行,选择商品的一行
        OnlyTitleRVChild daiChengJiaoJianCangRVChild = new OnlyTitleRVChild(context, ITEM_TITLE_TODAY_DEAL, R.drawable.bookmark);
        interfaceEntityArrayList.add(daiChengJiaoJianCangRVChild);
        //第二部分,当日成交
        // List<TradeDealData> tradeDealDataList = tradeHelper.getTodayDealDataListFilter(context, insertTodayDealCursor);//先进行过滤

        if (newTradeDealDataList != null) {
            showTradeDealDataList.addAll(newTradeDealDataList);
        }
        try {
            for (int i = 0; i < showTradeDealDataList.size(); i++) {
                TradeDealData dealData = showTradeDealDataList.get(i);
//            TradeHelper.setHoldDetailFlp(context, holdDetailData, allPriceList);
                ChengJiaoTodayItemRVChild currentHoldDetailChild = new ChengJiaoTodayItemRVChild(context, dealData);
                interfaceEntityArrayList.add(currentHoldDetailChild);
            }
            //加载更多
            if (newTradeDealDataList != null) {
                ifAddLoadMoreRow(newTradeDealDataList.size(), TODAY_LOADMORE);
            } else if (showTodayMore) {
                ifAddLoadMoreRow(5, TODAY_LOADMORE);
            }
            //第三部分纯文字
            daiChengJiaoJianCangRVChild = new OnlyTitleRVChild(context, ITEM_TITLE_HISTORY_DEAL, R.drawable.user_bookmark);
            interfaceEntityArrayList.add(daiChengJiaoJianCangRVChild);
            //第四部分,历史成交
            //   List<TradeReportDealData> fiveTradeTrustDatas = tradeHelper.getHistoryDealDataListFilter(context, insertHistoryDealCursor);
            if (newTradeReportDealDataList != null) {
                showTradeReportDealDataList.addAll(newTradeReportDealDataList);
            }
            for (TradeReportDealData tradeTrustData : showTradeReportDealDataList) {
                ChengJiaoHistoryItemRVChild trustDetailRVChild = new ChengJiaoHistoryItemRVChild(context, tradeTrustData);
                interfaceEntityArrayList.add(trustDetailRVChild);
            }
            //加载更多
            if (newTradeReportDealDataList != null) {
                ifAddLoadMoreRow(newTradeReportDealDataList.size(), HISTORY_LOADMORE);
            } else if (showHistoryMore) {
                ifAddLoadMoreRow(5, HISTORY_LOADMORE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new InterfaceRecycleViewAdapter(context, interfaceEntityArrayList);
        lv_main.setAdapter(adapter);
    }

    /**
     * 判断是否要添加 加载更多的行
     */
    private void ifAddLoadMoreRow(int size, int type) {
        if (((size % 5) == 0) && (size != 0)) {

            if (type == TODAY_LOADMORE) {
                showTodayMore = true;
            } else {

                showHistoryMore = true;
            }
            //==5说明可能还有更多
            ChengJiaoJiaZaiGengDuoRVChild trustDaiChengJiaoJianCangRVChild = new ChengJiaoJiaZaiGengDuoRVChild(context, buttonCallBack, type);
            interfaceEntityArrayList.add(trustDaiChengJiaoJianCangRVChild);
        } else {
            if (type == TODAY_LOADMORE) {
                showTodayMore = false;
            } else {
                showHistoryMore = false;
            }

        }
    }

    private void initButtonCallBack() {
        buttonCallBack = new buttonCallBack() {
            /**
             * 加载更多
             */
            @Override
            public void holdDetailLoadMore(int type) {
                if (type == TODAY_LOADMORE) {
                    //   insertTodayDealCursor = insertTodayDealCursor + 5;
                    requestData(TODAY_LOADMORE);
                } else {
                    if (type == HISTORY_LOADMORE) {
                        //insertHistoryDealCursor = insertHistoryDealCursor + 5;
                        requestData(HISTORY_LOADMORE);
                    }
                }
            }


        };
    }

    /**
     * 请求
     *
     * @param requestType 请求的类型
     */

    private void requestData(final int requestType) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;

            //            List<TradeDealData> newTradeDealDataList;
//            List<TradeReportDealData> newTradeReportDealDataList;
            protected void onPreExecute() {
                if (requestType == TODAY_LOADMORE || requestType == HISTORY_LOADMORE) {
                    dialog = ActivityUtils.showLoadingProgressDialog(context);
                }
                super.onPreExecute();
            }

            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, myapp.getCurrentTradeEntity());

                    if (requestType == REQUEST_REFRESH_ALL_DEAL_5) {
                        //请求持仓和委托
                        responseData = httpManagerTrade.dealRequest(1, 5);
                        if (responseData.getRetCode() == 0) {
                            // newTradeDealDataList = ((DealResponseData) responseData).dealDataArrayList;
                            myapp.getCurrentTradeEntity().getTradeData().setDealDataList(((DealResponseData) responseData).dealDataArrayList);
                            responseData = httpManagerTrade.reportDealRequest("0", ActivityUtils.getYesterday235959(), 1, 5);
                        }
                        if (responseData.getRetCode() == 0) {

                            //  newTradeReportDealDataList = ((ReportDealResponseData) responseData).dealDataArrayList;
                            myapp.getCurrentTradeEntity().getTradeData().setReportDealDataList(((ReportDealResponseData) responseData).dealDataArrayList);
                        }
                    } else if (requestType == TODAY_LOADMORE) {
                        //只请求今日
                        insertTodayDealCursor = insertTodayDealCursor + 5;

                        responseData = httpManagerTrade.dealRequest(insertTodayDealCursor, 5);
                        //  myapp.getCurrentTradeEntity().getTradeData().addDealDataList(((DealResponseData) responseData).dealDataArrayList);
                    } else if (requestType == HISTORY_LOADMORE) {
                        insertHistoryDealCursor = insertHistoryDealCursor + 5;

                        responseData = httpManagerTrade.reportDealRequest("0", ActivityUtils.getYesterday235959(), insertHistoryDealCursor, 5);
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
                if (requestType == TODAY_LOADMORE || requestType == HISTORY_LOADMORE) {
                    dialog.dismiss();
                }

                if (0 == responseData.getRetCode()) {
//                    reSetCursor();
                    if (requestType == REQUEST_REFRESH_ALL_DEAL_5) {
                        initListView();
                    } else if (requestType == TODAY_LOADMORE) {
                        refreshInterfaceEntityList(((DealResponseData) responseData).dealDataArrayList, null);
                        lv_main.scrollToPosition(interfaceEntityArrayList.size() - showTradeReportDealDataList.size() - 1);
                    } else if (requestType == HISTORY_LOADMORE) {
                        refreshInterfaceEntityList(null, ((ReportDealResponseData) responseData).dealDataArrayList);
                        lv_main.scrollToPosition(interfaceEntityArrayList.size() - 1);
                    }
                } else {
                    Snackbar.make(fView, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Snackbar.LENGTH_LONG)
                            .setAction("重试", new View.OnClickListener() {
                                public void onClick(View v) {
                                    requestData(requestType);
                                }
                            }).show();
                }
                srl_main.setRefreshing(false);
            }
        });
    }


    /**
     * 重置点击加载更多时,要显示的长度
     */
    private void reSetCursor() {

        insertTodayDealCursor = 1;
        insertHistoryDealCursor = 1;
    }


    private void initWdiget() {
        srl_main = (SwipeRefreshLayout) fView.findViewById(R.id.srl_main);
        lv_main = (RecyclerView) fView.findViewById(R.id.lv_main);
        srl_main.setColorSchemeResources(R.color.text_red, R.color.kline_j, R.color.kline_d);
        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                requestData(REQUEST_REFRESH_ALL_DEAL_5);
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
        requestData(REQUEST_REFRESH_ALL_DEAL_5);
    }

    /**
     * 按钮点击的回调接口
     */
    public interface buttonCallBack {
        void holdDetailLoadMore(int type);//平仓
    }


}
