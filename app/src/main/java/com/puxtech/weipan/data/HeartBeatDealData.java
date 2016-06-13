package com.puxtech.weipan.data;


public class HeartBeatDealData extends BaseResponseData {

	long ONO;//委托单号
	String CMOID;//商品代码
	long QT;//当前成交量
	int TYP;//委托类型：1开仓；2平仓
	int BS;//买卖标志：1买；2卖

	public long getONO() {
		return ONO;
	}

	public void setONO(long ONO) {
		this.ONO = ONO;
	}

	public String getCMOID() {
		return CMOID;
	}

	public void setCMOID(String CMOID) {
		this.CMOID = CMOID;
	}

	public long getQT() {
		return QT;
	}

	public void setQT(long QT) {
		this.QT = QT;
	}

	public int getTYP() {
		return TYP;
	}

	public void setTYP(int TYP) {
		this.TYP = TYP;
	}

	public int getBS() {
		return BS;
	}

	public void setBS(int BS) {
		this.BS = BS;
	}
}
