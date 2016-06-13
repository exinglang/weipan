package com.puxtech.weipan.data.entitydata;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 分时数据实体类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2013-12-27 下午5:33:56
 * @version V1.0
 * @FileName TimeLineEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class TimeLineEntity {

	int number;// 商品编号
	int cycle;// 周期
	double todayOpenPrice;// 开盘价
	double yesterdayClosePrice;// 昨收盘价
	double highestPrice;// 最高价
	long highestTime;// 最高价时间
	double lowestPrice;// 最低价
	long lowestTime;// 最低价时间
	int recordNum;// 总记录数
	int days;// 交易日天数
	List<TimePointEntity> recordList;//记录集合，每分钟一个
	List<String> zipUrls;
	short marketNumber;
	short envNumber;
	public short getMarketNumber() {
		return marketNumber;
	}
	public void setMarketNumber(short marketNumber) {
		this.marketNumber = marketNumber;
	}

	public short getEnvNumber() {
		return envNumber;
	}

	public void setEnvNumber(short envNumber) {
		this.envNumber = envNumber;
	}

	public List<String> getZipUrls() {
		return zipUrls;
	}

	public void setZipUrls(List<String> zupUrls) {
		this.zipUrls = zupUrls;
	}


	/**
	 * 刷新除了recordList和recordNum之外的其他内容
	 * @param entity
	 */
	public void refreshData(TimeLineEntity entity){
		this.number = entity.getNumber();
		this.cycle = entity.getCycle();
		this.todayOpenPrice = entity.getTodayOpenPrice();
		this.yesterdayClosePrice = entity.getYesterdayClosePrice();
		this.highestPrice = entity.getHighestPrice();
		this.highestTime = entity.getHighestTime();
		this.lowestPrice = entity.getLowestPrice();
		this.lowestTime = entity.getLowestTime();
		this.days = entity.getDays();
		this.zipUrls=entity.getZipUrls();
	}
	
	public int getHightestVolume(){
		
		int highestVolume=-1;
		for (int i = 0; i < getRecordList().size(); i++) {
			
			int volume = getRecordList().get(i).getVolume();
			if (volume > highestVolume) {
				highestVolume = volume;
			} 

		}
		return highestVolume;
		
	}
	public int getTotalVolume(){
		
		int totalVolume=0;
		for (int i = 0; i < getRecordList().size(); i++) {
			
			int volume = getRecordList().get(i).getVolume();
			totalVolume+=volume;

		}
		return totalVolume;
		
	}
	public List<TimePointEntity> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<TimePointEntity> recordList) {
		this.recordList = recordList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public double getTodayOpenPrice() {
		return todayOpenPrice;
	}

	public void setTodayOpenPrice(double todayOpenPrice) {
		this.todayOpenPrice = todayOpenPrice;
	}

	public double getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}

	public void setYesterdayClosePrice(double yestodayClosePrice) {
		
		this.yesterdayClosePrice = yestodayClosePrice;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public long getHighestTime() {
		return highestTime;
	}

	public void setHighestTime(long highestTime) {
		this.highestTime = highestTime;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public long getLowestTime() {
		return lowestTime;
	}

	public void setLowestTime(long lowestTime) {
		this.lowestTime = lowestTime;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
