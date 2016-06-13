package com.puxtech.weipan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.util.PriceUtil;
import com.puxtech.weipan.util.ScreenSizeUtil;
import com.puxtech.weipan.util.TimeUtil;
import com.puxtech.weipan.widget.CustomViewPager;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 分时十字线
 * </p>
 *
 * @author fanshuo
 * @version V1.0

 */
public class TimeLineCrossLineView extends View{
    boolean touchLeftArea = true;
    private static final float DEFAULT_TEXT_SIZE_DP = 10f;

    private Context context;
    private Paint linePaint;// 十字线
    private Paint valueTextPaint;// 带颜色数值
    private Paint nameTextPaint;// 白色名称
    private boolean canTouch = false;// 是否消耗touch事件，即：是否将touch事件传递里面一层（分时图的View）,此view一直为visible状态，依靠这个变量来确定是否响应触摸事件
    TimeLineEntity timeLineEntity;
    private double yesterdayClosePrice;// 昨收，十字线使用
    private double todayOpenPrice;// 今日开盘价，十字线使用
    private double highPrice;// 今日最高，十字线使用
    private double lowPrice;// 今日最低，十字线使用

    private CustomViewPager viewPager;
    float selectPointX = -1;
    private boolean clearCrossLine = false;


    public TimeLineCrossLineView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TimeLineCrossLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeLineCrossLineView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(clearCrossLine){
            //清空
            canTouch = false;
            if (viewPager != null) {
                viewPager.setCanScroll(true);
            }
            clearCrossLine = false;
        }
        else{
            drawAll(canvas);
        }

    }

    /**
     * 初始化构造函数
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        initPaint();
    }

    private void initPaint() {
        float textSize = ScreenSizeUtil.Dp2Px(context, DEFAULT_TEXT_SIZE_DP);

        this.linePaint = new Paint();
        this.linePaint.setAntiAlias(true);
        this.linePaint.setColor(getResources().getColor(R.color.cross_line));
        this.linePaint.setStyle(Paint.Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        this.linePaint.setPathEffect(effects);
        this.linePaint.setStrokeWidth(2);

        this.valueTextPaint = new Paint();
        this.valueTextPaint.setAntiAlias(true);
        this.valueTextPaint.setColor(getResources().getColor(R.color.text_red));
        this.valueTextPaint.setTextAlign(Align.LEFT);
        this.valueTextPaint.setStrokeWidth(1.0f);
        this.valueTextPaint.setTextSize(textSize);

        this.nameTextPaint = new Paint();
        this.nameTextPaint.setAntiAlias(true);
        this.nameTextPaint.setColor(Color.BLACK);
        this.nameTextPaint.setStrokeWidth(1.0f);
        this.nameTextPaint.setTextAlign(Align.LEFT);
        this.nameTextPaint.setTextSize(textSize);

    }

    public void startDrawCrossLine(float x) {
        this.selectPointX = x;
        initPaint();
        invalidate();
    }

    private void drawAll(Canvas canvas) {
        TimePointEntity nearPoint = getPointX(selectPointX);
        if (nearPoint != null) {
            // 横屏
        drawCrossLine(canvas, nearPoint.getPointX(),
                nearPoint.getPointY());
        checkTouchPoint(nearPoint.getPointX());
        drawText(canvas, nearPoint);
        }
    }

    private void drawCrossLine(Canvas canvas, float x, float y) {
        float screenWidth = 0;
        float screenHeight = 0;
        // 横屏

        screenWidth = ScreenSizeUtil.getScreenWidth(context);
        screenHeight = ScreenSizeUtil.getScreenHeight(context);

//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawLine(0, y, screenWidth, y, linePaint);
        canvas.drawLine(x, 0, x, screenHeight, linePaint);
    }

    float startX, startY, endX, endY;
    boolean moving = false;// 用于区分是点击消失还是开始滑动，因为点击消失的时候总会有微小的晃动，这里设置为超过10才算ACTION_MOVE

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canTouch) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                endX = event.getX();
                endY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                if (moving) {
                    this.selectPointX = endX;
                    startDrawCrossLine(endX);
                } else if (Math.abs(endX - startX) > 10) {
                    moving = true;
                    startDrawCrossLine(endX);
                }
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                endX = event.getX();
                endY = event.getY();
                if (Math.abs(endX - startX) < 10 && Math.abs(endY - startY) < 10) {
                    clearCrossLine();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void checkTouchPoint(float x) {
        float screenWidth;
        // 横屏

            screenWidth = ScreenSizeUtil.getScreenWidth(context);

        float centerX = screenWidth / 2;
        if (x <= centerX) {
            touchLeftArea = true;
        } else {
            touchLeftArea = false;
        }
    }

    /**
     * 清除十字线
     */
    public void clearCrossLine() {
        clearCrossLine = true;
        invalidate();
    }

    /**
     * 查找最近点的x坐标
     *
     * @return
     */
    private TimePointEntity getPointX(float x) {
        if(timeLineEntity == null){
            return null;
        }
        List<TimePointEntity> allPoint = timeLineEntity.getRecordList();
        if (allPoint == null || allPoint.size() == 0) {
            return null;
        }
        TimePointEntity retEntity = null;
        float deltaX = 10000;
        // 横屏

            for (int i = 0; i < allPoint.size(); i++) {
                TimePointEntity item = allPoint.get(i);
                if (Math.abs(item.getPointX() - x) < deltaX) {
                    deltaX = Math.abs(item.getPointX() - x);
                    retEntity = item;
                }
            }


        return retEntity;
    }

    private void drawText(Canvas canvas, TimePointEntity nearPoint) {


        // 整个文字部分的宽高
        float width = getWidth();
        float textStartY = ScreenSizeUtil.Dp2Px(context, DEFAULT_TEXT_SIZE_DP + 5);
        float textLength = nameTextPaint.measureText("时:");
        float space = width / 5;
        float textStartX = 0;
        float dateStartX = textStartX + textLength;

        // 画文字
        // 时间-名字
        canvas.drawText("时:", textStartX, textStartY, nameTextPaint);
        getPaintColor(valueTextPaint, nearPoint.getTime());
        canvas.drawText(TimeUtil.convertTimeLinePointTime(nearPoint.getTime()),
                dateStartX, textStartY, valueTextPaint);// 时间-数值
        textStartX = textStartX + space;
        dateStartX = textStartX + textLength;
        // 当前价格
        canvas.drawText("价:", textStartX, textStartY, nameTextPaint);
        getPaintColor(valueTextPaint, nearPoint.getLastPrice());
        //检查价格文字长度，动态调整文字大小
        autoTextSize("" + nearPoint.getLastPrice(), space - nameTextPaint.measureText("价:"), valueTextPaint);
        canvas.drawText(PriceUtil.formatPrice(context,
                        nearPoint.getLastPrice(), timeLineEntity.getNumber()),
                dateStartX, textStartY, valueTextPaint);
        float defaulttextSize = ScreenSizeUtil.Dp2Px(context, DEFAULT_TEXT_SIZE_DP);
        //恢复默认大小
        valueTextPaint.setTextSize(defaulttextSize);
        textStartX = textStartX + space;
        dateStartX = textStartX + textLength;
        // 均价
        canvas.drawText("均:", textStartX, textStartY, nameTextPaint);
        valueTextPaint.setColor(context.getResources().getColor(
                R.color.time_line_average));
        //检查价格文字长度，动态调整文字大小
        autoTextSize("" + nearPoint.getAveragePrice(), space - nameTextPaint.measureText("均:"), valueTextPaint);
        canvas.drawText(PriceUtil.formatPrice(context,
                        nearPoint.getAveragePrice(), timeLineEntity.getNumber()),
                dateStartX, textStartY, valueTextPaint);// 均价-数值
        //恢复默认大小
        valueTextPaint.setTextSize(defaulttextSize);

        textStartX = textStartX + space;
        dateStartX = textStartX + textLength;
        // 幅度
        canvas.drawText("幅:", textStartX, textStartY, nameTextPaint);
        double delta = nearPoint.getLastPrice() - yesterdayClosePrice;
        valueTextPaint.setTextSize(ScreenSizeUtil.Dp2Px(context, DEFAULT_TEXT_SIZE_DP));
        // 如果昨收是0，说明是此商品第一天上市，幅度显示--
        if (yesterdayClosePrice == 0) {
            valueTextPaint.setColor(Color.WHITE);
            canvas.drawText("--", dateStartX, textStartY, valueTextPaint);
        } else {
            double deltaPercent = delta / yesterdayClosePrice * 100;
            String deltaPercentString = PriceUtil
                    .formatPercentage(deltaPercent);
            //如果不足两位，就直接进位0.01
            if (deltaPercent > 0 && deltaPercent < 0.01) {
                deltaPercentString = "+0.01%";
            }
            if (deltaPercent < 0 && deltaPercent > -0.01) {
                deltaPercentString = "-0.01%";
            }
            if (deltaPercent == 0) {
                valueTextPaint.setColor(Color.WHITE);
            } else if (deltaPercent > 0) {
                valueTextPaint.setColor(context.getResources().getColor(
                        R.color.text_red));
            } else {
                valueTextPaint.setColor(context.getResources().getColor(
                        R.color.text_green));
            }
            canvas.drawText(deltaPercentString, dateStartX, textStartY,
                    valueTextPaint);
        }
        textStartX = textStartX + space;
        dateStartX = textStartX + textLength;
        // 量

//		int commodityType =((MyApplication) context.getApplicationContext()).getPriceData().getPriceRuntimeDataByZuId(zuId)
//				.getCommodityType();
//	if(commodityType==4) {
//		//2,5模式不显示量
//		canvas.drawText("量:", textStartX, textStartY, nameTextPaint);
//		valueTextPaint.setTextSize(ScreenSizeUtil.Dp2Px(context, DEFAULT_TEXT_SIZE_DP));
//		valueTextPaint.setColor(context.getResources().getColor(
//				R.color.text_red));
//		canvas.drawText(String.valueOf(nearPoint.getVolume()),
//				dateStartX, textStartY, valueTextPaint);//成交量
//	}

    }

    /**
     * 动态设置字体大小
     *
     * @param str      要显示的文字
     * @param maxWidth 该区域可容纳的最大宽度
     * @param paint    用于绘制该文字的画笔
     */
    private void autoTextSize(String str, float maxWidth, Paint paint) {
        float curWidthPx = paint.measureText(str);
        //如果默认总长度超过最大宽度，就重新设置字号  maxWidth:newTextSize = curWidthPx:oldTextSize
        if (curWidthPx > maxWidth) {
            float newTextSize = maxWidth / curWidthPx * paint.getTextSize();
            paint.setTextSize(newTextSize);
        }
    }

    private void getPaintColor(Paint paint, double price) {
        if (price == yesterdayClosePrice) {
            paint.setColor(Color.WHITE);
        } else if (price > yesterdayClosePrice) {
            paint.setColor(context.getResources().getColor(R.color.text_red));
        } else if (price < yesterdayClosePrice) {
            paint.setColor(context.getResources().getColor(R.color.text_green));
        }
    }

    // //////////////////////////////////////////////////////////////////////
    public TimeLineEntity getTimeLineEntity() {
        return timeLineEntity;
    }

    public void setTimeLineEntity(TimeLineEntity timeLineEntity) {
        this.timeLineEntity = timeLineEntity;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    public double getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(double yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public double getTodayOpenPrice() {
        return todayOpenPrice;
    }

    public void setTodayOpenPrice(double todayOpenPrice) {
        this.todayOpenPrice = todayOpenPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public CustomViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(CustomViewPager viewPager) {
        this.viewPager = viewPager;
    }



}
