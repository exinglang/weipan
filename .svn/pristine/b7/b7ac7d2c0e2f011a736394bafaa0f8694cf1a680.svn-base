package com.puxtech.weipan.data.entitydata;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 一段K线的实体类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-1-2 上午10:05:25
 * @version V1.0
 * @FileName KLineEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class KLineEntity {
	protected int number;// 商品编号
	protected int cycle;// k线周期
	private int recordNum;// 记录数
	ArrayList<KPointEntity> recordList;// 柱子集合
	private List<String> zipURLs;
	private int zipUrlsCursor;//指到当前下一次要下载zip包的地址
	private short marketNumber;
	private short envNumber;
	private boolean needCalUporDown=true;
	public boolean isNeedCalUporDown() {
		return needCalUporDown;
	}
	public void setNeedCalUporDown(boolean needCalUporDown) {
		this.needCalUporDown = needCalUporDown;
	}
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
	public  int getByteBufferSize(){
		int size = 0;
		size = size + 8;//number
		size = size + 8;//cycle
		size = size + 8;//recordNum
		size = size + 8;//zipUrlsCursor
		if(recordList!=null){
			size+=recordList.size()*KPointEntity.getByteBufferSize();
		}
		if(zipURLs!=null){
			for (String s : zipURLs) {
				size+=s.getBytes().length;
			}
		}
		return size;
	}
	public  int getByteBufferSizeInMemory(){
		int size=getByteBufferSize();
		size = size + 4;//marketNumber envNumber
		return size;
	}
	public List<String> getZipURLs() {
		return zipURLs;
	}
	public int getZipUrlsCursor() {
		return zipUrlsCursor;
	}
	public void setZipURLs(List<String> zipURLs) {
		if(zipURLs==null){
			zipUrlsCursor=-1;
			return;
		}
		this.zipURLs = zipURLs;
		if(zipURLs.size()>0)
		zipUrlsCursor=zipURLs.size()-1;
	}

	public boolean hasDownUrl() {
		if (getCurDayZipURL() != null) {
			zipUrlsCursor++;
			return true;
		}
		return false;
	}
	public String getCurDayZipURL() {
		if(zipURLs==null||zipURLs.size()==0)return null;
		if(zipUrlsCursor<0||zipUrlsCursor>=zipURLs.size())return null;
		return zipURLs.get(zipUrlsCursor--);
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

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public List<KPointEntity> getRecordList() {
		return recordList;
	}

	public void setRecordList(ArrayList<KPointEntity> recordList) {
		this.recordList = recordList;
	}

}
