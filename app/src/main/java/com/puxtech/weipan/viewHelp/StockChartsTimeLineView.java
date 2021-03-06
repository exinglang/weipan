package com.puxtech.weipan.viewHelp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.EnvTradeTimeResponseData;
import com.puxtech.weipan.data.PriceLogonResponseData;
import com.puxtech.weipan.data.PriceSercetListResponseData;
import com.puxtech.weipan.data.TimeLineDaysResponseData;
import com.puxtech.weipan.data.TimeLineOneResponseData;
import com.puxtech.weipan.data.TimeLineOneZipResponseData;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.helper.StockChartsTimeLineViewHelper;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.util.AES;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.PriceUtil;
import com.puxtech.weipan.view.TimeLineCrossLineView;
import com.puxtech.weipan.view.TimeLineView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 分时图控制类
 */
public class StockChartsTimeLineView extends BaseStockChartsView {
    TimeLineView timeLinePortrait;//分时线的关键绘图类
    View timeLinePagerView;//viewPager加载时用
    private ProgressBar timeLineProgressBar;//载入框
    private Timer autoRefreshTimer;// 自动刷新定时任务的定时器,分时线任务
    private TimeLineCrossLineView timeLineCrossLineView;// 分时线的十字线
    public TimeLineEntity timeLineEntity;// 从服务器获取的分时数据
    PriceEntity priceEntity;
    private Handler handler;
    boolean refreshTimeLine = true;

    public StockChartsTimeLineView(Context context, PriceEntity priceEntity) {
        super(context);

        initWidget();
        PriceRuntimeData prd = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        this.priceEntity = priceEntity;
        EnvTradeTime envTradeTime = prd.getEnvTradeTime(priceEntity.getMarketNumber(), priceEntity.getEnvNumber());
        timeLinePortrait.setEnvTradeTime(envTradeTime);
        getEnvStartTime();

        initHandler();
        initAutoRefreshTimeLineTask();
    }

    public void startTimeLine() {
        if (!getLocalTimeLine()) {
            //如果本地(myapp)中没有,就从网络获取
            getNetTimeLine();
        }
    }

