package com.puxtech.weipan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.EnvTradeTime;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimeLinePaintEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.helper.PriceViewData;
import com.puxtech.weipan.helper.StockChartsTimeLineViewHelper;
import com.puxtech.weipan.helper.TimeController;
import com.puxtech.weipan.helper.WDMXHelper;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;
import com.puxtech.weipan.util.PriceUtil;
import com.puxtech.weipan.util.ScreenSizeUtil;
import com.puxtech.weipan.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanshuo on 16/3/29.
 */
public class TimeLineView extends View {

    private TimeController tradeTimeController;
    TimeLinePaintEntity paintEntity;
    private static final int Y_COUNT = 10;// 横条格子数目
    TimeLineEntity timeLineEntity;
    private static final float DEFAULT_TIMELINE_TEXT_SIZE_DP_PORTRAIT = 10f;
    private static final float FRAME_MARGIN_DP_PORTRAIT = 20f;
    TimeLineCrossLineView timeLineCrossLineView;// 十字线
    float ySpacing;
    double priceSpacing;// 最左边的价钱间隔，根据涨或跌的最大的那个确定间隔价钱数
    int textSize;// 字体大小
    private int mSlop;// 视为滑动的临界点;
    float margin, marginPortrait;
    float frameStartX;// 分时线框架左上角x坐标
    float frameStartY;// 分时线框架左上角y坐标
    float frameEndXPortrait;// 竖屏，分时线框架右下角x坐标
    float frameEndYPortrait;// 竖屏，分时线框架右下角y坐标
    float startX = 0, startY, endX, endY;
    Context context;
    private int commodityType;
    StockChartsTimeLineViewHelper timeLineHelper;

    public TimeLineView(Context context) {
        super(context);
        constructor(context);
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(768, 343);
//        Log.d("fanshuo", "onMeasure...width = " + getMeasuredWidth() + ", height = " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTimeLine(timeLineEntity, canvas);

    }

    /**
     * 统一的结构类
     *
     * @param context
     */
    private void constructor(Context context) {
        this.context = context;
        timeLineHelper = new StockChartsTimeLineViewHelper();
        paintEntity = timeLineHelper.getTimeLinePaint(context);
        ;
        setBackgroundColor(Color.argb(255, 255, 255, 255));
        initData();
    }

    private void initData() {
        ViewConfiguration vc = ViewConfiguration.get(context);
        mSlop = vc.getScaledTouchSlop();
    }

    public void clearTimeLineView() {
        timeLineEntity = null;
        invalidate();
    }

    public void refreshTimeLine(TimeLineEntity entity){
        timeLineEntity = entity;
        invalidate();
    }

    private synchronized void drawTimeLine(TimeLineEntity entity, Canvas canvas) {
        setEndPoint();
        this.timeLineEntity = entity;

        canvas.drawColor(
                context.getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC);

        drawFrame(canvas);
        if (entity == null) {
            // 如果没有获取到数据就只画框架
            return;
        }

        calPriceSpacing(entity);
        drawBottomText(canvas);
        drawLeftText(canvas, entity);
        drawPriceByPath(canvas, entity);
        drawAveragePrice(canvas, entity);
    }

    /**
     * 刷新时出现新的点，就要告诉view添加并绘制
     */
    public void addOrUpdateTimeLineNewPoints(TimeLineEntity newEntity,TradeEntity tradeEntity) {
        // 这里增加二次验证，防止切换商品的同时返回了最新分时数据而进入这里
        if (newEntity == null || timeLineEntity == null
                || newEntity.getNumber() != this.timeLineEntity.getNumber()) {
            return;
        }
        // 增加验证，防止旋转屏幕的时候调用此方法，这是屏幕还没创建完成，所以priceSpacing会是0，这样算出来的坐标都是0，会出现跑到左上角的情况
        if (priceSpacing == 0) {
            return;
        }
        caltimeLineXYAvPoint(newEntity);


        this.timeLineEntity.getRecordList().remove(
                this.timeLineEntity.getRecordList().size() - 1);
        this.timeLineEntity.getRecordList().addAll(newEntity.getRecordList());
        this.timeLineEntity.setRecordNum(this.timeLineEntity.getRecordList()
                .size());
        this.timeLineEntity.refreshData(newEntity);
        //检查最高最低有没有变化，如果有变化，需要重新计算以前所有点的坐标，因为上下顶点变了，整体偏移量就变了。
        if (timeLineHelper.highestOrLowestHasChanged(this.timeLineEntity, newEntity)) {
            calTimeLinePointXY(timeLineEntity,tradeEntity);
        }

        invalidate();
    }

