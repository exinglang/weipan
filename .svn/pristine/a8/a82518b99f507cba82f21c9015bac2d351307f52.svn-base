package com.puxtech.weipan.data.entitydata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <b>Description:</b>
 * <p>
 * 商品信息实体类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2013-12-25 上午9:22:12
 * @version V1.0
 * @FileName CommodityEntity.java
 * @Package com.happyinsource.htjy.android.entity
 */
public class PriceCommodityEntity implements Parcelable {

	int number;// 商品编号
	String code;// 商品代码
	String name;// 商品名称
	/**
	 * 商品状态<br>
	 * 0：编辑中<br>
	 * 1：上市（需要上市流程：生成交易权限，）<br>
	 * 2：退市
	 */
	int status;
	/**
	 * 是否忽略大小写
	 */
	boolean ignoreCase;
	int spread;// 最小变动价位

	int marketNumber;// 市场编号
	int envNumber;// 环境编号
	int envType;// 环境类型,1：实盘，2：模拟盘，3：测试盘
	int mainEnvNumber;// 主环境编号，主盘或独立盘为0，从盘则填其主盘编号
	short jiaoYiLX;//商品交易类型
	public void setJiaoYiLX(short jiaoYiLX) {
		this.jiaoYiLX = jiaoYiLX;
	}
	public short getJiaoYiLX() {
		return jiaoYiLX;
	}

    int zuId;//行情前置机所在组的ID

	//首页需要显示的三个值
	String buy;
	String cha;
	String baifenbi;
    public static final Parcelable.Creator<PriceCommodityEntity> CREATOR = new Creator<PriceCommodityEntity>() {

		@Override
		public PriceCommodityEntity createFromParcel(Parcel source) {
			PriceCommodityEntity item = new PriceCommodityEntity();
			item.setNumber(source.readInt());
			item.setCode(source.readString());
			item.setName(source.readString());
			item.setStatus(source.readInt());
			item.setSpread(source.readInt());
			item.setMarketNumber(source.readInt());
			item.setEnvNumber(source.readInt());
			item.setEnvType(source.readInt());
			item.setMainEnvNumber(source.readInt());
			return item;
		}

		@Override
		public PriceCommodityEntity[] newArray(int size) {
			return new PriceCommodityEntity[size];
		}

	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(number);
		dest.writeString(code);
		dest.writeString(name);
		dest.writeInt(status);
		dest.writeInt(spread);
		dest.writeInt(marketNumber);
		dest.writeInt(envNumber);
		dest.writeInt(envType);
		dest.writeInt(mainEnvNumber);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSpread() {
		return spread;
	}

	public void setSpread(int spread) {
		this.spread = spread;
	}

	public int getMarketNumber() {
		return marketNumber;
	}

	public void setMarketNumber(int marketNumber) {
		this.marketNumber = marketNumber;
	}

	public int getEnvNumber() {
		return envNumber;
	}

	public void setEnvNumber(int envNumber) {
		this.envNumber = envNumber;
	}

	public int getEnvType() {
		return envType;
	}

	public void setEnvType(int envType) {
		this.envType = envType;
	}

	public int getMainEnvNumber() {
		return mainEnvNumber;
	}

	public void setMainEnvNumber(int mainEnvNumber) {
		this.mainEnvNumber = mainEnvNumber;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

    public int getZuId() {
        return zuId;
    }

    public void setZuId(int zuId) {
        this.zuId = zuId;
    }

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getBaifenbi() {
		return baifenbi;
	}

	public void setBaifenbi(String baifenbi) {
		this.baifenbi = baifenbi;
	}

	@Override
	public String toString() {
		return "CommodityInfoEntity [number=" + number + ", code=" + code
				+ ", name=" + name + ", status=" + status + ", spread="
				+ spread + ", marketNumber=" + marketNumber + ", envNumber="
				+ envNumber + ", envType=" + envType + ", mainEnvNumber="
				+ mainEnvNumber + "]";
	}

}
