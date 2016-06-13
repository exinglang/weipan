package com.puxtech.weipan.util;


import com.puxtech.weipan.data.entitydata.KPointEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 各种公式计算
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-1-10 上午9:54:07
 * @version V1.0
 * @FileName FormulaUtil.java
 * @Package com.happyinsource.htjy.android.util
 */
public class FormulaUtil {
	
	public static final int EMA_SHORT = 12;
	public static final int EMA_LONG = 26;
	public static final int EMA_M = 9;

	/**
	 * 计算所有PMA
	 * 
	 * @param allKPoint
	 * @return
	 */
	public List<KPointEntity> calAllPMA(List<KPointEntity> allKPoint) {
		allKPoint = calPMA(allKPoint, 5);
		allKPoint = calPMA(allKPoint, 10);
		allKPoint = calPMA(allKPoint, 20);
		allKPoint = calPMA(allKPoint, 30);
		return allKPoint;
	}

	/**
	 * 计算PMA
	 * 
	 * @param allKPoint
	 *            所有柱子的集合
	 * @param count
	 *            可以传5，10，20，30
	 * @return
	 */
	public List<KPointEntity> calPMA(List<KPointEntity> allKPoint, int count) {
		int total = allKPoint.size();
		for (int i = total - 1; i > count - 2; i--) {
			KPointEntity currentPoint = allKPoint.get(i);
			double totalPrice = 0.0;
			for (int j = 0; j < count; j++) {
				KPointEntity point = allKPoint.get(i - j);
				totalPrice = totalPrice + point.getClosePrice();
			}
			double pma = totalPrice / count;
			switch (count) {
			case 5:
				currentPoint.setPma5(pma);
				break;
			case 10:
				currentPoint.setPma10(pma);
				break;
			case 20:
				currentPoint.setPma20(pma);
				break;
			case 30:
				currentPoint.setPma30(pma);
				break;
			default:
				break;
			}
		}
		return allKPoint;
	}

/*	public List<KPointEntity> calLastPointAllPMA(List<KPointEntity> allKPoint) {
		calLastPointPMA(allKPoint, 5);
		calLastPointPMA(allKPoint, 10);
		calLastPointPMA(allKPoint, 20);
		calLastPointPMA(allKPoint, 30);
		return allKPoint;
	}

	public List<KPointEntity> calLastPointPMA(List<KPointEntity> allKPoint,
			int count) {
		KPointEntity currentPoint = allKPoint.get(allKPoint.size() - 1);// 取出最后一个
		double totalPrice = 0.0;
		if(allKPoint.size() < count){
			switch (count) {
			case 5:
				currentPoint.setPma5(0);
				break;
			case 10:
				currentPoint.setPma10(0);
				break;
			case 20:
				currentPoint.setPma20(0);
				break;
			case 30:
				currentPoint.setPma30(0);
				break;
			default:
				break;
			}
			return allKPoint;
		}
		for (int j = 0; j < count; j++) {
			KPointEntity point = allKPoint.get(allKPoint.size() - 1 - j);
			totalPrice = totalPrice + point.getClosePrice();
		}
		double pma = totalPrice / count;
		switch (count) {
		case 5:
			currentPoint.setPma5(pma);
			break;
		case 10:
			currentPoint.setPma10(pma);
			break;
		case 20:
			currentPoint.setPma20(pma);
			break;
		case 30:
			currentPoint.setPma30(pma);
			break;
		default:
			break;
		}
		return allKPoint;
	}*/

	/**
	 * 计算所有柱子的MACD
	 * @param allKPoint
	 * @return
	 */
	public List<KPointEntity> calAllPointMACD(List<KPointEntity> allKPoint) {
		
		for (int i = 0; i < allKPoint.size(); i++) {
			KPointEntity item = allKPoint.get(i);
			if(i == 0){
				item.setShortEMA(item.getClosePrice());
				item.setLongEMA(item.getClosePrice());
				item.setDif(0);
				item.setDea(0);
				item.setMacd(0);
			}
			else{
				KPointEntity preItem = allKPoint.get(i-1);
				item.setShortEMA(calEMA(item.getClosePrice(), EMA_SHORT, preItem.getShortEMA()));
				item.setLongEMA(calEMA(item.getClosePrice(), EMA_LONG, preItem.getLongEMA()));
				item.setDif(item.getShortEMA() - item.getLongEMA());
				item.setDea(calEMA(item.getDif(), EMA_M, preItem.getDea()));
				item.setMacd(2 * (item.getDif() - item.getDea()));
			}
		}
		return allKPoint;
	}
	
