package com.puxtech.weipan.data;

import java.io.Serializable;

/**
 * <b>Description:</b>
 * <p>
 * 商品持仓明细实体类
 * </p>
 * 
 * @Package com.happyinsource.htjy.android.entity
 */
public class HoldDetailData implements  Serializable {


	
	String holdId;//<H_ID>持仓单号<H_tID>
	String dealId;//<T_N>成交单号<T_N>
	String stuffId;//<CO_I>商品统一代码</CO_I>
	String buyOrSell;//<TY>买卖标志：1买；2卖</TY>
	String OpenNumber;//<O_QTY>开仓数量</O_QTY>
	String holdNumber;//<C_QTY>当前持仓数量</C_QTY>
	String buildPrice;//<PR>建仓价</PR>
	String holdPrice;//<H_P>持仓价<H_P>
	String buildTime;//<OR_T>建仓时间，毫秒</OR_T>
	String flp;//<FL_P>浮动赢亏</FL_P>
	String baoZhengJin;//<MAR>保证金</MAR>
	String jiaoYiShouXuFei;//<COMM>交易手续费</COMM>
	String dongJieNumber;//<F_QTY>冻结数量</F_QTY>
	String stopLoss;//<STOP_LOSS>止损</STOP_LOSS>
	String stopEarn;//<STOP_PROFIT>止盈</STOP_PROFIT>
	String otherId;//<OTHER_ID>成交对方ID</OTHER_ID>
	String co_id;//<CO_ID>代为委托员代码</CO_ID>
	String baoZhengJinBiLi;//	<MAR_RATE>持仓保证金比例</MAR_RATE> 2.66新增
	String dropPrice;//平仓价
	String stuffName;//商品名字
	
	public String getDropPrice() {
		return dropPrice;
	}
	public void setDropPrice(String dropPrice) {
		this.dropPrice = dropPrice;
	}
	public String getStuffName() {
		return stuffName;
	}
	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}
	public String getHoldId() {
		return holdId;
	}
	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getStuffId() {
		return stuffId;
	}
	public void setStuffId(String stuffId) {
		this.stuffId = stuffId;
	}
	public String getBuyOrSell() {
		return buyOrSell;
	}
	public void setBuyOrSell(String buyOrSell) {
		this.buyOrSell = buyOrSell;
	}
	public String getOpenNumber() {
		return OpenNumber;
	}
	public void setOpenNumber(String openNumber) {
		OpenNumber = openNumber;
	}
	public String getHoldNumber() {
		return holdNumber;
	}
	public void setHoldNumber(String holdNumber) {
		this.holdNumber = holdNumber;
	}
	public String getBuildPrice() {
		return buildPrice;
	}
	public void setBuildPrice(String buildPrice) {
		this.buildPrice = buildPrice;
	}
	public String getHoldPrice() {
		return holdPrice;
	}
	public void setHoldPrice(String holdPrice) {
		this.holdPrice = holdPrice;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getFlp() {
		return flp;
	}
	public void setFlp(String flp) {
		this.flp = flp;
	}
	public String getBaoZhengJin() {
		return baoZhengJin;
	}
	public void setBaoZhengJin(String baoZhengJin) {
		this.baoZhengJin = baoZhengJin;
	}
	public String getJiaoYiShouXuFei() {
		return jiaoYiShouXuFei;
	}
	public void setJiaoYiShouXuFei(String jiaoYiShouXuFei) {
		this.jiaoYiShouXuFei = jiaoYiShouXuFei;
	}
	public String getDongJieNumber() {
		return dongJieNumber;
	}
	public void setDongJieNumber(String dongJieNumber) {
		this.dongJieNumber = dongJieNumber;
	}
	public String getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(String stopLoss) {
		this.stopLoss = stopLoss;
	}
	public String getStopEarn() {
		return stopEarn;
	}
	public void setStopEarn(String stopEarn) {
		this.stopEarn = stopEarn;
	}
	public String getOtherId() {
		return otherId;
	}
	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getBaoZhengJinBiLi() {
		return baoZhengJinBiLi;
	}
	public void setBaoZhengJinBiLi(String baoZhengJinBiLi) {
		this.baoZhengJinBiLi = baoZhengJinBiLi;
	}
	
	
	


	

}
