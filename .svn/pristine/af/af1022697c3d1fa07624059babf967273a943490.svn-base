package com.puxtech.weipan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.data.entitydata.KPointEntity;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.KLineUtils;
import com.puxtech.weipan.util.PriceUtil;
import com.puxtech.weipan.util.ScreenSizeUtil;
import com.puxtech.weipan.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 竖屏K线view
 * </p>
 *
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-1-2 下午3:37:10
 * @version V1.0
 * @FileName KLinePortrait.java
 * @Package com.happyinsource.htjy.android.view
 */
@SuppressWarnings("ALL")
public class KLineView extends View implements KLineCrossLineView.OnKPointSelected {

	public void setHasLoadedAllPointList(boolean hasLoadedAllPointList) {
		this.hasLoadedAllPointList = hasLoadedAllPointList;
	}

	public boolean isHasLoadedAllPointList() {
		return hasLoadedAllPointList;
	}

	public int getkPointCount() {
		return kPointCount;
	}



	private static final int Y_COUNT = 4;// 竖向小格总数
	private float K_POINT_WIDTH_DP = 5;// 柱子宽度，单位是dp

	private Context context;
	private Paint linePaint;// 边框
	private Paint linePaintDashed;// 边框虚线
	private Paint kPaintRed;// 红色柱子
	private Paint kPaintGreen;// 绿色柱子
	private Paint greenTextPaint;// 临时使用的字体
	private Paint whiteTextPaint;// 白色文字
	private Paint pma5Paint;// pma5线
	private Paint zdygs_quato_paint;
	private Paint pma10Paint;// pma10线
	private Paint pma20Paint;// pma20线
	private Paint pma30Paint;// pma30线


	KLineUtils ku;
	private Paint pma5TextPaint;// pma5文字
	private Paint pma10TextPaint;// pma10文字
	private Paint pma20TextPaint;// pma20文字
	private Paint pma30TextPaint;// pma30文字


	private float frameStartX, frameStartY, frameEndX, frameEndY;// k线框架起点和终点

	//private float middleTextAreaHeight;//中间文字顶部的分割线

	private float ySpacing;// 一个横条格子的像素数
	private double priceSpacing;// 最左边的价钱间隔，根据涨或跌的最大的那个确定间隔价钱数
	private float marginLeftPortrait, horizonTextHeight;// marginHorizonTextPortrait:顶部中间底部水平文字区域的高度
	private int textSize;// 字体大小
	private int kPointWidth;// k线一个柱子的宽度,不包括右边的1像素空隙，单位是像素
	private int kPointCount;// 柱子的总数量
	private double highestPrice;
	private double lowestPrice;

	private List<KPointEntity> allPointList;// 所有柱子
	private List<KPointEntity> curPointList;// 当前显示的柱子
	private int offset = 0;// 因为滑动引起的偏移量，单位：柱子
	private MyApplication mApplication;
//	private BOTTOM_TYPE curType = BOTTOM_TYPE.MACD;// 当前底部指标类型

	private KLineCrossLineView view_cross_line;// 十字线，用于给该view添加curPointList的引用

	private CustomViewPager viewPager;// viewpager，是外面viewpager的引用，用来控制viewpager是否可以滑动


	public interface KLineViewCallBack {
		/**
		 * 显示kLineView的进度圈
		 */
		public void showProgressBar();

		/**
		 * 隐藏kLineView的进度圈
		 */
		public void hideProgressBar();
	}

	private KLineViewCallBack kLineViewCallBack;

	private boolean hasLoadedAllPointList = false;// 是否获取过所有的，如果获取之前判定为不用获取，需要把这个设置为true

	private boolean clean = false;

	private KPointEntity selectedKPoint;

	public KLineView(Context context) {
		super(context);
		init(context);
	}

