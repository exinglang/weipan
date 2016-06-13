package com.puxtech.weipan.data.entitydata;

/**
 * <b>Description:</b>
 * <p>
 * 分时线的一个点的数据
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2013-12-30 上午9:34:03
 * @version V1.0
 * @FileName TimePointEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class TimePointEntity {

	int tradeDate;// 交易日yyyyMMdd
	int time;// 分时时间，HHmm
	double lastPrice;// 周期最后一笔报价
	double averagePrice;// 周期内算数平均价
	int volume;// 现手，默认0
	long priceId;
	
	private float pointX, pointY, averageY;
	private float pointXLandscape, pointYLandscape, averageYLandscape;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + time;
		result = prime * result + tradeDate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimePointEntity other = (TimePointEntity) obj;
		if (time != other.time)
			return false;
		if (tradeDate != other.tradeDate)
			return false;
		return true;
	}
	public float getPointXLandscape() {
		return pointXLandscape;
	}

	public void setPointXLandscape(float pointXLandscape) {
		this.pointXLandscape = pointXLandscape;
	}

	public float getPointYLandscape() {
		return pointYLandscape;
	}

	public void setPointYLandscape(float pointYLandscape) {
		this.pointYLandscape = pointYLandscape;
	}

	public float getAverageYLandscape() {
		return averageYLandscape;
	}

	public void setAverageYLandscape(float averageYLandscape) {
		this.averageYLandscape = averageYLandscape;
	}

	public float getAverageY() {
		return averageY;
	}

	public void setAverageY(float averageY) {
		this.averageY = averageY;
	}

	public float getPointX() {
		return pointX;
	}

	public void setPointX(float pointX) {
		this.pointX = pointX;
	}

	public float getPointY() {
		return pointY;
	}

	public void setPointY(float pointY) {
		this.pointY = pointY;
	}

	public int getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(int tradeDate) {
		this.tradeDate = tradeDate;
	}

//	public int getCurrentDayRecordNum() {
//		return currentDayRecordNum;
//	}
//
//	public void setCurrentDayRecordNum(int currentDayRecordNum) {
//		this.currentDayRecordNum = currentDayRecordNum;
//	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public long getPriceId() {
		return priceId;
	}

	public void setPriceId(long priceId) {
		this.priceId = priceId;
	}

	@Override
	public String toString() {
		return "TimePointEntity [tradeDate=" + tradeDate
				+ ", time="
				+ time + ", lastPrice=" + lastPrice + ", averagePrice="
				+ averagePrice + ", volume=" + volume + ", priceId=" + priceId
				+ ", pointX=" + pointX + ", pointY=" + pointY + ", averageY="
				+ averageY + ", pointXLandscape=" + pointXLandscape
				+ ", pointYLandscape=" + pointYLandscape
				+ ", averageYLandscape=" + averageYLandscape + "]";
	}

	
}
