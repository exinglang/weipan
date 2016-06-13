package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.ResponseData.SysInfoDetailResponseData;
import com.puxtech.weipan.ResponseData.SysInfoResponseData;
import com.puxtech.weipan.adapter.GeRenSysInfoAdapter;
import com.puxtech.weipan.adapter.HorizontalScrollViewAdapter;
import com.puxtech.weipan.data.ADInfo;
import com.puxtech.weipan.data.AccountInfoData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.SysInfoData;
import com.puxtech.weipan.data.entitydata.AdPictureEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.helper.OtherHelper;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.service.PriceService;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.CycleViewPager;
import com.puxtech.weipan.viewHelp.CommodityListHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

public class TabHomeFragment extends BaseFragment {
    Toolbar toolbar;
    Button bt_zhang, bt_die, bt_chongzhi, bt_pincang;
    TextView tv_more, tv_12;
    CommodityListHorizontalScrollView mCommodityView;
    LinearLayout ll_pubinfo;
    RelativeLayout rl_function;
    public static final String CHONG_ZHI = "CHONG_ZHI";
    public static final String PING_CANG = "PING_CANG";
    public static final String HOME = "HOME";

    public static final String KAI_HU = "KAI_HU";
    public static final String CONTRACT = "CONTRACT";

    int commodityClickPosition = 0;//记录商品被点击的位置
//    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
//            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
//            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private View view;
    String selectedCode;//选择的商品代码
    PriceBroadCastReceiver priceReceiver;
    private Context context;
    private static TabHomeFragment homeFragment;
    ArrayList<PriceCommodityEntity> priceCommodityEntityArrayList;
    TradeHelper tradeHelper;

    public static TabHomeFragment getInstance(Context context) {
        if (homeFragment == null) {

            homeFragment = new TabHomeFragment(context);
        }
        return homeFragment;
    }

    private TabHomeFragment(Context context) {
        super();
        this.context = context;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            try {
                view = inflater.inflate(R.layout.fragment_home, container, false);
                tradeHelper = new TradeHelper(context);
                initWidget();

//                toolbar.setTitle("微盘");
//                ((BaseAppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                WidgetHelper.initToolBar((BaseAppCompatActivity) getActivity(), toolbar);
                setHasOptionsMenu(true);
                toolbar.setOnMenuItemClickListener(onMenuItemClick);
                priceReceiver = new PriceBroadCastReceiver();
                priceCommodityEntityArrayList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList();

                initTopADView();
                commodityDataListSetPrice(myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllPriceList());

                initCommodityList();
                setZhangDieClick();

                setKeYongZiJin();


            } catch (InflateException e) {
                e.printStackTrace();
            }

        }