    /**
     * 设置顶点和四周边距
     */
    private void setEndPoint() {
        MyApplication myapp = (MyApplication) context.getApplicationContext();
        setTextSize(ScreenSizeUtil.Dp2Px(context,
                DEFAULT_TIMELINE_TEXT_SIZE_DP_PORTRAIT));// 设置字体大小为10dp
        marginPortrait = ScreenSizeUtil.Dp2Px(context,
                FRAME_MARGIN_DP_PORTRAIT);

        margin = ScreenSizeUtil.Dp2Px(context, 8);
        float topMargin = this.margin;
        topMargin += getResources().getDimension(R.dimen.shi_jia_jun);
        frameStartX = 0f;
        frameStartY = 0f + topMargin;

        PriceRuntimeData prd = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData();
        commodityType = prd.getCommodityType();
        PriceViewData pvd = myapp.getCurrentTradeEntity().getPriceData().getPriceViewData();

        frameEndXPortrait = WDMXHelper.frameEndXPortrait(commodityType, pvd);

        frameEndYPortrait = WDMXHelper.frameEndYPortrait(commodityType, getHeight(), textSize, margin);
    }

    private void setTextSize(int size) {
        this.textSize = size;
        paintEntity.getRedTextPaint().setTextSize(size);
        paintEntity.getGreenTextPaint().setTextSize(size);
        paintEntity.getWhiteTextPaint().setTextSize(size);
    }

    /**
     * 画框架
     */
    private void drawFrame(Canvas canvas) {
        float startX, startY, endX, endY;
        // 竖屏
        startX = 0 + marginPortrait;
        startY = 0 + frameStartY;
        endX = frameEndXPortrait;
        endY = frameEndYPortrait;
        this.ySpacing = (endY - startY) / Y_COUNT;// 竖向间隔

        float curY = startY;// 画横线时，当前这条线的y坐标
        float curX = startX;// 画竖线时，当前这条线的x坐标
        // 画横线
        for (int i = 0; i < (Y_COUNT + 1); i++) {
            if (i == 0 || i == Y_COUNT) {
                canvas.drawLine(frameStartX, curY, endX, curY, paintEntity.getLinePaint());
            } else {
                canvas.drawLine(frameStartX, curY, endX, curY, paintEntity.getLinePaintDashed());
            }
            curY = curY + ySpacing;
        }
    }

    /**
     * 计算每个横格子的价钱
     */
    private void calPriceSpacing(TimeLineEntity timeLineEntity) {
        priceSpacing = timeLineHelper.calPriceSpacing(timeLineEntity, Y_COUNT);
    }

