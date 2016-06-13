package com.puxtech.weipan.viewHelp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.PortraitPriceDetailPagerAdapter;
import com.puxtech.weipan.data.entitydata.KLineCycleEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 分时和K线的总控制类
 */
public class StockChartsView extends BaseStockChartsView {
    private Context context;
    MyApplication myapp;
    CustomViewPager viewPager;
    //    PriceEntity priceEntity;
    StockChartsTimeLineView timeLineView;//分时控制类
    StockChartsKLineView kLineView;//k线kongzhlei
    PriceEntity priceEntity;
    public ChangeSelectBgCallBack changeSelectBgCallBack;

    public StockChartsView(Context context, View fView, PriceEntity priceEntity) {
        super();
        this.context = context;
        myapp = (MyApplication) context.getApplicationContext();
        this.priceEntity = priceEntity;
        timeLineView = new StockChartsTimeLineView(context, priceEntity);
        initChangeSelectCallBack();
        kLineView = new StockChartsKLineView(context, changeSelectBgCallBack, priceEntity);
        initWidget(fView);
        initViewPager();
        setupCyclesView(fView);
//        kLineView.kLinePortrait.setVisibility(View.INVISIBLE);
    }

    private void initChangeSelectCallBack() {
        changeSelectBgCallBack = new ChangeSelectBgCallBack() {
            @Override
            public void change_selectedview_bg() {
                View click_view = cycleViews.get(StockChartsView.click_view);
                if (click_view == null) {
                    return;
                }
                View cur_selected_view = cycleViews
                        .get(StockChartsView.cur_selected_view);
                cur_selected_view.setSelected(false);
                click_view.setSelected(true);
                cycleViews.append(StockChartsView.cur_selected_view, click_view);
                cycleViews.remove(StockChartsView.click_view);
            }
        };
    }

    private void initWidget(View fView) {
        viewPager = (CustomViewPager) fView.findViewById(R.id.viewpager);
        TextView pricedetail_fenshi = (TextView) fView.findViewById(R.id.pricedetail_fenshi);
        TextView pricedetail_kline_cycle1 = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle1);
        TextView pricedetail_kline_cycle2 = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle2);
        TextView pricedetail_kline_cycle3 = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle3);
        TextView pricedetail_kline_cycle4 = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle4);
        TextView pricedetail_kline_cycle5 = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle5);
        pricedetail_fenshi.setOnClickListener(listener);
        pricedetail_kline_cycle1.setOnClickListener(listener);
        pricedetail_kline_cycle2.setOnClickListener(listener);
        pricedetail_kline_cycle3.setOnClickListener(listener);
        pricedetail_kline_cycle4.setOnClickListener(listener);
        pricedetail_kline_cycle5.setOnClickListener(listener);
    }


    /**
     * 往llayout_cycles里添加周期的子view
     */
    // SparseArray<View> cycleViews;
    SparseArray<View> cycleViews;

    private static final int cur_selected_view = -10000000;
    private static final int click_view = -100000001;

    private void setupCyclesView(View fView) {
        List<KLineCycleEntity> cycles = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()
                .getkLineCycleList();
        TextView tv;
        KLineCycleEntity item;
        tv = (TextView) fView.findViewById(R.id.pricedetail_fenshi);
        tv.setSelected(true);
        cycleViews = new SparseArray<>();
        cycleViews.append(cur_selected_view, tv);
        int[] cycleId = new int[]{R.id.pricedetail_kline_cycle1,
                R.id.pricedetail_kline_cycle2, R.id.pricedetail_kline_cycle3,
                R.id.pricedetail_kline_cycle4, R.id.pricedetail_kline_cycle5};
        for (int i = 0; i < 5; i++) {
            tv = (TextView) fView.findViewById(cycleId[i]);
            tv.setText(cycles.get(i).getName());
            item = cycles.get(i);
            cycleViews.append(item.getId(), tv);
        }

        initLastCycleTV(fView);
    }
