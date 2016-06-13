package com.puxtech.weipan.data.entitydata;

import android.os.Parcel;
import android.os.Parcelable;

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
public class WuDangEntity implements Parcelable {
	
	
	boolean ifBuy;//买卖标志
	int guaPaiNumber;//挂牌数量
	int zhaiPaiNumber;//摘牌数量
	double price;//价格
	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	public boolean isIfBuy() {
		return ifBuy;
	}

	public void setIfBuy(boolean ifBuy) {
		this.ifBuy = ifBuy;
	}

	public int getGuaPaiNumber() {
		return guaPaiNumber;
	}

	public void setGuaPaiNumber(int guaPaiNumber) {
		this.guaPaiNumber = guaPaiNumber;
	}

	public int getZhaiPaiNumber() {
		return zhaiPaiNumber;
	}

	public void setZhaiPaiNumber(int zhaiPaiNumber) {
		this.zhaiPaiNumber = zhaiPaiNumber;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(ifBuy?1:0);
		dest.writeInt(guaPaiNumber);
		dest.writeInt(zhaiPaiNumber);
		dest.writeDouble(price);
	}
	public static final Parcelable.Creator<WuDangEntity> CREATOR = new Creator<WuDangEntity>() {

		@Override
		public WuDangEntity createFromParcel(Parcel source) {
			WuDangEntity item = new WuDangEntity();
			item.setIfBuy(source.readInt()==1?true:false);
			item.setGuaPaiNumber(source.readInt());
			item.setZhaiPaiNumber(source.readInt());
			item.setPrice(source.readDouble());
			return item;
		}

		@Override
		public WuDangEntity[] newArray(int size) {
			return new WuDangEntity[size];
		}

	};
	
	
	
	

}
