package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.CommodityData;
import com.puxtech.weipan.data.entitydata.InterfaceRVEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.lvchild.ChiCangCurrentHoldDetailRVChild;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TradeOrderActivity extends BaseAppCompatActivity {
    private static final String[] m = {"市价", "指价"};
    //公用
    TextView tv_name, tv_shuliang, tv_title;
    Spinner sp_type;
    TextView tv_zhijia_dayu, tv_zhijia_xiaoyu;
    EditText et_shuliang;
    Button bt_commit;
    LinearLayout ll_jiagequjian;
    //MODE4
    LinearLayout ll_maizhang_mode_tvet;
    TextView tv_maizhang_mode_tvet_tv;
    EditText et_maizhang_mode_tvet_et;
    //MODE25
    LinearLayout ll_maizhang_mode_tvtv, ll_jiacha_mode_2;
    TextView tv_maizhang_mode_tvtv_tv1;
    TextView tv_maizhang_mode_tvtv_tv2;
    EditText et_jiacha_mode_2;
    PriceBroadCastReceiver priceReceiver;

    boolean isShiJia;//市价还是指价

    CommodityData commodityData;
    PriceEntity priceEntity;
    String trade_buy_sell;//买还是卖 Constant.BUY,Constant.SELL
    TradeHelper tradeHelper;
    PriceRuntimeData priceRuntimeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_order);
        tradeHelper = new TradeHelper(context);
        initWidget();
        initSpinner();
        Intent intent = getIntent();
        trade_buy_sell = intent.getStringExtra(PanFragmentWeiTuoXiaDan.TRADE_TYPE);
        setTextAndBGColorFromDifferentTradeType(trade_buy_sell);
        String commodityCode = intent.getStringExtra(PanFragmentWeiTuoXiaDan.TRANSFER_CODE);
        isShiJia = true;//默认是市价下单Id(intent.getIntExtra(ShiPanFragmentWei
        priceRuntimeData = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        commodityData = (new TradeHelper(context)).getCommodityDataByCode( commodityCode);
        tv_name.setText(commodityData.getName());
        priceEntity = priceRuntimeData.getPriceEntityByCode(commodityCode);
        setWidgetAndRefresh(isShiJia);
        initCommitClick();
        priceReceiver = new PriceBroadCastReceiver();
        registerReceiver(priceReceiver, new IntentFilter(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE));
    }

    private void initCommitClick() {
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTrade();

            }
        });
    }

    /**
     * 初始化市价/指价的SPINNER
     */
    private void initSpinner() {
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, m);
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isShiJia = true;

                } else if (position == 1) {
                    isShiJia = false;
                }
                setWidgetAndRefresh(isShiJia);
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //将adapter 添加到spinner中
    }

    /**
     * 根据商品的方向,设置不同的文字和背景颜色
     *
     * @param trade_type
     */
    private void setTextAndBGColorFromDifferentTradeType(String trade_type) {
        if (trade_type.equals(Constant.BUY)) {//设置买涨的界面
            tv_title.setText("买涨");
            tv_shuliang.setText("买涨数量");
            tv_maizhang_mode_tvet_tv.setText("买涨价格");
            tv_maizhang_mode_tvtv_tv1.setText("买涨价格");
            tv_title.setBackgroundColor(getResources().getColor(R.color.text_red));
            bt_commit.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_function_zhang));
        } else if (trade_type.equals(Constant.SELL)) {
            tv_title.setText("买跌");
            tv_shuliang.setText("买跌数量");
            tv_maizhang_mode_tvet_tv.setText("买跌价格");
            tv_maizhang_mode_tvtv_tv1.setText("买跌价格");
            tv_title.setBackgroundColor(getResources().getColor(R.color.text_green));
            bt_commit.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_function_die));
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(priceReceiver);
    }

    /**
     * 根据市价还是指价,设置对应的控件可见
     *
     * @param isShiJia 市价还是指价
     */
    private void setWidgetVisiableFromhiJiaOrShiJia(boolean isShiJia) {
        if (isShiJia) {
            //显示市价页面
            ll_maizhang_mode_tvtv.setVisibility(View.VISIBLE);
            ll_jiacha_mode_2.setVisibility(View.VISIBLE);
            ll_jiagequjian.setVisibility(View.GONE);
            ll_maizhang_mode_tvet.setVisibility(View.GONE);

        } else {
            //显示指价页面
            ll_jiagequjian.setVisibility(View.VISIBLE);
            ll_maizhang_mode_tvet.setVisibility(View.VISIBLE);
            ll_maizhang_mode_tvtv.setVisibility(View.GONE);
            ll_jiacha_mode_2.setVisibility(View.GONE);
        }
    }


    /**
     * 接受从priceService中传来的所有一口价
     */
    public class PriceBroadCastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {
                ArrayList<PriceEntity> allCommodityPrice = intent
                        .getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
                for (PriceEntity mPriceEntity : allCommodityPrice) {
                    if (mPriceEntity.getNumber() == priceEntity.getNumber()) {
                        refreshView(mPriceEntity);
                    }

                }

            }
        }

    }

    /**
     * 刷新页面的数据
     *
     * @param priceEntity
     */
    private void refreshView(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
        //最小变动价
        String zuiXiaoBianDongJia = new TradeHelper(context).getCommodityZuiXiaoBianDongJia(commodityData.getCode());
        switch (Integer.valueOf(commodityData.getTRADEMODE())) {
            case 4:

                if (trade_buy_sell.equals(Constant.BUY)) {
                    tv_maizhang_mode_tvtv_tv2.setText(ActivityUtils.changeDouble(priceEntity.getBuy() + "", zuiXiaoBianDongJia));

                }
                if (trade_buy_sell.equals(Constant.SELL)) {
                    tv_maizhang_mode_tvtv_tv2.setText(ActivityUtils.changeDouble(priceEntity.getSale() + "", zuiXiaoBianDongJia));

                }
                tv_zhijia_dayu.setText("大于" + tradeHelper.getHighPrice(commodityData, priceEntity));
                tv_zhijia_xiaoyu.setText("小于" + tradeHelper.getLowPrice(commodityData, priceEntity));

                break;
            case 2:
            case 5:
                if (trade_buy_sell.equals(Constant.BUY)) {
                    tv_maizhang_mode_tvtv_tv2.setText(ActivityUtils.changeDouble(priceEntity.getBuy() + "", zuiXiaoBianDongJia));
                }
                if (trade_buy_sell.equals(Constant.SELL)) {
                    tv_maizhang_mode_tvtv_tv2.setText(ActivityUtils.changeDouble(priceEntity.getSale() + "", zuiXiaoBianDongJia));
                }
                tv_zhijia_dayu.setText("大于" + tradeHelper.getHighPrice(commodityData, priceEntity));
                tv_zhijia_xiaoyu.setText("小于" + tradeHelper.getLowPrice(commodityData, priceEntity));

                break;
            default:


        }

    }
    /**
     * 建仓请求
     */
    private void requestTrade() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;

            protected void onPreExecute() {
                dialog = ActivityUtils.showLoadingProgressDialog(context, "");
                super.onPreExecute();
            }

            protected Boolean doInBackground(Void... params) {
                try {
                    if(isShiJia) {
                        //市价
                        HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, myapp.getCurrentTradeEntity());
                        responseData = httpManagerTrade.requestShiJiaTrade(commodityData.getCode(), trade_buy_sell.equals(Constant.BUY) ? true : false, et_shuliang.getText().toString(),
                                et_jiacha_mode_2.getText().toString(), true, "1", "", trade_buy_sell.equals(Constant.BUY) ? priceEntity.getBuy() : priceEntity.getSale());

                    }else{
                        //指价

                        HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, myapp.getCurrentTradeEntity());
                        responseData = httpManagerTrade.requesthiJiaTrade(commodityData.getCode(), trade_buy_sell.equals(Constant.BUY) ? true : false, et_maizhang_mode_tvet_et.getText().toString(),
                                et_shuliang.getText().toString(), "0",
                                "0");
                    }