    /**
     * 画底部的时间文字
     *
     * @param canvas Canvas
     */
    private void drawBottomText(Canvas canvas) {
        float leftX;
        float rightX;
        float lineEndY;

        leftX = marginPortrait;
        rightX = frameEndXPortrait;
        lineEndY = frameEndYPortrait;

        // // 画分割线

        float oneMinX = (rightX - leftX) / tradeTimeController.getTOTAL_MINUTES();
        String[] BOTTOM_TEXT = tradeTimeController.getBOTTOM_TEXT();
        int[] BOTTOM_TEXT_IN_MINUTES = tradeTimeController.getBOTTOM_TEXT_IN_MINUTES();
        String curText;
        for (int i = 0; i < BOTTOM_TEXT.length; i++) {
            int minutes = BOTTOM_TEXT_IN_MINUTES[i];
            float x = leftX + minutes * oneMinX;
            float y = getHeight();
            curText = BOTTOM_TEXT[i];
            // 先画竖线
            // 画文字的基准是右下角
            // x-2是因为不和时间右边的竖线重叠
            // y-margin/2是因为整个绘图区域上下都有margin，这样可以让文字保持在margin的中间
            Paint redTextPaint = paintEntity.getRedTextPaint();
            if (i == 1) {
                float text_len = redTextPaint.measureText(curText);
                float interval = (BOTTOM_TEXT_IN_MINUTES[1] - BOTTOM_TEXT_IN_MINUTES[0])
                        * oneMinX;
                if (interval < text_len)
                    continue;// 不画正数第二个防止字重叠
            }
            if (i == BOTTOM_TEXT.length - 2) {
                float text_len = redTextPaint
                        .measureText(BOTTOM_TEXT[BOTTOM_TEXT.length - 1]);
                float interval = (BOTTOM_TEXT_IN_MINUTES[BOTTOM_TEXT_IN_MINUTES.length - 1] - BOTTOM_TEXT_IN_MINUTES[BOTTOM_TEXT_IN_MINUTES.length - 2])
                        * oneMinX;
                if (interval < text_len)
                    continue;// 不画正数第二个和倒数第二个,宽度不够防止字重叠
            }
            canvas.drawText(curText, x - 2, y - margin / 2, paintEntity.getWhiteTextPaint());
            // canvas.drawText(curText, x - 2, y - margin / 2, i == 0
            // || i == BOTTOM_TEXT.length - 1 ? redTextPaint
            // : whiteTextPaint);
        }
    }

    /**
     * 画左边的分段价格文字，动态的,加入绘制右边的百分比
     */
    private void drawLeftText(Canvas canvas, TimeLineEntity timeLineEntity) {

        double closePrice = timeLineEntity.getYesterdayClosePrice();
        List<String> allPrice = new ArrayList<>();
        List<String> allPercentage = new ArrayList<>();
        // 添加涨的部分
        for (int i = Y_COUNT / 2; i > 0; i--) {
            String price = PriceUtil.formatPrice(context, (closePrice + i
                    * priceSpacing), timeLineEntity.getNumber());
            allPrice.add(price);
            // 如果昨收是0，说明是此商品第一天上市，幅度显示--
            if (closePrice == 0) {
                allPercentage.add("--");
            } else {
                String percentage = PriceUtil
                        .formatPercentage((i * priceSpacing) / closePrice * 100);
                allPercentage.add(percentage);
            }
        }
        // 平的那一个
        allPrice.add(PriceUtil.formatPrice(context, closePrice,
                timeLineEntity.getNumber()));
        allPercentage.add(PriceUtil.formatPercentage(0));
        // 添加跌的部分
        for (int i = 1; i <= Y_COUNT / 2; i++) {
            String price = PriceUtil.formatPrice(context, (closePrice - i
                    * priceSpacing), timeLineEntity.getNumber());
            allPrice.add(price);
            // 如果昨收是0，说明是此商品第一天上市，幅度显示--
            if (closePrice == 0) {
                allPercentage.add("--");
            } else {
                String percentage = PriceUtil
                        .formatPercentage(-(i * priceSpacing) / closePrice
                                * 100);
                allPercentage.add(percentage);
            }
        }
        int fixedTextSize = textSize - 4;// 为了使文字中间和线对齐，微调此处的高度，直接用字体的大小不能严格匹配
        float curY = 0 + frameStartY - fixedTextSize - 4;// 画横线时，当前这条线的y坐标
        float curX, percentageX;
        curX = marginPortrait - 4;
        percentageX = frameEndXPortrait;

        // 根据最长价格确定字体的大小 --------start
        int maxLength = 0;
        String maxPrice = "";
        for (String priceString : allPrice) {
            if (priceString.length() > maxLength) {
                maxLength = priceString.length();
                maxPrice = priceString;
            }
        }
        float marginLeftPx;

        marginLeftPx = marginPortrait - 4;
        Paint redTextPaint = paintEntity.getRedTextPaint();
        Paint whiteTextPaint = paintEntity.getWhiteTextPaint();
        Paint greenTextPaint = paintEntity.getGreenTextPaint();


        float maxPriceWidth = redTextPaint.measureText(maxPrice);
//		如果最长的价格超过了该区域宽度，就缩小字号 maxPriceWidth:curTextSize = marginLeftPx:newTextSize
        if (maxPriceWidth > marginLeftPx) {
            float newTextSize = marginLeftPx / maxPriceWidth * redTextPaint.getTextSize();
            redTextPaint.setTextSize(newTextSize);
            whiteTextPaint.setTextSize(newTextSize);
            greenTextPaint.setTextSize(newTextSize);
        }
        // 根据最长价格确定字体的大小 -------------end
        for (int i = 0; i < allPrice.size(); i++) {
            if (i < 5) {
                // 向下移动一单位字体高度,外加2px防止压线
                canvas.drawText(allPrice.get(i), curX,
                        curY + fixedTextSize + 2, redTextPaint);
                // 画右边百分比
                canvas.drawText(allPercentage.get(i), percentageX, curY
                        + fixedTextSize + 2, redTextPaint);
            } else if (i == 5) {
                canvas.drawText(allPrice.get(i), curX,
                        curY + fixedTextSize + 2, whiteTextPaint);
                // 画右边百分比
                canvas.drawText(allPercentage.get(i), percentageX, curY
                        + fixedTextSize + 2, whiteTextPaint);
            } else {
                // 向下移动一单位字体高度
                canvas.drawText(allPrice.get(i), curX,
                        curY + fixedTextSize + 2, greenTextPaint);
                // 画右边百分比
                canvas.drawText(allPercentage.get(i), percentageX, curY
                        + fixedTextSize + 2, greenTextPaint);
            }
            curY = curY + ySpacing;
        }
    }