    /**
     * 本地(MYAPP)检查是否有分时数据,有的话直接用本地刷新TIMELINE,没有从网络获取
     *
     * @return 本地是否有缓存数据
     */
    private boolean getLocalTimeLine() {
        final SparseArray<TimeLineEntity> allTimeLine = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()
                .getAllTimeLine();
        final TimeLineEntity localEntity = allTimeLine.get(priceEntity.getNumber());
        if (localEntity != null && localEntity.getRecordNum() > 0) {
            // timeLinePortraitSetTimeLineEntity(localEntity);
            refreshTimeLine(localEntity);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 从网络获取最新的一段分时图数据,
     */
    private void getNetTimeLine() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            TimeLineDaysResponseData responseData;
            TradeEntity tradeEntity;

            protected void onPreExecute() {
                super.onPreExecute();
                onLoadingData();
                tradeEntity = myapp.getCurrentTradeEntity();
            }

            protected Boolean doInBackground(Void... params) {
                responseData = httpManagerPrice.getTimeLineDays(
                        priceEntity.getNumber(),
                        priceEntity.getMarketNumber(),
                        priceEntity.getEnvNumber());
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (responseData.getRetCode() == 0) {
                    TimeLineEntity timeLineEntity = responseData.getTimeLineEntity();
                    if (timeLineEntity.getRecordNum() == 0) {
                        ActivityUtils.showCenterToast(context, "暂时没有分时数据",
                                Toast.LENGTH_SHORT);
                        onLoadedData();
                        // 清空当前view
                        timeLinePortraitClearTimeLineView();
                        timeLinePortraitSetTimeLineEntity(timeLineEntity);
                    } else {
                        // 异步计算每个点的坐标
                        calTimeLineDataAndRefresh(timeLineEntity, tradeEntity);
                        List<String> urls = timeLineEntity.getZipUrls();
                        if (urls != null && urls.size() > 0)
                            getZipTimeLine(timeLineEntity);
                    }
                } else {
                    ActivityUtils.showCenterToast(
                            context, "获取分时线失败", Toast.LENGTH_LONG);
                }

            }
        });

    }

    private void getZipTimeLine(final TimeLineEntity newTimeLineEntity) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            TimeLineEntity zipTimeLineEntity;
            TradeEntity tradeEntity = myapp.getCurrentTradeEntity();

            @Override
            protected Boolean doInBackground(Void... params) {

                try {
                    List<String> urls = newTimeLineEntity.getZipUrls();
                    String zipUrl = urls.get(urls.size() - 1);
                    String key = PriceUtil.getSecretKey(zipUrl);
                    if (myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getPriceSecretEntityArrayList() == null) {
                        PriceSercetListResponseData priceSercetListResponseData = httpManagerPrice.getPriceSercetList();
                        myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setPriceSecretEntityArrayList(priceSercetListResponseData.getPriceSec());
                    }
                    byte[] secretOfKey = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getSecret(key);
                    String keyStr = new String(secretOfKey);
                    secretOfKey = Base64.decode(keyStr, Base64.DEFAULT);

                    byte[] zip = httpManagerPrice.downLoadZip(zipUrl);//下载分时zip包
                    byte[] zipbyte = AES.decrypt(zip, secretOfKey);
                    zipbyte = PriceUtil.decompress(zipbyte);
                    if (zipbyte == null) {
                        return false;

                    }
                    TimeLineOneZipResponseData response = new TimeLineOneZipResponseData();
                    response.convertTimeLineOneZip(zipbyte);// 转成entity
                    zipTimeLineEntity = response.getTimeLineEntity();

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

//            @Override
//            protected void onCancelled() {
//                super.onCancelled();
//                SparseArray<TimeLineEntity> allTimeLine = myapp.getPriceData().getPriceRuntimeDataByZuId(zuId)
//                        .getAllTimeLine();
//                int commodityNumber = myapp.getPriceData().getPriceRuntimeDataByZuId(zuId)
//                        .getCurrentCommodityNumber();
//                allTimeLine.remove(commodityNumber);
//            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    ArrayList<TimePointEntity> temp = PriceUtil.deRepeatTList(newTimeLineEntity.getRecordList(), zipTimeLineEntity.getRecordList());
                    newTimeLineEntity.setRecordList(temp);
                    newTimeLineEntity.setRecordNum(temp.size());

                    calTimeLineDataAndRefresh(newTimeLineEntity, tradeEntity);
                }
            }
        });
    }

    /**
     * 初始化并开启自动刷新分时线,定时任务
     */
    private void initAutoRefreshTimeLineTask() {
        if (autoRefreshTimer == null) {
            autoRefreshTimer = new Timer();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (refreshTimeLine) {
                    autoRefreshTimeLine();
                }
            }
        };
        autoRefreshTimer.schedule(task, 500, 1000);//　暂时每隔1秒取一次
    }

    /**
     * 自动刷新分时图,非异步获取
     */
    private void autoRefreshTimeLine() {
        final TradeEntity tradeEntity = myapp.getCurrentTradeEntity();
        // 先取出最后一个点的时间
        SparseArray<TimeLineEntity> allTimeLine = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllTimeLine();
        TimeLineEntity entity = allTimeLine.get(myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()
                .getCurrentCommodityNumber());
        if (entity != null) {
            long timeLong = StockChartsTimeLineViewHelper.getLastLongTimeFromTimeLineEntity(entity);
            // 再从网络获取
            TimeLineOneResponseData timeLineOneResponseData = httpManagerPrice.getTimeLineSync(priceEntity,
                    myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getCurrentCommodityNumber(),
                    (short) 1, timeLong, 1, 1322);
            if (timeLineOneResponseData.getRetCode() == 0) {
                final TimeLineEntity newEntity = timeLineOneResponseData.getTimeLineEntity();
                // 如果获取到数据时，用户已经更换了商品，就不往下执行了
                if (newEntity.getNumber() != myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData()
                        .getCurrentCommodityNumber()) {
                    return;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeLinePortrait.addOrUpdateTimeLineNewPoints(newEntity, tradeEntity);
                    }
                });

            } else if (timeLineOneResponseData.getRetCode() == 1001) {
                // 1001错误，交易日已切换，清屏并重新获取数据
                handler.post(new Runnable() {

                    public void run() {
                        myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setAllTimeLine(new SparseArray<TimeLineEntity>());
                        timeLinePortraitClearTimeLineView();
                        getNetTimeLine();

                    }
                });
            }
        }
    }


    private void calTimeLineDataAndRefresh(final TimeLineEntity entity, final TradeEntity tradeEntity) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            protected Boolean doInBackground(Void... params) {
                StockChartsTimeLineViewHelper helper = new StockChartsTimeLineViewHelper();
                helper.setHighestAndLowestPrice(entity);
                return null;
            }

            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                onLoadedData();
                calTimeLinePointXY(entity, tradeEntity);
                refreshTimeLine(entity);
            }
        });
    }

    /**
     * 获取K线下面显示的交易时间,失败默认6点开市
     */
    private void getEnvStartTime() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            private EnvTradeTime ett;
            EnvTradeTimeResponseData envTradeTimeResponseData;

            protected Boolean doInBackground(Void... params) {
                try {
                    envTradeTimeResponseData = httpManagerPrice.getEnvTradeTime(priceEntity.getMarketNumber(),
                            priceEntity.getEnvNumber());
                    ett = envTradeTimeResponseData.getEntity();
                } catch (Exception e) {
                    e.printStackTrace();
                    envTradeTimeResponseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    envTradeTimeResponseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (0 == envTradeTimeResponseData.getRetCode()) {
                    if (!ett.equals(timeLinePortrait.getEnvTradeTime())) {
                        timeLinePortraitSetEnvTradeTime(ett);
                        startTimeLine();
                    }
                } else {
                    ActivityUtils.showCenterToast(context, envTradeTimeResponseData.getRetMessage() + "(" + envTradeTimeResponseData.getRetCode() + ")", Toast.LENGTH_LONG);

                    // if (responseData.getRetCode() == 0) {
                    if (envTradeTimeResponseData.getRetCode() == 2003) {
                        //session失效.重登
                        relogon();
                    }
                    // }
                }
            }
        });
    }


    /**
     * session过期,重新登录
     */
    private void relogon() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            PriceLogonResponseData responseData;

            protected Boolean doInBackground(Void... params) {
                try {
                    responseData = httpManagerPrice.priceLogonRequest();//登录协议

                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (0 == responseData.getRetCode()) {
                    myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().setSessionId((responseData).getSessionIdByte());
                    startTimeLine();
                } else {
                    ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     * 创建Handler
     */
    private void initHandler() {
        if (handler == null) {
            handler = new Handler();
        }
    }
    private void initWidget() {
        LayoutInflater inflater = LayoutInflater.from(context);
        timeLinePagerView = inflater.inflate(
                R.layout.stock_chart_timeline, null);
        // 分时图view
        timeLinePortrait = (TimeLineView) timeLinePagerView
                .findViewById(R.id.view_time_portrait);
        timeLineCrossLineView = (TimeLineCrossLineView) timeLinePagerView
                .findViewById(R.id.view_cross_line_time);
        timeLineProgressBar = (ProgressBar) timeLinePagerView
                .findViewById(R.id.progressBar);
        timeLinePortrait.setTimeLineCrossLineView(timeLineCrossLineView);
    }

    public void onLoadingData() {

        timeLineProgressBar.setVisibility(View.VISIBLE);

    }

    public void onLoadedData() {

        timeLineProgressBar.setVisibility(View.GONE);

    }

    public void calTimeLinePointXY(TimeLineEntity timeLineEntity, TradeEntity tradeEntity) {
        timeLinePortrait.calTimeLinePointXY(timeLineEntity, tradeEntity);
    }

    public void refreshTimeLine(TimeLineEntity timeLineEntity) {
        // 刷新view
        timeLinePortrait.refreshTimeLine(timeLineEntity);
    }

    public TimeLineEntity getTimeLineEntity() {
        return timeLineEntity;
    }

    public void setTimeLineEntity(TimeLineEntity timeLineEntity) {
        this.timeLineEntity = timeLineEntity;
    }

    public void timeLinePortraitSetEnvTradeTime(EnvTradeTime envTradeTime) {
        timeLinePortrait.setEnvTradeTime(envTradeTime);
    }

    public void timeLinePortraitSetTimeLineEntity(TimeLineEntity timeLineEntity) {
        timeLinePortrait.setTimeLineEntity(timeLineEntity);
    }

    public void timeLinePortraitClearTimeLineView() {
        timeLinePortrait.clearTimeLineView();
    }

    public View getTimeLinePagerView() {
        return timeLinePagerView;
    }

    public void setTimeLinePagerView(View timeLinePagerView) {
        this.timeLinePagerView = timeLinePagerView;
    }

    public TimeLineCrossLineView getTimeLineCrossLineView() {
        return timeLineCrossLineView;
    }

    public void setTimeLineCrossLineView(TimeLineCrossLineView timeLineCrossLineView) {
        this.timeLineCrossLineView = timeLineCrossLineView;
    }

    public void setPriceEntity(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
    }



    public boolean isRefreshTimeLine() {
        return refreshTimeLine;
    }

    public void setRefreshTimeLine(boolean refreshTimeLine) {
        this.refreshTimeLine = refreshTimeLine;
    }

    public void setTimerNullWhenDestory() {
        autoRefreshTimer.cancel();
        autoRefreshTimer=null;
    }
}