//                    if (responseData.getRetCode() == 0) {
//                        responseData = httpManagerTrade.trustRequest();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }

            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                dialog.dismiss();
                if (0 == responseData.getRetCode()) {
                    ActivityUtils.showCenterToast(context, Constant.DEAL_SECCESS, Toast.LENGTH_SHORT);
                    setResult(Constant.REQUEST_SUCCESS);
                    finish();


                } else {
                    ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    /**
     * 设置哪些控件该显示,与刷新数据
     *
     * @param isShiJia
     */
    private void setWidgetAndRefresh(boolean isShiJia) {
        setWidgetVisiableFromhiJiaOrShiJia(isShiJia);
        refreshView(priceEntity);

    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        sp_type = (Spinner) findViewById(R.id.sp_type);
        //公共的控件
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_shuliang = (TextView) findViewById(R.id.tv_shuliang);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        tv_zhijia_dayu = (TextView) findViewById(R.id.tv_zhijia_dayu);
        tv_zhijia_xiaoyu = (TextView) findViewById(R.id.tv_zhijia_xiaoyu);
        et_shuliang = (EditText) findViewById(R.id.et_shuliang);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        ll_jiagequjian = (LinearLayout) findViewById(R.id.ll_jiagequjian);
        //MODE4
        ll_maizhang_mode_tvet = (LinearLayout) findViewById(R.id.ll_maizhang_mode_tvet);
        tv_maizhang_mode_tvet_tv = (TextView) findViewById(R.id.tv_maizhang_mode_tvet_tv);
        et_maizhang_mode_tvet_et = (EditText) findViewById(R.id.et_maizhang_mode_tvet_et);
        //MODE25
        ll_maizhang_mode_tvtv = (LinearLayout) findViewById(R.id.ll_maizhang_mode_tvtv);
        ll_jiacha_mode_2 = (LinearLayout) findViewById(R.id.ll_jiacha_mode_2);
        tv_maizhang_mode_tvtv_tv1 = (TextView) findViewById(R.id.tv_maizhang_mode_tvtv_tv1);
        tv_maizhang_mode_tvtv_tv2 = (TextView) findViewById(R.id.tv_maizhang_mode_tvtv_tv2);
        et_jiacha_mode_2 = (EditText) findViewById(R.id.et_jiacha_mode_2);
    }
//    /**
//     * 接受从priceService中传来的所有一口价
//     */
//    public class PriceBroadCastReceiver extends BroadcastReceiver {
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {
//
//
//                if (myapp.currentTradeEntityIsShiPan == true && !new TradeHelper(context).hasOpen()) {
//                    return;
//                }
//                allPriceList = intent
//                        .getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
//                for (InterfaceRVEntity rvEntity : interfaceEntityArrayList) {
//
////                    for (HoldDetailData holdDetailData : showHoldDetailDataList) {
//                    if (rvEntity instanceof ChiCangCurrentHoldDetailRVChild) {
//                        new TradeHelper(context).setHoldDetailFlp(((ChiCangCurrentHoldDetailRVChild) rvEntity).getHoldDetailData());
////                    }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//        }
//    }
}