    private void drawPriceByPath(Canvas canvas, TimeLineEntity entity) {
        List<TimePointEntity> pointList = entity.getRecordList();
        if (pointList == null || pointList.size() <= 1) {
            return;
        }
        // 使用path方法绘制
        Path path = new Path();// 分时线的填充区域
        float xPixel, yPixel;
        TimePointEntity item;

        for (int i = 0; i < pointList.size(); i++) {
            item = pointList.get(i);
            xPixel = item.getPointX();
            yPixel = item.getPointY();
            // 如果这个点的坐标超出了框架，就跳过这个点
            if (item.getPointX() <= frameStartX
                    || item.getPointX() >= frameEndXPortrait) {
                // PriceLogger.e("drawPriceByPath...PointX = " +
                // item.getPointX() + " (item.getTime = " + item.getTime() +
                // ")");
                continue;
            }
            if (yPixel < frameStartY)
                yPixel = frameStartY;
            if (yPixel > frameEndYPortrait)
                yPixel = frameEndYPortrait;
            if (i == 0)
                path.moveTo(xPixel, yPixel);
            else
                path.lineTo(xPixel, yPixel);
        }

        canvas.drawPath(path, paintEntity.getPricePaint());
    }

    /**
     * 黄色的均价线
     *
     * @param entity TimeLineEntity
     */
    private void drawAveragePrice(Canvas canvas, TimeLineEntity entity) {
        Path path = new Path();
        List<TimePointEntity> pointList = entity.getRecordList();
        TimePointEntity item;
        float yPixel, xPixel;
        for (int i = 0; i < pointList.size(); i++) {
            item = pointList.get(i);
            xPixel = item.getPointX();
            yPixel = item.getAverageY();
            if (yPixel < frameStartY)
                yPixel = frameStartY;
            if (yPixel > frameEndYPortrait)
                yPixel = frameEndYPortrait;
            if (i == 0) {
                path.moveTo(xPixel, yPixel);
            } else {
                path.lineTo(xPixel, yPixel);
            }
        }
        canvas.drawPath(path, paintEntity.getAveragePricePaint());
    }

    private void caltimeLineXYAvPoint(TimeLineEntity timeLineEntity) {

        List<TimePointEntity> pointList = timeLineEntity.getRecordList();
        if (pointList == null || pointList.size() == 0) {
            return;
        }
        /**
         * 总个数为：(pointList.size()-1) * 4，开头和末尾只需要2个，中间的都需要4个 0 1 2 3 4 5 6 7 8 9
         * 1011 x1y1 x2y2x2y2 x3y3x3y3 x4y4
         */

        for (int i = 0; i < pointList.size(); i++) {
            TimePointEntity item = pointList.get(i);
            // 坐标
            float pointXPortrait = (float) getXPixelByTime(item);
            float pointYPortrait = (float) getYPixelByPrice(
                    item.getLastPrice(),
                    timeLineEntity.getYesterdayClosePrice());
            float averageYPortrait = (float) getYPixelByPrice(
                    item.getAveragePrice(),
                    timeLineEntity.getYesterdayClosePrice());
            item.setPointX(pointXPortrait);
            item.setPointY(pointYPortrait);
            item.setAverageY(averageYPortrait);


        }

    }

