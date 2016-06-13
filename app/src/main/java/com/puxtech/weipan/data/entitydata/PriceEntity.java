package com.puxtech.weipan.data.entitydata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * <b>Description:</b>
 * <p>
 * 单个商品的价格实体类
 * </p>
 *
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2013-12-24 上午9:23:40
 * @version V1.0
 * @FileName PriceEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class PriceEntity implements Parcelable {


	int wuDangId;//五档序号
	ArrayList<WuDangEntity> wuDangList;
	ArrayList<FenBi> fenBiList;

	int envNumber;//环境编号
	int marketNumber;//交易所编号
	int commodityType;//盘下商品类型
	String buyguaPaiNumber;//挂牌数量
	String sellguaPaiNumber;//挂牌数量
	int number;// 商品编号
	int pankou_index;
	double price;// 最新价格
	double buy;// 买价
	double sale;// 卖价
	double high;// 最高
	double low;// 最低
	double yesterdayClosePrice;// 昨日收盘价
	double yesterdayJieSuanPrice;// 昨日结算价
	double todayOpenPrice;// 今日开盘价
	long priceTime;
	int dealCount;//成交量
	double amount;//金额
	int volume;//现手
	int buyNumber;//买量
	int saleNumber;//卖量
	int holdNumber;//	持仓量
	int cangCha;//	仓差
	int jieSuanJia;//结算价
	long neiPan;//内盘
	long waiPan;//外盘
	ArrayList<Integer> weiTuoBuyPrice = new ArrayList<Integer>();// 委托买价
	ArrayList<Integer> weiTuoSalePrice = new ArrayList<Integer>();// 委托卖价
	// 自选商品列表专用
	/**
	 * 上标，为null的时候不显示，数量为一个的时候不切换，数量为多个的时候切换
	 */
	String[] envShortNameArray;
	String name;// 商品名称
	String code;

	int todayJieSuanJia;//当日结算价
	float zhangDieFu;//涨跌幅
	int buildNumber;//建仓量
	int dropNumber;//平仓量



    public PriceEntity(String name, int number) {
        super();
        this.name = name;
        this.number = number;
    }

	public PriceEntity() {
		super();
	}

	public void refresh(PriceEntity newEntity) {
		if (newEntity == null) {
			return;
		}
		this.amount = newEntity.getAmount();
		this.buildNumber = newEntity.getBuildNumber();
		this.buy = newEntity.getBuy();
		this.buyguaPaiNumber = newEntity.getBuyguaPaiNumber();
		this.buyNumber = newEntity.getBuyNumber();
		this.cangCha = newEntity.getCangCha();
		this.code = newEntity.getCode();
		this.commodityType = newEntity.getCommodityType();
		this.dealCount = newEntity.getDealCount();
		this.dropNumber = newEntity.getDropNumber();
		this.envNumber = newEntity.getEnvNumber();
		this.envShortNameArray = newEntity.getEnvShortNameArray();
		this.fenBiList = newEntity.getFenBiList();
		this.high = newEntity.getHigh();
		this.holdNumber = newEntity.getHoldNumber();
		this.jieSuanJia = newEntity.getJieSuanJia();
		this.low = newEntity.getLow();
		this.marketNumber = newEntity.getMarketNumber();
		this.name = newEntity.getName();
		this.neiPan = newEntity.getNeiPan();
		this.number = newEntity.getNumber();
		this.pankou_index = newEntity.getPankou_index();
		this.price = newEntity.getPrice();
		this.priceTime = newEntity.getPriceTime();
		this.sale = newEntity.getSale();
		this.saleNumber = newEntity.getSaleNumber();
		this.sellguaPaiNumber = newEntity.getSellguaPaiNumber();
		this.todayJieSuanJia = newEntity.getTodayJieSuanJia();
		this.todayOpenPrice = newEntity.getTodayOpenPrice();
		this.volume = newEntity.getVolume();
		this.waiPan = newEntity.getWaiPan();
		this.weiTuoBuyPrice = newEntity.getWeiTuoBuyPrice();
		this.weiTuoSalePrice = newEntity.getWeiTuoSalePrice();
		this.wuDangId = newEntity.getWuDangId();
		this.wuDangList = newEntity.getWuDangList();
		this.yesterdayClosePrice = newEntity.getYesterdayClosePrice();
		this.yesterdayJieSuanPrice = newEntity.getYesterdayJieSuanPrice();
		this.zhangDieFu = newEntity.getZhangDieFu();
	}


	public String getBuyguaPaiNumber() {
		return buyguaPaiNumber;
	}

	public void setBuyguaPaiNumber(String buyguaPaiNumber) {
		this.buyguaPaiNumber = buyguaPaiNumber;
	}

	public String getSellguaPaiNumber() {
		return sellguaPaiNumber;
	}

	public void setSellguaPaiNumber(String sellguaPaiNumber) {
		this.sellguaPaiNumber = sellguaPaiNumber;
	}

	public int getTodayJieSuanJia() {
		return todayJieSuanJia;
	}

	public void setTodayJieSuanJia(int todayJieSuanJia) {
		this.todayJieSuanJia = todayJieSuanJia;
	}

	public float getZhangDieFu() {
		return zhangDieFu;
	}

	public void setZhangDieFu(float zhangDieFu) {
		this.zhangDieFu = zhangDieFu;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	public int getDropNumber() {
		return dropNumber;
	}

	public void setDropNumber(int dropNumber) {
		this.dropNumber = dropNumber;
	}


	public ArrayList<FenBi> getFenBiList() {
		return fenBiList;
	}

	public void setFenBiList(ArrayList<FenBi> fenBiList) {
		this.fenBiList = fenBiList;
	}
	public int getWuDangId() {
		return wuDangId;
	}

	public void setWuDangId(int wuDangId) {
		this.wuDangId = wuDangId;
	}

	public ArrayList<WuDangEntity> getWuDangList() {
		return wuDangList;
	}

	public void setWuDangList(ArrayList<WuDangEntity> wuDangList) {
		this.wuDangList = wuDangList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(int commodityType) {
		this.commodityType = commodityType;
	}

	public double getYesterdayJieSuanPrice() {
		return yesterdayJieSuanPrice;
	}

	public void setYesterdayJieSuanPrice(double yesterdayJieSuanPrice) {
		this.yesterdayJieSuanPrice = yesterdayJieSuanPrice;
	}
	public int getPankou_index() {
		return pankou_index;
	}

	public void setPankou_index(int pankou_index) {
		this.pankou_index = pankou_index;
	}
	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public int getHoldNumber() {
		return holdNumber;
	}

	public void setHoldNumber(int holdNumber) {
		this.holdNumber = holdNumber;
	}

	public int getCangCha() {
		return cangCha;
	}

	public void setCangCha(int cangCha) {
		this.cangCha = cangCha;
	}

	public int getJieSuanJia() {
		return jieSuanJia;
	}

	public void setJieSuanJia(int jieSuanJia) {
		this.jieSuanJia = jieSuanJia;
	}

	public static Parcelable.Creator<PriceEntity> getCreator() {
		return CREATOR;
	}

	public long getNeiPan() {
		return neiPan;
	}

	public void setNeiPan(long neiPan) {
		this.neiPan = neiPan;
	}

	public long getWaiPan() {
		return waiPan;
	}

	public void setWaiPan(long waiPan) {
		this.waiPan = waiPan;
	}

	public ArrayList<Integer> getWeiTuoBuyPrice() {
		return weiTuoBuyPrice;
	}

	public void setWeiTuoBuyPrice(ArrayList<Integer> weiTuoBuyPrice) {
		this.weiTuoBuyPrice = weiTuoBuyPrice;
	}

	public ArrayList<Integer> getWeiTuoSalePrice() {
		return weiTuoSalePrice;
	}

	public void setWeiTuoSalePrice(ArrayList<Integer> weiTuoSalePrice) {
		this.weiTuoSalePrice = weiTuoSalePrice;
	}

	public static final Parcelable.Creator<PriceEntity> CREATOR = new Creator<PriceEntity>() {

		@Override
		public PriceEntity createFromParcel(Parcel source) {
			PriceEntity item = new PriceEntity();


			item.setEnvNumber(source.readInt());
			item.setMarketNumber(source.readInt());
			item.setCommodityType(source.readInt());
			item.setNumber(source.readInt());
			item.setPrice(source.readDouble());
			item.setBuy(source.readDouble());
			item.setSale(source.readDouble());
			item.setHigh(source.readDouble());
			item.setLow(source.readDouble());
			item.setYesterdayClosePrice(source.readDouble());
			item.setYesterdayJieSuanPrice(source.readDouble());
			item.setTodayOpenPrice(source.readDouble());
			item.setPriceTime(source.readLong());
			item.setDealCount(source.readInt());
			item.setAmount(source.readDouble());
			item.setVolume(source.readInt());
			item.setBuyNumber(source.readInt());
			item.setSaleNumber(source.readInt());
			item.setHoldNumber(source.readInt());
			item.setCangCha(source.readInt());
			item.setJieSuanJia(source.readInt());
			item.setNeiPan(source.readLong());
			item.setWaiPan(source.readLong());
			item.setName(source.readString());
			item.setPankou_index(source.readInt());
			item.setCode(source.readString());

			ClassLoader cl=getClass().getClassLoader();
			item.setWuDangList(source.readArrayList(cl));
			item.setWeiTuoBuyPrice(source.readArrayList(cl));
			item.setWeiTuoSalePrice(source.readArrayList(cl));
			item.setFenBiList(source.readArrayList(cl));
			return item;
		}

		@Override
		public PriceEntity[] newArray(int size) {
			return new PriceEntity[size];
		}

	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(envNumber);
		dest.writeInt(marketNumber);
		dest.writeInt(commodityType);
		dest.writeInt(number);
		dest.writeDouble(price);
		dest.writeDouble(buy);
		dest.writeDouble(sale);
		dest.writeDouble(high);
		dest.writeDouble(low);
		dest.writeDouble(yesterdayClosePrice);
		dest.writeDouble(yesterdayJieSuanPrice);
		dest.writeDouble(todayOpenPrice);
		dest.writeLong(priceTime);
		dest.writeInt(dealCount);
		dest.writeDouble(amount);
		dest.writeInt(volume);
		dest.writeInt(buyNumber);
		dest.writeInt(saleNumber);
		dest.writeInt(holdNumber);
		dest.writeInt(cangCha);
		dest.writeInt(jieSuanJia);
		dest.writeLong(neiPan);
		dest.writeLong(waiPan);
		dest.writeString(name);
		dest.writeInt(pankou_index);
		dest.writeString(code);

		dest.writeList(wuDangList);
		dest.writeList(weiTuoBuyPrice);
		dest.writeList(weiTuoSalePrice);
		dest.writeList(fenBiList);

	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getBuy() {
		return buy;
	}

	public void setBuy(double buy) {
		this.buy = buy;
	}

	public double getSale() {
		return sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}



	public long getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(long priceTime) {
		this.priceTime = priceTime;
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

	public String[] getEnvShortNameArray() {
		return envShortNameArray;
	}

	public void setEnvShortNameArray(String[] envShortNameArray) {
		this.envShortNameArray = envShortNameArray;
	}



	public int getEnvNumber() {
		return envNumber;
	}

	public void setEnvNumber(int envNumber) {
		this.envNumber = envNumber;
	}

	public int getMarketNumber() {
		return marketNumber;
	}

	public void setMarketNumber(int marketNumber) {
		this.marketNumber = marketNumber;
	}



    @Override
	public String toString() {
		return "PriceEntity [name=" + name + ", number=" + number + ", envNumber=" + envNumber + "]";
	}




}
