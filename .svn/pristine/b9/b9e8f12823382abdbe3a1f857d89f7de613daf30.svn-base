package com.puxtech.weipan.data.entitydata;

public class EnvTradeTime {
	private short market_num;
	private short env_num;
	private long startTime;//交易起始时间
	private long endTime;//交易结束时间
	private long lastTradeTime;//最后交易时间
	private long tradeDay;//交易日
	public long getTradeDay() {
		return tradeDay;
	}
	public void setTradeDay(long tradeDay) {
		this.tradeDay = tradeDay;
	}
	public short getMarket_num() {
		return market_num;
	}
	public void setMarket_num(short market_num) {
		this.market_num = market_num;
	}
	public short getEnv_num() {
		return env_num;
	}
	public void setEnv_num(short env_num) {
		this.env_num = env_num;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getLastTradeTime() {
		return lastTradeTime;
	}
	public void setLastTradeTime(long lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (endTime ^ (endTime >>> 32));
		result = prime * result + env_num;
		result = prime * result
				+ (int) (lastTradeTime ^ (lastTradeTime >>> 32));
		result = prime * result + market_num;
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		result = prime * result + (int) (tradeDay ^ (tradeDay >>> 32));
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
		EnvTradeTime other = (EnvTradeTime) obj;
		if (endTime != other.endTime)
			return false;
		if (env_num != other.env_num)
			return false;
		if (lastTradeTime != other.lastTradeTime)
			return false;
		if (market_num != other.market_num)
			return false;
		if (startTime != other.startTime)
			return false;
		if (tradeDay != other.tradeDay)
			return false;
		return true;
	}
	
}