	/**
	 * 计算新点的macd
	 * @param allKPoint
	 * @param newKPoints
	 * @return
	 */
	public List<KPointEntity> calNewPointsMACD(List<KPointEntity> allKPoint, List<KPointEntity> newKPoints){
		List<KPointEntity> totalList = new ArrayList<KPointEntity>();
		totalList.addAll(allKPoint);
		totalList.addAll(newKPoints);
		for (int i = allKPoint.size(); i < totalList.size(); i++) {
			KPointEntity item = totalList.get(i);
			if(i == 0){
				item.setShortEMA(item.getClosePrice());
				item.setLongEMA(item.getClosePrice());
				item.setDif(0);
				item.setDea(0);
				item.setMacd(0);
			}
			else{
				KPointEntity preItem = totalList.get(i-1);
				item.setShortEMA(calEMA(item.getClosePrice(), EMA_SHORT, preItem.getShortEMA()));
				item.setLongEMA(calEMA(item.getClosePrice(), EMA_LONG, preItem.getLongEMA()));
				item.setDif(item.getShortEMA() - item.getLongEMA());
				item.setDea(calEMA(item.getDif(), EMA_M, preItem.getDea()));
				item.setMacd(2 * (item.getDif() - item.getDea()));

			}
		}
		return totalList.subList(allKPoint.size(), totalList.size());//subList(start, end)截取从start到end的元素，包含start，不包含end
	}

	/**
	 * 计算EMA(X, N)
	 * @param X
	 * @param N
	 * @param preEMA 前一个EMA
	 * @return
	 */
	private double calEMA(double X, int N, double preEMA) {
		double ema = (2 * X + (N - 1) * preEMA) / (N + 1);
		return ema;
	}
	
	/**
	 * 计算KDJ(9,3,3)
	 * @param allKPoint
	 * @return
	 */
	public List<KPointEntity> calAllPointKDJ(List<KPointEntity> allKPoint){
		for (int i = 0; i < allKPoint.size(); i++) {
			KPointEntity item = allKPoint.get(i);
			if(i < 9){
				//前9个设为50
				item.setRsv(0);
				item.setK(50);
				item.setD(50);
				item.setJ(50);
			}
			else{
				//找出9日内最大值和最小值
				double min = item.getLowestPrice();
				double max = item.getHighestPrice();
				for (int j = i-8; j < i; j++) {
					KPointEntity point = allKPoint.get(j);
					if(point.getLowestPrice() < min){
						min = point.getLowestPrice();
					}
					if(point.getHighestPrice() > max){
						max = point.getHighestPrice();
					}
				}
				double rsv = calRSV(item.getClosePrice(), min, max);
				KPointEntity preItem = allKPoint.get(i-1);
				double k = calSMA(rsv, 3, 1, preItem.getK());//K = SMA(RSV,3,1)
				double d = calSMA(k, 3, 1, preItem.getD());//D = SMA(K,3,1)
				double j = (3*k)-(2*d);//J = 3*K-2*D
				//不能超过上下边界
				if(j >= 100){
					j = 100;
				}
				if(j < 0){
					j = 0;
				}
				item.setRsv(rsv);
				item.setK(k);
				item.setD(d);
				item.setJ(j);
			}
		}
		return allKPoint;
	}
	
	/**
	 * 计算KDJ(9,3,3)
	 * @param allKPoint
	 * @return
	 */
	public List<KPointEntity> calAllNewPointsKDJ(List<KPointEntity> allKPoint, List<KPointEntity> newKPoints){
		List<KPointEntity> totalList = new ArrayList<KPointEntity>();
		totalList.addAll(allKPoint);
		totalList.addAll(newKPoints);
		for (int i = allKPoint.size(); i < totalList.size(); i++) {
			KPointEntity item = totalList.get(i);
			if(i < 9){
				//前9个设为50
				item.setRsv(0);
				item.setK(50);
				item.setD(50);
				item.setJ(50);
			}
			else{
				//找出9日内最大值和最小值
				double min = item.getLowestPrice();
				double max = item.getHighestPrice();
				for (int j = i-8; j < i; j++) {
					KPointEntity point = totalList.get(j);
					if(point.getLowestPrice() < min){
						min = point.getLowestPrice();
					}
					if(point.getHighestPrice() > max){
						max = point.getHighestPrice();
					}
				}
				double rsv = calRSV(item.getClosePrice(), min, max);
				KPointEntity preItem = totalList.get(i-1);
				double k = calSMA(rsv, 3, 1, preItem.getK());//K = SMA(RSV,3,1)
				double d = calSMA(k, 3, 1, preItem.getD());//D = SMA(K,3,1)
				double j = (3*k)-(2*d);//J = 3*K-2*D
				//不能超过上下边界
				if(j >= 100){
					j = 100;
				}
				if(j < 0){
					j = 0;
				}
				item.setRsv(rsv);
				item.setK(k);
				item.setD(d);
				item.setJ(j);
			}
		}
		return totalList.subList(allKPoint.size(), totalList.size());//subList(start, end)截取从start到end的元素，包含start，不包含end;
	}
	
	/**
	 * 计算RSV
	 * @param close
	 * @param min
	 * @param max
	 * @return
	 */
	private double calRSV(double close, double min, double max){
		//如果max - min为0，就赋一个最小值
		double float2 = max - min;
		if(float2 == 0){
			float2 = 1f/(float) Long.MAX_VALUE;
		}
		double rsv = (close - min)/float2 * 100;
		return rsv;
	}
	
	/**
	 * SMA(X,N,M)
	 * @param M
	 * @param X
	 * @param N
	 * @param preSMA
	 * @return
	 */
	private double calSMA(double X, int N, int M, double preSMA){
		double sma = (M * X + (N - 1) * preSMA) / N;
		return sma;
	}

}