        return view;
    }

    //初始化公告列表
    private void initPublicInfo() {
        ArrayList<SysInfoData> infoDataArrayList = myapp.getCurrentTradeEntity().getInfoList();

        for (int i = 0; i < infoDataArrayList.size(); i++) {
            LayoutInflater flater = LayoutInflater.from(context);
            View view = flater.inflate(R.layout.fragment_home_pubinfo_ll, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_title.setText(infoDataArrayList.get(i).getTITLE());
            tv_time.setText(infoDataArrayList.get(i).getSEND_TIME());
            view.setTag(infoDataArrayList.get(i).getID());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  id= (String) v.getTag();
                    requestSysInfo(id);

                    //setTransferData(Constant.BUY);
                }
            });
            ll_pubinfo.addView(view);

        }


    }

    private void setKeYongZiJin() {
        if (new TradeHelper(context).hasOpen()) {
            AccountInfoData data = new TradeHelper(context).calSaveAccountInfo();
            tv_12.setText(data.getKeyongbaozhenjin());
        }
    }


    private void setZhangDieClick() {
        bt_zhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTransferData(Constant.BUY);

            }
        });
        bt_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTransferData(Constant.SELL);

            }


        });
        bt_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasContract()) {
                    gotoTargetPage(TabActivity.fragment_geren, CHONG_ZHI);

                }
            }
        });
        bt_pincang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasContract()) {

                    gotoTargetPage(TabActivity.fragment_shipan, PING_CANG);

                }

                //setTransferData(Constant.BUY);
            }
        });
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasContract()) {

                    gotoTargetPage(TabActivity.fragment_shipan, HOME);

                }

                //setTransferData(Constant.BUY);
            }
        });
    }


    private void requestSysInfo(final String id) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
                         BaseResponseData responseData;
                         Dialog dialog;
                         HttpManagerTrade httpManager = new HttpManagerTrade(context, myapp.getShipanTradeEntity());

                         protected void onPreExecute() {
                             super.onPreExecute();
                             dialog = ActivityUtils.showLoadingProgressDialog(context, "");
                         }

                         @SuppressWarnings("ResourceType")
                         protected Boolean doInBackground(Void... params) {
                             try {


                                 responseData = httpManager.requestSysInfoDetail(id);


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


                                 SysInfoDetailResponseData sysInfoResponseData = (SysInfoDetailResponseData) responseData;

//                                     Intent intent = new Intent();
//                                     intent.setClass(context, GeRenSysInfoDetailActivity.class);
//                                     Bundle bundle = new Bundle();
//                                     bundle.putSerializable(CONTRACT_CHOOSE_ITEM_DATA,  sysInfoResponseData.dataList);
//                                     intent.putExtras(bundle);
//                                     startActivity(intent);

                                 Intent intent = new Intent();
                                 intent.setClass(context, GeRenSysInfoDetailActivity.class);
                                 Bundle bundle = new Bundle();
                                 bundle.putSerializable(GeRenSysInfoActivity.CONTRACT_CHOOSE_ITEM_DATA, sysInfoResponseData.getDetailData());
                                 intent.putExtras(bundle);
                                 startActivity(intent);

                             } else {
                                 ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_LONG);

                             }

                         }

                     }

        );
    }

    /**
     * 判断是否已开户并签约,如果没有,进行提示
     */
    private boolean hasContract() {
        if (!tradeHelper.hasOpen()) {
            showSnackNoOpen(view);
            return false;
        } else if (!tradeHelper.hasContract()) {
            showSnackNoContract(view);
            return false;
        } else {
            // gotoTargetPage(TabActivity.fragment_shipan,PING_CANG);
            return true;
        }

    }

    private void showSnackNoOpen(View v) {
        ActivityUtils.showSnackbar(context, v, Constant.NO_OPEN, Constant.LIJIKAIHU, new View.OnClickListener() {
                    public void onClick(View v) {
                        gotoTargetPage(TabActivity.fragment_geren, KAI_HU);
                    }
                }
        );
    }

    private void showSnackNoContract(View v) {
        ActivityUtils.showSnackbar(context, v, Constant.NO_CONTRACT, Constant.LIJIQIANYUE, new View.OnClickListener() {
                    public void onClick(View v) {
                        gotoTargetPage(TabActivity.fragment_geren, CONTRACT);
                    }
                }
        );
    }

    /**
     * 跳转至对应页面
     */
    private void gotoTargetPage(int targetTabFragment, String targetAction) {
        Bundle bd = new Bundle();
        bd.putString(Constant.TYPE, targetAction);
        ((TabActivity) getActivity()).setCheck();
        ((TabActivity) getActivity()).changeFragment(targetTabFragment, bd);
    }

    /**
     * 设置要传送给其他fragment的数据
     *
     * @param tradeType 涨或跌
     */
    private void setTransferData(String tradeType) {
        if (hasContract()) {

            Bundle bd = new Bundle();
            Intent intent = new Intent(context, TradeOrderActivity.class);
            bd.putString(PanFragmentWeiTuoXiaDan.TRANSFER_CODE, selectedCode);
            bd.putString(PanFragmentWeiTuoXiaDan.TRADE_TYPE, tradeType);
            intent.putExtras(bd);
            startActivity(intent);
        }
    }

    private void initWidget() {
        mCommodityView = (CommodityListHorizontalScrollView) view.findViewById(R.id.mView);
        rl_function = (RelativeLayout) view.findViewById(R.id.rl_function);
        bt_zhang = (Button) view.findViewById(R.id.bt_zhang);
        bt_chongzhi = (Button) view.findViewById(R.id.bt_chongzhi);
        bt_pincang = (Button) view.findViewById(R.id.bt_pincang);
        bt_die = (Button) view.findViewById(R.id.bt_die);
        tv_more = (TextView) view.findViewById(R.id.tv_more);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tv_12 = (TextView) view.findViewById(R.id.tv_12);
        ll_pubinfo = (LinearLayout) view.findViewById(R.id.ll_pubinfo);
    }

    //初始化商品列表
    private void initCommodityList() {
        setAdapter(-1);
        mCommodityView.setOnItemClickListener(new CommodityListHorizontalScrollView.OnItemClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View view, int position) {
                clearBackground();
                view.setBackgroundColor(getResources().getColor(R.color.common_selected));
                if (position == commodityClickPosition) {
                    if (rl_function.getVisibility() == View.GONE) {
                        rl_function.setVisibility(View.VISIBLE);
                        view.setBackgroundColor(getResources().getColor(R.color.common_selected));
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.white));
                        rl_function.setVisibility(View.GONE);
                    }
                } else {
                    rl_function.setVisibility(View.VISIBLE);
                }
                selectedCode = priceCommodityEntityArrayList.get(position).getCode();
                commodityClickPosition = position;
            }

            /**
             * 将所有背景色设为未点击的颜色
             */
            private void clearBackground() {
                for (int i = 0; i < mCommodityView.getmContainer().getChildCount(); i++) {
                    //noinspection deprecation
                    mCommodityView.getmContainer().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });


    }

    /**
     * 当前被选中的商品,(背景置灰,)若无选中,传-1
     */
    private void setAdapter(int clickPosition) {
        HorizontalScrollViewAdapter adapter;

        adapter = new HorizontalScrollViewAdapter(context, priceCommodityEntityArrayList);
        mCommodityView.setmAdapter(adapter, clickPosition);

    }

    @Override
    public void onResume() {
        super.onResume();
        context.registerReceiver(priceReceiver, new IntentFilter(
                PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE));
        if (tradeHelper.hasOpen()) {
            setHasOptionsMenu(false);//已开户,不显示最上面的开户按钮
            initPublicInfo();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context.unregisterReceiver(priceReceiver);
    }

    /**
     * 初始化顶部的广告栏
     */
    private void initTopADView() {
        OtherHelper.configImageLoader(context);//配置ImageLoder
        List<ImageView> views = new ArrayList<>();
        List<ADInfo> infos = new ArrayList<>();
        CycleViewPager cycleViewPager;
        cycleViewPager = (CycleViewPager) getChildFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        ArrayList<AdPictureEntity> adPictureEntityArrayList=myapp.getOpenAccountContractEntity().getAdPictureEntityArrayList();//轮播图片

        if(adPictureEntityArrayList==null||adPictureEntityArrayList.size()==0){
            return;
        }
        for (int i = 0; i < adPictureEntityArrayList.size(); i++) {
            ADInfo info = new ADInfo();
            info.setUrl(adPictureEntityArrayList.get(i).getPic_url());
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(OtherHelper.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(OtherHelper.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(OtherHelper.getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, new CycleViewPager.ImageCycleViewListener() {

                    @Override
                    public void onImageClick(ADInfo info, int position, View imageView) {
                        //  if (cycleViewPager.isCycle()) {
                        //position = position - 1;
//                Toast.makeText(getActivity(),
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
                        // }

                    }

                }
        );

    }

    public class PriceBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PanFragmentWeiTuoXiaDan.ACTION_ALL_COMMODITY_PRICE)) {
                ArrayList<PriceEntity> allCommodityPrice = intent
                        .getParcelableArrayListExtra(PriceService.KEY_ALL_COMMODITY_PRICE);
                commodityDataListSetPrice(allCommodityPrice);
                int clickPosition = -1;
                if (rl_function.getVisibility() == View.VISIBLE) {
                    clickPosition = commodityClickPosition;
                }
                // selectedCode = myapp.getCurrentTradeEntity().getTradeData().getCommodityDataList().get(position).getCode();
                //   commodityClickPosition = position;
                setAdapter(clickPosition);
                setKeYongZiJin();
//                if (rl_function.getVisibility() == View.VISIBLE) {
//                    mCommodityView.getChildAt(commodityClickPosition).setBackgroundColor(getResources().getColor(R.color.fragment_bg));
//                }
                // mCommodityView.notifyCurrentImgChanged();
            }
        }

    }

    private void commodityDataListSetPrice(ArrayList<PriceEntity> allCommodityPrice) {
        for (PriceEntity priceEntity : allCommodityPrice) {
            for (PriceCommodityEntity commodityData : priceCommodityEntityArrayList) {
                if (commodityData.getCode().equals(priceEntity.getCode())) {

                    String zuiXiaoBianDongJia = new TradeHelper(context).getCommodityZuiXiaoBianDongJia(commodityData.getCode());
                    commodityData.setBuy(priceEntity.getBuy() + "");
                    double zhangDieE = priceEntity.getPrice() - priceEntity.getYesterdayClosePrice();
                    commodityData.setCha(ActivityUtils.changeDouble(zhangDieE + "", zuiXiaoBianDongJia));
                    double changePercentage = zhangDieE / priceEntity.getYesterdayClosePrice() * 100;
                    try {
                        commodityData.setBaifenbi(ActivityUtils.changeDouble(changePercentage + "", zuiXiaoBianDongJia));
                    } catch (NumberFormatException e) {
                        commodityData.setBaifenbi("--");
                    }
                }
            }
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            //  String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_settings:
                    gotoTargetPage(TabActivity.fragment_geren, KAI_HU);
                    break;
            }
            return true;
        }
    };


}