    /**
     * 根据当前价钱算出Y坐标(此方法耗时小于1ms)
     *
     * @param price      价格
     * @param closePrice 收盘价
     * @return Y坐标
     */
    private double getYPixelByPrice(double price, double closePrice) {
        float endY = this.frameEndYPortrait;
        double pixelsInPrice = (endY - this.frameStartY)
                / (this.priceSpacing * 10);// 每1块钱是多少像素
        double ret;
        double closeY = (endY - this.frameStartY) / 2 + frameStartY;
        if (price > closePrice) {
            ret = closeY - (price - closePrice) * pixelsInPrice;

        } else {
            ret = closeY + (closePrice - price) * pixelsInPrice;
        }
        return ret;
    }

    /**
     * 根据当前时间算出X坐标（此方法耗时1ms-2ms）
     *
     * @param entity TimePointEntity
     * @return X坐标
     */
    private double getXPixelByTime(TimePointEntity entity) {
        float endX = this.frameEndXPortrait;

        float startX = this.marginPortrait;
        double ret;
        double pixelsOneMinute = (endX - startX) / tradeTimeController.getTOTAL_MINUTES();// 每1分钟时多少像素
        long currentTime = 0;
        long startTime = tradeTimeController.getStartTimeLong();
        try {
            currentTime = TimeUtil.getTimeByTimePoint(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentTime < startTime) {
            currentTime -= 1000 * 60 * 60 * 24;
        }
        long delta = currentTime - startTime;
        ret = pixelsOneMinute * (delta / 1000f / 60f);
        ret = ret + marginPortrait;
        return ret;
    }


    /**
     * 计算分时图所有点的x，y坐标
     */
    public void calTimeLinePointXY(TimeLineEntity timeLineEntity,TradeEntity tradeEntity) {

        calPriceSpacing(timeLineEntity);
        caltimeLineXYAvPoint(timeLineEntity);
        // 保存到内存中
        saveTimeLineEntity(timeLineEntity,tradeEntity);
    }

    private void saveTimeLineEntity(TimeLineEntity timeLineEntity,TradeEntity tradeEntity) {

        // 存入缓存中(Application中)
        SparseArray<TimeLineEntity> allTimeLine = tradeEntity.getPriceData().getPriceRuntimeData().getAllTimeLine();
        allTimeLine.put(timeLineEntity.getNumber(), timeLineEntity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                endX = event.getX();
                endY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                float offset2 = endX - startX;
                // 如果点击的是上面的部分
                if (Math.abs(offset2) < mSlop) {
                    // 如果有分时数据，就开始画十字线
                    if (timeLineEntity != null && timeLineEntity.getRecordNum() > 0) {
                        // 把curPointList传给画十字线的view，让它画十字线
                        timeLineCrossLineView.setTimeLineEntity(timeLineEntity);
                        timeLineCrossLineView.startDrawCrossLine(event.getX());
                        timeLineCrossLineView.setCanTouch(true);
//                        if (viewPager != null) {
//                            viewPager.setCanScroll(false);
//                        }
                    }
                }
                break;
        }
        return true;
    }

    public void setEnvTradeTime(EnvTradeTime ett) {
        tradeTimeController = new TimeController(ett);
    }

    public EnvTradeTime getEnvTradeTime() {
        return tradeTimeController.getEtt();
    }

    public void setTimeLineEntity(TimeLineEntity timeLineEntity) {
        this.timeLineEntity = timeLineEntity;
    }

    public void setTimeLineCrossLineView(
            TimeLineCrossLineView timeLineCrossLineView) {
        this.timeLineCrossLineView = timeLineCrossLineView;
    }


}