	public KLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public KLineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		setBackgroundColor(Color.argb(255, 255, 255, 255));
		kPointWidth = ScreenSizeUtil.Dp2Px(context, K_POINT_WIDTH_DP);// 暂时设为5dp
		initPaint();
		this.curPointList = new ArrayList<KPointEntity>();
		this.allPointList = new ArrayList<KPointEntity>();
		ku=new KLineUtils();
		mApplication = (MyApplication) context.getApplicationContext();
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(clean){
			canvas.drawColor(
					context.getResources().getColor(
							R.color.white),
					PorterDuff.Mode.SRC);

			drawFrame(canvas);
			clean = false;
		}
		else{
			refreshKLine(canvas);
		}

	}

	/**
	 * 设置顶点和四周边距
	 */
	private void setEndPoint() {
		//pma和macd文字区域的高度
		horizonTextHeight = ScreenSizeUtil.Dp2Px(context, 17);
		//macd上面分割线的高度
//		middleTextAreaHeight = ScreenSizeUtil.Dp2Px(context, 4);
		marginLeftPortrait = ScreenSizeUtil.Dp2Px(context, 40);
		// 设置字体大小为10dp
		setTextSize(ScreenSizeUtil.Dp2Px(context, 10));
		// 竖向间隔,Y_COUNT+1是算上了下面的其它线的一个格子
//		this.ySpacing = (getHeight() - ( horizonTextHeight + middleTextAreaHeight)) / (Y_COUNT+1 );
		this.ySpacing = (getHeight() -  horizonTextHeight-20) / (Y_COUNT+1 );
		// 起点，整个view的左上角
		this.frameStartX = 0f;
		this.frameStartY = 0f + horizonTextHeight;
		// 终点，右下角减去其它线所占空间
		this.frameEndX = getWidth();
		this.frameEndY = this.ySpacing * (Y_COUNT) + this.frameStartY;

	}

	private void setTextSize(int size) {
		this.textSize = size;
		this.greenTextPaint.setTextSize(size);
		this.whiteTextPaint.setTextSize(size);

		this.pma5TextPaint.setTextSize(size);// pma5文字
		this.pma10TextPaint.setTextSize(size);// pma10文字
		this.pma20TextPaint.setTextSize(size);// pma20文字
		this.pma30TextPaint.setTextSize(size);// pma30文字

	}

	/**
	 * 画上面的K线的框架
	 *
	 * @param canvas
	 */
	private void drawFrame(Canvas canvas) {

		float curY = frameStartY;// 画横线时，当前这条线的y坐标
		float curX = getMarginLeft();// 画竖线时，当前这条线的x坐标
		// 画横线
		for (int i = 0; i < (Y_COUNT + 1); i++) {
			if (i == 0 || i == Y_COUNT) {
				//横向第一条和最后一条直线
				canvas.drawLine(frameStartX, curY, frameEndX, curY, linePaint);
			} else {
				//中间的N条虚线
				canvas.drawLine(frameStartX, curY, frameEndX, curY,
						linePaintDashed);
			}
			curY = curY + ySpacing;
		}
		// 画竖线，只画一根
		canvas.drawLine(curX, frameStartY, curX, frameEndY, linePaint);
	}


	/**
	 * k线图上面的MA数字
	 *
	 * @param canvas
	 */
	private void drawTopText(Canvas canvas, KPointEntity kPoint) {
		// 画背景
		Paint darkBgPaint = new Paint();
		darkBgPaint.setColor(getResources().getColor(
				R.color.white));
		canvas.drawRect(new Rect((int) frameStartX, 0, (int) frameEndX,
				(int) frameStartY), darkBgPaint);
		float startX = this.frameStartX + 10;
		float pixelY = this.frameStartY - ((horizonTextHeight - textSize) / 2);
		canvas.drawText(
				"5MA:"
						+ PriceUtil.formatPrice(context, kPoint.getPma5(),
						getCommodityNumber()), startX, pixelY,
				this.pma5TextPaint);
		canvas.drawText(
				"10MA:"
						+ PriceUtil.formatPrice(context, kPoint.getPma10(),
						getCommodityNumber()), startX + this.textSize
						* 8, pixelY, this.pma10TextPaint);
		canvas.drawText(
				"20MA:"
						+ PriceUtil.formatPrice(context, kPoint.getPma20(),
						getCommodityNumber()), startX + this.textSize
						* 8 * 2, pixelY, this.pma20TextPaint);
		canvas.drawText(
				"30MA:"
						+ PriceUtil.formatPrice(context, kPoint.getPma30(),
						getCommodityNumber()), startX + this.textSize
						* 8 * 3, pixelY, this.pma30TextPaint);

	}


	/**
	 * 初始化curPositionList  List<ExpressionValue> values
	 */
	private void initCurPositionList(int raw_size) {
		this.kPointCount = (int) ((getWidth() - getMarginLeft()) / (kPointWidth + 1));// +1是因为左右两边的1像素间隔
		this.curPointList.clear();
		// 从整个list中取出要显示的个数，从末尾开始取
		int start,end;
		if (allPointList.size() > kPointCount) {
			start=(allPointList.size() - kPointCount) - offset;
			end=allPointList.size() - offset;
			// 如果柱子总数超过一屏
			for (int i = start; i < end; i++) {
				this.curPointList.add(allPointList.get(i));
			}
		} else {
			// 如果总柱子数不满一屏
			for (int i = 0; i < allPointList.size(); i++) {
				this.curPointList.add(allPointList.get(i));
			}
		}
	}

	/**
	 * 画左边的价格，并且确定价格的最大值和最小值，必须优先执行此方法，因为影响到后面画线的单位大小
	 */
	public void drawLeftPriceText(Canvas canvas) {
		// 从取出的list中找出最高价和最低价
		this.highestPrice = 0;// 要显示的所有柱子中的最高价
		this.lowestPrice = this.curPointList.get(0).getLowestPrice();// 要显示的所有柱子中的最低价
		for (KPointEntity item : this.curPointList) {
			if (item.getHighestPrice() > highestPrice) {
				highestPrice = item.getHighestPrice();
			}
			if (item.getLowestPrice() < lowestPrice) {
				lowestPrice = item.getLowestPrice();
			}
			if (item.getPma5() > highestPrice) {
				highestPrice = item.getPma5();
			}
			if (item.getPma10() > highestPrice) {
				highestPrice = item.getPma10();
			}
			if (item.getPma20() > highestPrice) {
				highestPrice = item.getPma20();
			}
			if (item.getPma30() > highestPrice) {
				highestPrice = item.getPma30();
			}
			if (item.getPma5() > 0 && item.getPma5() < lowestPrice) {
				lowestPrice = item.getPma5();
			}
			if (item.getPma10() > 0 && item.getPma10() < lowestPrice) {
				lowestPrice = item.getPma10();
			}
			if (item.getPma20() > 0 && item.getPma20() < lowestPrice) {
				lowestPrice = item.getPma20();
			}
			if (item.getPma30() > 0 && item.getPma30() < lowestPrice) {
				lowestPrice = item.getPma30();
			}
		}
		// 这里排除最高最低一样的情况，避免最后得到无穷大的数
		if ((highestPrice - lowestPrice) == 0) {
			highestPrice += 1f;
			lowestPrice -= 1f;
		}
		// TODO 具体增量需要研究，如果波动只有0.x，3就太大了，导致所有柱子都在中间
		// this.highestPrice = this.highestPrice + 3;
		// this.lowestPrice = this.lowestPrice - 3;
		// 算出一个小格子是多少价钱
		this.priceSpacing = (highestPrice - lowestPrice) / Y_COUNT;
		whiteTextPaint.setTextAlign(Align.RIGHT);
		for (int i = 0; i < Y_COUNT + 1; i++) {
			if (i == 0) {
				// 第一个
				canvas.drawText(PriceUtil.formatPrice(context, highestPrice,
								getCommodityNumber()), this.marginLeftPortrait - 4,
						this.frameStartY + this.textSize, whiteTextPaint);
			} else if (i == Y_COUNT) {
				// 最后一个
				canvas.drawText(PriceUtil.formatPrice(context, lowestPrice,
								getCommodityNumber()), this.marginLeftPortrait - 4,
						this.frameEndY - 4, whiteTextPaint);
			} else {
				// 中间的
				canvas.drawText(
						PriceUtil.formatPrice(context, highestPrice
								- this.priceSpacing * i, getCommodityNumber()),
						this.marginLeftPortrait - 4,
						(float) (this.frameStartY + this.ySpacing * i) - 4,
						whiteTextPaint);
			}
		}
	}

	private void drawAllCurrentKPpoint(Canvas canvas) {
		for (int i = 0; i < this.curPointList.size(); i++) {
			drawKPoint(canvas, this.curPointList.get(i), i);
		}
	}

	private void drawKPoint(Canvas canvas, KPointEntity entity, int position) {
		float marginLeft = marginLeftPortrait;
		float kPointWidthFixed = this.kPointWidth + 1;// 每个柱子的宽度，加1表示柱子右边的间隙
		float pointX = kPointWidthFixed * position + kPointWidthFixed / 2
				+ marginLeft;// 整个poiont的x坐标是固定的
		float pixelsInPrice = (float) (this.ySpacing / this.priceSpacing);// 1块钱占的像素数
		float lineStartY = (float) ((this.highestPrice - entity
				.getHighestPrice()) * pixelsInPrice) + this.frameStartY;
		float lineEndY = (float) ((this.highestPrice - entity.getLowestPrice()) * pixelsInPrice)
				+ this.frameStartY;
		if (entity.getClosePrice() > entity.getOpenPrice()) {
			// 如果收盘价高于开盘价，上涨，红色柱子
			canvas.drawLine(pointX, lineStartY, pointX, lineEndY, kPaintRed);
			float left = pointX - this.kPointWidth / 2;
			float right = pointX + this.kPointWidth / 2;
			float top = (float) ((this.highestPrice - entity.getClosePrice()) * pixelsInPrice)
					+ this.frameStartY;
			float bottom = (float) ((this.highestPrice - entity.getOpenPrice()) * pixelsInPrice)
					+ this.frameStartY;
			canvas.drawRect(left, top, right, bottom, kPaintRed);

			// 记录柱子的一些坐标，供十字线使用
			entity.setCenterX(pointX);
			entity.setCloseY(top);
		} else if (entity.getClosePrice() < entity.getOpenPrice()) {
			// 如果收盘价低于开盘价，下跌，绿色柱子
			canvas.drawLine(pointX, lineStartY, pointX, lineEndY, kPaintGreen);
			float left = pointX - this.kPointWidth / 2;
			float right = pointX + this.kPointWidth / 2;
			float top = (float) ((this.highestPrice - entity.getOpenPrice()) * pixelsInPrice)
					+ this.frameStartY;
			float bottom = (float) ((this.highestPrice - entity.getClosePrice()) * pixelsInPrice)
					+ this.frameStartY;
			canvas.drawRect(left, top, right, bottom, kPaintGreen);

			// 记录柱子的一些坐标，供十字线使用
			entity.setCenterX(pointX);
			entity.setCloseY(bottom);
		} else {
			// 如果收盘价等于开盘价，红色横线
			float left = pointX - this.kPointWidth / 2;
			float right = pointX + this.kPointWidth / 2;
			float top = (float) ((this.highestPrice - entity.getOpenPrice()) * pixelsInPrice)
					+ this.frameStartY;
			canvas.drawLine(pointX, lineStartY, pointX, lineEndY, kPaintRed);
			canvas.drawLine(left, top, right, top, kPaintRed);

			// 记录柱子的一些坐标，供十字线使用
			entity.setCenterX(pointX);
			entity.setCloseY(top);
		}
	}

	public void drawPMA(Canvas canvas) {
		float kPointWidthFixed = this.kPointWidth + 1;// 每个柱子的宽度，加2表示柱子左右的间隙
		Path path5 = new Path();
		Path path10 = new Path();
		Path path20 = new Path();
		Path path30 = new Path();
		float pixelsInPrice = (float) (this.ySpacing / this.priceSpacing);// 1块钱占的像素数
		for (int i = curPointList.size() - 1; i >= 0; i--) {
			KPointEntity item = curPointList.get(i);
			float pointX = kPointWidthFixed * i + kPointWidthFixed / 2
					+ getMarginLeft();// 整个poiont的x坐标是固定的
			float pointY5 = (float) ((this.highestPrice - item.getPma5()) * pixelsInPrice)
					+ this.frameStartY;
			float pointY10 = (float) ((this.highestPrice - item.getPma10()) * pixelsInPrice)
					+ this.frameStartY;
			float pointY20 = (float) ((this.highestPrice - item.getPma20()) * pixelsInPrice)
					+ this.frameStartY;
			float pointY30 = (float) ((this.highestPrice - item.getPma30()) * pixelsInPrice)
					+ this.frameStartY;
			if (i == curPointList.size() - 1) {
				path5.moveTo(pointX, pointY5);
				path10.moveTo(pointX, pointY10);
				path20.moveTo(pointX, pointY20);
				path30.moveTo(pointX, pointY30);
			} else {
				if (item.getPma5() > 0) {
					path5.lineTo(pointX, pointY5);
				}
				if (item.getPma10() > 0) {
					path10.lineTo(pointX, pointY10);
				}
				if (item.getPma20() > 0) {
					path20.lineTo(pointX, pointY20);
				}
				if (item.getPma30() > 0) {
					path30.lineTo(pointX, pointY30);
				}
			}
		}
		canvas.drawPath(path5, pma5Paint);
		canvas.drawPath(path10, pma10Paint);
		canvas.drawPath(path20, pma20Paint);
		canvas.drawPath(path30, pma30Paint);
	}

	/**
	 * 清空view，只留下框子
	 */
	public void clearView() {

		clean = true;
		invalidate();
	}

	/**
	 * 重新绘制整个view
	 *
	 * @param selectedKPoint
	 *            可以为null，为null时横向文字部分显示最后一个柱子，非null时显示该柱子的信息
	 */
	private  void refreshKLine(Canvas canvas) {
		setEndPoint();
		List<KPointEntity> allPointList=this.allPointList;
		int raw_size=allPointList.size();//allPointList在刷新时长度会变，这里先取下长度;
		boolean hasLoadedAllPointList=this.hasLoadedAllPointList;
		canvas.drawColor(
				context.getResources().getColor(
						R.color.white),
				PorterDuff.Mode.SRC);

		drawFrame(canvas);
		if (raw_size > 0) {
			initCurPositionList(raw_size);
			if (selectedKPoint == null) {
				drawTopText(canvas,this.curPointList.get(this.curPointList.size() - 1));// 画PMA的数字，默认的画最后一个柱子
				if (hasLoadedAllPointList) {
					//drawMidleText(canvas,this.curPointList.get(this.curPointList.size() - 1),raw_size);// 画指标数字
				}
			} else {
				drawTopText(canvas, selectedKPoint);// 画PMA的数字，绘制传过来的柱子
				if (hasLoadedAllPointList) {
					//drawMidleText(canvas, selectedKPoint,raw_size);// 画指标数字
				}
			}
			drawLeftPriceText(canvas);// y轴文字
			//	drawBottomText(canvas,raw_size);// x轴文字
			drawAllCurrentKPpoint(canvas);
			drawPMA(canvas);
//			if (hasLoadedAllPointList) {
//				if(values!=null)
//				drawBottomQuato(values, canvas, entity,raw_size);
//				else{
//					draw_Middle_Error(canvas,entity);
//				}
//			}
		}
	}

	public void move(boolean isMoveToRight, int count) {
		// 如果柱子总数不满一屏，就不移动
		if (allPointList.size() <= this.kPointCount) {
			return;
		}
		if (isMoveToRight) {
			offset += count;
			// 有可能只差1个就到头了，而count传过来是大于1的的数，所以应该在计算完判断
			if (offset > allPointList.size() - this.kPointCount) {
				offset -= count;// 恢复原状
				return;
			}
		} else {
			offset -= count;
			// 有可能只差1个就到头了，而count传过来是大于1的的数，所以应该在计算完判断
			if (offset < 0) {
				offset += count;
				return;
			}
		}
		selectedKPoint = null;
		invalidate();
	}

	// //////////////////////////////////////////////////onTouch事件
	float startX = 0, startY, endX, endY;
	float offsetPixels;
	int mode = 0;// 触摸屏幕的点的数量
	float oldDist;
	boolean canMove = true;// 防止双指抬起时的抖动，双指抬起一个手指后禁止滑动。
	float startX2 = 0, endX2;// 用于判断从按下到抬起是否算作移动

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startX2 = event.getX();
				startY = event.getY();
				endX = event.getX();
				endX2 = event.getX();
				endY = event.getY();
				mode = 1;
				break;
			case MotionEvent.ACTION_MOVE:
//			if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//				return super.onTouchEvent(event);
//			}
				// 如果是缩放手势
				if (mode >= 2) {
					float newDist = KLineUtils.spacing(event);
					if (newDist > oldDist + 1) {
						zoom(newDist / oldDist);
						oldDist = newDist;
					}
					if (newDist < oldDist - 1) {
						zoom(newDist / oldDist);
						oldDist = newDist;
					}
				}
				else if (canMove) {
					endX = event.getX();
					endY = event.getY();
					offsetPixels = endX - startX;
					if (offsetPixels > (this.kPointWidth + 1)) {
						move(true, (int) (offsetPixels / (this.kPointWidth + 1)));
						startX = endX;
						startY = endY;
					} else if (offsetPixels < -(this.kPointWidth + 1)) {
						move(false, (int) (-offsetPixels / (this.kPointWidth + 1)));
						startX = endX;
						startY = endY;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				endX2 = event.getX();
				// 双指全都抬起后才能滑动（当然，下次需要单指触摸）
				canMove = true;
				float offset2 = endX2 - startX2;
//			if(event.getY()<this.bottomFrameStartY){
				if (Math.abs(offset2) < (this.kPointWidth + 1)) {
					// 把curPointList传给画十字线的view，让它画十字线
					view_cross_line.setCurPointList(curPointList);
					view_cross_line.startDrawCrossLine(event.getX());
					view_cross_line.setCanTouch(true);
					if (viewPager != null) {
						viewPager.setCanScroll(false);
					}
				}
//			}else {
//				//changeQuato();
//			}
				mode = 0;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				// 双指抬起一个手指后禁止滑动
				canMove = false;
				mode -= 1;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = KLineUtils.spacing(event);
				mode += 1;
				break;
		}
		return true;
	}

	/**
	 * 伸缩柱子宽度
	 *
	 * @param f
	 */
	private void zoom(float f) {
		if (K_POINT_WIDTH_DP > 30 && f >= 1) {
			return;
		}
		if (K_POINT_WIDTH_DP < 5 && f <= 1) {
			return;
		}
		K_POINT_WIDTH_DP = K_POINT_WIDTH_DP * f;
		int newKPointWidth = ScreenSizeUtil.Dp2Px(context, K_POINT_WIDTH_DP);
		float frameWidth = this.frameEndX - this.frameStartX;
		float delta = frameWidth % (newKPointWidth + 1);
		if (delta < 5) {
			kPointWidth = newKPointWidth;
			selectedKPoint = null;
			invalidate();
		}
	}

	/**
	 * 添加新柱子以后，计算涨跌并刷新
	 */
	public void afterAppendNewKPoints(List<KPointEntity> newPointsList) {
		// 根据前一个点计算涨跌，此处应该只计算后面的
		KLineUtils.calUpOrDown(this.allPointList,
				(allPointList.size() - newPointsList.size()));
		selectedKPoint = null;
		invalidate();
	}

	// ///////////////////////////////////////getters and setters
	public void setAllPointList(final KLineEntity entity) {

		AsyncTask<Void, Void, Boolean> aync=new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				if(!entity.isNeedCalUporDown()){
					allPointList = entity.getRecordList();
					return true;
				}
				ActivityUtils.initStartTime(System.currentTimeMillis());
				allPointList = entity.getRecordList();
				KLineUtils.calUpOrDown(allPointList);
				entity.setNeedCalUporDown(false);
				return null;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				selectedKPoint = null;
				invalidate();
			}
		}.execute();
		//ku.addAsynTask(this,aync);
	}

	public List<KPointEntity> getAllPointList() {
		return allPointList;
	}

	/**
	 * 重置一些重要数值
	 */
	public void reset() {
		this.allPointList = new ArrayList<KPointEntity>();// 所有柱子
		this.curPointList = new ArrayList<KPointEntity>();// 当前显示的柱子
		this.offset = 0;// 因为滑动引起的偏移量，单位：柱子
	}

	public void setView_cross_line(KLineCrossLineView view_cross_line) {
		// 设置滑动十字线时的回调函数
		view_cross_line.setOnKPointSelectedListener(this);
		this.view_cross_line = view_cross_line;
	}

	public void setViewPager(CustomViewPager viewPager) {
		this.viewPager = viewPager;
	}

	private float getMarginLeft() {
		return marginLeftPortrait;
	}

	@Override
	public void onKPointSelected(KPointEntity kPoint) {
		selectedKPoint = kPoint;
		invalidate();
	}

	public KLineViewCallBack getkLineViewCallBack() {
		return kLineViewCallBack;
	}

	public void setkLineViewCallBack(KLineViewCallBack kLineViewCallBack) {
		this.kLineViewCallBack = kLineViewCallBack;
	}

	/**
	 * 统一获取商品编号
	 */
	private int getCommodityNumber() {
		return mApplication.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getCurrentCommodityNumber();
	}

	@Override
	public void onClearCrossLine() {
		selectedKPoint = null;
		invalidate();
	}


	private void initPaint() {
		this.linePaint = new Paint();
		this.linePaint.setAntiAlias(true);
		this.linePaint.setColor(getResources().getColor(R.color.text_grey));
		this.linePaint.setStrokeWidth(1);

		this.linePaintDashed = new Paint();
		this.linePaintDashed.setAntiAlias(true);
		this.linePaintDashed.setColor(getResources().getColor(R.color.text_grey));
		this.linePaintDashed.setStrokeWidth(1);
		PathEffect effects = new DashPathEffect(new float[] { 4, 4, 4, 4 }, 1);
		this.linePaintDashed.setPathEffect(effects);

		this.kPaintRed = new Paint();
		this.kPaintRed.setAntiAlias(true);
		this.kPaintRed.setColor(getResources().getColor(
				R.color.text_red));
		this.kPaintRed.setStrokeWidth(1.0f);

		this.kPaintGreen = new Paint();
		this.kPaintGreen.setAntiAlias(true);
		this.kPaintGreen.setColor(getResources().getColor(
				R.color.text_green));
		this.kPaintGreen.setStrokeWidth(1.0f);

		this.greenTextPaint = new Paint();
		this.greenTextPaint.setAntiAlias(true);
		this.greenTextPaint.setColor(getResources().getColor(
				R.color.text_green));
		this.greenTextPaint.setStrokeWidth(1);
		this.greenTextPaint.setTextSize(20);// 字号在后面会根据小格的宽度重新设定，此处无效

		this.whiteTextPaint = new Paint();
		this.whiteTextPaint.setAntiAlias(true);
		this.whiteTextPaint.setColor(getResources().getColor(
				R.color.black));
		this.whiteTextPaint.setStrokeWidth(1);
		this.whiteTextPaint.setTextAlign(Align.RIGHT);// 这是默认的，注意：每次使用之前都需要手动设定对齐方向！
		this.whiteTextPaint.setTextSize(20);// 字号在后面会根据小格的宽度重新设定，此处无效

		int dp1 = ScreenSizeUtil.Dp2Px(context, 1);
		this.pma5Paint = new Paint();
		this.pma5Paint.setAntiAlias(true);
		this.pma5Paint.setColor(getResources().getColor(
				R.color.kline_pma5));
		this.pma5Paint.setStrokeWidth(dp1);
		this.pma5Paint.setStyle(Paint.Style.STROKE);

		this.zdygs_quato_paint = new Paint();
		this.zdygs_quato_paint.setAntiAlias(true);
		this.zdygs_quato_paint.setStrokeWidth(dp1);
		this.zdygs_quato_paint.setStyle(Paint.Style.STROKE);

		this.pma10Paint = new Paint();
		this.pma10Paint.setAntiAlias(true);
		this.pma10Paint.setColor(getResources().getColor(
				R.color.kline_pma10));
		this.pma10Paint.setStrokeWidth(dp1);
		this.pma10Paint.setStyle(Paint.Style.STROKE);

		this.pma20Paint = new Paint();
		this.pma20Paint.setAntiAlias(true);
		this.pma20Paint.setColor(getResources().getColor(
				R.color.kline_pma20));
		this.pma20Paint.setStrokeWidth(dp1);
		this.pma20Paint.setStyle(Paint.Style.STROKE);

		this.pma30Paint = new Paint();
		this.pma30Paint.setAntiAlias(true);
		this.pma30Paint.setColor(getResources().getColor(
				R.color.kline_pma30));
		this.pma30Paint.setStrokeWidth(dp1);
		this.pma30Paint.setStyle(Paint.Style.STROKE);



		// 文字
		this.pma5TextPaint = new Paint();
		this.pma5TextPaint.setAntiAlias(true);
		this.pma5TextPaint.setColor(getResources().getColor(
				R.color.kline_pma5));
		this.pma5TextPaint.setTextSize(20);

		this.pma10TextPaint = new Paint();
		this.pma10TextPaint.setAntiAlias(true);
		this.pma10TextPaint.setColor(getResources().getColor(
				R.color.kline_pma10));
		this.pma10TextPaint.setTextSize(20);

		this.pma20TextPaint = new Paint();
		this.pma20TextPaint.setAntiAlias(true);
		this.pma20TextPaint.setColor(getResources().getColor(
				R.color.kline_pma20));
		this.pma20TextPaint.setTextSize(20);

		this.pma30TextPaint = new Paint();
		this.pma30TextPaint.setAntiAlias(true);
		this.pma30TextPaint.setColor(getResources().getColor(
				R.color.kline_pma30));
		this.pma30TextPaint.setTextSize(20);


	}
}