//    private Runnable back_action;

    private void initLastCycleTV(View fView) {
        final TextView v = (TextView) fView.findViewById(R.id.pricedetail_kline_cycle5);
        List<KLineCycleEntity> cyclesD = getDropDownCycleEntity();
        if (viewPager.getCurrentItem() == 1) {
            int cur_cycle = getCurrentCycleId();
            for (KLineCycleEntity klce : cyclesD) {

                if (cur_cycle == klce.getId()) {
                    cycleViews.append(cur_selected_view, v);
                    v.setSelected(true);
                    v.setText(klce.getName());
                }
            }
        }
        ActivityUtils.setSpinnerAdapter(v, getDropDownCycles(),
                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        final String back_cycle = v.getText().toString();
                        v.setText(back_cycle);
                        List<KLineCycleEntity> entities = getDropDownCycleEntity();
                        v.setText(entities.get(position).getName());
                        cycleViews.append(click_view, v);
                        setKLinePage((short) entities.get(position).getId());
                        ((PopupWindow) parent.getTag()).dismiss();

                    }
                });
    }

    private void setKLinePage(short request_cycleId) {
        int pageIndex = viewPager.getCurrentItem();
        if (pageIndex != 1) {
            viewPager.setCurrentItem(1, false);
        }
//        changeSelectBgCallBack.change_selectedview_bg();
//                    kLineView.kLinePortrait.setBackgroundColor(Color.argb(255, 255, 255, 255));
//////		Color.TRANSPARENT
//            kLineView.kLinePortrait.setZOrderOnTop(true);

        clearCrossLine();
        // 切换周期时，取消所有任务，防止切换周期引起任务过多r
        clearAsyncTask();
        setCurrentCycleId(request_cycleId);
        kLineView.getKLineData(request_cycleId);
    }


    TextView.OnClickListener listener = new TextView.OnClickListener() {//创建监听对象
        public void onClick(View v) {
            int clickid = v.getId();
            View pre_selected = cycleViews.get(cur_selected_view);
            if (v == pre_selected) {
                return;
            }
            if (pre_selected.getId() == R.id.pricedetail_kline_cycle5) {
                ((TextView) pre_selected).setText(getDropDownCycleEntity().get(0)
                        .getName());
            }
            cycleViews.append(click_view, v);
            timeLineView.setRefreshTimeLine(false);
            kLineView.setRefreshKLine(false);
//            kLineView.getKLinePortrait().setZOrderOnTop(false);
//            timeLineView.timeLinePortrait.setZOrderOnTop(true);
//            kLineView.getKLinePortrait().getHolder().setFormat(PixelFormat.TRANSPARENT);
//            kLineView.getKLinePortrait().clearViewNoFrame();
//            kLineView.kLinePortrait.setVisibility(View.INVISIBLE);
//            kLineView.kLinePortrait.setBackgroundColor(Color.argb(255, 255, 255, 255));
//     		  Color.TRANSPARENT
//            kLineView.kLinePortrait.setZOrderOnTop(true);
//            kLineView.kLinePortrait.setBackgroundColor(Color.TRANSPARENT);
//            kLineView.kLinePortrait.setZOrderOnTop(false);
//            timeLineView.timeLinePortrait.setZOrderOnTop(true);
//            timeLineView.timeLinePortrait.setBackgroundColor(Color.argb(255, 255, 255, 255));
            if (clickid == R.id.pricedetail_kline_cycle1) {
//                viewPager.setCurrentItem(1, false);
                setKLinePage(request_cycleId(0));
                kLineView.setRefreshKLine(true);
            } else if (clickid == R.id.pricedetail_fenshi) {
                timeLineView.setRefreshTimeLine(true);


                int pageIndex = viewPager.getCurrentItem();
                if (pageIndex != 0) {

                    viewPager.setCanScroll(true);
                    viewPager.setCurrentItem(0, false);

//                if(needReloadData){
//                    getTimeLine(mApplication.getPriceData(priceEntity.getZuId()).getCurrentCommodityNumber());// 获取分时图数据
//                    needReloadData=false;
//                }
                }
                changeSelectBgCallBack.change_selectedview_bg();
            } else if (clickid == R.id.pricedetail_kline_cycle2) {
                kLineView.setRefreshKLine(true);

                setKLinePage(request_cycleId(1));
            } else if (clickid == R.id.pricedetail_kline_cycle3) {
                kLineView.setRefreshKLine(true);

                setKLinePage(request_cycleId(2));
            } else if (clickid == R.id.pricedetail_kline_cycle4) {
                kLineView.setRefreshKLine(true);
                setKLinePage(request_cycleId(3));
            }
        }
    };

    private short request_cycleId(int clickId) {
        PriceRuntimeData priceRuntimeData = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        List<KLineCycleEntity> kLineCycleEntityList = priceRuntimeData.getkLineCycleList();
        KLineCycleEntity kLineCycleEntity = kLineCycleEntityList.get(clickId);
        return (short) kLineCycleEntity.getId();

    }


    public void clearCrossLine() {


        if (timeLineView.getTimeLineCrossLineView() != null) {
            timeLineView.getTimeLineCrossLineView().clearCrossLine();
        }
        if (kLineView.getkLineCrossLineView() != null) {
            kLineView.getkLineCrossLineView().clearCrossLine();
        }
    }

    private List<String> getDropDownCycles() {
        List<KLineCycleEntity> cycles = getDropDownCycleEntity();
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < cycles.size(); i++) {
            ret.add(cycles.get(i).getName());
        }
        return ret;
    }

    private List<KLineCycleEntity> getDropDownCycleEntity() {
        List<KLineCycleEntity> cycles = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()
                .getkLineCycleList();

        return cycles.subList(4, cycles.size());
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager.setCanScroll(false);
        List<View> views = new ArrayList<>();
        views.add(timeLineView.getTimeLinePagerView());
        views.add(kLineView.getkLinePagerView());
        PortraitPriceDetailPagerAdapter adapter = new PortraitPriceDetailPagerAdapter(
                views, context);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCanScroll(false);
                    timeLineView.getTimeLineCrossLineView().onTouchEvent(arg1);
                } else if (viewPager.getCurrentItem() == 1) {
                    // 如果显示了十字线，就将事件传递给十字线view
                    if (kLineView.getkLineCrossLineView().isCanTouch()) {
                        viewPager.setCanScroll(false);
                        kLineView.getkLineCrossLineView().onTouchEvent(arg1);
                    } else {
                        kLineView.getkLineCrossLineView().onTouchEvent(arg1);
                    }
                }
                viewPager.setCanScroll(true);
                return false;
            }
        });

    }

    public void clearTimeLineView() {
        timeLineView.timeLinePortraitClearTimeLineView();
    }

    public void clearKLineView() {

        if (kLineView != null) {
            kLineView.clearKLineView();
        }
    }

    private short getCurrentCycleId() {
        return myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getCurrentCycleId();
    }

    private void setCurrentCycleId(short cycleId) {
        myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setCurrentCycleId(cycleId);
    }

    public interface ChangeSelectBgCallBack {
        void change_selectedview_bg();
    }

    public CustomViewPager getViewPager() {
        return viewPager;
    }

    public StockChartsKLineView getkLineView() {
        return kLineView;
    }

    public StockChartsTimeLineView getTimeLineView() {
        return timeLineView;
    }
}
