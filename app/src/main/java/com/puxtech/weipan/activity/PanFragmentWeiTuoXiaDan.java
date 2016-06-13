package com.puxtech.weipan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.TradeData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.viewHelp.StockChartsView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PanFragmentWeiTuoXiaDan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PanFragmentWeiTuoXiaDan extends BaseFragment {
    //   private OnFragmentInteractionListener mListener;
    public static final String ACTION_ALL_COMMODITY_PRICE = "ACTION_ALL_COMMODITY_PRICE";// 广播接收器的action
    public static final int ZHANG = 1;
    public static final int DIE = 2;

    public static final int MESSAGE_COMMODITY_PRICE = 1;
    public static final String ZU_ID = "ZU_ID";
    public static final String TRANSFER_CODE = "TRANSFER_CODE";//往交易页面跳转时传送的商品代码
    //    public static final String TRADE_MODE = "TRADE_MODE";//往交易页面跳转时传送的商品模式
    public static final String TRADE_TYPE = "TRADE_TYPE";//往交易页面跳转时传送的买卖方向
    PriceEntity mPriceEntity;
    LinearLayout ll_commodity;//选择商品的行
    private View fView;//fragment当前的VIEW
    private String selectedCode;//选择商品的代码
    TextView tv_commodity;//选择的商品名称
    StockChartsView stockCharts;
    PriceBroadCastReceiver priceReceiver;
    private static PanFragmentWeiTuoXiaDan shiPanFragmentWeiTuoXiaDan;
    PriceRuntimeData priceRuntimeData;
    TextView tv_buy, tv_sell, tv_open, tv_zhangdiefu, tv_zhangdiee, tv_zuojie, tv_high, tv_low;//报价版
    Button bt_zhang, bt_die;
//    Boolean isUserVisiable;
    PanFragmentWeiTuoXiaDan(Context context) {
        super();
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //CommodityData firstCommodity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fView = inflater.inflate(R.layout.fragment_shipan_weituoxiadan, null);
        initWidget();
        priceRuntimeData = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        TradeEntity currentEntity = myapp.getCurrentTradeEntity();
        // TradeData tradeData = currentEntity.getTradeData();
        selectedCode = priceRuntimeData.getCurrentCommodityCode();
        if (selectedCode == null) {
            selectedCode = priceRuntimeData.getAllCommodityList().get(0).getCode();
        }
        String firstCode = selectedCode;
        PriceEntity priceEntity = priceRuntimeData.getPriceEntityByCode(firstCode);
        stockCharts = new StockChartsView(context, fView, priceEntity);
        //默认选择第一个商品


//        if(!isUserVisiable) {
//
//            stockCharts.getkLineView().getKLinePortrait().setZOrderOnTop(false);
//        }else{
//            stockCharts.getkLineView().getKLinePortrait().setZOrderOnTop(true);
//
//        }




        refreshDataByCode(new TradeHelper(context).getPriceCommodityEntityDataByCode(selectedCode));
        setCommodityClick();
        setZhangDieClick();
        initReceiver();
        return fView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stockCharts.getTimeLineView().setTimerNullWhenDestory();
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//
//        } else {
//            stockCharts.getkLineView().getKLinePortrait().setVisibility(View.VISIBLE);
//
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();


    }

    /**
     * 设置买涨,买跌的点击事件
     */
    private void setZhangDieClick() {
        bt_zhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hasContract()) {

                    return;
                }
                Intent intent = new Intent(context, TradeOrderActivity.class);
                intent.putExtra(TRANSFER_CODE, selectedCode);
                intent.putExtra(TRADE_TYPE, Constant.BUY);
                startActivityForResult(intent, ZHANG);

            }
        });
        bt_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasContract()) {

                    return;
                }
                Intent intent = new Intent(context, TradeOrderActivity.class);
                intent.putExtra(TRANSFER_CODE, selectedCode);
                intent.putExtra(TRADE_TYPE, Constant.SELL);

                startActivityForResult(intent, DIE);

            }
        });
    }

    /**
     * 根据选择的商品代码,设置需要更新的内容
     *
     * @param priceCommodityEntity 商品代码
     */
    private void refreshDataByCode(PriceCommodityEntity priceCommodityEntity) {

        PriceEntity priceEntity = priceRuntimeData.getPriceEntityByCode(priceCommodityEntity.getCode());
//        int oldCommodityNumber = priceRuntimeData
//                .getCurrentCommodityNumber();

        selectedCode = priceCommodityEntity.getCode();
        tv_commodity.setText(priceCommodityEntity.getName());
        // 查找allPriceList有没有此商品
        mPriceEntity = new TradeHelper(context).getNewPrice(priceEntity);
        // 修改Application中保存的当前商品id
        priceRuntimeData.setCurrentCommodityNumber(mPriceEntity.getNumber());
        priceRuntimeData.setCurrentCommodityCode(selectedCode);
        // 刷新开盘收盘价
        refreshBaoJiaBan(mPriceEntity);

        clearStockCharts();


        if (stockCharts.getViewPager().getCurrentItem() == 0) {
            // 分时
            // 分时图的十字线也要用到收盘价和开盘价
            stockCharts.getTimeLineView().getTimeLineCrossLineView().setYesterdayClosePrice(mPriceEntity.getYesterdayClosePrice());
            stockCharts.getTimeLineView().setPriceEntity(mPriceEntity);
            stockCharts.getTimeLineView().startTimeLine();
        } else {
            // k线
            stockCharts.getkLineView().getKLineData(stockCharts.getkLineView().getCurrentCycleId());
        }
    }

    public void clearStockCharts() {

        stockCharts.clearCrossLine();// 清除十字线
        stockCharts.clearTimeLineView();// 清除分时线
        stockCharts.clearKLineView();// 清除k线
    }

    /**
     * 刷新报价版的八个价格
     *
     * @param priceEntity priceEntity
     */
    private void refreshBaoJiaBan(PriceEntity priceEntity) {
        String zuiXiaoBianDongJia = new TradeHelper(context).getCommodityZuiXiaoBianDongJia(selectedCode);


        tv_buy.setText(ActivityUtils.changeDouble(priceEntity.getBuy() + "", zuiXiaoBianDongJia));
        tv_sell.setText(ActivityUtils.changeDouble(priceEntity.getSale() + "", zuiXiaoBianDongJia));
        tv_high.setText(ActivityUtils.changeDouble(priceEntity.getHigh() + "", zuiXiaoBianDongJia));
        tv_low.setText(ActivityUtils.changeDouble(priceEntity.getLow() + "", zuiXiaoBianDongJia));

        // tv_zhangdiee.setText( ActivityUtils.changeDouble(priceEntity.getBuy() + "", zuiXiaoBianDongJia));
        double zhangDieE = priceEntity.getPrice() - priceEntity.getYesterdayClosePrice();
        tv_zhangdiee.setText(ActivityUtils.changeDouble(zhangDieE + "", zuiXiaoBianDongJia));

        double changePercentage = zhangDieE / priceEntity.getYesterdayClosePrice() * 100;

        tv_zhangdiefu.setText(ActivityUtils.changeDouble(changePercentage + "", zuiXiaoBianDongJia));
        tv_zuojie.setText(ActivityUtils.changeDouble(priceEntity.getYesterdayJieSuanPrice() + "", zuiXiaoBianDongJia));
        tv_open.setText(ActivityUtils.changeDouble(priceEntity.getTodayOpenPrice() + "", zuiXiaoBianDongJia));
    }

    /**
     * 设置商品的点击事件
     */
    private void setCommodityClick() {
        List list = new TradeHelper(context).getPriceCommodityNameList();
        //顶部商品列表的点击事件
        ActivityUtils.setSpinnerAdapter(ll_commodity, list,
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        PriceCommodityEntity priceCommodityEntity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList().get(position);




                            refreshDataByCode(priceCommodityEntity);

                        ((PopupWindow) parent.getTag()).dismiss();

                    }
                });
    }

    private void initWidget() {
        ll_commodity = (LinearLayout) fView.findViewById(R.id.ll_commodity);
        tv_commodity = (TextView) fView.findViewById(R.id.tv_commodity);
        tv_buy = (TextView) fView.findViewById(R.id.tv_buy);
        tv_sell = (TextView) fView.findViewById(R.id.tv_sell);
        tv_open = (TextView) fView.findViewById(R.id.tv_open);
        tv_zhangdiefu = (TextView) fView.findViewById(R.id.tv_zhangdiefu);
        tv_zhangdiee = (TextView) fView.findViewById(R.id.tv_zhangdiee);
        tv_zuojie = (TextView) fView.findViewById(R.id.tv_zuojie);
        tv_high = (TextView) fView.findViewById(R.id.tv_high);
        tv_low = (TextView) fView.findViewById(R.id.tv_low);
        bt_zhang = (Button) fView.findViewById(R.id.bt_zhang);
        bt_die = (Button) fView.findViewById(R.id.bt_die);

    }

    @Override
    public void onResume() {
        super.onResume();

        context.registerReceiver(priceReceiver, new IntentFilter(
                ACTION_ALL_COMMODITY_PRICE));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context.unregisterReceiver(priceReceiver);
    }


    private void initReceiver() {
        priceReceiver = new PriceBroadCastReceiver();
    }

    public class PriceBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {
                ArrayList<PriceEntity> allCommodityPrice = intent
                        .getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
                for (PriceEntity priceEntity : allCommodityPrice) {
                    int priceEntityNumber = priceEntity.getNumber();
                    if (mPriceEntity == null) {
                        return;
                    }
                    int mPriceEntityNumber = mPriceEntity.getNumber();
                    if (priceEntityNumber == mPriceEntityNumber) {
                        refreshBaoJiaBan(priceEntity);

                    }

                }

            }
        }

    }


    /**
     * 判断是否已开户并签约,如果没有,进行提示
     *
     * @return
     */
    private boolean hasContract() {
        TradeHelper tradeHelper = new TradeHelper(context);

        if (myapp.currentTradeEntityIsShiPan == true) {
            if (!tradeHelper.hasOpen()) {
                showSnackNoOpen(((TabPanFragment) getParentFragment()).fView);
                return false;
            } else if (!tradeHelper.hasContract()) {
                showSnackNoContract(((TabPanFragment) getParentFragment()).fView);
                return false;
            } else {

                return true;
            }
        } else {

            if (!tradeHelper.hasMoNiContract()) {
                ActivityUtils.showSnackbar(context, fView, Constant.NO_CONTRACT, Constant.LIJIQIANYUE, new View.OnClickListener() {
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), AutoLogin.class));
                                getActivity().finish();
                            }
                        }
                );
                return false;
            } else {
                return true;
            }
        }
    }

    private void showSnackNoOpen(View v) {
        ActivityUtils.showCenterToast(context,Constant.NO_OPEN,Toast.LENGTH_SHORT);

//        ActivityUtils.showSnackbar(context, v, Constant.NO_OPEN, Constant.LIJIKAIHU, new View.OnClickListener() {
//                    public void onClick(View v) {
//                        gotoTargetPage(TabActivity.fragment_geren, TabHomeFragment.KAI_HU);
//                    }
//                }
//        );
    }

    private void showSnackNoContract(View v) {
       ActivityUtils.showCenterToast(context,Constant.NO_CONTRACT,Toast.LENGTH_SHORT);
//        ActivityUtils.showSnackbar(context, v, Constant.NO_CONTRACT, Constant.LIJIQIANYUE, new View.OnClickListener() {
//                    public void onClick(View v) {
//                        gotoTargetPage(TabActivity.fragment_geren, TabHomeFragment.CONTRACT);
//                    }
//                }
//        );
    }


}
