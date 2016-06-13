package com.puxtech.weipan.data.entitydata;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <b>Description:</b>
 * <p>
 * K线的一个柱子的实体类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-1-2 上午10:03:12
 * @version V1.0
 * @FileName KPointEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class KPointEntity {
	// 服务器返回的原始数据
	long time;// k线时间,yyyyMMddHHmm
	double openPrice;// 开盘价
	double closePrice;// 收盘价
	double highestPrice;// 最高价
	double lowestPrice;// 最低价
	int volume;// 现手
	long priceId;

	// 供十字线使用
	float centerX;// 此柱子的中心x轴坐标
	float closeY;// 此柱子的收盘y坐标
	private String change = "0.00%";// 改变的比率
	private int upOrDown = 0;// 涨1/跌-1/平0
	private int highUpOrDown = 0; // 涨1/跌-1/平0;
	private int lowUpOrDown = 0; // 涨1/跌-1/平0;
	private int openUpOrDown = 0; // 涨1/跌-1/平0;

	// 本地计算的数据
	double pma5;
	double pma10;
	double pma20;
	double pma30;

	// MACD
	double shortEMA;
	double longEMA;
	double dif;
	double dea;
	double macd;

	// KDJ
	double rsv;
	double k;
	double d;
	double j;

	// 供程序方便使用
	int commodityNumber;
	int byteBufferSize;

	public KPointEntity() {
		super();
		byteBufferSize = getByteBufferSize();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
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
		KPointEntity other = (KPointEntity) obj;
		if (time != other.time)
			return false;
		return true;
	}
	/**
	 * 返回要缓存的所有字段的总大小
	 * @return
	 */
	public static int getByteBufferSize(){
		int size = 0;
		size = size + 8;//time
		size = size + 8;//openPrice
		size = size + 8;//closePrice
		size = size + 8;//highestPrice
		size = size + 8;//lowestPrice
		size = size + 4;//volume
		size = size + 8;//priceId
		size = size + 8;//pma5
		size = size + 8;//pma10
		size = size + 8;//pma20
		size = size + 8;//pma30
		size = size + 8;//shortEMA
		size = size + 8;//longEMA
		size = size + 8;//dif
		size = size + 8;//dea
		size = size + 8;//macd
		size = size + 8;//rsv
		size = size + 8;//k
		size = size + 8;//d
		size = size + 8;//j
		return size;
	}
	
	/**
	 * 返回要缓存的所有字段的总大小
	 * @return
	 */
	public static int getByteBufferSizeInMemory(){
		int size=getByteBufferSize();
		size+=32;
		size+=5;
		return size;
	}
	
	/**
	 * 刷新所有数值
	 * 
	 * @param newEntity
	 *            从服务器获取的新数值
	 */
	public void refreshSourceData(KPointEntity newEntity) {
		this.time = newEntity.getTime();
		this.openPrice = newEntity.getOpenPrice();
		this.closePrice = newEntity.getClosePrice();
		this.highestPrice = newEntity.getHighestPrice();
		this.lowestPrice = newEntity.getLowestPrice();
		this.volume = newEntity.getVolume();
		// 计算出来的
		this.pma5 = newEntity.getPma5();
		this.pma10 = newEntity.getPma10();
		this.pma20 = newEntity.getPma20();
		this.pma30 = newEntity.getPma30();
		this.shortEMA = newEntity.getShortEMA();
		this.longEMA = newEntity.getLongEMA();
		this.dif = newEntity.getDif();
		this.dea = newEntity.getDea();
		this.macd = newEntity.getMacd();
		this.rsv = newEntity.getRsv();
		this.k = newEntity.getK();
		this.d = newEntity.getD();
		this.j = newEntity.getJ();
	}

	/**
	 * 返回yyyy-mm-dd
	 * 
	 * @return
	 */
	public String getFormatDate() {
		try {
			
//			long time = TimeUtil.getTimeByString(this.time + "");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			String t = format.format(new Date(this.time));
			return t.split(" ")[0];
		} catch (Exception e) {
			e.printStackTrace();
			return "0000-00-00";
		}
	}

	/**
	 * 返回HH:mm
	 * 
	 * @return
	 */
	public String getFormatTime() {
		try {
//			long time = TimeUtil.getTimeByString(this.time + "");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			String t = format.format(new Date(this.time));
			String timeStr = t.split(" ")[1];
			return timeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "00:00";
		}
	}
	
	/**
	 * 将需要缓存的数据按顺序组装成byte数组
	 * @return
	 */
	public byte[] buildByteArray(){
		ByteBuffer buffer = ByteBuffer.allocate(byteBufferSize);
		buffer.putLong(time);
		buffer.putDouble(openPrice);
		buffer.putDouble(closePrice);
		buffer.putDouble(highestPrice);
		buffer.putDouble(lowestPrice);
		buffer.putInt(volume);
		buffer.putLong(priceId);
		buffer.putDouble(pma5);
		buffer.putDouble(pma10);
		buffer.putDouble(pma20);
		buffer.putDouble(pma30);
		buffer.putDouble(shortEMA);
		buffer.putDouble(longEMA);
		buffer.putDouble(dif);
		buffer.putDouble(dea);
		buffer.putDouble(macd);
		buffer.putDouble(rsv);
		buffer.putDouble(k);
		buffer.putDouble(d);
		buffer.putDouble(j);
		return buffer.array();
	}
	
	// /////////////////////getter setter

	public double getRsv() {
		return rsv;
	}

	public void setRsv(double rsv) {
		this.rsv = rsv;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getJ() {
		return j;
	}

	public void setJ(double j) {
		this.j = j;
	}

	public double getShortEMA() {
		return shortEMA;
	}

	public void setShortEMA(double shortEMA) {
		this.shortEMA = shortEMA;
	}

	public double getLongEMA() {
		return longEMA;
	}

	public void setLongEMA(double longEMA) {
		this.longEMA = longEMA;
	}

	public double getDif() {
		return dif;
	}

	public void setDif(double dif) {
		this.dif = dif;
	}

	public double getDea() {
		return dea;
	}

	public void setDea(double dea) {
		this.dea = dea;
	}

	public double getMacd() {
		return macd;
	}

	public void setMacd(double macd) {
		this.macd = macd;
	}

	public double getPma5() {
		return pma5;
	}

	public void setPma5(double pma5) {
		this.pma5 = pma5;
	}

	public double getPma10() {
		return pma10;
	}

	public void setPma10(double pma10) {
		this.pma10 = pma10;
	}

	public double getPma20() {
		return pma20;
	}

	public void setPma20(double pma20) {
		this.pma20 = pma20;
	}

	public double getPma30() {
		return pma30;
	}

	public void setPma30(double pma30) {
		this.pma30 = pma30;
	}

	public long getTime() {
		return time;
		
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCloseY() {
		return closeY;
	}

	public void setCloseY(float closeY) {
		this.closeY = closeY;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public int getUpOrDown() {
		return upOrDown;
	}

	public void setUpOrDown(int upOrDown) {
		this.upOrDown = upOrDown;
	}

	public int getHighUpOrDown() {
		return highUpOrDown;
	}

	public void setHighUpOrDown(int highUpOrDown) {
		this.highUpOrDown = highUpOrDown;
	}

	public int getLowUpOrDown() {
		return lowUpOrDown;
	}

	public void setLowUpOrDown(int lowUpOrDown) {
		this.lowUpOrDown = lowUpOrDown;
	}

	public int getOpenUpOrDown() {
		return openUpOrDown;
	}

	public void setOpenUpOrDown(int openUpOrDown) {
		this.openUpOrDown = openUpOrDown;
	}

	public long getPriceId() {
		return priceId;
	}

	public void setPriceId(long priceId) {
		this.priceId = priceId;
	}

}
