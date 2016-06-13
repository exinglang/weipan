package com.puxtech.weipan.util;


import android.content.Context;
import android.os.AsyncTask;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.puxtech.weipan.data.entitydata.KPointEntity;

import java.text.DecimalFormat;
import java.util.List;

public class KLineUtils {
	public boolean isDoubleValid(double d){
		if(Double.compare(d, Double.NaN)==0)return false;
		if(d== Double.POSITIVE_INFINITY)return false;
		if(d== Double.NEGATIVE_INFINITY)return false;
		return true;
	}
	public double[] getMaxMinInArray(double [] data, int start, int end){
		double[]mm =new double[2];//0 min 1 max
		mm[0]= Double.NaN;
		mm[1]= Double.NaN;
		double d;
		for (int i = start; i < end; i++) {
			d=data[i];
			if(isDoubleValid(d)){
				mm[0]=d;
				mm[1]=d;
			}
		}
		if(!isDoubleValid(mm[0]))return mm;
		for (int i = start; i < end; i++) {
			d=data[i];
			if(!isDoubleValid(d))continue;
			if(mm[0]>d){
				mm[0]=d;
			}
			if(mm[1]<d){
				mm[1]=d;
			}
		}
		return mm;
	}
	/**
	 * 计算涨跌
	 *
	 * @param list
	 * @param start
	 *            只计算从start往后的所有点,包括start
	 */
	public static void calUpOrDown(List<KPointEntity> list, int start) {
		KPointEntity item ;
		if (start <= 0) {
			item= list.get(0);
			item.setUpOrDown(0);
			item.setChange("0.00%");
			item.setHighUpOrDown(0);
			item.setLowUpOrDown(0);
			item.setOpenUpOrDown(0);
			return;
		}
		DecimalFormat ddf = new DecimalFormat("0.00");
		double aft,pre,diff,change;String changeStr;
		for (int i = start; i < list.size(); i++) {
			item = list.get(i);
			// 计算涨跌幅
			aft = item.getClosePrice();
			pre = list.get(i - 1).getClosePrice();
			diff = aft - pre;
			item.setUpOrDown(diff < 0 ? -1 : diff > 0 ? 1 : 0);
			change  = diff / pre * 100;
			changeStr = ddf.format(change);
			if (diff < 0) {
				item.setChange(changeStr + "%");
			} else if (diff > 0) {
				item.setChange("+" + changeStr + "%");
			} else {
				item.setChange(changeStr + "%");
			}
			// 计算价格颜色
			// 最高价
			diff = item.getHighestPrice() - pre;
			item.setHighUpOrDown(diff < 0 ? -1 : diff > 0 ? 1 : 0);
			// 最低价
			diff = item.getLowestPrice() - pre;
			item.setLowUpOrDown(diff < 0 ? -1 : diff > 0 ? 1 : 0);
			// 开盘价
			diff = item.getOpenPrice() - pre;
			item.setOpenUpOrDown(diff < 0 ? -1 : diff > 0 ? 1 : 0);
		}
	}

	/**
	 * 计算涨跌
	 */
	public static void calUpOrDown(List<KPointEntity> list) {
		calUpOrDown(list, 1);
	}

	/**
	 * 计算两指之间的距离
	 *
	 * @param event
	 * @return
	 */
	public static float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}
//	public double[] getMaxMin(List<ExpressionValue> values, int start, int end){
//		double[] data;
//		int pathCount=0;
//		for (ExpressionValue val:values) {
//			if(val.isNeedDraw())
//			pathCount++;
//		}
//		double [] temp=new double[pathCount*2];
//		double[]mm;
//		int index=0;
//		for (ExpressionValue value:values) {
//			if(value.isNeedDraw()){
//				data=(double[]) value.getData();
//				mm= getMaxMinInArray(data,start,end);
//				if(isDoubleValid(mm[0])){
//				System.arraycopy(mm, 0, temp, index, mm.length);
//				index+=2;
//				}
//			}
//		}
//		return getMaxMinInArray(temp,0,temp.length);
	//}



//	public void addAsynTask(SurfaceView v,AsyncTask<Void, Void, Boolean> aync) {
//		Context ctx=v.getContext();
//		if(ctx instanceof PriceDetailActivity){
//			PriceDetailActivity pda=(PriceDetailActivity)ctx;
//			pda.putAsyncTask(aync);
//		}
//	}
}
