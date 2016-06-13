package com.puxtech.weipan.data.entitydata;

import android.os.Parcel;
import android.os.Parcelable;

public class FenBi implements Parcelable, Comparable<FenBi> {
	private int index;
	private long time;
	private double price;//价格  与基准价差值
	private int volume;//成交量
	private boolean buy ;//买卖标志卖是1，买是0 
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public boolean getFlag() {
		return buy;
	}
	public void setFlag(boolean buy) {
		this.buy = buy;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		
		dest.writeInt(index);
		dest.writeLong(time);
		dest.writeDouble(price);
		dest.writeInt(volume);
		dest.writeInt(buy?1:0);
	}
	public static final Parcelable.Creator<FenBi> CREATOR = new Creator<FenBi>() {

		@Override
		public FenBi createFromParcel(Parcel source) {
			FenBi item = new FenBi();
			
			
			item.setIndex(source.readInt());
			item.setTime(source.readLong());
			item.setPrice(source.readDouble());
			item.setVolume(source.readInt());
			item.setFlag(source.readInt()==1?true:false);
			return item;
		}

		@Override
		public FenBi[] newArray(int size) {
			return new FenBi[size];
		}

	};
	@Override
	public int compareTo(FenBi another) {
		return (int) (this.time - another.getTime());
	}
	
	
	
}
