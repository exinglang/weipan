package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
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
import com.puxtech.weipan.data.HoldDetailData;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class TradeOrderDropActivity extends BaseAppCompatActivity {
    private static final String[] m = {"市价", "指价"};
    //公用
    TextView tv_name, tv_zhangdie, tv_shuliang, tv_price;
    EditText et_shuliang, et_jiacha, et_zhiyin, et_zhisun;
    LinearLayout ll_zhiyin, ll_zhisun, ll_jiacha;
    TextInputLayout til_shuliang, til_zhiyin, til_zhisun;
    Button bt_commit;
    Spinner sp_type;

    PriceBroadCastReceiver priceReceiver;

    boolean isShiJia;//市价或指价
    boolean isZhang;//单据买涨还是买跌(购入还是售出)

    CommodityData commodityData;
    PriceEntity priceEntity;
    TradeHelper tradeHelper;
    PriceRuntimeData priceRuntimeData;
    HoldDetailData holdDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_drop);
        tradeHelper = new TradeHelper(context);
        initWidget();
        initSpinner();
        holdDetailData = (HoldDetailData) getIntent().getExtras().getSerializable(PanFragmentChiCangMingXi.SERIALIZABLE_KEY);

        String commodityCode = holdDetailData.getStuffId();
        isShiJia = true;//默认是市价下单Id
        isZhang = holdDetailData.getBuyOrSell().equals(Constant.BUY) ? true : false;
        priceRuntimeData = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        commodityData = (new TradeHelper(context)).getCommodityDataByCode(commodityCode);
        initWidgetData(holdDetailData);
        priceEntity = priceRuntimeData.getPriceEntityByCode(commodityCode);
        refreshView(priceEntity);
        priceReceiver = new PriceBroadCastReceiver();


    }

    private void initWidgetData(HoldDetailData holdDetailData) {
        tv_name.setText(holdDetailData.getStuffName());
        tv_zhangdie.setText(isZhang ? Constant.DIE : Constant.ZHANG);
        et_shuliang.setText(holdDetailData.getHoldNumber());
        et_shuliang.addTextChangedListener(WidgetHelper.checkIfEtDaYuIntNumber(til_shuliang, Integer.valueOf(holdDetailData.getHoldNumber())));
        et_jiacha.setText(commodityData.getU_O_D_D_DF());



        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDrop();
            }
        });
    }

    private void dianChaAddTextChangedListener() {
        if (isZhang) {
            et_zhiyin.addTextChangedListener(WidgetHelper.setEtDaYuFloatNumber(til_zhiyin, Float.valueOf(String.valueOf(til_zhiyin.getHint()).substring(1,til_zhiyin.getHint().length()))));
            et_zhisun.addTextChangedListener(WidgetHelper.setEtXiaoYuFloatNumber(til_zhisun, Float.valueOf(String.valueOf(til_zhisun.getHint()).substring(1, til_zhisun.getHint().length()))));
        } else {
            et_zhiyin.addTextChangedListener(WidgetHelper.setEtXiaoYuFloatNumber(til_zhiyin, Float.valueOf(String.valueOf(til_zhiyin.getHint()).substring(1, til_zhiyin.getHint().length()))));
            et_zhisun.addTextChangedListener(WidgetHelper.setEtDaYuFloatNumber(til_zhisun, Float.valueOf(String.valueOf(til_zhisun.getHint()).substring(1, til_zhisun.getHint().length()))));


        }
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
     * 根据市价还是指价,设置对应的控件可见
     *
     * @param isShiJia 市价还是指价
     */
    private void setWidgetVisiableFromZhiJiaOrShiJia(boolean isShiJia) {
        if (isShiJia) {
            //显示市价页面
            ll_zhiyin.setVisibility(View.GONE);
            ll_zhisun.setVisibility(View.GONE);
            ll_jiacha.setVisibility(View.VISIBLE);
        } else {
            //显示指价页面
            ll_zhiyin.setVisibility(View.VISIBLE);
            ll_zhisun.setVisibility(View.VISIBLE);
            ll_jiacha.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onResume() {
        registerReceiver(priceReceiver, new IntentFilter(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(priceReceiver);
        super.onDestroy();

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
     * 平仓请求
     */
    private void requestDrop() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;

            protected void onPreExecute() {
                dialog = ActivityUtils.showLoadingProgressDialog(context, "");
                super.onPreExecute();
            }

            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context, myapp.getCurrentTradeEntity());
                    responseData = httpManagerTrade.requestShiJiaTrade(holdDetailData.getStuffId(), holdDetailData.getBuyOrSell().equals(Constant.BUY) ? false : true,
                            et_shuliang.getText().toString(),
                            et_jiacha.getText().toString(),
                            false, "2", holdDetailData.getHoldId(), Double.valueOf(tv_price.getText().toString()));
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
     * 刷新页面的数据
     *
     * @param priceEntity
     */
    private void refreshView(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
        //最小变动价
        String zuiXiaoBianDongJia = new TradeHelper(context).getCommodityZuiXiaoBianDongJia(commodityData.getCode());
        tv_price.setText(isZhang ? priceEntity.getSale() + "" : priceEntity.getBuy() + "");


        til_zhiyin.setHint(isZhang ? updatebuywin(priceEntity)
                : updatesellwin(priceEntity));
        til_zhisun.setHint(isZhang ? updatebuylose(priceEntity)
                : updateselllose(priceEntity));
        dianChaAddTextChangedListener();
    }

    /**
     * 设置哪些控件该显示,与刷新数据
     *
     * @param isShiJia
     */
    private void setWidgetAndRefresh(boolean isShiJia) {
        setWidgetVisiableFromZhiJiaOrShiJia(isShiJia);

        refreshView(priceEntity);
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        sp_type = (Spinner) findViewById(R.id.sp_type);
        tv_zhangdie = (TextView) findViewById(R.id.tv_zhangdie);
        //公共的控件
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_shuliang = (TextView) findViewById(R.id.tv_shuliang);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        til_shuliang = (TextInputLayout) findViewById(R.id.til_shuliang);

        til_zhiyin = (TextInputLayout) findViewById(R.id.til_zhiyin);
        til_zhisun = (TextInputLayout) findViewById(R.id.til_zhisun);
        et_shuliang = (EditText) findViewById(R.id.et_shuliang);
        tv_price = (TextView) findViewById(R.id.tv_price);
        et_jiacha = (EditText) findViewById(R.id.et_jiacha);
        et_zhiyin = (EditText) findViewById(R.id.et_zhiyin);
        et_zhisun = (EditText) findViewById(R.id.et_zhisun);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        ll_zhiyin = (LinearLayout) findViewById(R.id.ll_zhiyin);
        ll_zhisun = (LinearLayout) findViewById(R.id.ll_zhisun);
        ll_jiacha = (LinearLayout) findViewById(R.id.ll_jiacha);

    }

    /**
     * 设置买的止损 公式：止损价格<卖价-止损点差×最小变动单位
     */
    public String updatebuylose(PriceEntity priceEntity) {
//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
//        String price = map.get("sell");
//        double blose;
//        try {
//            blose = Double.parseDouble(price) - Double.parseDouble(stoplp)
//                    * Double.parseDouble(spread);
//        } catch (Exception e) {
//            return "";
//        }
//        return ActivityUtils.changeDouble(String.valueOf(blose), myapp
//                .getNyTrade().getJyData().getProducesDetailMap().get(stuffCode)
//                .get(12));

//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
        String price = priceEntity.getSale()+"";
        double blose;
        try {
            blose = Double.parseDouble(price) - Double.parseDouble(commodityData.getSTOP_L_P())
                    * Double.parseDouble(commodityData.getSPREAD());
        } catch (Exception e) {
            return "";
        }
        return "<"+ActivityUtils.changeDouble(String.valueOf(blose), commodityData.getSPREAD());
    }


    /**
     * 设置卖的止损价格 公式：止损价格>买价+止损点差×最小变动单位
     */
    public String updateselllose(PriceEntity priceEntity) {
//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
//        String price = map.get("buy");
//        double slose;
//        try {
//            slose = Double.parseDouble(price) + Double.parseDouble(stoplp)
//                    * Double.parseDouble(spread);
//        } catch (Exception e) {
//            return "";
//        }
//        return ActivityUtils.changeDouble(String.valueOf(slose), myapp
//                .getNyTrade().getJyData().getProducesDetailMap().get(stuffCode)
//                .get(12));


        String price = priceEntity.getBuy() + "";
        double slose;
        try {
            slose = Double.parseDouble(price) + Double.parseDouble(commodityData.getSTOP_L_P())
                    * Double.parseDouble(commodityData.getSPREAD());
        } catch (Exception e) {
            return "";
        }
        return  ">"+ActivityUtils.changeDouble(String.valueOf(slose), commodityData.getSPREAD());
    }

    /**
     * 设置买的止盈 公式：止盈价格>卖价+止盈点差×最小变动单位
     */
    public String updatebuywin(PriceEntity priceEntity) {
//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
//        String price = map.get("sell");
//        double bwin;
//        try {
//            bwin = Double.parseDouble(price) + Double.parseDouble(stoppp)
//                    * Double.parseDouble(spread);
//        } catch (Exception e) {
//            return "";
//        }
//        return ActivityUtils.changeDouble(String.valueOf(bwin), myapp
//                .getNyTrade().getJyData().getProducesDetailMap().get(stuffCode)
//                .get(12));

        String price = priceEntity.getSale() + "";
        double bwin;
        try {
            bwin = Double.parseDouble(price) + Double.parseDouble(commodityData.getSTOP_P_P())
                    * Double.parseDouble(commodityData.getSPREAD());
        } catch (Exception e) {
            return "";
        }
        return  ">"+ActivityUtils.changeDouble(String.valueOf(bwin), commodityData.getSPREAD());
    }

    /**
     * 设置卖的止盈 止盈价格<买价-止盈点差×最小变动单位式
     */
    public String updatesellwin(PriceEntity priceEntity) {

//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
//
//        String price = map.get("buy");
//
//        double sbuy;
//        try {
//            sbuy = Double.parseDouble(price) - Double.parseDouble(stoppp)
//                    * Double.parseDouble(spread);
//        } catch (Exception e) {
//            return "";
//        }
//        return ActivityUtils.changeDouble(String.valueOf(sbuy), myapp
//                .getNyTrade().getJyData().getProducesDetailMap().get(stuffCode)
//                .get(12));

//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);

        String price =priceEntity.getBuy()+"";

        double sbuy;
        try {
            sbuy = Double.parseDouble(price) - Double.parseDouble(commodityData.getSTOP_P_P())
                    * Double.parseDouble(commodityData.getSPREAD());
        } catch (Exception e) {
            return "";
        }
        return "<"+ActivityUtils.changeDouble(String.valueOf(sbuy),commodityData.getSPREAD());
    }
}
